package dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionDB {
	public Connection connect() {
		Connection conn = null;
		String connURL = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_afernandez";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connURL, "afernandez", "12345");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/***
	 * Ejecuta los ficheros sql relativos a las tablas
	 */
	public void crearTablas() {
		String basePATH = "src/dal/";
		Connection conn = this.connect();
		String[] ficheros = {"Alumnado.sql", "Profesores.sql", "Matricula.sql", };
		try {
			String linea = "";
			String query = "";
			for (int i = 0; i < ficheros.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(basePATH + ficheros[i]));
				while((linea = br.readLine()) != null) {
					query += linea;
				}
				
				PreparedStatement ejecutarFichero = conn.prepareStatement(query);
				ejecutarFichero.executeUpdate();
				query = "";
				br.close();
			}
			System.out.println("Se han creado todas las tablas.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
