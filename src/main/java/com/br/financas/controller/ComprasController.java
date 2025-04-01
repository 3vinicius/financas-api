package com.br.financas.controller;

import com.br.financas.dto.LoginRequest;
import com.br.financas.model.Usuario;
import com.br.financas.services.AuthService;
import com.br.financas.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.br.financas.shareds.urls.compras.*;

@RestController
@RequestMapping(Url_Compras)
public class ComprasController {


    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public ComprasController (AuthService authService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String password = loginRequest.password();
        String login = loginRequest.login();

        var loginPassword = new UsernamePasswordAuthenticationToken(login, password);
        var auth = authenticationManager.authenticate(loginPassword);
        var token = tokenService.generateToken((UserDetails) auth.getPrincipal());

        Usuario usuario = authService.getUsuario();

        return ResponseEntity.ok().body(token);
    }




}
