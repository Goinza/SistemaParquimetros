package VistaAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/*
 * Una vez que el usuario se identifique como admin,
 * puede usar esta ventana para realizar consultas de mysql
 */

public class VentanaAdmin extends JFrame {
	
	private JPanel contentPane;
	
	//Texto done se pone la consulta de SQL
	private JTextArea textConsultas;
	private JButton confirmarConsulta;
	
	//Tabla que muestra el resultado de la ultima consutlta
	private JTable tablaResultado;
	
	private JList<String> listaBaseDatos;
		
	public VentanaAdmin() {
		super();
		inicializarFrame();
		inicializarComponentes();
		repaint();		
	}
	
	private void inicializarFrame() {
		setVisible(true);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		contentPane= new JPanel();
		setBounds(600, 150, 1000, 750); //Valores temporales
		contentPane.setSize(1000, 750);
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void inicializarComponentes() {
		textConsultas = new JTextArea("Select * from Conductores;", 10, 30);
		textConsultas.setBounds(0, 0, 400, 250);
		textConsultas.setLineWrap(true);
		contentPane.add(textConsultas);		
		
		confirmarConsulta = new JButton("Consultar");
		confirmarConsulta.setBounds(120, 260, 100, 50);
		confirmarConsulta.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				realizarConsulta();
			}			
		});
		contentPane.add(confirmarConsulta);		
		
		//Lista con las tablas de la base de datos
		try {
			//DEBERIA TENER UNA SOLA CONEXION EN TODA LA VENTANA???
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parquimetros?serverTimezone=America/Argentina/Buenos_Aires", "admin", "admin");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SHOW TABLES;");
			Vector<String> aux = new Vector<String>();
			while (rs.next()) {
				aux.add(rs.getString(1));
			}			
			DefaultListModel<String> model = new DefaultListModel<String>();
			listaBaseDatos = new JList<String>(model);
			for (String s : aux) {
				model.addElement(s);
			}
			listaBaseDatos.setBounds(50, 350, 200, 200);
			listaBaseDatos.addMouseListener(new ListaMouseListener());
			contentPane.add(listaBaseDatos);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	private void realizarConsulta() {
		String consulta = textConsultas.getText();		
		try {
			ResultSet rs;
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parquimetros?serverTimezone=America/Argentina/Buenos_Aires", "admin", "admin");
			Statement statement = connection.createStatement();
			if (statement.execute(consulta)) {
				//La consulta dio un resultado, se debe mostrar el resultado en la tabla
				rs = statement.getResultSet();
				llenarTabla(rs);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void llenarTabla(ResultSet rs) {
		Vector<String> nombreColumnas = new Vector<String>();
		Vector<Vector<Object>> datos = new Vector<Vector<Object>>();
		Vector<Object> aux;
		
		try {
			ResultSetMetaData md = rs.getMetaData();
			int cantColumnas = md.getColumnCount();	
			//Agrego las columnas
			for (int i=1; i<=cantColumnas; i++) {
				nombreColumnas.add(md.getColumnLabel(i));
			}			
			
			//Agrego las tuplas
			while (rs.next()) {
				aux = new Vector<Object>();
				for (int i=1; i<=cantColumnas; i++) {
					aux.add(rs.getString(i));
				}
				datos.add(aux);
			}
			
			//Creo la tabla con los datos de arriba
			tablaResultado = new JTable(datos, nombreColumnas);
			tablaResultado.setBounds(500, 150, 400, 250);
			//El scrollpane es necesario para que los nombres de las columnas aparezcan
			//El problema con esta tabla es que se puede editar, pero deberia ser no editable
			JScrollPane scrollPane = new JScrollPane(tablaResultado);
			scrollPane.setBounds(500, 150, 400, 250);
			contentPane.add(scrollPane);
			repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
