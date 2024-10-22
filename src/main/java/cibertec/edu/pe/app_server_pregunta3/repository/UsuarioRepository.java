package cibertec.edu.pe.app_server_pregunta3.repository;

import cibertec.edu.pe.app_server_pregunta3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCodigo(String codigo);

}
