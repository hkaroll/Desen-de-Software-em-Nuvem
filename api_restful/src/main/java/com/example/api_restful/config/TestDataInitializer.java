package com.example.api_restful.config;

import com.example.api_restful.model.Usuario;
import com.example.api_restful.model.enums.Perfil;
import com.example.api_restful.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Profile("dev")
public class TestDataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência via construtor (boa prática)
    public TestDataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            usuarioRepository.deleteAll();

            Usuario admin = new Usuario();
            admin.setNome("Admin Teste");
            admin.setEmail("admin@email.com");
            admin.setSenha(passwordEncoder.encode("12345")); // Senha criptografada
            admin.setPerfis(Set.of(Perfil.ADMIN, Perfil.USUARIO)); // Adiciona perfis

            usuarioRepository.save(admin);

            System.out.println(">>> [TestDataInitializer] Usuário ADMIN de teste salvo: " + admin);
        };
    }
}
