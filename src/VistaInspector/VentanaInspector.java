package VistaInspector;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaInspector extends JFrame {
	
	private int legajo;
	private Connection connection;
	
	private JPanel contentPane;
	
	public VentanaInspector(int legajo, Connection connection) {
		this.legajo = legajo;
		this.connection = connection;
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
		
	}

}
