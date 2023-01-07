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

import BackEnd.Reserva;
import Factory.ConnectionFactory;

// Capa de persistencia
public class ReservaDAO {
    public Double getCostoDeHabitacion(int tipoHabitacion) throws SQLException {
        double costoDeHabitacion = 299;
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final Statement statement = con.createStatement();

            try(statement) {
                statement.execute("SELECT Costo FROM TipoHabitacion WHERE Id='" + (tipoHabitacion + 1) + "'");
                
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet) {
                    if(resultSet.next()){
                        costoDeHabitacion = resultSet.getInt(1);
                    }
                }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return costoDeHabitacion;
    }

    /**
     * Método que conecta con la base de datos y devuelve una lista con todos los registros de la tabla Reservas.
     *  */
    public List<Map<String, String>> listar() throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        // Variable para almacenar el listado
        ArrayList<Map<String, String>> resultado = new ArrayList<>();

        try(con) {
            final PreparedStatement statement = con.prepareStatement("SELECT Reservas.Id, Reservas.FechaEntrada, Reservas.FechaSalida, Reservas.ImporteTotal, FormasPago.Descripcion, Reservas.IdHuesped " +
                "FROM Reservas " +
                "INNER JOIN FormasPago ON FormasPago.Id = Reservas.FormaPago;");
            try(statement) {
                // Java en automático sugiere almacenar el resultado en una variable de tipo boolean
                // Esto se debe a que identifica si el resultado es una lista de registros (como en el caso de un SELECT) o no (como UPDATE, DELETE, etc)
                statement.execute();
                
                // Permite almacenar el listado resultante
                final ResultSet resultSet = statement.getResultSet();
                
                try(resultSet) {
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
                }  
            }
        }
        return resultado;
    }

    /**
     * Método que realiza el registro de una reserva en base de datos a partir de un objeto instanciado de esta clase
     * @throws SQLException
     */
    public void registarEnDB(Reserva reserva) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();

        try(con) {
            final PreparedStatement statement = con.prepareStatement("INSERT INTO Reservas (FechaEntrada, FechaSalida, ImporteTotal, FormaPago, IdHuesped)" 
                + "VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS); // Recupera el id creado en la tabla con el AUTO_INCREMENT de la clave primaria
            
            try(statement) {
                statement.setString(1, reserva.getFechaCheckIn().toString());
                statement.setString(2, reserva.getFechaCheckOut().toString());
                statement.setDouble(3, reserva.getImporteTotal());
                statement.setInt(4, reserva.getFormaPago());
                statement.setInt(5, reserva.huesped.getId());
                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();
                try(resultSet) {
                    while(resultSet.next()) {
                        reserva.setId(resultSet.getInt(1)); // Lo asigna al atributo Id del objeto para enviarlo al mensaje de éxito
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void eliminar(int idReserva) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM Reservas WHERE Id= ?");

            try(statement) {
                statement.setInt(1, idReserva);
                statement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar(String fechaEntrada, String fechaSalida, double importeTotal, int formaPago, int idReserva) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try(con) {
            final PreparedStatement statement = con.prepareStatement("UPDATE Reservas"
                + "SET FechaEntrada = ?,"
                + "FechaSalida = ?,"
                + "ImporteTotal = ?,"
                + "FormaPago= ?,"
                + "WHERE Id= ?");
            try(statement) {
                statement.setString(1, fechaEntrada);
                statement.setString(2, fechaSalida);
                statement.setDouble(3, importeTotal);
                statement.setInt(4, formaPago);
                statement.setInt(5, idReserva);

                statement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
