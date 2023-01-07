package Factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
// Biblioteca de clases para la creación y configuración de un pool de conexiones: C3P0
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
    String url = "jdbc:mysql://localhost/hotel?useTimeZone=true&serverTimeZone=UTC";
    String user = "root";
    String password = "";
    private DataSource datasource;

    /**
     * Constructor de conexión empleando un pool de conexiones a la base de datos
     * Devuelve un objeto del tipo ComboPooledDataSource() que recibe los datos de conexión a la DB
     * Incluye la configuración del pool de conexiones
     */
    public ConnectionFactory() {
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl(url);
        pooledDataSource.setUser(user);
        pooledDataSource.setPassword(password);
        pooledDataSource.setMaxPoolSize(10); // Definición del tope de conexiones que permite el pool

        this.datasource = pooledDataSource;
    }

    public Connection recuperaConexion() throws SQLException {
        return this.datasource.getConnection();
    }
}