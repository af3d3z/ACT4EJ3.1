package dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ent.Matricula;

public class GestionMatriculas {
	public boolean insertar(Matricula matricula) {
		boolean insertado = false;
		GestionDB gest = new GestionDB();
		Connection conn = null;
		
		conn = gest.connect();
		try {
			Statement statement = conn.createStatement();
			insertado = statement.execute(
					String.format(
						"INSERT INTO Matricula VALUES (%d, %d, %d, %s, %d)",
						matricula.getId(), matricula.getIdProfesor(), matricula.getIdAlumno(), matricula.getAsignatura(), matricula.getCurso()
					));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}
}
