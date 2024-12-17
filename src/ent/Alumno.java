package ent;

import java.util.Date;

public class Alumno {
	private int id;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	
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
	 * Constructor sin parámetros
	 */
	public Alumno() {}
	
	/***
	 * Constructor con todos los parámetros menos el id para los inserts
	 * @param nombre
	 * @param apellidos
	 * @param fechaNacimiento
	 */
	public Alumno(String nombre, String apellidos, Date fechaNacimiento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/***
	 * Constructor con todos los parámetros
	 * @param id
	 * @param nombre
	 * @param apellidos
	 * @param fechaNacimiento
	 */
	public Alumno(int id, String nombre, String apellidos, Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}
}
