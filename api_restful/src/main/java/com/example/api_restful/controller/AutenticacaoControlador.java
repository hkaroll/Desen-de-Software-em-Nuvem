package com.example.api_restful.controller;

import com.example.api_restful.security.ProvedorDeTokenJWT;
import com.example.api_restful.security.dto.JwtAuthenticationResponse;
import com.example.api_restful.security.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Autenticação", description = "Endpoint para autenticação de usuários")
public class AutenticacaoControlador {

    private final AuthenticationManager authenticationManager;
    private final ProvedorDeTokenJWT provedorDeToken;

    public AutenticacaoControlador(AuthenticationManager authenticationManager, ProvedorDeTokenJWT provedorDeToken) {
        this.authenticationManager = authenticationManager;
        this.provedorDeToken = provedorDeToken;
    }

    @Operation(summary = "Autentica um usuário e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = provedorDeToken.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
