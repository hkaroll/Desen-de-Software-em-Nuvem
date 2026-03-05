package com.example.api_restful;

import com.example.api_restful.model.Usuario;
import com.example.api_restful.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepository) {
		return args -> {
			// Cria um novo usuário
			Usuario novoUsuario = new Usuario();
			novoUsuario.setNome("Usuario Teste");
			novoUsuario.setEmail("teste@email.com");

			// Salva o usuário no banco de dados
			usuarioRepository.save(novoUsuario);

			System.out.println(">>> Usuário de teste salvo no banco de dados: " + novoUsuario);
		};
	}
}
