package BackEnd;

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

import DAO.ReservaDAO;

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
        ReservaDAO reservaDAO = new ReservaDAO();

        return reservaDAO.getCostoDeHabitacion(tipoHabitacion);
    }

    public void setId(int id) {
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
        ReservaDAO reservaDAO = new ReservaDAO();

        return reservaDAO.listar();
    }

    /**
     * Método que realiza el registro de una reserva en base de datos a partir de un objeto instanciado de esta clase
     * @throws SQLException
     */
    public void registarEnDB() throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO();
        
        reservaDAO.registarEnDB(this);
    }

    public void eliminar(int idReserva) throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO();

        reservaDAO.eliminar(idReserva);
    }

    public void editar(String fechaEntrada, String fechaSalida, double importeTotal, int formaPago, int idReserva) throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO();

        reservaDAO.editar(fechaEntrada, fechaSalida, importeTotal, formaPago, idReserva);
    }
}