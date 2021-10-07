package launch;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Cli {
    @Parameter
    public List<String> paths = new ArrayList<>();

    @Parameter(names = "--session-timeout", description = "The number of minutes of inactivity before a user's session is timed out.")
    public Integer sessionTimeout;

    @Parameter(names = "--context-xml", description = "The path to the context xml to use.")
    public String contextXml;

    @Parameter(names = "--path", description = "The context path")
    public String contextPath = "";

    @Parameter(names = "--help", help = true)
    public boolean help;

    @Parameter(names = "--enable-ssl", description = "Specify -Djavax.net.ssl.keyStore, -Djavax.net.ssl.keystoreStorePassword, -Djavax.net.ssl.trustStore and -Djavax.net.ssl.trustStorePassword in JAVA_OPTS. ")
    public boolean enableSSL;

    @Parameter(names = "--scanBootstrapClassPath", description = "Set jar scanner scan bootstrap classpath.")
    public boolean scanBootstrapClassPath = false;

    @Parameter(names = "--session-store-pool-size", description = "Pool size of the session store connections (default is 10. Has no effect for 'memcache')")
    public Integer sessionStorePoolSize = 10;

    @Parameter(names = "--session-store-operation-timeout", description = "Operation timeout for the memcache session store. (default is 5000ms)")
    public Integer sessionStoreOperationTimout = 5000;

    @Parameter(names = "--session-store", description = "Session store to use (valid options are 'memcache' or 'redis')")
    public String sessionStore;

    /*
      <Resource
        name="jdbc/hikariOracle"
        auth="Container"
        type="javax.sql.DataSource"
        factory="com.zaxxer.hikari.HikariJNDIFactory"
        dataSourceClassName="oracle.jdbc.pool.OracleDataSource"
        dataSource.user="user1"
        dataSource.password="user1password"
        dataSource.url="jdbc:oracle:thin:@127.0.0.1:1521:RA"
        maximumPoolSize="10"
        minimumIdle="5"
        connectionTimeout="300000"
        closeMethod="close"
        />
*/
    /*specity that db pool will be used*/
    @Parameter(names = "--db-pool", description = "set to 'true' to enable db connection pool')")
    Boolean dbPool=false;

    @Parameter(names = "--db-user", description = "login user )")
    public String dbUser;

    @Parameter(names = "--db-pass", description = "db login user password)")
    public String dbPass;

    @Parameter(names = "--db-url", description = "connect string example: 'jdbc:oracle:thin:@127.0.0.1:1521:RA')")
    public String connectString;

    /*sepcify the jndi name here*/
    @Parameter(names = "--jndi-name", description = "example: jdbc/hikariOracl)")
    public String jndiName="jdbc/hikariOracl";

    /*sepcify the connection pool factory here*/
    @Parameter(names = "--factory", description = "example 'com.zaxxer.hikari.HikariJNDIFactory')")
    public String factory="com.zaxxer.hikari.HikariJNDIFactory";

    /*specity the dbms */
    @Parameter(names = "--datasource-class-name", description = "example 'oracle.jdbc.pool.OracleDataSource')")
    public String dataSourceClassName="oracle.jdbc.pool.OracleDataSource";

    @Parameter(names = "--max-pool-size", description = "default is '10')")
    public String maximumPoolSize="10";

    @Parameter(names = "--min-idle", description = "default is '5')")
    public String minimumIdle="5";

    @Parameter(names = "--con-timeout", description = "default is '30000')")
    public String connectionTimeout="30000";

    @Parameter(names = "--close-method", description = "default is: 'close')")
    public String closeMethod="close";
}