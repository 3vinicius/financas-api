package com.br.financas.repositorys;

import com.br.financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Override
    Optional<Usuario> findById(Integer integer);
    UserDetails findByLogin(String login);
}