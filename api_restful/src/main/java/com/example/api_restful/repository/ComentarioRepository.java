package com.example.api_restful.repository;

import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByChamado(Chamado chamado);
}
