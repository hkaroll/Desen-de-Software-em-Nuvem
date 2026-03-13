package com.example.api_restful.repository;

import com.example.api_restful.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * O Spring Data JPA cria automaticamente a implementação deste método.
     *
     * @param email O e-mail do usuário a ser buscado.
     * @return Um Optional contendo o usuário se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Usuario> findByEmail(String email);
}
