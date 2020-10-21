package TestPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * Una vez que el usuario se identifique como admin,
 * puede usar esta ventana para realizar consultas de mysql
 */

public class VentanaAdmin extends JFrame {
	
	private JPanel contentPane;
	
	private JTextArea textConsultas;
	private JButton confirmarConsulta;
	
	public static void main(String[] args) {
		new VentanaAdmin();
	}
	
	public VentanaAdmin() {
		super();
		setVisible(true);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		contentPane= new JPanel();
		setBounds(600, 150, 500, 500); //Valores temporales
		contentPane.setSize(500, 500);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textConsultas = new JTextArea(10, 30);
		textConsultas.setBounds(0, 0, 400, 250);
		textConsultas.setLineWrap(true);
		contentPane.add(textConsultas);		
		confirmarConsulta = new JButton("Consultar");
		confirmarConsulta.setBounds(120, 260, 100, 50);
		confirmarConsulta.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				//Obtiene el texto de la consulta sql
				System.out.println(textConsultas.getText());
			}			
		});
		contentPane.add(confirmarConsulta);
		
		repaint();
		
	}

}
