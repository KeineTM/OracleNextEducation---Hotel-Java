package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Huesped;
import Controller.Reserva;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Optional;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	public static JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloH;
	JComboBox formasPago;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		// Campo de texto para la variable de búsqueda
		txtBuscar = new JTextField();
		txtBuscar.setBounds(540, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscar.setColumns(10);
		contentPane.add(txtBuscar);
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		// Tabla para impresión de registros de la tabla Reservas		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas, null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Número de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		modelo.addColumn("Número de Huésped");
		
		// Lógica para imprimir los registros de la tabla con la lista de reservas
		cargarTablaReservas();
		crearComboBoxFormasPago();

		// Tabla para impresión de registros de la tabla Huéspedes		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes, null);
		modeloH = (DefaultTableModel) tbHuespedes.getModel();
		modeloH.addColumn("Número de Huesped");
		modeloH.addColumn("Nombre");
		modeloH.addColumn("Apellido");
		modeloH.addColumn("Fecha de Nacimiento");
		modeloH.addColumn("Teléfono");
		modeloH.addColumn("Email");

		// Lógica para imprimir los registros de la tabla con la lista de huéspedes
		cargarTablaHuéspedes();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarReserva();
			}
		});
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Lógica para la edición de resgistros desde la tabla hacia la base de datos.
				editarReserva();
			}
		});
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Lógica para la eliminación de registros de la base de datos desde la interfaz gráfica.
				eliminarReserva();
			}
		});
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	    int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
	}

	/**
	 * Método que agrega una JComboBox para la edición del campo "FormaPago"
	 */
	private void crearComboBoxFormasPago() {
        //Combo y valores
        formasPago = new JComboBox();
        formasPago.addItem("Efectivo");
        formasPago.addItem("Tarjeta de Débito");
        formasPago.addItem("Tarjeta de Crédito");
        formasPago.addItem("Tarjeta de Regalo");

        //se indica que columna tendra el JComboBox
        javax.swing.table.TableColumn formaPagoColumn = tbReservas.getColumnModel().getColumn(4);
		formaPagoColumn.setCellEditor(new DefaultCellEditor(formasPago));
    }

	private void cargarTablaReservas() {
		// Instanciamiento de objeto de la clase Reserva para poder emplear el método que devuelve la lista de registros de la tabla
		Reserva reservas = new Reserva();

		reservas.listar().forEach(reserva -> modelo.addRow(new Object[] {
			reserva.get("Número de Reserva"),
			reserva.get("Fecha Check in"),
			reserva.get("Fecha Check out"),
			reserva.get("Valor"),
			reserva.get("Forma de pago"),
			reserva.get("Número de Huésped")
		} ));
	}

	private void cargarTablaHuéspedes() {
		// Instanciamiento de objeto de la clase Reserva para poder emplear el método que devuelve la lista de registros de la tabla
		Huesped huespedes = new Huesped();
		
		huespedes.listar().forEach(huesped -> modeloH.addRow(new Object[] {
			huesped.get("Número de Huésped"),
			huesped.get("Nombre"),
			huesped.get("Apellido"),
			huesped.get("Fecha de Nacimiento"),
			huesped.get("Teléfono"),
			huesped.get("Email")
		} ));
	}

	/**
	 * Método del botón Buscar que recupera la cadena introducida por el usuario en el campo txtBuscar
	 * Evalúa que la cadena no se encuentre vacía
	 * Y que el contenido corresponda con un número entero
	 * Para desplegar la ventana con el resultado de la búsqueda
	 */
	private void buscarReserva() {
		if(txtBuscar.getText().length() != 0) {
			if(esUnNumero(txtBuscar.getText())) {
				Reserva reserva = new Reserva();
				reserva = reserva.buscar(Integer.valueOf(txtBuscar.getText()));
				if(reserva != null) {
					BusquedaResultado resultado = new BusquedaResultado();
					resultado.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(this, "No existe el número de reserva: " + txtBuscar.getText());
				}
			} else {
				JOptionPane.showMessageDialog(this, "Ingrese un número entero como ID de Reserva para buscar");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Ingrese un número entero como ID de Reserva para buscar");
		}
	}

	/**
	 * Método que elimina el registro seleccionado en base de datos.
	 * Valida que se haya seleccionado una fila de la JTable para ejecutar la eliminación.
	 */
	private void eliminarReserva() {
		if(tbReservas.getSelectedRow() == -1 ) {
			JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
		} else {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())).ifPresentOrElse(fila -> {
				Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
	
				Reserva reserva = new Reserva();
				reserva.eliminar(id);
	
				modelo.removeRow(tbReservas.getSelectedRow());
				JOptionPane.showMessageDialog(this, "Reserva eliminada exitosamente.");
	
			}, () -> JOptionPane.showMessageDialog(this, "Seleccione una reserva."));
		}
		
	}

	/**
	 * Método para editar el registro directamente desde los campos de la Jtable en la ventana.
	 * Detecta un sólo registro seleccionado y ejecuta la actualización en la tabla de la DB sobre él.
	 * NO se pueden modificar los ID de la Reserva o del Huesped, ni cambiar la relación existente entre ellos.
	 * Es decir, no se puede cambiar el IdHuesped del Huesped ligado a la Reserva.
	 */
	public void editarReserva() {
		if(tbReservas.getSelectedRow() == -1 ) {
			JOptionPane.showMessageDialog(this, "Seleccione un registro para editar.");
		} else {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())).ifPresentOrElse(fila -> {
				Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

				int registro = tbReservas.getSelectedRow();

				Reserva reserva = new Reserva(
					(int) id, // IdReserva
					String.valueOf(this.modelo.getValueAt(registro, 1).toString()), // fechaEntrada
					String.valueOf(this.modelo.getValueAt(registro, 2).toString()), // fechaSalida
					formasPago.getSelectedIndex(), // formaPago
					Double.parseDouble(this.modelo.getValueAt(registro, 3).toString()) // importeTotal
				);
			
				reserva.editar();
				
				// Limpia la tabla
				limpiarTabla(tbReservas, modelo);
				// Reimprime el contenido de la tabla
				cargarTablaReservas();
	
			}, () -> JOptionPane.showMessageDialog(this, "Seleccione una reserva."));
		}
		
	}

	/**
	 * Método para limpiar la tabla
	 * @param cadena
	 * @return
	 */
	private static void limpiarTabla(JTable tabla, DefaultTableModel modelo) {
		for (int i = 0; i < tabla.getRowCount(); i++) {
			modelo.removeRow(i);
			i-=1;
		}
	}

	/**
	 * Método para evaluar la entrada de números enteros (Integer)
	 * @param cadena tipo String
	 * @return true o false
	*/
	private static boolean esUnNumero(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
}
