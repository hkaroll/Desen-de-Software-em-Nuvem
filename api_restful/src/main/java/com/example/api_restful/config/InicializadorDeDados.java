package com.example.api_restful.config;

import com.example.api_restful.model.Usuario;
import com.example.api_restful.model.enums.Perfil;
import com.example.api_restful.repository.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Profile("dev")
public class InicializadorDeDados {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public InicializadorDeDados(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            usuarioRepositorio.deleteAll();

            Usuario admin = new Usuario();
            admin.setNome("Admin Teste");
            admin.setEmail("admin@email.com");
            admin.setSenha(passwordEncoder.encode("12345"));
            admin.setPerfis(Set.of(Perfil.ADMIN, Perfil.USUARIO));

            usuarioRepositorio.save(admin);

            System.out.println(">>> [InicializadorDeDados] Usuário ADMIN de teste salvo: " + admin);
        };
    }
}
