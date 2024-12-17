package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import dal.GestionAlumnado;
import dal.GestionDB;
import dal.GestionMatriculas;
import dal.GestionProfesorado;
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
			sc.nextLine();
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
			sc.nextLine();
		}while(opcion <= 0 && opcion > 4);
		
		return opcion;
	}
	
	static int menuModificarAlumno(Scanner sc) {
		int opcion = 0;
		do {
			System.out.println("¿Qué quieres modificar?");
			System.out.println("1. Nombre.");
			System.out.println("2. Apellidos.");
			System.out.println("3. Fecha de nacimiento.");
			opcion = sc.nextInt();
			sc.nextLine();
		}while(opcion <= 0 && opcion > 3);
		
		return opcion;
	}
	
	static int menuModificarProfesor(Scanner sc) {
		int opcion = 0;
		do {
			System.out.println("¿Qué quieres modificar?");
			System.out.println("1. Nombre.");
			System.out.println("2. Apellidos.");
			System.out.println("3. Fecha de nacimiento.");
			System.out.println("4. Antiguedad");
			opcion = sc.nextInt();
			sc.nextLine();
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
			sc.nextLine();
			switch(opcion) {
				// OPCIONES PROFESORADO
				case 1 ->{
					int opcionMenu = menuOperaciones(sc, "Profesorado");
					switch(opcionMenu) {
						// Crear tabla
						case 1 -> {
							int affectedRows = db.crearTablaProfesores();
							System.out.println("Filas afectadas: " + affectedRows);
						}
						// listar profesores
						case 2 -> {
							ArrayList<Profesor> profesores = GestionProfesorado.listadoProfesores();
							for(Profesor prof: profesores) {
								System.out.println("ID: " + prof.getId());
								System.out.println("Nombre: " + prof.getNombre());
								System.out.println("Apellidos: " + prof.getApellidos());
								System.out.println("Fecha de nacimiento: " + prof.getFechaNacimiento());
								System.out.println("Antiguedad: " + prof.getAntiguedad());
								System.out.println("--------------------------");
							}
						}
						// Consultar uno en específico
						case 3 -> {
							try {
								System.out.println("Introduce el id del profesor a mostrar: ");
								int id = sc.nextInt();
								sc.nextLine();
								Profesor profesor = GestionProfesorado.conseguirProfesor(id);
								if (profesor != null) {
									System.out.println("ID: " + profesor.getId());
									System.out.println("Nombre: " + profesor.getNombre());
									System.out.println("Apellidos: " + profesor.getApellidos());
									System.out.println("Fecha de nacimiento: " + profesor.getFechaNacimiento());
									System.out.println("Antiguedad: " + profesor.getAntiguedad());
								}else {
									System.err.println("No se encuentra el profesor.");
								}
							}catch(IllegalArgumentException e) {
								System.out.println("Id no válido. Inténtalo otra vez.");
							}
						}
						// insertar un profesor
						case 4 -> {
							boolean insertado;
							String nombre;
							String apellidos;
							String fechaNac;
							int antiguedad;
							try {
								System.out.println("Introduzca el nombre: ");
								nombre = sc.nextLine();
								System.out.println("Introduzca los apellidos: ");
								apellidos = sc.nextLine();
								System.out.println("Introduce la fecha de nacimiento (yyyy-mm-dd): ");
								fechaNac = sc.nextLine();
								Date fecha = new SimpleDateFormat("yyyy-mm-dd").parse(fechaNac);
								System.out.println("Introduce los años de antiguedad:");
								antiguedad = sc.nextInt();
								sc.nextLine();
								// insertamos el profesor
								insertado = GestionProfesorado.insertar(new Profesor(nombre, apellidos, fecha, antiguedad));
								if (insertado) {
									System.out.println("Se ha añadido el nuevo profesor correctamente.");
								}
							}catch(IllegalArgumentException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							}catch(ParseException p) {
								System.err.println("Formato de fecha incorrecto, inténtelo de nuevo.");
							}
						}
						// modificar los atributos de un profesor
						case 5 -> {
							try {
								int id;
								System.out.println("Introduce el id del profesor a modificar:");
								id = sc.nextInt();
								sc.nextLine();
								if (id > 0) {
									switch(menuModificarProfesor(sc)) {
										case 1 -> {
											String nombre = "";
											boolean modificado = false;
											System.out.println("Introduce el nombre del profesor:");
											nombre = sc.nextLine();
											modificado = GestionProfesorado.modificarNombre(id, nombre);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
										case 2 -> {
											boolean modificado = false;
											System.out.println("Introduce los apellidos del profesor:");
											String apellidos = sc.nextLine();
											modificado = GestionProfesorado.modificarApellidos(id, apellidos);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
										case 3 -> {
											boolean modificado = false;
											System.out.println("Introduce la fecha de nacimiento (yyyy-mm-dd):");
											String fecha = sc.nextLine();
											modificado = GestionProfesorado.modificarFechaNacimiento(id, fecha);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
										case 4 -> {
											boolean modificado = false;
											int antiguedad = 0;
											try {
												System.out.println("Introduce los años de antiguedad: ");
												antiguedad = sc.nextInt();
												sc.nextLine();
												modificado = GestionProfesorado.modificarAntiguedad(id, antiguedad);
												if (modificado) {
													System.out.println("Se ha modificado correctamente.");
												}
											}catch(IllegalArgumentException E) {
												System.err.println("Datos introducidos de forma erronea, inténtelo otra vez.");
											}
										}
									}
								}	
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							}
						}
						case 6 -> {
							try {
								int id;
								boolean borrado = false;
								String confirmacion = "";
								System.out.println("Introduce el id del profesor a borrar: ");
								id = sc.nextInt();
								sc.nextLine();
								System.out.println("¿Seguro que quieres borrar al profesor con id " + id + "?");
								confirmacion = sc.nextLine();
								if(confirmacion.toLowerCase().charAt(0) == 's') {
									borrado = GestionProfesorado.borrar(id);
									if (borrado) {
										System.out.println("Se ha borrado correctamente");
									}
								}
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo de nuevo.");
							}
						}
					}
				}
				case 2 -> {
					// OPCIONES ALUMNADO
					int opcionMenu = menuOperaciones(sc, "Alumnado");
					switch(opcionMenu) {
						// crear tabla
						case 1 -> {
							int affectedRows = db.crearTablaAlumnos();
							System.out.println("Filas afectadas: " + affectedRows);
						}
						// Listar registros
						case 2 -> {
							ArrayList<Alumno> alumnos = GestionAlumnado.listadoCompleto();
							for(Alumno alum : alumnos) {
								System.out.println("ID: " + alum.getId());
								System.out.println("Nombre: " + alum.getNombre());
								System.out.println("Apellidos: " + alum.getApellidos());
								System.out.println("Fecha de nacimiento: " + alum.getFechaNacimiento());
								System.out.println("--------------------------");
							}
						}
						// Consultar uno en específico
						case 3 -> {
							try {
								System.out.println("Introduce el id del alumno a mostrar: ");
								int id = sc.nextInt();
								sc.nextLine();
								Alumno alumno = GestionAlumnado.conseguirAlumno(id);
								if (alumno != null) {
									System.out.println("ID: " + alumno.getId());
									System.out.println("Nombre: " + alumno.getNombre());
									System.out.println("Apellidos: " + alumno.getApellidos());
									System.out.println("Fecha de nacimiento: " + alumno.getFechaNacimiento());
								}else {
									System.err.println("No se encuentra el alumno.");
								}
							}catch(IllegalArgumentException e) {
								System.out.println("Id no válido. Inténtalo otra vez.");
							}
						}
						// insertar un alumno
						case 4 -> {
							boolean insertado;
							String nombre;
							String apellidos;
							String fechaNac;
							try {
								System.out.println("Introduzca el nombre: ");
								nombre = sc.nextLine();
								System.out.println("Introduzca los apellidos: ");
								apellidos = sc.nextLine();
								System.out.println("Introduce la fecha de nacimiento (yyyy-mm-dd): ");
								fechaNac = sc.nextLine();
								Date fecha = new SimpleDateFormat("yyyy-mm-dd").parse(fechaNac);
								// insertamos el alumno
								insertado = GestionAlumnado.insertar(new Alumno(nombre, apellidos, fecha));
								if (insertado) {
									System.out.println("Se ha añadido el nuevo alumno correctamente.");
								}
							}catch(IllegalArgumentException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							}catch(ParseException p) {
								System.err.println("Formato de fecha incorrecto, inténtelo de nuevo.");
							}
						}
						// modifica un alumno
						case 5 -> {
							try {
								int id;
								System.out.println("Introduce el id del alumno a modificar:");
								id = sc.nextInt();
								sc.nextLine();
								if (id > 0) {
									switch(menuModificarAlumno(sc)) {
										case 1 -> {
											String nombre = "";
											boolean modificado = false;
											System.out.println("Introduce el nombre del alumno:");
											nombre = sc.nextLine();
											modificado = GestionAlumnado.modificarNombre(id, nombre);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
										case 2 -> {
											boolean modificado = false;
											System.out.println("Introduce los apellidos del alumno:");
											String apellidos = sc.nextLine();
											modificado = GestionAlumnado.modificarApellidos(id, apellidos);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
										case 3 -> {
											boolean modificado = false;
											System.out.println("Introduce la fecha de nacimiento (yyyy-mm-dd):");
											String fecha = sc.nextLine();
											modificado = GestionAlumnado.modificarFechaNacimiento(id, fecha);
											if(modificado) {
												System.out.println("Se ha modificado correctamente.");
											}
										}
									}
								}	
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo otra vez.");
							}
						}
						// borrar un alumno
						case 6 -> {
							try {
								int id;
								boolean borrado = false;
								String confirmacion = "";
								System.out.println("Introduce el id del alumno a borrar: ");
								id = sc.nextInt();
								sc.nextLine();
								System.out.println("¿Seguro que quieres borrar al alumno con id " + id + "?");
								confirmacion = sc.nextLine();
								if(confirmacion.toLowerCase().charAt(0) == 's') {
									borrado = GestionAlumnado.borrar(id);
									if (borrado) {
										System.out.println("Se ha borrado correctamente");
									}
								}
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo de nuevo.");
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
								Matricula matricula = GestionMatriculas.conseguirMatricula(id);
								if (matricula != null) {
									System.out.println("ID: " + matricula.getId());
									System.out.println("Id Profesor: " + matricula.getIdProfesor());
									System.out.println("Id Alumno: " + matricula.getIdAlumno());
									System.out.println("Asignatura: " + matricula.getAsignatura());
									System.out.println("Curso: " + matricula.getCurso());
								}else {
									System.out.println("No se ha encontrado la matrícula.");
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
						// modifica una matrícula
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
						// borrar una matrícula
						case 6 -> {
							try {
								int id;
								boolean borrado = false;
								String confirmacion = "";
								System.out.println("Introduce el id de la matrícula a borrar: ");
								id = sc.nextInt();
								sc.nextLine();
								System.out.println("¿Seguro que quieres borrar la matrícula con id " + id + "?");
								confirmacion = sc.nextLine();
								if(confirmacion.toLowerCase().charAt(0) == 's') {
									borrado = GestionMatriculas.borrar(id);
									if (borrado) {
										System.out.println("Se ha borrado correctamente");
									}
								}
							}catch(InputMismatchException e) {
								System.err.println("Ha introducido un campo de forma incorrecta, inténtelo de nuevo.");
							}
						}
					}
				}
				case 4 -> {
					String confirmacion = "";
					System.out.println("¿Quieres crearlas todas? (Sí/No)");
					confirmacion = sc.nextLine();
					
					if(confirmacion.toLowerCase().charAt(0) == 's') {
						db.borrarTablaMatriculas();
						int affectedRowsAlumnos = db.crearTablaAlumnos();
						int affectedRowsProfesores = db.crearTablaProfesores();
						
						if (affectedRowsAlumnos > 0 && affectedRowsProfesores > 0 && (db.crearTablaMatricula())> 0) {
							System.out.println("Se han creado correctamente todas las tablas.");
						}else {
							System.out.println("No se han podido crear correctamente todas las tablas.");
						}
					}else {
						int indiceTabla = 0;
						System.out.println("¿Cuál quieres crear?");
						System.out.println("1. Profesores.");
						System.out.println("2. Alumnado");
						System.out.println("3. Matriculas");
						indiceTabla = sc.nextInt();
						sc.nextLine();
						
						switch(indiceTabla) {
							case 1 -> {
								int filasAfectadas = db.crearTablaProfesores();
								if (filasAfectadas > 0) {
									System.out.println("Filas afectadas: " + filasAfectadas);
								}
							}
							case 2 -> {
								int filasAfectadas = db.crearTablaAlumnos();
								if (filasAfectadas > 0) {
									System.out.println("Filas afectadas: " + filasAfectadas);
								}
							}
							case 3 -> {
								int filasAfectadas = db.crearTablaMatricula();
								if (filasAfectadas > 0) {
									System.out.println("Filas afectadas: " + filasAfectadas);
								}
							}
						}
					}
				}
				case 5 -> {
					String confirmacion = "";
					System.out.println("¿Quieres borrarlas todas? (Sí/No)");
					confirmacion = sc.nextLine();
					
					if(confirmacion.toLowerCase().charAt(0) == 's') {
						db.borrarTablaMatriculas();
						db.borrarTablaProfesores();
						db.borrarTablaAlumnos();
					}else {
						int indiceTabla = 0;
						System.out.println("¿Cuál quieres borrar?");
						System.out.println("1. Profesores.");
						System.out.println("2. Alumnado");
						System.out.println("3. Matriculas");
						indiceTabla = sc.nextInt();
						sc.nextLine();
						
						switch(indiceTabla) {
							case 1 -> {
								if(db.borrarTablaProfesores()) {
									System.out.println("Se ha borrado correctamente.");
								}
							}
							case 2 -> {
								if(db.borrarTablaAlumnos()) {
									System.out.println("Se ha borrado correctamente.");
								}
							}
							case 3 -> {
								if(db.borrarTablaMatriculas()) {
									System.out.println("Se ha borrado correctamente.");
								}
							}
						}
					}	
				}
			}
		}
	}
}
