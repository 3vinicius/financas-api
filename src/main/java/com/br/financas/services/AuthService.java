package com.br.financas.services;

import com.br.financas.model.Usuario;
import com.br.financas.repositorys.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;


    public Usuario getUsuario(){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(1);
        return usuarioOptional.orElse(null);
    }

    public UserDetails loadUserByUsername(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
