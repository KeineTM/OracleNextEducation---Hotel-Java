package Controller;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

import DAO.ReservaDAO;

import java.time.temporal.ChronoUnit;

public class Reserva {
    // Atributos
    private int id;
    private LocalDateTime fechaCheckIn;
    private LocalDateTime fechaCheckOut;
    private double importeTotal;
    private int formaPago;
    public Huesped huesped;
    private String stringFechaCheckIn;
    private String stringFechaCheckOut;

    // Constructores
    public Reserva(int tipoHabitacion, LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut, int formaPago, Huesped huesped) {
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.importeTotal = diasDeReserva(fechaCheckIn, fechaCheckOut) * getCostoDeHabitacion(tipoHabitacion);
        this.formaPago = formaPago;
        this.huesped = huesped;
    }

    public Reserva(int tipoHabitacion, LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut, double importeTotal, int formaPago) {
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.importeTotal = importeTotal;
        this.formaPago = formaPago;
    }

    public Reserva(int id, String stringFechaCheckIn, String stringFechaCheckOut, int formaPago, double importeTotal, Huesped huesped) {
        this.id = id;
        this.stringFechaCheckIn = stringFechaCheckIn;
        this.stringFechaCheckOut = stringFechaCheckOut;
        this.importeTotal = importeTotal;
        this.formaPago = formaPago;
        this.huesped = huesped;
    }

    public Reserva(){}

    public Reserva(int id, String stringFechaCheckIn, String stringFechaCheckOut, int formaPago, double importeTotal) {
        this.id = id;
        this.stringFechaCheckIn = stringFechaCheckIn;
        this.stringFechaCheckOut = stringFechaCheckOut;
        this.importeTotal = importeTotal;
        this.formaPago = formaPago;
    }


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

    public Double getCostoDeHabitacion(int tipoHabitacion) {
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

    public String getStringFechaCheckIn() {
        return stringFechaCheckIn;
    }

    public String getStringFechaCheckOut() {
        return stringFechaCheckOut;
    }

    @Override
    public String toString() {
        return this.id + ", " + this.fechaCheckIn + ", " + this.fechaCheckOut + ", " + this.importeTotal + ", " + this.formaPago;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Reservas.
     *  */
    public List<Map<String, String>> listar() {
        ReservaDAO reservaDAO = new ReservaDAO();

        return reservaDAO.listar();
    }

    public void registarEnDB() {
        ReservaDAO reservaDAO = new ReservaDAO();
        
        reservaDAO.registarEnDB(this);
    }

    public void eliminar(int idReserva) {
        ReservaDAO reservaDAO = new ReservaDAO();

        reservaDAO.eliminar(idReserva);
    }

    public void editar() {
        ReservaDAO reservaDAO = new ReservaDAO();

        reservaDAO.editar(this);
    }

    public Reserva buscar(int idReserva) {
        ReservaDAO reservaDAO = new ReservaDAO();

        return reservaDAO.buscar(idReserva);
    }
}