package BackEnd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import Factory.ConnectionFactory;

/**
 * Clase para generar un huesped, contiene métodos setters and getters para sus atributos:
 * id, nombre, apellido, fechaNacimiento, telefono y email.
 */
public class Huesped {
    // Atributos
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento; // Formato AAAA/MM/DD
    private String telefono;
    private String email;

    // Setters y Getters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * La fecha de nacimiento se establece con LocalDate y el método of() para asignarla.
     * Ej: LocalDate.of(1990, Month.MAY, 23)
     * @param fechaNacimiento
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Este método contiene una validación para el formato del email, en caso de no corresponder lanza la advertencia correspondiente
     * @param email
     */
    public void setEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher validacion = pattern.matcher(email);

        if(validacion.find() == true) {
            this.email = email;
        } else {
            JOptionPane.showMessageDialog(null, "Formato de e-mail no válido.");
        }
    }

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Huespedes.
     *  */
    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        Statement statement = con.createStatement();
        
        statement.execute("SELECT * FROM Huespedes");
        
        // Permite almacenar el listado resultante
        ResultSet resultSet = statement.getResultSet();
        // Variable para almacenar el listado
        ArrayList<Map<String, String>> resultado = new ArrayList<>();
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

        con.close();

        return resultado;
    }
}