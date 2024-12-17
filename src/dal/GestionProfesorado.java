package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ent.Profesor;

public class GestionProfesorado {
	/***
	 * Inserta un profesor en la base de datos
	 * @param profesor
	 * @return booleano que indica si se ha podido insertar o no
	 */
	public static boolean insertar(Profesor profesor) {
		boolean insertado = false;
		Connection conn = GestionDB.connect();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sql = String.format("INSERT INTO Profesores (Nombre, Apellidos, FechaNacimiento, Antiguedad) VALUES ('%s', '%s', '%s', %d)",
					profesor.getNombre(), profesor.getApellidos(), formatter.format(profesor.getFechaNacimiento()), profesor.getAntiguedad());
		
		try {
			Statement statement = conn.createStatement();
			int filasInsertadas = statement.executeUpdate(sql);
			if(filasInsertadas > 0) {
				insertado = true;
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido insertar el profesor, comprueba que se ha creado la tabla y que los campos introducidos son correctos.");
		}
		return insertado;
	}
	
	/***
	 * Devuelve un profesor en base a su id
	 * @param id
	 * @return
	 */
	public static Profesor conseguirProfesor(int id) {
		Connection conn = GestionDB.connect();
		Profesor profesor = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(String.format("SELECT * FROM Profesores WHERE id = %d", id));
			if(res.next()) {
				profesor = new Profesor(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento"), res.getInt("Antiguedad"));
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido listar el profesor, comprueba que el profesor con el id: " + id + " existe y que existe la tabla Profesores.");
		}
		
		return profesor;
	}
	
	
	/***
	 * Devuelve un arraylist de profesores
	 * @return
	 */
    public static ArrayList<Profesor> listadoProfesores(){
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
    		System.err.println("No se pueden listar los profesores ahora mismo, inténtelo más tarde.");
    	}
    	return listado;
    }
    
    /***
     * Modifica el nombre del profesor mediante el id
     * @param id
     * @param nombre
     * @return true si se ha podido modificar satisfactoriamente, false de lo contrario
     */
    public static boolean modificarNombre(int id, String nombre) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Profesores SET Nombre = '%s' WHERE id = %d", nombre, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el nombre del profesor, comprueba que existan el profesor y la tabla Profesores.");
		}
		return actualizado;
	}
    
    /***
     * Modifica los apellidos de un profesor mediante su id
     * @param id
     * @param apellidos
     * @return true si se ha podido modificar satisfactoriamente, false de lo contrario
     */
    public static boolean modificarApellidos(int id, String apellidos) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Profesores SET Apellidos = '%s' WHERE id = %d", apellidos, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar los apellidos del profesor, comprueba que existan el profesor y la tabla Profesores.");
		}
		return actualizado;
	}
    
    /***
     * Modifica la fecha de nacimiento de un profesor
     * @param id
     * @param fecha
     * @return
     */
    public static boolean modificarFechaNacimiento(int id, String fecha) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Profesores SET FechaNacimiento = '%s' WHERE id = %d", fecha, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar los apellidos del profesor, comprueba que existan el profesor y la tabla Profesores.");
		}
		return actualizado;
	}

    public static boolean modificarAntiguedad(int id, int antiguedad) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Profesores SET Antiguedad = '%d' WHERE id = %d", antiguedad, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar los apellidos del profesor, comprueba que existan el profesor y la tabla Profesores.");
		}
		return actualizado;
	}
    
	/***
	 * Borra un alumno de la tabla alumnado
	 * @param id
	 * @return
	 */
	public static boolean borrar(int id) {
		boolean borrado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			borrado = statement.executeUpdate(String.format("DELETE FROM Profesores WHERE id = %d", id)) > 0 ? true : false;
		} catch (SQLException e) {
			System.err.println("No se ha podido borrar el profesor, comprueba que existan el profesor y la tabla Profesores");
		}
		return borrado;
	}


}
