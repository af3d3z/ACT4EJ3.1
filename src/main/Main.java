package main;

import java.util.Scanner;

import dal.GestionDB;

public class Main {
	static void menu() {
		System.out.println("MENU:");
		System.out.println("1. Crear tablas.");
		System.out.println("2. Profesorado.");
		System.out.println("3. Alumnado.");
		System.out.println("4. Matriculas.");
		System.out.println("0. Salir.");
	}
	
	static int menuOperaciones(Scanner sc, String entidad) {
		int opcion = 0;
		do {
			System.out.println(entidad.toUpperCase() + ":");
			System.out.println("1. Listar todo.");
			System.out.println("2. Ver un registro en concreto.");
			System.out.println("3. Insertar un registro.");
			System.out.println("4. Modificar un registro.");
			System.out.println("5. Borrar un registro.");
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
				case 1 -> {
					int affectedRows = db.createDatabase();
					if (affectedRows == 600) {
						System.out.println("Se han creado las tablas correctamente.");
					}else {
						System.out.println("No se han podido crear las tablas.");
					}
				}
				case 2 ->{
					int opcionMenu = menuOperaciones(sc, "Profesorado");
					switch(opcionMenu) {
						case 1 -> {
							
						}
					}
				}
			}
		}
	}
}
