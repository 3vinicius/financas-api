package com.br.financas.controler;

import com.br.financas.dto.LoginRequest;
import com.br.financas.model.Usuario;
import com.br.financas.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;


import static com.br.financas.shareds.urls.auth.*;

@RestController
@RequestMapping(Url_auth)
public class Auth {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;


    public Auth(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = Url_login)
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        String password = loginRequest.getPassword();
        String login = loginRequest.getLogin();

        var loginPassword = new UsernamePasswordAuthenticationToken(login, password);
        var auth = authenticationManager.authenticate(loginPassword);

        Usuario usuario = authService.getUsuario();

        return ResponseEntity.ok().body(usuario);
    }
}
