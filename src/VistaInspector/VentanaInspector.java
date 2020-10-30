package VistaInspector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import quick.dbtable.DBTable;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class VentanaInspector extends JFrame {
	
	private int legajo;
	private Connection connection;
	
	private JPanel contentPane;
	private JTextField textoPatente;
	private Vector<String> patentes = new Vector<String>();
	DefaultListModel<String> model;
	JList<String> listaPatentes;
	
	private DBTable tabla;
	private DBTable tabla2;
	private DBTable ubicacionesAsignadas;
	private JTextField textIDParquimetro;
	private JTextField textCalle;
	private JTextField textAltura;
	private int seleccionado;
	
	JProgressBar progressBar;
	JLabel lblConexion;
	java.sql.Date fechaAcceso;
	java.sql.Time horaAcceso;
	String calle;
	String altura;
	int parquimetroID;
	
	JScrollPane scrTabla;
	JTable tablaMulta;
	
	int ID=100;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	
	
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
		setBounds(600, 150, 1000, 650); //Valores temporales
		contentPane.setSize(1000, 650);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 964, 320);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAgregarPatente = new JButton("Agregar Patente");
		btnAgregarPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ((validarPatente(textoPatente.getText())) && (textoPatente.getText().length()==6)) {
				
					patentes.add(textoPatente.getText());
					model.clear();
					llenarTablaPatentes();
					textoPatente.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "La patente no se corresponde con un formato valido.", "Error", JOptionPane.ERROR_MESSAGE);
					textoPatente.setText("");
					
				}
				
						
			}
		});
		btnAgregarPatente.setBounds(654, 11, 168, 23);
		panel.add(btnAgregarPatente);
		
		textoPatente = new JTextField();
		textoPatente.setBounds(832, 12, 86, 20);
		panel.add(textoPatente);
		textoPatente.setColumns(10);
		
		model = new DefaultListModel<String>();		
		listaPatentes = new JList<String>(model);
		listaPatentes.setBounds(726, 74, 168, 193);
		panel.add(listaPatentes);
		
		JLabel lblNewLabel = new JLabel("Lista Patentes");
		lblNewLabel.setBounds(771, 45, 94, 31);
		panel.add(lblNewLabel);
		
		JButton btnBorrarListaPatentes = new JButton("Borrar Lista Patentes");
		btnBorrarListaPatentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clear();
				patentes.clear();
				textoPatente.setText("");
				
			}
		});
		btnBorrarListaPatentes.setBounds(749, 278, 145, 23);
		panel.add(btnBorrarListaPatentes);
		
		tabla=new DBTable();
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tablaMouseClicked(e);
			}
		});
		tabla.setLocation(28, 11);
		tabla.setSize(302, 256);
		//Se agrega la tabla al panel
		panel.add(tabla);
		
		//Se setea la tabla para solo lectura
		tabla.setEditable(false);
		
		JLabel lblNewLabel_1 = new JLabel("Parquimetro y Ubicacion Seleccionada");
		lblNewLabel_1.setBounds(361, 13, 225, 19);
		panel.add(lblNewLabel_1);
		
		JLabel lblIDParquimetro = new JLabel("ID Parquimetro");
		lblIDParquimetro.setBounds(361, 53, 94, 23);
		panel.add(lblIDParquimetro);
		
		JLabel lblCalle = new JLabel("Calle");
		lblCalle.setBounds(361, 90, 67, 23);
		panel.add(lblCalle);
		
		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setBounds(361, 124, 67, 23);
		panel.add(lblAltura);
		
		textIDParquimetro = new JTextField();
		textIDParquimetro.setEditable(false);
		textIDParquimetro.setBounds(456, 56, 53, 20);
		panel.add(textIDParquimetro);
		textIDParquimetro.setColumns(10);
		
		textCalle = new JTextField();
		textCalle.setEditable(false);
		textCalle.setBounds(456, 87, 100, 20);
		panel.add(textCalle);
		textCalle.setColumns(10);
		
		textAltura = new JTextField();
		textAltura.setEditable(false);
		textAltura.setBounds(456, 125, 60, 20);
		panel.add(textAltura);
		textAltura.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textIDParquimetro.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "No hay ninguna selecion de campos", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					
				lblConexion.setVisible(false);
				administrarBarra();
				insertarAcceso();
				realizarMultas();
				llenarTablaMultas();
				}
				
				
			}
				
						
		});
		btnConectar.setBounds(452, 223, 89, 23);
		panel.add(btnConectar);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(423, 257, 146, 14);
		panel.add(progressBar);
		
		lblConexion = new JLabel("Conexion Establecida");
		lblConexion.setBounds(446, 282, 123, 14);
		panel.add(lblConexion);
		
		tabla2 = new DBTable();
		tabla2.setBounds(31, 39, 80, 184);
		tabla2.setEditable(false);
		
		ubicacionesAsignadas = new DBTable();		
		ubicacionesAsignadas.setSize(303, 200);
		ubicacionesAsignadas.setLocation(154, 35);
		ubicacionesAsignadas.setEditable(false);
		
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(10, 342, 964, 258);
		panel2.setLayout(null);
		panel2.add(tabla2);
		panel2.add(ubicacionesAsignadas);
		contentPane.add(panel2);
		
		JLabel lblTablaMultas = new JLabel("Tabla de Multas Labradas");
		lblTablaMultas.setBounds(648, 6, 178, 29);
		panel2.add(lblTablaMultas);
		
		scrTabla = new JScrollPane();
		scrTabla.setBounds(467, 39, 487, 196);
		panel2.add(scrTabla);
		lblConexion.setVisible(false);
		
		TableModel MultaModel =   
                new DefaultTableModel  
                (
                   new String[][] {},
                   new String[] {"Nro Multa", "Fecha", "Hora","Calle","Altura","Patente","Legajo"}
                );
		
		tablaMulta = new JTable(); // Crea una tabla
		scrTabla.setViewportView(tablaMulta); //setea la tabla dentro del JScrollPane srcTabla                     
        tablaMulta.setModel(MultaModel); // setea el modelo de la tabla  
        tablaMulta.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
        
        lblNewLabel_2 = new JLabel("Patentes en los estacionamientos");
        lblNewLabel_2.setBounds(0, 13, 220, 14);
        panel2.add(lblNewLabel_2);
        
        lblNewLabel_3 = new JLabel("Ubicaciones Asignadas al Inspector");
        lblNewLabel_3.setBounds(208, 10, 207, 14);
        panel2.add(lblNewLabel_3);
                                            // que se ordene al hacer click en una columna
		
		
		
		refrescarTabla();
		
		
		
	}
	
	private void inicializarComponentes() {
		
	}
	
	private void llenarTablaPatentes() {
		
		for (String s : patentes) {
			model.addElement(s);
		}
		
	}
	
	private void refrescarTabla() {
		
		try {
		
		Statement stmt = connection.createStatement();
		String sql = "SELECT id_parq, calle, altura " + 
					"FROM Parquimetros";
					
		
		ResultSet rs = stmt.executeQuery(sql);
		tabla.refresh(rs);
		rs.close();
		stmt.close();
		
		}
		catch (SQLException ex)
	      {
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
			
		
	}
	
	
	
	private void tablaMouseClicked(MouseEvent e) {
		if ((this.tabla.getSelectedRow() != -1) && (e.getClickCount() == 2))
	      {
	         this.seleccionarFila();
	      }
		
	}
	
	private void seleccionarFila() {
		
		this.seleccionado = this.tabla.getSelectedRow();
		textIDParquimetro.setText(this.tabla.getValueAt(seleccionado, 0).toString());
		textCalle.setText(this.tabla.getValueAt(seleccionado, 1).toString());
		textAltura.setText(this.tabla.getValueAt(seleccionado, 2).toString());
		
		//Valores que necesito para la tabla de multas
		calle = textCalle.getText();
		altura = textAltura.getText();
		parquimetroID = Integer.parseInt(textIDParquimetro.getText());
		
	}
	
	private void administrarBarra() {
		new Thread(new Runnable() {
		      @Override public void run() {
		        for (int i = 0; i<=100; i++) {
		          try {
		            Thread.sleep(25);
		          } catch (InterruptedException e) {
		            e.printStackTrace();
		          }
		          progressBar.setValue(i);
		        }
		        lblConexion.setVisible(true);
		        
		      }
		    }).start();
		
	}
	
	private void insertarAcceso() {
		
		try {
			
			Statement stmt = connection.createStatement();
			String sql = "SELECT CURDATE()";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			fechaAcceso = rs.getDate(1);
			
			sql = "SELECT CURTIME()";
			rs = stmt.executeQuery(sql); 
			rs.next();
			horaAcceso = rs.getTime(1);
			
			sql = "INSERT INTO Accede " +
                    "VALUES ('" + legajo  + "', '" + Integer.parseInt(textIDParquimetro.getText()) + "','" + fechaAcceso  + "','" + horaAcceso  + "')";
       stmt.execute(sql);
       stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this,
                    "Se produjo un error al insertar el nuevo registro.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
	}
		private void realizarMultas() {
			
			/*    Este metodo es el pincipal, realiza una busqueda de todas las patentes que estan en la lista que cargo el inspector en una
			 * 	  ubicacion determinada pero que no estan registradas en los estacinamientos, luego verifica que la ubicacion este asignada al inspector
			 * 	  para poder labrar la multa.
			 * 
			 * 
			 */
			
			try {
				Statement stmt2 = connection.createStatement();
				
				//patentes que estan en estacionamientos de la ubicacion que accedio el Inspector
				String sql2 = "SELECT patente " +  
				"from Tarjetas, Estacionamientos, Parquimetros " +
				"where (Tarjetas.id_tarjeta = Estacionamientos.id_tarjeta) and (Estacionamientos.id_parq = Parquimetros.id_parq) and (Parquimetros.calle = '" + calle + "' and Parquimetros.altura = '" + altura +"')";
				
				ResultSet rs2 = stmt2.executeQuery(sql2);
				tabla2.refresh(rs2);
				
				//Ubicaciones Asignadas al Inspector
				Statement stmt3 = connection.createStatement();
				String sql3 = "select calle, altura, dia, turno from Asociado_con where legajo = '" + legajo + "'";
				ResultSet rs3 = stmt3.executeQuery(sql3);
				ubicacionesAsignadas.refresh(rs3);
				
				
				if (ubicacionAsociada()==false) {
					
					JOptionPane.showMessageDialog(this,
		                    "No esta autorizado para labrar multas en ésta ubicacion.\n",
		                    "Error",
		                    JOptionPane.ERROR_MESSAGE);
					
				}else {
				
				
				
					for (String s : patentes) {
					
						Statement stmt = connection.createStatement();
					
						//patentes que estan en estacionamientos de la ubicacion que accedio el Inspector
						String sql = "SELECT patente " +  
								"from Tarjetas, Estacionamientos, Parquimetros " +
								"where (Tarjetas.id_tarjeta = Estacionamientos.id_tarjeta) and (Estacionamientos.id_parq = Parquimetros.id_parq) and (Parquimetros.calle = '" + calle + "' and Parquimetros.altura = '" + altura +"')";
					
						ResultSet rs = stmt.executeQuery(sql);
									
					
					
						System.out.println("La patente a analizar es : " + s);
						//Si la patente no esta en la lista de estacionamientos y la ubicacion actual esta asociada a un inpsector, labro la multa
						/*if ((estaPatente(s,rs)==false) & (ubicacionAsociada())) {
						labrarMulta(s);
						}
						 */
					
						boolean condicion1 = estaPatente(s,rs);
						boolean condicion2 = ubicacionAsociada();
						if ((condicion1==false) && (condicion2)) {
							labrarMulta(s);
						
					}				
					
					
					
				}
				
				
				}
				
				//tabla2.refresh(rs);
				//rs.close();
				//stmt.close();
				
				
			} catch (SQLException e) {
				
				System.out.println("SQLException: " + e.getMessage());
		         System.out.println("SQLState: " + e.getSQLState());
		         System.out.println("VendorError: " + e.getErrorCode());
			}
		
			
		
		
	}
			
		
		
	private boolean estaPatente(String patente, ResultSet respuesta) {
		
		boolean encontre = false;
		try {
			while ((respuesta.next()) && (encontre == false)) 
			{
				
				String patenteTabla = respuesta.getString(1);
				System.out.println("La patente de una fila es: " + patenteTabla);
				if (patente.compareTo(patenteTabla)==0) {
					encontre = true;
					System.out.println("La patente: " + patente + " esta en la lista.");
				}
				
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encontre;
	}
	
	private boolean ubicacionAsociada() {
		
		boolean tiene= false;
		
		try {
			Statement stmtconsulta = connection.createStatement();
			
			String sql = "select calle, altura, dia, turno from Asociado_con where legajo = '" + legajo +"'";
			
			ResultSet rsconsulta = stmtconsulta.executeQuery(sql);
			
			while ((rsconsulta.next()) && (tiene==false)) {
				
				String calleConsulta = rsconsulta.getString(1);
				String alturaConsulta = rsconsulta.getString(2);
				String diaConsulta = rsconsulta.getString(3);
				String turnoConsulta = rsconsulta.getString(4);
				System.out.println(calleConsulta + " " + alturaConsulta + " " + diaConsulta + " " + turnoConsulta);
				if (condicionInspector(calleConsulta,alturaConsulta,diaConsulta,turnoConsulta)) {
					tiene=true;
					
				}
				
			}
			
			stmtconsulta.close();
			rsconsulta.close();
			
			
			
			
			
						
			
			
			
			
		} catch (SQLException e) {
			
			System.out.println("SQLException: " + e.getMessage());
	         System.out.println("SQLState: " + e.getSQLState());
	         System.out.println("VendorError: " + e.getErrorCode());
		}
		
		
		return tiene;
		
		
	}
	
	private boolean condicionInspector(String calleAccedida,String alturaAccedida, String diaAccedido, String turnoAccedido) {
		
		boolean condicion1 = calleAccedida.compareTo(calle)==0;
		boolean condicion2 = alturaAccedida.compareTo(altura)==0;
		boolean condicion3;
		boolean condicion4;
		
		Calendar cal = Calendar.getInstance();
		int dia = cal.get(Calendar.DAY_OF_WEEK);
		System.out.println(Integer.toString(dia));
		
		switch (dia) {
		
		case 1: condicion3 = diaAccedido.compareTo("do")==0;
				break;
		case 2: condicion3 = diaAccedido.compareTo("lu")==0;
				break;
		case 3: condicion3 = diaAccedido.compareTo("ma")==0;
				break;
		case 4: condicion3 = diaAccedido.compareTo("mi")==0;
				break;
		case 5: condicion3 = diaAccedido.compareTo("ju")==0;
				break;
		case 6: condicion3 = diaAccedido.compareTo("vi")==0;
				break;
		case 7: condicion3 = diaAccedido.compareTo("sa")==0;
				break;
			
		default: condicion3=false;
						
		
		}
		
		LocalTime horaBD= horaAcceso.toLocalTime();
		LocalTime horaMañanaEntrada = LocalTime.of(8,00,00);
		LocalTime horaMañanaSalida = LocalTime.of(13,59,00);
		LocalTime horaTardeEntrada = LocalTime.of(14,00,00);
		LocalTime horaTardeSalida = LocalTime.of(20,00,00);
		
		if (turnoAccedido.compareTo("m")==0){
			condicion4= (horaMañanaEntrada.isBefore(horaBD)) && (horaMañanaSalida.isAfter(horaBD));
			
		}else {
			condicion4 = (horaTardeEntrada.isBefore(horaBD)) && (horaTardeSalida.isAfter(horaBD));
		}
		
		if (condicion1 && condicion2 && condicion3 && condicion4) {
			return true;
			
		}
		else {
			return false;
		}
		
		
		

		
	}
	
	private void labrarMulta(String patente) {
		
		try {
			Statement declaracion = connection.createStatement();
			String sql = "INSERT INTO Multa(fecha, hora, patente, id_asociado_con) " +
                    "VALUES ('" + fechaAcceso  + "', '" + horaAcceso + "','" + patente  + "','" + ID + "')";
       declaracion.execute(sql);
       declaracion.close();
       ID++;
			
					
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void llenarTablaMultas() {
		
		try {
			Statement stmt = connection.createStatement();			
			String sql = "SELECT numero, Multa.fecha, Multa.hora, patente FROM Multa, Accede where " +  
			"Multa.fecha = Accede.fecha and Multa.hora = Accede.hora and Accede.legajo = '" + legajo + "'";
			
			ResultSet respuesta = stmt.executeQuery(sql);
			((DefaultTableModel)this.tablaMulta.getModel()).setRowCount(0);
			int i=0;
			while (respuesta.next()) {
				
				((DefaultTableModel)this.tablaMulta.getModel()).setRowCount(i+1);
				this.tablaMulta.setValueAt(respuesta.getInt(1),i ,0);
				this.tablaMulta.setValueAt(respuesta.getDate(2), i, 1);
				this.tablaMulta.setValueAt(respuesta.getTime(3), i, 2);
				this.tablaMulta.setValueAt(calle, i, 3);
				this.tablaMulta.setValueAt(altura, i, 4);
				this.tablaMulta.setValueAt(respuesta.getString(4), 0, 5);
				this.tablaMulta.setValueAt(legajo, i, 6);
				i++;
								
				
			}
			stmt.close();
			respuesta.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
	}
	
	private boolean validarPatente(String texto){
		
		String letras_mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String numeros="0123456789";
	    
	    boolean es=true;
		
		
		
		   return es;
		
		
	}
}
