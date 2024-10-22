package cibertec.edu.pe.app_server_pregunta3.controller;

import cibertec.edu.pe.app_server_pregunta3.dto.request.UsuarioRequestDto;
import cibertec.edu.pe.app_server_pregunta3.dto.response.UsuarioResponseDto;
import cibertec.edu.pe.app_server_pregunta3.model.Usuario;
import cibertec.edu.pe.app_server_pregunta3.service.IUsuarioService;
import cibertec.edu.pe.app_server_pregunta3.service.impl.DetailsUserService;
import cibertec.edu.pe.app_server_pregunta3.service.impl.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final DetailsUserService detailsUserService;
    private final AuthenticationManager authenticationManager;
    private final IUsuarioService usuarioService;
    private final String clave = "Adriano2k03";

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> autenticacionUsuario(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(usuarioRequestDto.getCodigo(), usuarioRequestDto.getPassword()));
            if(authentication.isAuthenticated()) {
                Usuario authUsuario = usuarioService.obtenerUsuarioPorCodigo(usuarioRequestDto.getCodigo());
                String token = generarToken(authUsuario);
                return new ResponseEntity<>(
                        UsuarioResponseDto.builder()
                                .id(authUsuario.getId())
                                .codigo(authUsuario.getCodigo())
                                .token(token)
                                .build(),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generarToken(Usuario usuario) {
        List<GrantedAuthority> authorityList = detailsUserService.getRolUser(usuario.getRol());
        return Jwts.builder()
                .setId(usuario.getId().toString())
                .setSubject(usuario.getCodigo())
                .claim("rol",
                        authorityList.stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
    }

}
