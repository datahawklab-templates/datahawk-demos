package launch;

import org.apache.catalina.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class SessionManager {

    public SessionManager() {

    }

    public static SessionManager getInstance(String sessionStore)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        return (SessionManager) Class.forName("launch.RedisSessionManager").getDeclaredConstructor().newInstance();
    }

    public void configureSessionManager(Cli commandLineParams, Context ctx) throws URISyntaxException, IOException {
        // do nothing, let tomcat use the default
        System.out.println("WARNING: session manager " + commandLineParams.sessionStore + " unsupported using default");
    }
}
