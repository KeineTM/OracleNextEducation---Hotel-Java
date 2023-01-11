package Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import DAO.HuespedDAO;

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
    private String stringfechaNacimiento;

    // Constructor con validación de email
    public Huesped(String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher validacion = pattern.matcher(email);

        if(validacion.find() == true) {
            this.email = email;
        } else {
            JOptionPane.showMessageDialog(null, "Formato de e-mail no válido.");
        }
    }

    public Huesped() {}

    public Huesped(int id, String nombre, String apellido, String stringfechaNacimiento, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.stringfechaNacimiento = stringfechaNacimiento;
        this.telefono = telefono;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
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

    public String getStringfechaNacimiento() {
        return stringfechaNacimiento;
    }

    @Override
    public String toString() {
        return this.id + " " + this.nombre + " " + this.apellido + " " + this.fechaNacimiento + " " + this.telefono + " " + this.email;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Huespedes.
     *  */
    public List<Map<String, String>> listar() {
        HuespedDAO huespedDAO = new HuespedDAO();

        return huespedDAO.listar();
    }

    /**
     * Método que realiza el registro de un huesped en base de datos a partir de un objeto instanciado de esta clase
     */
    public void registarEnDB() {
        HuespedDAO huespedDAO = new HuespedDAO();

        huespedDAO.registarEnDB(this);
    }
}