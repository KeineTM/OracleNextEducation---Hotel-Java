package BackEnd;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
    public Reserva(int tipoHabitacion, LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut, int formaPago, Huesped huesped) throws SQLException {
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.importeTotal = diasDeReserva(fechaCheckIn, fechaCheckOut) * getCostoDeHabitacion(tipoHabitacion);
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
    public Double diasDeReserva(LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut) {
        return (double) ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
    }

    public Double getCostoDeHabitacion(int tipoHabitacion) throws SQLException {
        double costoDeHabitacion = 299;
        Connection con = new ConnectionFactory().recuperaConexion();
        Statement statement = con.createStatement();

        try {
            statement.execute("SELECT Costo FROM TipoHabitacion WHERE Id='" + (tipoHabitacion + 1) + "'");
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                costoDeHabitacion = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

        return costoDeHabitacion;
    }

    private void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return this.id + ", " + this.fechaCheckIn + ", " + this.fechaCheckOut + ", " + this.importeTotal + ", " + this.formaPago;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Reservas.
     *  */
    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        PreparedStatement statement = con.prepareStatement("SELECT Reservas.Id, Reservas.FechaEntrada, Reservas.FechaSalida, Reservas.ImporteTotal, FormasPago.Descripcion, Reservas.IdHuesped " +
            "FROM Reservas " +
            "INNER JOIN FormasPago ON FormasPago.Id = Reservas.FormaPago;");
        // Java en automático sugiere almacenar el resultado en una variable de tipo boolean
        // Esto se debe a que identifica si el resultado es una lista de registros (como en el caso de un SELECT) o no (como UPDATE, DELETE, etc)
        statement.execute();
        
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
            fila.put("Forma de pago", resultSet.getString("Descripcion"));
            fila.put("Número de Huésped", String.valueOf(resultSet.getInt("IdHuesped")));

            resultado.add(fila);
        }

        con.close();

        return resultado;
    }

    /**
     * Método que realiza el registro de una reserva en base de datos a partir de un objeto instanciado de esta clase
     * @param reserva
     * @throws SQLException
     */
    public void registarEnDB() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        PreparedStatement statement = con.prepareStatement("INSERT INTO Reservas (FechaEntrada, FechaSalida, ImporteTotal, FormaPago, IdHuesped)" 
                + "VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS); // Recupera el id creado en la tabla con el AUTO_INCREMENT de la clave primaria
        statement.setString(1, this.fechaCheckIn.toString());
        statement.setString(2, this.fechaCheckOut.toString());
        statement.setDouble(3, this.importeTotal);
        statement.setInt(4, this.formaPago);
        statement.setInt(5, this.huesped.getId());
        
        try {
            statement.execute(); 

            ResultSet resultSet = statement.getGeneratedKeys();

            while(resultSet.next()) {
                setId(resultSet.getInt(1)); // Lo asigna al atributo Id del objeto para enviarlo al mensaje de éxito
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        
    }

    public void eliminar(int idReserva) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        PreparedStatement statement = con.prepareStatement("DELETE FROM Reservas WHERE Id= ?");
        statement.setInt(1, idReserva);

        try {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public void editar(int idReserva) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        PreparedStatement statement = con.prepareStatement("DELETE FROM Reservas WHERE Id= ?");
        statement.setInt(1, idReserva);
         
        try {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}