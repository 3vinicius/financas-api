package com.br.financas.controller;

import com.br.financas.dto.LoginRequest;
import com.br.financas.model.Usuario;
import com.br.financas.services.AuthService;
import com.br.financas.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

import static com.br.financas.shareds.urls.auth.*;

@RestController
@RequestMapping(Url_auth)
public class Auth {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public Auth(AuthService authService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping(value = Url_login)
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String password = loginRequest.login();
        String login = loginRequest.password();
        String token = null;
        try {
            var loginPassword = new UsernamePasswordAuthenticationToken(login, password);
            var auth = authenticationManager.authenticate(loginPassword);
            token = tokenService.generateToken((UserDetails) auth.getPrincipal());
        }
        catch (Exception e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }

        return ResponseEntity.ok().body(Map.of("token", token));
    }

}
