package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dal.GestionDB;
import dal.GestionMatriculas;
import ent.Alumno;
import ent.Matricula;
import ent.Profesor;

public class Main {
	static void menu() {
		System.out.println("MENU:");
		System.out.println("1. Profesorado.");
		System.out.println("2. Alumnado.");
		System.out.println("3. Matriculas.");
		System.out.println("4. Crear tablas");
		System.out.println("5. Borrar tablas.");
		System.out.println("0. Salir.");
	}
	
	static int menuOperaciones(Scanner sc, String entidad) {
		int opcion = 0;
		do {
			System.out.println(entidad.toUpperCase() + ":");
			System.out.println("1. Crear tabla.");
			System.out.println("2. Listar todo.");
			System.out.println("3. Ver un registro en concreto.");
			System.out.println("4. Insertar un registro.");
			System.out.println("5. Modificar un registro.");
			System.out.println("6. Borrar un registro.");
			opcion = sc.nextInt();
		}while(opcion <= 0 && opcion > 5);
		
		return opcion;
	}
	
	static int menuModificarMatricula(Scanner sc) {
		int opcion = 0;
		do {
			System.out.println("¿Qué quieres modificar?");
			System.out.println("1. Id Profesor.");
			System.out.println("2. Id Alumno.");
			System.out.println("3. Nombre de la asignatura.");
			System.out.println("4. Curso.");
			opcion = sc.nextInt();
		}while(opcion <= 0 && opcion > 4);
		
		return opcion;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		GestionDB db = new GestionDB();
		int opcion = 10;
		while (opcion != 0) {
			menu();
			opcion = sc.nextInt();
			switch(opcion) {
				case 1 ->{
					int opcionMenu = menuOperaciones(sc, "Profesorado");
					switch(opcionMenu) {
						case 1 -> {
							int affectedRows = db.crearTablaProfesores();
						}
						case 2 -> {
							ArrayList<Profesor> profesores = db.listadoProfesores();
							for(Profesor prof: profesores) {
								System.out.println("ID: " + prof.getId());
								System.out.println("Nombre: " + prof.getNombre());
								System.out.println("Apellidos: " + prof.getApellidos());
								System.out.println("Fecha de nacimiento: " + prof.getFechaNacimiento());
								System.out.println("Antiguedad: " + prof.getAntiguedad());
								System.out.println("--------------------------");
							}
						}
					}
				}
				case 2 -> {
					int opcionMenu = menuOperaciones(sc, "Alumnado");
					switch(opcionMenu) {
						case 1 -> {
							int affectedRows = db.crearTablaAlumnos();
							System.out.println("Filas afectadas: " + affectedRows);
						}
						case 2 -> {
							ArrayList<Alumno> alumnos = db.listadoAlumnos();
							for(Alumno alum : alumnos) {
								System.out.println("ID: " + alum.getId());
								System.out.println("Nombre: " + alum.getNombre());
								System.out.println("Apellidos: " + alum.getApellidos());
								System.out.println("Fecha de nacimiento: " + alum.getFechaNacimiento());
								System.out.println("--------------------------");
							}
						}
					}
				} 
				case 3 -> {
					// OPCIONES MATRICULAS
					int opcionMenu = menuOperaciones(sc, "Matricula");
					switch(opcionMenu) {
						// CREAR TABLA
						case 1 -> {
							int affectedRows = db.crearTablaMatricula();
							if (affectedRows == -1) {
								System.out.println("Se deben de crear previamente las tablas Profesores y Alumnado.");
							}else {
								System.out.println("Filas afectadas: " + affectedRows);
							}
						}
						// Listar registros
						case 2 -> {
							ArrayList<Matricula> matriculas = GestionMatriculas.listadoCompleto();
							for(Matricula matr: matriculas) {
								System.out.println("ID: " + matr.getId());
								System.out.println("Id Profesor: " + matr.getIdProfesor());
								System.out.println("Id Alumno: " + matr.getIdAlumno());
								System.out.println("Asignatura: " + matr.getAsignatura());
								System.out.println("Curso: " + matr.getAsignatura());
								System.out.println("--------------------------");
							}
						}
						// Consultar uno en específico
						case 3 -> {
							try {
								System.out.println("Introduzca el id de la matrícula a mostrar:");
								int id = sc.nextInt();
								sc.nextLine();
								Matricula matricula = GestionMatriculas.verMatricula(id);
								if (matricula != null) {
									System.out.println("ID: " + matricula.getId());
									System.out.println("Id Profesor: " + matricula.getIdProfesor());
									System.out.println("Id Alumno: " + matricula.getIdAlumno());
									System.out.println("Asignatura: " + matricula.getAsignatura());
									System.out.println("Curso: " + matricula.getCurso());
								}
							}catch(IllegalArgumentException e) {
								System.err.println("Id no válido. Intentalo otra vez.");
							}
						}
						// Insertar una nueva matricula
						case 4 -> {
							/**
							 * Por mi salud mental y por el bien de que este proyecto vea la luz del día voy a suponer que el usuario introduce los datos de forma correcta
							 */
							boolean insertado;
							int idProfesorado;
							int idAlumnado;
							String asignatura;
							int curso;
							try {
								System.out.println("Introduzca el id del profesor: ");
								idProfesorado = sc.nextInt();
								sc.nextLine();
								System.out.println("Introduzca el id del alumno: ");
								idAlumnado = sc.nextInt();
								sc.nextLine();
								System.out.println("Introduzca el nombre de la asignatura: ");
								asignatura = sc.nextLine();
								System.out.println("Introduzca el curso que está cursando el alumno: ");
								curso = sc.nextInt();
								sc.nextLine();
								// insertamos la matríula
								insertado = GestionMatriculas.insertar(new Matricula(idProfesorado, idAlumnado, asignatura, curso));
								if (insertado) {
									System.out.println("Se ha añadido la nueva matrícula correctamente.");
								}
							}catch(IllegalArgumentException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							}
						}
						case 5 -> {
							try {
								int id;
								System.out.println("Introduce el id de la matrícula a modificar:");
								id = sc.nextInt();
								sc.nextLine();
								if (id > 0) {
									switch(menuModificarMatricula(sc)) {
										case 1 -> {
											System.out.println("Introduce el id del profesor:");
											int idProfesor = sc.nextInt();
											sc.nextLine();
											GestionMatriculas.modificarIdProfesor(id, idProfesor);
										}
										case 2 -> {
											System.out.println("Introduce el id del alumno:");
											int idAlumno = sc.nextInt();
											sc.nextLine();
											GestionMatriculas.modificarIdAlumno(id, idAlumno);
										}
										case 3 -> {
											System.out.println("Introduce el nombre de la asignatura:");
											String asignatura = sc.nextLine();
											GestionMatriculas.modificarAsignatura(id, asignatura);
										}
										case 4 -> {
											System.out.println("Introduce el curso:");
											int curso = sc.nextInt();
											sc.nextLine();
											GestionMatriculas.modificarCurso(id, curso);
										}
									}
								}	
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							} 
							
						}
					}
				}
				case 4 -> {
					db.borrarTablaMatriculas();
					int affectedRowsAlumnos = db.crearTablaAlumnos();
					int affectedRowsProfesores = db.crearTablaProfesores();
					
					if (affectedRowsAlumnos > 0 && affectedRowsProfesores > 0 && (db.crearTablaMatricula())> 0) {
						System.out.println("Se han creado correctamente todas las tablas.");
					}else {
						System.out.println("No se han podido crear correctamente todas las tablas.");
					}
				}
				case 5 -> {
					db.borrarTablaMatriculas();
					db.borrarTablaProfesores();
					db.borrarTablaAlumnos();
				}
			}
		}
	}
}
