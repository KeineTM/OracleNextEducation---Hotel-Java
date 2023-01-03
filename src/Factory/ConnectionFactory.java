package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    String url = "jdbc:mysql://localhost/hotel?useTimeZone=true&serverTimeZone=UTC";
    String user = "root";
    String password = "";
    
    public Connection recuperaConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}