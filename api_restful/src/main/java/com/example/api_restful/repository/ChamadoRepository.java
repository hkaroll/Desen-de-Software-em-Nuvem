package com.example.api_restful.repository;

import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findBySolicitante(Usuario solicitante);
}
