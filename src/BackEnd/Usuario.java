package BackEnd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        Connection con = new ConnectionFactory().recuperaConexion();
        Statement statemen = con.createStatement();

        try {
            statemen.execute("SELECT * FROM Usuarios WHERE Id='" + id + "'");
            ResultSet resultSet = statemen.getResultSet();

            if(resultSet.next() && resultSet.getString("Pass").equals(String.valueOf(password)) ) {
                return true; 
            }
            else {
                 return false; 
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        } finally {
            con.close();
        }
    }
}