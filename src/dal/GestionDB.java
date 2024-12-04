package dal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ent.Alumno;
import ent.Profesor;

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
    public int createDatabase() {
        int affectedRows = 0;
        Connection conn = null;
        String[] scriptFiles = {"Alumnado.sql", "Profesores.sql", "Matricula.sql"};

        try {
            conn = this.connect();
            Statement statement = conn.createStatement();

            for (String scriptFile : scriptFiles) {
                System.out.println("Processing: " + scriptFile);

                try (BufferedReader br = new BufferedReader(new FileReader("src/dal/" + scriptFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().isEmpty() && !line.startsWith("--")) {
                            statement.addBatch(line);
                        }
                    }
                }

                int[] results = statement.executeBatch();
                for (int res : results) {
                    affectedRows += res;
                }
                System.out.println("Executed script: " + scriptFile);
            }

            System.out.println("Database initialization completed. Total rows affected: " + affectedRows);
        } catch (IOException e) {
            System.err.println("Error reading script file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        return affectedRows;
    }
    
    /***
     * Lista los alumnos en la base de datos
     * @param entidad
     * @return
     */
    public ArrayList<Alumno> listadoAlumnos(){
    	Connection conn = null;
    	ArrayList<Alumno> listado = new ArrayList<Alumno>();
    	try {
    		conn = this.connect();
    		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Alumnado");
    		ResultSet res = statement.executeQuery();
    		
    		while(res.next()) {
    			listado.add(new Alumno(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento")));
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return listado;
    }
    
    public ArrayList<Profesor> listadoProfesores(){
    	Connection conn = null;
    	ArrayList<Profesor> listado = new ArrayList<Profesor>();
    	try {
    		conn = this.connect();
    		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Profesorado");
    		ResultSet res = statement.executeQuery();
    		
    		while(res.next()) {
    			listado.add(new Profesor(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento"), res.getInt("Antiguedad")));
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return listado;
    }


}
