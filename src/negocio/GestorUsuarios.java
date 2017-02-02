package negocio;

import java.util.List;
import entidades.Usuario;
import persistencia.UsuarioDaoMySql;
import persistenciaInterfaces.UsuarioDao;

public class GestorUsuarios {

	public boolean alta(Usuario u){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		boolean alta = usuarioDao.alta(u);
		return alta;
	}
	
	public boolean baja(int id){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		boolean baja = usuarioDao.baja(id);
		return baja;
	}
	
	public boolean modificar(Usuario u){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		boolean modificar = usuarioDao.modificar(u);
		return modificar;
	}
	
	public List<Usuario> listar(){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		List<Usuario> listaUsuarios = usuarioDao.listar();
		return listaUsuarios;
	}
	
	public Usuario buscar(int id){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		Usuario usuario = usuarioDao.buscar(id);
		return usuario;
	}
	
	public List<Usuario> listarPorNombre(){
		UsuarioDao usuarioDao = new UsuarioDaoMySql();
		List<Usuario> listaUsuariosNombre = usuarioDao.listarPorNombre();
		return listaUsuariosNombre;
	}
	
	
}
