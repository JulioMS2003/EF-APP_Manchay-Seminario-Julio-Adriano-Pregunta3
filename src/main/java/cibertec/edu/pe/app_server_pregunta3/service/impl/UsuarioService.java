package cibertec.edu.pe.app_server_pregunta3.service.impl;

import cibertec.edu.pe.app_server_pregunta3.model.Usuario;
import cibertec.edu.pe.app_server_pregunta3.repository.UsuarioRepository;
import cibertec.edu.pe.app_server_pregunta3.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public Usuario obtenerUsuarioPorCodigo(String codigo) {
        Usuario usuario = usuarioRepository.findByCodigo(codigo);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }
}
