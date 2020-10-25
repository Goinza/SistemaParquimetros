package VistaPrincipal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import VistaAdmin.VentanaAdmin;
import VistaInspector.VentanaInspector;

public class VentanaPrincipal extends JFrame {
	
	public static void main(String[] args) {
		new VentanaPrincipal();
	}
	
	private JPanel contentPane;
	
	private JTextField adminText;
	private JPasswordField adminPass;
	private JButton adminAceptar;
	
	private JTextField inspectorText;
	private JTextField inspectorUsuario;
	private JPasswordField inspectorPass;
	private JButton inspectorAceptar;
	
	private Connection connection;
	
	public VentanaPrincipal() {
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
		adminText = new JTextField("Ingrese como administrador");
		adminText.setBounds(50, 100, 200, 25);
		adminText.setEditable(false);
		contentPane.add(adminText);
		
		adminPass = new JPasswordField();
		adminPass.setBounds(50, 125, 200, 25);
		contentPane.add(adminPass);
		
		adminAceptar = new JButton("Entrar");
		adminAceptar.setBounds(100, 150, 100, 25);
		adminAceptar.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				String pass = new String(adminPass.getPassword());
				if (pass.equals("admin")) {
					setVisible(false);
					new VentanaAdmin();
				}
				else {
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		contentPane.add(adminAceptar);
		
		inspectorText = new JTextField("Ingrese como inspector");
		inspectorText.setBounds(350, 100, 200, 25);
		inspectorText.setEditable(false);
		contentPane.add(inspectorText);
		
		inspectorUsuario = new JTextField();
		inspectorUsuario.setBounds(350, 125, 200, 25);
		contentPane.add(inspectorUsuario);
		
		inspectorPass = new JPasswordField();
		inspectorPass.setBounds(350, 150, 200, 25);
		contentPane.add(inspectorPass);
		
		inspectorAceptar = new JButton("Entrar");
		inspectorAceptar.setBounds(400, 175, 100, 25);
		inspectorAceptar.addMouseListener(new MouseAdapter() {
			
				public void mouseClicked(MouseEvent e) {
					try {
						if (connection == null) {
							connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parquimetros?serverTimezone=America/Argentina/Buenos_Aires", "inspector", "inspector");
						}
						Statement st = connection.createStatement();
						try {
							int user = Integer.parseInt(inspectorUsuario.getText());
							String pass = new String(inspectorPass.getPassword());
							String consulta = "SELECT * FROM INSPECTORES WHERE LEGAJO=" + user + " AND PASSWORD=MD5('" + pass + "');";
							ResultSet rs = st.executeQuery(consulta);
							if (rs.next()) {
								//El resultado tiene al menos una tupla,
								//por lo tanto el usuario insertado es correcto
								setVisible(false);
								new VentanaInspector(user, connection);
							}
							else {
								JOptionPane.showMessageDialog(null, "Legajo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch (NumberFormatException en) {
							JOptionPane.showMessageDialog(null, "Legajo deber ser un número", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					catch (SQLException ex) {
						ex.printStackTrace();
					}
					
				}
			
		});
		contentPane.add(inspectorAceptar);		
	}

}
