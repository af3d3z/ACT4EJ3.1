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
import java.sql.Statement;
import java.util.ArrayList;

import ent.Alumno;
import ent.Matricula;
import ent.Profesor;

public class GestionDB {
	public static Connection connect() {
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
	
	public int crearTablaAlumnos() {
		int affectedRows = 0;
		Connection conn = null;
		
		try {
			conn = this.connect();
			Statement statement = conn.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS Alumnado");
			statement.executeUpdate("CREATE TABLE Alumnado (id INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(50), Apellidos VARCHAR(50), FechaNacimiento DATE)");

			try (BufferedReader br = new BufferedReader(new FileReader("src/dal/Alumnado.sql"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty() && !line.startsWith("--")) {
                        statement.addBatch(line);
                    }
                }
                
	            int[] results = statement.executeBatch();
	            for (int res : results) {
	                affectedRows += res;
	            }
	            
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	public int crearTablaProfesores() {
		int affectedRows = 0;
		Connection conn = null;
		
		try {
			conn = GestionDB.connect();
			Statement statement = conn.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS Profesores");
			statement.executeUpdate("CREATE TABLE Profesores (id INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(50), Apellidos VARCHAR(50), FechaNacimiento DATE, Antiguedad INT)");

			try (BufferedReader br = new BufferedReader(new FileReader("src/dal/Profesores.sql"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty() && !line.startsWith("--")) {
                        statement.addBatch(line);
                    }
                }
                
	            int[] results = statement.executeBatch();
	            for (int res : results) {
	                affectedRows += res;
	            }
	            
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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

	public int crearTablaMatricula() {
		int affectedRows = 0;
		Connection conn = null;
		boolean existeAlumnado = false;
		boolean existeProfesorado = false;
		
		try {
			conn = this.connect();
			Statement statement = conn.createStatement();
			
			ResultSet infoTablaAlumnado = statement.executeQuery(""
					+ "SELECT EXISTS ("
					+ "    SELECT "
					+ "        TABLE_NAME"
					+ "    FROM "
					+ "    information_schema.TABLES "
					+ "    WHERE \n"
					+ "    TABLE_SCHEMA LIKE 'ad2425_afernandez' AND "
					+ "        TABLE_TYPE LIKE 'BASE TABLE' AND"
					+ "        TABLE_NAME = 'Alumnado'"
					+ "    )");
			
			if (infoTablaAlumnado.next()) {
				existeAlumnado = infoTablaAlumnado.getBoolean(1);
			}
			
			ResultSet infoTablaProfesorado = statement.executeQuery(""
					+ "SELECT EXISTS ("
					+ "    SELECT "
					+ "        TABLE_NAME"
					+ "    FROM "
					+ "    information_schema.TABLES "
					+ "    WHERE "
					+ "    TABLE_SCHEMA LIKE 'ad2425_afernandez' AND "
					+ "        TABLE_TYPE LIKE 'BASE TABLE' AND"
					+ "        TABLE_NAME = 'Profesores'"
					+ "    )");
			
			if(infoTablaProfesorado.next()) {
				existeProfesorado = infoTablaProfesorado.getBoolean(1);
			}
			
			
			if(existeAlumnado && existeProfesorado) {
				statement.executeUpdate("DROP TABLE IF EXISTS Matriculas");
				statement.executeUpdate("create table Matriculas ( id INT PRIMARY KEY AUTO_INCREMENT, idProfesorado INT, idAlumnado INT, Asignatura VARCHAR(50), Curso INT, FOREIGN KEY (idProfesorado) REFERENCES Profesores(id), FOREIGN KEY (idAlumnado) REFERENCES Alumnado(id))\n");

				try (BufferedReader br = new BufferedReader(new FileReader("src/dal/Matricula.sql"))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	                    if (!line.trim().isEmpty() && !line.startsWith("--")) {
	                        statement.addBatch(line);
	                    }
	                }
	                
		            int[] results = statement.executeBatch();
		            for (int res : results) {
		                affectedRows += res;
		            }
		            
	            } catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				}
			}else {
				affectedRows = -1;
			}
	}catch(Exception e) {
		e.printStackTrace();
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
    		conn = GestionDB.connect();
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
    		conn = GestionDB.connect();
    		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Profesores");
    		ResultSet res = statement.executeQuery();
    		
    		while(res.next()) {
    			listado.add(new Profesor(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento"), res.getInt("Antiguedad")));
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return listado;
    }

    public void borrarTablaMatriculas() {
    	Connection conn = null;
    	try {
    		conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		statement.executeUpdate("DROP TABLE IF EXISTS Matriculas");
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Matriculas. ");
    	}
    }
    
    public void borrarTablaProfesores() {
    	Connection conn = null;
    	try {
    		conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		statement.executeUpdate("DROP TABLE IF EXISTS Profesores");
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Profesores.");
    	}
    }
    
    public void borrarTablaAlumnos() {
    	Connection conn = null;
    	try {
    		conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		statement.executeUpdate("DROP TABLE IF EXISTS Alumnado");
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Alumnado.");
    	}
    }

}
