package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ent.Alumno;

public class GestionAlumnado {
	/***
	 * Inserta un alumno en la base de datos
	 * @param alumno
	 * @return booleano que indica si se ha podido insertar o no
	 */
	public static boolean insertar(Alumno alumno) {
		boolean insertado = false;
		Connection conn = GestionDB.connect();
		String sql = String.format("INSERT INTO Alumnado (Nombre, Apellidos, FechaNacimiento) VALUES (%s, %s, %s)", 
				alumno.getNombre(), alumno.getApellidos(), alumno.getFechaNacimiento());
		
		try {
			Statement statement = conn.createStatement();
			int filasInsertadas = statement.executeUpdate(sql);
			if(filasInsertadas > 0) {
				insertado = true;
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido insertar el alumno, comprueba que se ha creado la tabla y que los campos introducidos son correctos.");
		}
		return insertado;
	}
	
	/***
	 * Devuelve un alumno en base a su id
	 * @param id
	 * @return
	 */
	public static Alumno conseguirAlumno(int id) {
		Connection conn = GestionDB.connect();
		Alumno alumno = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(String.format("SELECT * FROM Alumnado WHERE id = %d", id));
			if(res.next()) {
				alumno = new Alumno(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento"));
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido listar el alumno, comprueba que el alumno con el id: " + id + " existe y que existe la tabla Alumnado.");
		}
		
		return alumno;
	}
	
	/***
	 * Devuelve un listado de alumnos
	 * @return ArrayList de alumnos
	 */
	public static ArrayList<Alumno> listadoCompleto() {
		Connection conn = GestionDB.connect();
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Alumnado");
			ResultSet res = statement.executeQuery();
			while(res.next()) {
				alumnos.add(new Alumno(res.getInt("id"), res.getString("Nombre"), res.getString("Apellidos"), res.getDate("FechaNacimiento")));
			}
		} catch (SQLException e) {
    		System.err.println("No se han podido listar los alumnos por el siguiente motivo: " + e.getMessage());
		}
		return alumnos;
	}
	
	/***
	 * Modifica el nombre del alumno mediante el id
	 * @param id
	 * @param nombre
	 * @return true si se ha podido modificar satisfactoriamente
	 */
	public static boolean modificarNombre(int id, String nombre) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Alumnado SET Nombre = %s WHERE id = %d", nombre, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar el nombre del alumno, comprueba que existan el alumno y la tabla Alumnado.");
		}
		return actualizado;
	}
	/***
	 * Modifica el apellido de un alumno mediante su id
	 * @param id
	 * @param apellidos
	 * @return true si se ha podido modificar satisfactoriamente
	 */
	public static boolean modificarApellidos(int id, String apellidos) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Alumnado SET Apellidos = %s WHERE id = %d", apellidos, id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar los apellidos del alumno, comprueba que existan el alumno y la tabla Alumnado.");
		}
		return actualizado;
	}
	
	/***
	 * Modifica la fecha de nacimiento de un alumno
	 * @param id
	 * @param fechaNacimiento
	 * @return true si se ha podido modificar satisfactoriamente
	 */
	public static boolean modificarFechaNacimiento(int id, Date fechaNacimiento) {
		boolean actualizado = false;
		Connection conn = GestionDB.connect();
		
		try {
			Statement statement = conn.createStatement();
			actualizado = statement.executeUpdate(String.format("UPDATE Alumnado SET FechaNacimiento = '%s' WHERE id = %d", fechaNacimiento.toString(), id)) > 0 ? true: false;
		} catch (SQLException e) {
			System.err.println("No se ha podido actualizar los apellidos del alumno, comprueba que existan el alumno y la tabla Alumnado.");
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
			borrado = statement.executeUpdate(String.format("DELETE FROM Alumnado WHERE id = %d", id)) > 0 ? true : false;
		} catch (SQLException e) {
			System.err.println("No se ha podido borrar el alumno, comprueba que existan el alumno y la tabla Alumnado");
		}
		return borrado;
	}
}
