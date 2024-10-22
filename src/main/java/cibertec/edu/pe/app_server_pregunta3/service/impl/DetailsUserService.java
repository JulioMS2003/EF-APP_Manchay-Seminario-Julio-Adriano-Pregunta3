package cibertec.edu.pe.app_server_pregunta3.service.impl;

import cibertec.edu.pe.app_server_pregunta3.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailsUserService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String codigo) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorCodigo(codigo);
        return crearUserDetails(usuario, getRolUser(usuario.getRol()));
    }

    public List<GrantedAuthority> getRolUser(String rol) {
        List<GrantedAuthority> rolesAuth = new ArrayList<>();
        rolesAuth.add(new SimpleGrantedAuthority("ROLE_" + rol));
        return rolesAuth;
    }

    private UserDetails crearUserDetails(Usuario usuario, List<GrantedAuthority> authorityList) {
        return new User(
                usuario.getCodigo(),
                usuario.getPassword(),
                usuario.isActivo(),
                true,
                true,
                true,
                authorityList
        );
    }
}
