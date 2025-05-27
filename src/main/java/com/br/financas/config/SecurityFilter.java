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
import java.util.Map;

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
        if (request.getRequestURI().startsWith("/auth/login")) {
            filterChain.doFilter(request, response);
            return ;
        }
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            this.responseInvalidToken(response);
            return;
        }
        String token = authorization.replace("Bearer ", "");
        var login = tokenService.validateToken(token);
        if (login.isEmpty()) {
            this.responseInvalidToken(response);
            return;
        }
        UserDetails userDetails = authService.loadUserByUsername(login);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void responseInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Token invalido ou expirado\"}");
        response.getWriter().flush();
    }

}
