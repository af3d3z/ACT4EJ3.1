package dal;

import java.sql.Connection;
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
	 * @return
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
	
	
}
