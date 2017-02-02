package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;
import persistenciaInterfaces.UsuarioDao;

public class UsuarioDaoMySql implements UsuarioDao {

	private Connection conexion;
	
	/**
	 * Método que comprueba si se ha podido establecer una conexión con los driver de MySql.
	 */
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver cargado");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no cargado");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que conecta con la base de datos de MySql. 
	 * @return Devuelve verdadero o falso en funcion de si se ha podido o no conectar.
	 */
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/prueba";
		String usuario = "root";
		String password = "telefonica";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Método que cierra la conexion con la base de datos de MySql. 
	 * @return Devuelve verdadero o falso en funcion de si no se ha podido o no conectar.
	 */
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Método que incorpora los datos de un usuario a la tabla usuarios en la base de datos prueba. 
	 * @return Devuelve como true o false si se han podido introducir los datos correctamente en la tabla.
	 */
	@Override
	public boolean alta(Usuario u) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		String query = "insert into usuarios (NOMBRE,PASSWORD,EDAD) values (?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1,u.getNombre());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getEdad());
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0){
				alta = false;
			}else{
				alta = true;
			}
		} catch (SQLException e) {
			System.out.println("alta -> Error al introducir el usuario: "+u.getNombre());
			alta=false;
			e.printStackTrace();
		}finally{
			cerrarConexion();
		}
	return alta;
}

	/**
	 * Método que elimina los datos de un usuario de la tabla usuarios en la base de datos prueba a traves del "ID". 
	 * @return Devuelve como true o false si se han podido eliminar los datos correctamente en la tabla.
	 */
	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		boolean borrado = true;
		String query = "delete from usuarios where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1,id);
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0){
				borrado = false;
			}else{
				borrado = true;
			}
		} catch (SQLException e) {
			System.out.println("baja -> No se ha podido dar de baja al usuario con la id: "+id);
			e.printStackTrace();
			borrado= false;
		}finally {
			cerrarConexion();
		}
		return borrado;
	}

	/**
	 * Método que modifica los datos de un usuario de la tabla usuarios en la base de datos prueba a traves de un "ID". 
	 * @return Devuelve como true o false si se han podido modificar los datos correctamente en la tabla.
	 */
	@Override
	public boolean modificar(Usuario u) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update usuarios set NOMBRE=?, PASSWORD=?, EDAD=? where ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, u.getNombre());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getEdad());
			ps.setInt(4, u.getId());
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0){
				modificado = false;
			}else{
				modificado = true;
			}
		} catch (SQLException e) {
			System.out.println("modificar -> error al modificar al usuario: "+u.getNombre());
			e.printStackTrace();
			modificado= false;
		}finally {
			cerrarConexion();
		}
		return modificado;
	}
	
	/**
	 * Método que muestra los datos de todos los usuarios de la tabla usuarios en la base de datos prueba. 
	 * @return Devuelve los datos de todos los usuarios incluidos en la tabla usuarios.
	 */
	@Override
	public List<Usuario> listar() {
		if(!abrirConexion()){
			return null;
		}
		List<Usuario> listaUsuarios = new ArrayList<>();
		String query = "select ID,NOMBRE,PASSWORD,EDAD from usuarios";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setPassword(rs.getString(3));
				usuario.setEdad(rs.getInt(4));
				listaUsuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los datos de los usuarios ");
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return listaUsuarios;
	}
		
	/**
	 * Método que muestra los datos de un usuario en concreto de la tabla usuarios en la base de datos prueba a traves de su "ID". 
	 * @return Devuelve los datos del usuario incluidos en la tabla usuarios.
	 */
	@Override
	public Usuario buscar(int id) {
		if(!abrirConexion()){
			return null;
		}
		Usuario usuario = null;
		String query = "select ID,NOMBRE,PASSWORD,EDAD from usuarios where id=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				usuario = new Usuario();
				usuario.setId(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setPassword(rs.getString(3));
				usuario.setEdad(rs.getInt(4));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener al usuario con la id "+id);
			e.printStackTrace();
			usuario = null;
		}finally {
			cerrarConexion();
		}
		return usuario;
	}
	
	/**
	 * Método que muestra los datos de todos los usuarios de la tabla usuarios ordenados en funcion del nombre
	 * de forma ascendente en la base de datos prueba. 
	 * @return Devuelve los datos de todos los usuarios incluidos en la tabla usuarios ordenados de forma ascendente
	 * por el nombre del usuario.
	 */
	public List<Usuario> listarPorNombre() {
		if(!abrirConexion()){
			return null;
		}
		List<Usuario> listaUsuariosNombre = new ArrayList<>();
		String query = "select ID,NOMBRE,PASSWORD,EDAD from usuarios order by NOMBRE";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt(1));
				usuario.setNombre(rs.getString(2));
				usuario.setPassword(rs.getString(3));
				usuario.setEdad(rs.getInt(4));
				listaUsuariosNombre.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los datos de los usuarios ");
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return listaUsuariosNombre;
	}
	
}
