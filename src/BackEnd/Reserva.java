package BackEnd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

import Factory.ConnectionFactory;

public class Reserva {
    // Atributos
    private int id;
    private LocalDateTime fechaCheckIn;
    private LocalDateTime fechaCheckOut;
    private double importeTotal;
    private int formaPago;
    public Huesped huesped;

    // Constructores
    public Reserva(LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut, int formaPago, Huesped huesped) {
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.importeTotal = 399 * diasDeReserva(fechaCheckIn, fechaCheckOut);
        this.formaPago = formaPago;
        this.huesped = huesped;
    }

    public Reserva(){}

    /**
     * Método que permite calcular los días de que abarca la reserva.
     * Emplea la clase ChronoUnit y su método between() para realizar la comparación entre fechas.
     * @param fechaCheckIn
     * @param fechaCheckOut
     * @return
     */
    private long diasDeReserva(LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut) {
        return ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getFechaCheckIn() {
        return fechaCheckIn;
    }

    public LocalDateTime getFechaCheckOut() {
        return fechaCheckOut;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public int getFormaPago() {
        return formaPago;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Reservas.
     *  */
    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        Statement statement = con.createStatement();
        // Java en automático sugiere almacenar el resultado en una variable de tipo boolean
        // Esto se debe a que identifica si el resultado es una lista de registros (como en el caso de un SELECT) o no (como UPDATE, DELETE, etc)
        statement.execute("SELECT * FROM Reservas");
        
        // Permite almacenar el listado resultante
        ResultSet resultSet = statement.getResultSet();
        // Variable para almacenar el listado
        ArrayList<Map<String, String>> resultado = new ArrayList<>();
        // Mientras exista un elemento en la lista se almacenará la información como un registro o fila
        while(resultSet.next()) {
            Map<String, String> fila = new HashMap<>();
            fila.put("Número de Reserva", String.valueOf(resultSet.getInt("Id")));
            fila.put("Fecha Check in", resultSet.getString("FechaEntrada"));
            fila.put("Fecha Check out", resultSet.getString("FechaSalida"));
            fila.put("Valor", String.valueOf(resultSet.getDouble("ImporteTotal")));
            fila.put("Forma de pago", resultSet.getString("FormaPago"));
            fila.put("Número de Huésped", String.valueOf(resultSet.getInt("IdHuesped")));

            resultado.add(fila);
        }

        con.close();

        return resultado;
    }

    /**
     * Método que realiza el registro de una reserva en base de datos a partir de un objeto instanciado de esta clase
     * @param reserva
     */
    public void registarEnDB(Reserva reserva) {

    }
}