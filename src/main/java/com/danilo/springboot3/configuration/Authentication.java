package com.danilo.springboot3.configuration;

import com.danilo.springboot3.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class Authentication extends OncePerRequestFilter {

    private UserService service;
    private Jwt jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filter) throws ServletException,IOException {
        String token = this.jwt.getToken(request);

        if (Objects.nonNull(token)) {
            String nomeUsuario = this.jwt.getUsername(token);

            if (Objects.nonNull(nomeUsuario)) {
                UserDetails usuario = this.service.loadUserByUsername(nomeUsuario);

                if (this.jwt.validateToken(token,usuario)) {
                    UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                    autenticacao.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }

        }

        filter.doFilter(request,response);
    }

}
