package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.Huesped;
import Factory.ConnectionFactory;

public class HuespedDAO {
    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Huespedes.
     *  */
    public List<Map<String, String>> listar() {
        // Variable para almacenar el listado
        ArrayList<Map<String, String>> resultado = new ArrayList<>();
        final Connection con = new ConnectionFactory().recuperaConexion();

        try(con) {
        final PreparedStatement statement = con.prepareStatement("SELECT * FROM Huespedes");
        
            try(statement) {
                statement.execute();
                // Permite almacenar el listado resultante
                final ResultSet resultSet = statement.getResultSet();
                

                try(resultSet) {
                    // Mientras exista un elemento en la lista se almacenará la información como un registro o fila
                    while(resultSet.next()) {
                        Map<String, String> fila = new HashMap<>();
                        fila.put("Número de Huésped", String.valueOf(resultSet.getInt("Id")));
                        fila.put("Nombre", resultSet.getString("Nombre"));
                        fila.put("Apellido", resultSet.getString("Apellido"));
                        fila.put("Fecha de Nacimiento", resultSet.getString("FechaNacimiento"));
                        fila.put("Teléfono", resultSet.getString("Telefono"));
                        fila.put("Email", resultSet.getString("Email"));

                        resultado.add(fila);
                    }
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    /**
     * Método que realiza el registro de un huesped en base de datos a partir de un objeto instanciado de esta clase
     * @throws SQLException
     */
    public void registarEnDB(Huesped huesped) {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("INSERT INTO Huespedes (Nombre, Apellido, FechaNacimiento, Telefono, Email)" 
            + "VALUES (?,?,?,?,?)", 
            Statement.RETURN_GENERATED_KEYS); // Recupera el id creado en la tabla con el AUTO_INCREMENT de la clave primaria);

            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setString(3, huesped.getFechaNacimiento().toString());
            statement.setString(4, huesped.getTelefono());
            statement.setString(5, huesped.getEmail());

            try(statement) {
                statement.execute(); 
                ResultSet resultSet = statement.getGeneratedKeys();
                
                try(resultSet) {
                    while(resultSet.next()) {
                        huesped.setId(resultSet.getInt(1)); // Lo asigna al atributo Id del objeto para enviarlo al registro de la reserva
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
