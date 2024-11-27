package ent;

import java.sql.Date;

public class Profesor {
	/***
	 * Id profesor
	 */
	private int id;
	/***
	 * Nombre del profesor
	 */
	private String nombre;
	/***
	 * Apellidos del profesor
	 */
	private String apellidos;
	/***
	 * Fecha de nacimiento del profesor
	 */
	private Date fechaNacimiento;
	/***
	 * Antiguedad del profesor en el centro
	 */
	private int antiguedad;
	
	public int getAntiguedad() {
		return antiguedad;
	}
	
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	
	/***
	 * Constructor vacío
	 */
	public Profesor() {}
	
	/***
	 * Constructor con todos los parámetros
	 * @param id
	 * @param nombre
	 * @param apellido
	 * @param fechaNacimiento
	 * @param antiguedad
	 */
	public Profesor(int id, String nombre, String apellido, Date fechaNacimiento, int antiguedad) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.antiguedad = antiguedad;
	}
	
}
