package dal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GestionDB {
	/***
	 * Devuelve una conexión a la base de datos
	 * @return
	 */
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
	
	/***
	 * Crea la tabla de alumnos en la bd
	 * @return
	 */
	public int crearTablaAlumnos() {
		int affectedRows = 0;
		Connection conn = null;
		
		try {
			conn = GestionDB.connect();
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
            	System.err.println("No se encuentra el ficheriño.");
            } catch (IOException e) {
				System.err.println("Error de Entrada/Salida.");
			}
		}catch(SQLException e) {
			System.err.println("Hubo un error con la base de datos, inténtelo más tarde." + e.getMessage());
		}finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                System.err.println("Error cerrando la conexión: " + e.getMessage());
	            }
	        }
	    }
		
		return affectedRows;
	}
	
	/***
	 * Crea la tabla profesores en la bd
	 * @return
	 */
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
				System.err.println("No se encuentra el ficheriño");
			} catch (IOException e) {
				System.err.println("Error de Entrada/Salida");
			}
		}catch(SQLException e) {
			System.err.println("Hubo un error con la base de datos, inténtelo más tarde. " + e.getMessage());
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

	/***
	 * Crea la tabla matrícula comprobando antes de que existen las otras dos
	 * @return
	 */
	public int crearTablaMatricula() {
		int affectedRows = 0;
		Connection conn = null;
		boolean existeAlumnado = false;
		boolean existeProfesorado = false;
		
		try {
			conn = GestionDB.connect();
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
	 * Borra la tabla Matriculas de la bd
	 */
    public boolean borrarTablaMatriculas() {
    	boolean borrada = false;
    	try {
    		Connection conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		borrada = statement.executeUpdate("DROP TABLE IF EXISTS Matriculas") > 0 ? true : false;
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Matriculas. ");
    	}
    	return borrada;
    }
    
    /***
     * Borra la tabla Profesores de la bd
     */
    public boolean borrarTablaProfesores() {
    	boolean borrada = false;
    	try {
    		Connection conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		borrada = statement.executeUpdate("DROP TABLE IF EXISTS Profesores") > 0 ? true : false;
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Profesores.");
    	}
    	
    	return borrada;
    }
    
    /***
     * Borra la tabla Alumnos de la bd
     */
    public boolean borrarTablaAlumnos() {
    	boolean borrada = false;
    	try {
    		Connection conn = GestionDB.connect();
    		Statement statement = conn.createStatement();
    		borrada = statement.executeUpdate("DROP TABLE IF EXISTS Alumnado") > 0 ? true : false;
    	}catch(Exception e) {
    		System.err.println("No se ha podido borrar la tabla Alumnado.");
    	}
    	
    	return borrada;
    }

}
