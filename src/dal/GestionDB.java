package dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
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
	
	public void crearTablas() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/dal/Profesores.sql"));
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
