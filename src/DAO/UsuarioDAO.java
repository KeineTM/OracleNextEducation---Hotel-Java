package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controller.Usuario;
import Factory.ConnectionFactory;

// Clase que permite ordenar el código concentrando los métodos que se conectan con la base de datos
// Y  Administrar la persistencia.
public class UsuarioDAO {
    /**
     * Método que valida si el Id del usuario existe en la tabla de usuarios de la DB.
     * Recibe una cadena de texto con el Id y devuelve un boolean.
     * @param usuario Objeto usuario del que extrae los atributos Id y Password.
     * @return true: si se encuentra el Id en la tabla o false: si no se encuentra.
     * @throws SQLException
     */
    public boolean validar(Usuario usuario){
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("SELECT * FROM Usuarios WHERE Id= ?");    
            try(statement) {
                statement.setString(1, usuario.getId());
                statement.execute();
                
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet) {
                    if(resultSet.next() && resultSet.getString("Pass").equals(String.valueOf(usuario.getPassword())) ) return true; 
                    else return false; 
                }
            }   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
