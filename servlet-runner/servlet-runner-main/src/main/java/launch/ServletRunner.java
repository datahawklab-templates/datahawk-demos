package launch;

import com.beust.jcommander.JCommander;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ExpandWar;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.scan.StandardJarScanner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.InvalidParameterException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;


public class ServletRunner {
    private static final String keyAlias = "run";
    private static final String keystorePass = "changeit";
    private static final Path keystoreFile = FileSystems.getDefault().getPath("src", "main", "main/resources", "keystore.jks");
    private static final String path = "src/main/webapp";
    private static final String expandedDirName = "expanded";
    private static final int nonSSLPort = 8080;
    private static final int sslPort = 443;
    private static int port;
    private static Cli commandLineParams = new Cli();
    private static JCommander jCommander;

    public void run(String[] args) throws Exception {

        jCommander = new JCommander(commandLineParams);
        jCommander.parse(args);
        if (commandLineParams.help) {
            jCommander.usage();
            System.exit(1);
        }
        Tomcat tomcat = servletInstance();
        try {
            tomcat.start();
        } catch (LifecycleException exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }

        tomcat.getServer().await();
    }

    private Tomcat servletInstance() throws IOException {
        Tomcat servletInstance = new Tomcat();
        servletInstance.setConnector(connector());
        servletInstance.setPort(port);
        servletInstance.setBaseDir(System.getProperty("user.dir") + "/target/tomcat." + port);
        addContext(servletInstance);
        servletInstance.enableNaming();
        commandLineParams = null;
        return servletInstance;
    }


    private void addContext(Tomcat tomcat) throws IOException {

        Context ctx;
        final String ctxName = commandLineParams.contextPath;

        commandLineParams.paths.add(path);
        String path = commandLineParams.paths.get(0);
        File war = new File(path);

        if (!war.exists()) {
            System.err.println("The specified path \"" + path + "\" does not exist.");
            jCommander.usage();

            System.exit(1);
        }

        URL fileUrl = new URL("jar:" + war.toURI().toURL() + "!/");
        File appBase = new File(System.getProperty(Globals.CATALINA_BASE_PROP), tomcat.getHost().getAppBase());
        if (appBase.exists()) {
            appBase.delete();
        }
        appBase.mkdir();

        String expandedDir = ExpandWar.expand(tomcat.getHost(), fileUrl, "/" + expandedDirName);
        System.out.println("Expanding " + war.getName() + " into " + expandedDir);

        System.out.println("Adding Context " + ctxName + " for " + expandedDir);
        ctx = tomcat.addWebapp(commandLineParams.contextPath, expandedDir);

        ((StandardContext) ctx).setUnpackWAR(false);

        if (commandLineParams.scanBootstrapClassPath) {
            StandardJarScanner scanner = new StandardJarScanner();
            scanner.setScanBootstrapClassPath(true);
            ctx.setJarScanner(scanner);
        }

        if (commandLineParams.contextXml != null) {
            System.out.println("Using context config: " + commandLineParams.contextXml);
            ctx.setConfigFile(new File(commandLineParams.contextXml).toURI().toURL());
        }

        if (commandLineParams.sessionTimeout != null) {
            ctx.setSessionTimeout(commandLineParams.sessionTimeout);
        }

        if (commandLineParams.dbPool) {
            ctx.getNamingResources().addResource(jndiDbConnectionPool());
        }

        //;
    }

    private Connector connector() {
        Connector connector = new Connector(Http11Nio2Protocol.class.getName());

        //String keystoreFile = System.getProperty("javax.net.ssl.keyStore");
        boolean pathExists = Files.exists(keystoreFile, LinkOption.NOFOLLOW_LINKS);

        if (pathExists) { // HTTPS connector
            port = nonSSLPort;
            connector.setPort(sslPort);
            connector.setSecure(true);
            connector.setScheme("https");
            connector.setProperty("SSLEnabled", "true");
            SSLHostConfig sslHostConfig = new SSLHostConfig();
            SSLHostConfigCertificate cert = new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.Type.RSA);
            cert.setCertificateKeystoreFile(keystoreFile.toAbsolutePath().toString());
            cert.setCertificateKeystorePassword(keystorePass);
            cert.setCertificateKeyAlias(keyAlias);
            sslHostConfig.addCertificate(cert);
            connector.addSslHostConfig(sslHostConfig);
        } else {
            port = nonSSLPort; // HTTP connector
            connector.setPort(nonSSLPort);
            connector.setSecure(false);
            connector.setScheme("http");
        }

        connector.addUpgradeProtocol(new Http2Protocol());
        connector.setXpoweredBy(false); // Keep quiet about the server type
        connector.setProperty("maxThreads", "400"); // Basic tuning params:
        connector.setProperty("acceptCount", "50"); //connector.setAttribute("connectionTimeout", 2000);
        connector.setProperty("maxKeepAliveRequests", "100"); // Avoid running out of ephemeral ports under heavy load?
        connector.setProperty("socket.soReuseAddress", "true");
        connector.setMaxPostSize(0);
        connector.setProperty("disableUploadTimeout", "false"); // Allow long URLs
        connector.setProperty("maxHttpHeaderSize", "65536"); // Enable response compression
        connector.setProperty("compression", "on"); // Defaults are text/html,text/xml,text/plain,text/css
        connector.setProperty("compressableMimeType", "text/html,text/xml,text/plain,text/css,text/csv,application/json");
        return connector;

    }


    public ContextResource jndiDbConnectionPool() throws InvalidParameterException{
        ContextResource jndiResource = new ContextResource();

        jndiResource.setName(commandLineParams.jndiName);
        jndiResource.setAuth("Container");
        jndiResource.setType("javax.sql.DataSource");
        jndiResource.setProperty("factory", commandLineParams.factory);
        jndiResource.setProperty("dataSourceClassName", commandLineParams.dataSourceClassName);
        jndiResource.setProperty("dataSource.user", commandLineParams.dbUser);
        jndiResource.setProperty("dataSource.password", commandLineParams.dbPass);
        jndiResource.setProperty("dataSource.url", commandLineParams.connectString);
        jndiResource.setProperty("maximumPoolSize", commandLineParams.maximumPoolSize);
        jndiResource.setProperty("minimumIdle", commandLineParams.minimumIdle);
        jndiResource.setProperty("connectionTimeout", commandLineParams.connectionTimeout);
        jndiResource.setProperty("closeMethod", commandLineParams.closeMethod);

        return jndiResource;
    }
}
