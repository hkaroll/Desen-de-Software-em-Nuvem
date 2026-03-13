package com.example.api_restful.security;

import com.example.api_restful.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {

    public static Usuario getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Usuario) {
            return (Usuario) principal;
        }
        throw new IllegalStateException("Nenhum usuário autenticado encontrado no contexto de segurança.");
    }
}
