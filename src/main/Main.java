package main;

import java.util.ArrayList;
import java.util.Scanner;

import dal.GestionDB;
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
						case 1 -> {
							int affectedRows = db.crearTablaMatricula();
							if (affectedRows == -1) {
								System.out.println("Se deben de crear previamente las tablas Profesores y Alumnado.");
							}else {
								System.out.println("Filas afectadas: " + affectedRows);
							}
						}
						case 2 -> {
							ArrayList<Matricula> matriculas = db.listadoMatriculas();
							for(Matricula matr: matriculas) {
								System.out.println("ID: " + matr.getId());
								System.out.println("Id Profesor: " + matr.getIdProfesor());
								System.out.println("Id Alumno: " + matr.getIdAlumno());
								System.out.println("Asignatura: " + matr.getAsignatura());
								System.out.println("Curso: " + matr.getAsignatura());
								System.out.println("--------------------------");
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
			}
		}
	}
}
