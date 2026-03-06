package com.example.api_restful.config;

import com.example.api_restful.model.Usuario;
import com.example.api_restful.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // Este bean só será ativado quando o perfil "dev" estiver ativo
public class TestDataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository) {
        return args -> {
            // Limpa o repositório para evitar duplicatas a cada reinicialização
            usuarioRepository.deleteAll();

            // Cria um novo usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("Usuario Teste");
            novoUsuario.setEmail("teste@email.com");

            // Salva o usuário no banco de dados
            usuarioRepository.save(novoUsuario);

            System.out.println(">>> [TestDataInitializer] Usuário de teste salvo no banco de dados: " + novoUsuario);
        };
    }
}
