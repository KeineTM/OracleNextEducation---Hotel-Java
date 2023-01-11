package views;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.Reserva;

@SuppressWarnings("serial")
public class BusquedaResultado extends JFrame{

    private JPanel contentPane;
    private JTextField txtIdReserva;
    private JTextField txtFechaCheckIn;
    private JTextField txtFechaCheckOut;
    private JTextField txtImporteTotal;
    private JTextField txtFormaPago;
    private JTextField txtIdHuesped;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JLabel labelAtras;
    int xMouse, yMouse;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusquedaResultado frame = new BusquedaResultado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

    public BusquedaResultado () {
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroHuesped.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 634);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(12, 138, 199));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
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
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(new Color(12, 138, 199));
			     labelAtras.setForeground(Color.white);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(new Color(12, 138, 199));
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setForeground(Color.WHITE);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

        // Datos de la Reserva
        JLabel lblIdReserva = new JLabel("ID RESERVA");
		lblIdReserva.setForeground(SystemColor.WHITE);
		lblIdReserva.setBounds(68, 89, 269, 14);
		lblIdReserva.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblIdReserva);

        JLabel lblCheckIn = new JLabel("FECHA CHECK IN");
		lblCheckIn.setForeground(SystemColor.WHITE);
		lblCheckIn.setBounds(68, 159, 269, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblCheckIn);

        JLabel lblCheckOut = new JLabel("FECHA CHECK OUT");
		lblCheckOut.setForeground(SystemColor.WHITE);
		lblCheckOut.setBounds(68, 229, 269, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblCheckOut);

        JLabel lblValor = new JLabel("VALOR TOTAL");
		lblValor.setForeground(SystemColor.WHITE);
		lblValor.setBounds(68, 299, 269, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblValor);

        JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
		lblFormaPago.setForeground(SystemColor.WHITE);
		lblFormaPago.setBounds(68, 369, 269, 14);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblFormaPago);

        txtIdReserva = new JTextField();
		txtIdReserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtIdReserva.setBounds(68, 109, 285, 33);
		txtIdReserva.setBackground(Color.WHITE);
		txtIdReserva.setColumns(10);
		txtIdReserva.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtIdReserva.setEditable(false);
		contentPane.add(txtIdReserva);

        txtFechaCheckIn = new JTextField();
		txtFechaCheckIn.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFechaCheckIn.setBounds(68, 179, 285, 33);
		txtFechaCheckIn.setBackground(Color.WHITE);
		txtFechaCheckIn.setColumns(10);
		txtFechaCheckIn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtFechaCheckIn.setEditable(false);
		contentPane.add(txtFechaCheckIn);

        txtFechaCheckOut = new JTextField();
		txtFechaCheckOut.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFechaCheckOut.setBounds(68, 249, 285, 33);
		txtFechaCheckOut.setBackground(Color.WHITE);
		txtFechaCheckOut.setColumns(10);
		txtFechaCheckOut.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtFechaCheckOut.setEditable(false);
		contentPane.add(txtFechaCheckOut);

        txtImporteTotal = new JTextField();
		txtImporteTotal.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtImporteTotal.setBounds(68, 319, 285, 33);
		txtImporteTotal.setBackground(Color.WHITE);
		txtImporteTotal.setColumns(10);
		txtImporteTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtImporteTotal.setEditable(false);
		contentPane.add(txtImporteTotal);

        txtFormaPago = new JTextField();
		txtFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFormaPago.setBounds(68, 389, 285, 33);
		txtFormaPago.setBackground(Color.WHITE);
		txtFormaPago.setColumns(10);
		txtFormaPago.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtFormaPago.setEditable(false);
		contentPane.add(txtFormaPago);
		
        // Datos del Huesped
        JLabel lblIdHuesped = new JLabel("ID HUESPED");
		lblIdHuesped.setBounds(562, 89, 253, 14);
		lblIdHuesped.setForeground(SystemColor.WHITE);
		lblIdHuesped.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblIdHuesped);

        JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(562, 159, 253, 14);
		lblNombre.setForeground(SystemColor.WHITE);
		lblNombre.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBounds(560, 229, 255, 14);
		lblApellido.setForeground(SystemColor.WHITE);
		lblApellido.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblApellido);

        JLabel lblFechaNacimiento = new JLabel("FECHA DE NACIMIENTO");
		lblFechaNacimiento.setBounds(560, 299, 255, 14);
		lblFechaNacimiento.setForeground(SystemColor.WHITE);
		lblFechaNacimiento.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblFechaNacimiento);

        JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(560, 369, 255, 14);
		lblTelefono.setForeground(SystemColor.WHITE);
		lblTelefono.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblTelefono);

        JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(560, 439, 255, 14);
		lblEmail.setForeground(SystemColor.WHITE);
		lblEmail.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblEmail);

        txtIdHuesped = new JTextField();
		txtIdHuesped.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtIdHuesped.setBounds(560, 109, 285, 33);
		txtIdHuesped.setBackground(Color.WHITE);
		txtIdHuesped.setColumns(10);
		txtIdHuesped.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtIdHuesped.setEditable(false);
		contentPane.add(txtIdHuesped);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNombre.setBounds(560, 179, 285, 33);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setColumns(10);
		txtNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtNombre.setEditable(false);
		contentPane.add(txtNombre);
		
		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtApellido.setBounds(560, 249, 285, 33);
		txtApellido.setColumns(10);
		txtApellido.setBackground(Color.WHITE);
		txtApellido.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtApellido.setEditable(false);
		contentPane.add(txtApellido);

        txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFechaNacimiento.setBounds(560, 319, 285, 33);
		txtFechaNacimiento.setColumns(10);
		txtFechaNacimiento.setBackground(Color.WHITE);
		txtFechaNacimiento.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtFechaNacimiento.setEditable(false);
		contentPane.add(txtFechaNacimiento);

        txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtTelefono.setBounds(560, 389, 285, 33);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtTelefono.setEditable(false);
		contentPane.add(txtTelefono);

        txtEmail = new JTextField();
		txtEmail.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtEmail.setBounds(560, 459, 285, 33);
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtEmail.setEditable(false);
		contentPane.add(txtEmail);

        mostrarReserva();
        
    }

    private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
	}

    private void mostrarReserva() {
        Reserva reserva = new Reserva();
		
        reserva = reserva.buscar(Integer.valueOf(Busqueda.txtBuscar.getText()));
        
        txtIdReserva.setText(String.valueOf(reserva.getId()));
		txtFechaCheckIn.setText(reserva.getStringFechaCheckIn());
		txtFechaCheckOut.setText(reserva.getStringFechaCheckOut());
		txtImporteTotal.setText(String.valueOf(reserva.getImporteTotal()));
		
		switch(reserva.getFormaPago()) {
			case 0:
				txtFormaPago.setText("Efectivo");
				break;
			case 1:
				txtFormaPago.setText("Tarjeta de Débito");
				break;
			case 2:
				txtFormaPago.setText("Tarjeta de Crédito");
				break;
			case 3:
				txtFormaPago.setText("Tarjeta de Regalo");
			default:
				txtFormaPago.setText("No especificado.");
			break;
		}

		txtIdHuesped.setText(String.valueOf(reserva.huesped.getId()));
		txtNombre.setText(reserva.huesped.getNombre());
		txtApellido.setText(reserva.huesped.getApellido());
		txtFechaNacimiento.setText(reserva.huesped.getStringfechaNacimiento());
		txtTelefono.setText(reserva.huesped.getTelefono());
		txtEmail.setText(reserva.huesped.getEmail());
    }
}
