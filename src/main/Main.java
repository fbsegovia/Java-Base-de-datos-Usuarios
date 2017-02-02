package main;

import java.util.List;
import java.util.Scanner;
import entidades.Usuario;
import negocio.GestorUsuarios;

public class Main {
	
	public static void main(String[] args) {
			Main main = new Main();
			int opcion = 0;
			do{
				opcion = main.mostrarMenu(); 
				switch (opcion) {
				case 1://Dar de Alta a un Usuario
					main.alta();
					break;
				case 2://Dar de a un Usuario
					main.baja();
					break;
				case 3://Modificar los datos de un Usuario
					main.modificar();
					break;
				case 4://Mostrar los datos de todos los Usuarios
					main.listar();
					break;
				case 5://Mostrar los datos de un Usuario concreto
					main.buscar();
					break;
				case 6://Mostrar los datos de todos los Usuarios ordenados alfabeticamente por el nombre
					main.listarPorNombre();
					break;
				case 7://Salir Programa
					System.out.println("Fin del programa");
					break;
				default:
					System.out.println("El sistema solo admite valores de 1 a 5");
					System.out.println("Por favor introduzca un valor válido en el sistema");
					break;
				}
			}while(main.continuar(opcion));

		}
		
	/**
	 * Método que muestra al usuario las opciones diponibles.
	 * @return Devuelve la opcion tomada por el ususario.
	 */
	private int mostrarMenu() {
		System.out.println("Seleccione la opción que desee realizar");
		System.out.println("1. Dar de alta a un Usuario en la base de datos.");
		System.out.println("2. Dar de baja a un Usuario en la base de datos.");
		System.out.println("3. Modificar los datos de un Usuario en la base de datos.");
		System.out.println("4. Listar los datos de todos los Usuarios.");
		System.out.println("5. Mostrar los datos de un Usuario en concreto.");
		System.out.println("6. Listar los datos de todos los Usuarios ordenados alfabeticamente por nombre.");
		System.out.println("7. Salir de la aplicación.");
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		return opcion;
	}
	
	/**
	 * Método que pide datos al usuario para crear un usuario y añadirlos a la tabla Usuarios.
	 */
	private void alta() {
		Usuario usuario = new Usuario();
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Cual es el nombre del Usuario que desea introducir?");
		String nombre = sc.next();
		usuario.setNombre(nombre);
		System.out.println("¿Cual es la Contraseña del nuevo usuario?");
		String password = sc.next();
		usuario.setPassword(password);
		
		int edad = preguntarEdad();
		usuario.setEdad(edad);
		
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		boolean alta = gestorUsuarios.alta(usuario);
		if(alta){
			System.out.println("El Usuario se ha dado de alta");
		}else{
			System.out.println("El usuario no se ha dado de alta");
		}
		
	}
	
	/**
	 * Método que elimina un Usuario de la base de datos introduciendo su ID.
	 */
	private void baja(){
		Scanner sc = new Scanner(System.in);
		GestorUsuarios gu = new GestorUsuarios();
		System.out.println("¿Cual es la ID del usuario que desea eliminar?");
		int id = sc.nextInt();
		boolean baja = gu.baja(id);
		if(baja){
			System.out.println("El Usuario se ha dado de baja");
		}else{
			System.out.println("El Usuario NO se ha dado de baja");
		}
	}
	
	/**
	 * Método que modifica los datos de un Usuario de la base de datos introduciendo su ID.
	 */
	private void modificar(){
		Usuario usuario = new Usuario();
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Cual es el ID del Usuario que desea modificar?");
		int id = sc.nextInt();
		usuario.setId(id);
		System.out.println("¿Cual es el nuevo Nombre del Usuario?");
		String nombre = sc.next();
		usuario.setNombre(nombre);
		System.out.println("¿Cual es la nueva Password(o Contraseña) del Usuario?");
		String password = sc.next();
		usuario.setPassword(password);
		
		int edad = preguntarEdad();
		usuario.setEdad(edad);
		
			GestorUsuarios gu = new GestorUsuarios();
			boolean modificar = gu.modificar(usuario);
			if(modificar){
				System.out.println("El coche se ha modificado");
			}else{
				System.out.println("El coche NO se ha modificado");
			}
	}
	
	/**
	 * Método que lista todos los datos de los Usuario contenidos en la base de datos.
	 */
	private void listar(){
		GestorUsuarios gu = new GestorUsuarios();
		List<Usuario> listaUsuarios = gu.listar();
		System.out.println(listaUsuarios);
	}
	
	/**
	 * Método que muestra los datos de un Usuario introduciendo su ID.
	 */
	private void buscar(){
		Scanner sc = new Scanner(System.in);
		GestorUsuarios gu= new GestorUsuarios();
		System.out.println("¿Cual es la ID del Usuario que desea buscar?");
		int id = sc.nextInt();
		Usuario usuario = gu.buscar(id);
		System.out.println(usuario);
	}
	
	/**
	 * Método que lista todos los datos de los Usuario contenidos en la base de datos ordenados alfabeticamente por su nombre.
	 */
	private void listarPorNombre(){
		GestorUsuarios gu = new GestorUsuarios();
		List<Usuario> listaUsuariosNombre = gu.listarPorNombre();
		System.out.println(listaUsuariosNombre);
	}
	
	/**
	 * Método que permite salir del bucle del Do...While del menu principal.
	 * @param En función de la opción introducida por el usuario en el menu.
	 * @return Devuelde un valor booleano que permite o niega salir del bucle.
	 */
	private boolean continuar(int opcion) {
		if(opcion==7){
			return false;
		}else{
			return true;
		}
	}

	
	private int preguntarEdad(){
		int edad = 0;
		do{
			Scanner sc = new Scanner(System.in);
			System.out.println("¿Cual es la Edad del usuario?");
			edad = sc.nextInt();
			if(edad<18 || edad>67){
				System.out.println("El nuevo usuario no reune las condicones de edad necesarias para su alta");
				System.out.println("La edad del usuario debe de estar comprendida entre 18 y 67 años para poder ser dado de alt");
				System.out.println("No se ha podido dar de alta al usuario, por que se ha imcumplido un regla de negocio");
				System.out.println("Por favor intentelo de nuevo");
			}
		}while(edad<18 || edad>67);
		return edad;
		
	}

}
