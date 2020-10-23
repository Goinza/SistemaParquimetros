package VistaAdmin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListaMouseListener extends MouseAdapter {

	public void mouseClicked(MouseEvent e) {
		JList<String> lista = (JList<String>) e.getSource();
		
		if (e.getClickCount() == 2) {
			DefaultListModel<String> modelo = (DefaultListModel<String>) lista.getModel();
			while (modelo.getSize() > 0) {				
				modelo.removeElementAt(0);
				//Se eliminan los elementos de la lista
				//Lo que deberia hacer es mostrar la descrpcion de la tabla seleccionada
			}
			lista.repaint();
		}
	}

}
