package BackEnd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Factory.ConnectionFactory;

public class Usuario {
    private String id;
    private String password;

    // Getters
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Método que valida si el Id del usuario existe en la tabla de usuarios de la DB.
     * Recibe una cadena de texto con el Id y devuelve un boolean.
     * @param id String con el Id a buscar.
     * @return true: si se encuentra el Id en la tabla o false: si no se encuentra.
     * @throws SQLException
     */
    public boolean validar(String id, char[] password) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("SELECT * FROM Usuarios WHERE Id= ?");    
            try(statement) {
                statement.setString(1, id);
                statement.execute();
                
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet) {
                    if(resultSet.next() && resultSet.getString("Pass").equals(String.valueOf(password)) ) return true; 
                    else return false; 
                }
            }   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
}