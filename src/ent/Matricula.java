package ent;

public class Matricula {
	private int id;
	private int idProfesor;
	private int idAlumno;
	private String asignatura;
	private int curso;
	
	public int getCurso() {
		return curso;
	}
	
	public void setCurso(int curso) {
		this.curso = curso;
	}
	
	public String getAsignatura() {
		return asignatura;
	}
	
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
	public int getIdAlumno() {
		return idAlumno;
	}
	
	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	public int getIdProfesor() {
		return idProfesor;
	}
	
	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}
	
	public int getId() {
		return id;
	}
	
	/***
	 * Constructor sin parámetros
	 */
	public Matricula() {}
	
	/***
	 * Constructor con todos los parámetros
	 * @param id
	 * @param idProfesor
	 * @param idAlumno
	 * @param asignatura
	 * @param curso
	 */
	public Matricula(int id, int idProfesor, int idAlumno, String asignatura, int curso) {
		this.id = id;
		this.idProfesor = idProfesor;
		this.idAlumno = idAlumno;
		this.asignatura = asignatura;
		this.curso = curso;
	}
}
