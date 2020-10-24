package VistaPrincipal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import VistaAdmin.VentanaAdmin;

public class VentanaPrincipal extends JFrame {
	
	public static void main(String[] args) {
		new VentanaPrincipal();
	}
	
	private JPanel contentPane;
	
	private JTextField adminText;
	private JPasswordField adminPass;
	private JButton adminAceptar;
	
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
		adminText = new JTextField("Ingrese la contraseña de administrador");
		adminText.setBounds(50, 100, 250, 25);
		adminText.setEditable(false);
		contentPane.add(adminText);
		
		adminPass = new JPasswordField();
		adminPass.setBounds(50, 125, 200, 25);
		contentPane.add(adminPass);
		
		adminAceptar = new JButton("Entrar");
		adminAceptar.setBounds(50, 150, 100, 25);
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
	}

}
