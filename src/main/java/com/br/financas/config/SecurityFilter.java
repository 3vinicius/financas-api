package com.br.financas.config;

import com.br.financas.services.AuthService;
import com.br.financas.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final AuthService authService;

    public SecurityFilter(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;

        String uri = request.getRequestURI();
        // Verifique se a URI é "/auth/login/**" e não processe o filtro
        if (uri.startsWith("/auth/login")) {
            filterChain.doFilter(request, response); // Ignora a autenticação para login
            return;
        }


        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.replace("Bearer ", "");
            var login = tokenService.validateToken(token);
            if (login != null) {
                UserDetails userDetails = authService.loadUserByUsername(login);
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
