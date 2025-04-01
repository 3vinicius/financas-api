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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String password = loginRequest.getPassword();
        String login = loginRequest.getLogin();

        var loginPassword = new UsernamePasswordAuthenticationToken(login, password);
        var auth = authenticationManager.authenticate(loginPassword);
        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        Usuario usuario = authService.getUsuario();

        Cookie cookie = new Cookie("token_session", token);
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.addHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self';");
        response.addHeader("Cache-Control", "no-cache, must-revalidate, proxy-revalidate");

        return ResponseEntity.ok().body(token);
    }

}
