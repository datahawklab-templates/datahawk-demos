package launch;

import com.zaxxer.hikari.HikariDataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
  private static DBManager instance;
  private final HikariDataSource hikariDataSource;

  private DBManager() throws NamingException, SQLException {

    this.hikariDataSource = (HikariDataSource) new InitialContext().lookup("java:comp/env/jdbc/hikariSrc");
  }

  public static DBManager getInstance() {
    return instance;
  }

  public static synchronized void start() throws ServerErrorException {
    if (instance == null) {
      try {
        instance = new DBManager();
      } catch (NamingException | SQLException ex) {
        throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, ex);
      }
    }
  }

  public static void shutdown() {
    instance.hikariDataSource.close();
  }

  public static Connection getConnection() throws SQLException {
    Connection conn = instance.hikariDataSource.getConnection();
    return conn;
  }

}
