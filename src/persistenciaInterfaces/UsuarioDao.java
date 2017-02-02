package persistenciaInterfaces;

import java.util.List;
import entidades.Usuario;


public interface UsuarioDao {
	
	boolean alta(Usuario u);
	boolean baja(int id);
	boolean modificar(Usuario u);
	List<Usuario> listar();
	Usuario buscar(int id);
	List<Usuario> listarPorNombre();
	
}
