package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ent.Matricula;

public class GestionMatriculas {
	/***
	 * Inserta una matricula en la base de datos
	 * @param matricula
	 * @return booleano que indica si se ha insertado o no
	 */
	public static boolean insertar(Matricula matricula) {
		boolean insertado = false;
		Connection conn = GestionDB.connect();
		String sql = String.format(
				"INSERT INTO Matriculas (idProfesorado, idAlumnado, Asignatura, Curso) VALUES (%d, %d, '%s', %d)",
				matricula.getIdProfesor(), matricula.getIdAlumno(), matricula.getAsignatura(), matricula.getCurso());
		try {
			Statement statement = conn.createStatement();
			int filasInsertadas = statement.executeUpdate(sql);
			
			if (filasInsertadas > 0) {
				insertado = true;
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido insertar la matrícula, comprueba que se ha creado la tabla y que los campos introducidos son correctos.");
		}
		
		return insertado;
	}
	
	/***
	 * Devuelve una matrícula en base a su id
	 * @param id
	 * @return
	 */
	public static Matricula conseguirMatricula(int id) {
		Connection conn = GestionDB.connect();
		Matricula matricula = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(String.format("SELECT * FROM Matriculas WHERE id = %d", id));
			if(res.next()) {
				matricula = new Matricula(res.getInt("id"), res.getInt("idProfesorado"), res.getInt("idAlumnado"), res.getString("Asignatura"), res.getInt("Curso"));
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido listar la matrícula, comprueba que la matrícula con el id: " + id + " existe y que existe la tabla Matriculas.");
		}
		
		return matricula;
	}
	
	/***
	 * Devuelve un listado de matrículas
	 * @return
	 */
    public static ArrayList<Matricula> listadoCompleto() {
    	Connection conn = null;
    	ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
    	try {
    		conn = GestionDB.connect();
    		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Matriculas");
    		ResultSet res = statement.executeQuery();
    		while(res.next()) {
    			matriculas.add(new Matricula(res.getInt("id"), res.getInt("idProfesorado"), res.getInt("idAlumnado"), res.getString("Asignatura"), res.getInt("Curso")));
    		}
    	}catch(Exception e) {
    		System.err.println("No se han podido listar las matrículas por el siguiente motivo: " + e.getMessage());
    	}
    	
    	return matriculas;
    }
    
    /***
     * Modifica el id del profesor en una mátricula, haciendo que el alumno cambie de profesor en una materia
     * @param id
     * @param idProfesor
     * @return
     */
    public static boolean modificarIdProfesor(int id, int idProfesor) {
    	boolean actualizado = false;
    	Connection conn = null;
    	
    	conn = GestionDB.connect();
    	
    	try {
			Statement statement = conn.createStatement();
			actualizado = statement.execute(String.format("UPDATE Matriculas SET idProfesorado = %d WHERE id = %d", idProfesor, id));
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el id del profesor en la matrícula con id: " + id + ". Comprueba que tanto el id de profesor como el id de matrícula sean válidos y que la tabla exista.");
		}
		return actualizado;
    }
    
    /***
     * Modifica el id del alumno en una matrícula, posiblemente corrigiendo un fallo de asignar un id relativo al alumno de forma erronea
     * @param id
     * @param idAlumno
     * @return
     */
    public static boolean modificarIdAlumno(int id, int idAlumno) {
    	boolean actualizado = false;
    	Connection conn = null;
    	
    	conn = GestionDB.connect();
    	
    	try {
			Statement statement = conn.createStatement();
			actualizado = statement.execute(String.format("UPDATE Matriculas SET idAlumnado = %d WHERE id = %d", idAlumno, id));
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el id del alumno en la matrícula con id: " + id + ". Comprueba que tanto el id de alummno como el id de matrícula sean válidos y que la tabla exista.");
		}
		return actualizado;
    }

    /***
     * Modifica el nombre de la asignatura en una matrícula
     * @param id
     * @param asignatura
     * @return
     */
    public static boolean modificarAsignatura(int id, String asignatura) {
    	boolean actualizado = false;
    	Connection conn = null;
    	
    	conn = GestionDB.connect();
    	
    	try {
			Statement statement = conn.createStatement();
			actualizado = statement.execute(String.format("UPDATE Matriculas SET Asignatura = %s WHERE id = %d", asignatura, id));
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el nombre de la asignatura en la matrícula con id: " + id + ". Comprueba que tanto el id de alummno como el nombre de la asignatura sean válidos y que la tabla exista.");
		}
		return actualizado;
    }
    
    /***
     * Modifica el número de curso de una matrícula.
     * @param id
     * @param curso
     * @return
     */
    public static boolean modificarCurso(int id, int curso) {
    	boolean actualizado = false;
    	Connection conn = null;
    	
    	conn = GestionDB.connect();
    	
    	try {
			Statement statement = conn.createStatement();
			actualizado = statement.execute(String.format("UPDATE Matriculas SET Curso = %d WHERE id = %d", curso, id));
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el número de curso en la matrícula con id: " + id + ". Comprueba que tanto el número de curso como el id de matrícula sean válidos y que la tabla exista.");
		}
		return actualizado;
    }
    
    /***
     * Borra una matrícula de la tabla.
     * @param id
     * @return
     */
    public static boolean borrar(int id) {
    	int filasAfectadas = 0;
    	boolean borrado = false;
    	Connection conn = GestionDB.connect();
    	
    	try {
			Statement statement = conn.createStatement();
			filasAfectadas = statement.executeUpdate(String.format("DELETE FROM Matriculas WHERE id = %d", id));
			
			if (filasAfectadas > 0) {
				borrado = true;
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido borrar la matrícula con id: " + id + ". Comprueba que existe tanto la matrícula con ese id como la tabla Matriculas.");
		}
    	
    	return borrado;
    }
}
