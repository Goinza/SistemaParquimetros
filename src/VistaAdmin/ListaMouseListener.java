package VistaAdmin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListaMouseListener extends MouseAdapter {
	
	VentanaAdmin ventana;
	Connection connection;
	
	public ListaMouseListener(VentanaAdmin ventana, Connection connection) {
		this.ventana = ventana;
		this.connection = connection;
	}

	public void mouseClicked(MouseEvent e) {
		JList<String> lista = (JList<String>) e.getSource();
		
		if (e.getClickCount() == 2) {
			DefaultListModel<String> modelo = (DefaultListModel<String>) lista.getModel();
			
			int index = lista.getSelectedIndex();
			if (index >= 0) {
				//Guarda el string asociado a la tabla elegida
				String seleccion = lista.getSelectedValue();
								
				//Pide la descripcion de la tabla
				try {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("DESCRIBE " + seleccion + ";");
					
					Vector<String> nombreColumnas = new Vector<String>();
					Vector<Vector<Object>> datos = new Vector<Vector<Object>>();
					Vector<Object> aux;
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
					
					ventana.cargarTablaDescribe(nombreColumnas, datos);
					
				}
				catch (SQLException exc) {
					exc.printStackTrace();
				}	
			}
			lista.repaint();
		}
	}

}
