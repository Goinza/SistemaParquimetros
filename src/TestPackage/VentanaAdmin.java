package TestPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/*
 * Una vez que el usuario se identifique como admin,
 * puede usar esta ventana para realizar consultas de mysql
 */

public class VentanaAdmin extends JFrame {
	
	private JPanel contentPane;
	
	private JTextArea textConsultas;
	private JButton confirmarConsulta;
	private JTable tablaResultado;
	
	public static void main(String[] args) {
		new VentanaAdmin();
	}
	
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
	}
	
	
	private void realizarConsulta() {
		String consulta = textConsultas.getText();		
		try {
			ResultSet rs;
			ResultSetMetaData md;
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parquimetros?serverTimezone=America/Argentina/Buenos_Aires", "admin", "admin");
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (statement.execute(consulta)) {
				rs = statement.getResultSet();
				/*md = rs.getMetaData();
				rs.last();
				tablaResultado = new JTable();// new JTable(rs.getRow(), md.getColumnCount());
				rs.beforeFirst();*/
				llenarTabla(rs);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void llenarTabla(ResultSet rs) {
		Vector<String> nombreColumnas = new Vector<String>();
		Vector<Vector<Object>> datos = new Vector<Vector<Object>>();
		
		try {
			ResultSetMetaData md = rs.getMetaData();
			for (int i=1; i<=md.getColumnCount(); i++) {
				nombreColumnas.add(md.getColumnLabel(i));
			}
			Vector<Object> aux = new Vector<Object>();
			rs.next();
			//Simple test que solo agregar la primera tupla del resultado
			aux.add(rs.getString(1));
			aux.add(rs.getString(2));
			aux.add(rs.getString(3));
			aux.add(rs.getString(4));
			aux.add(rs.getString(5));
			aux.add(rs.getString(6));
			datos.add(aux);
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
