package com.example.api_restful.service;

import com.example.api_restful.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioServico {

    List<ComentarioDTO> findByChamadoId(Long chamadoId);

    ComentarioDTO addComentario(Long chamadoId, ComentarioDTO comentarioDTO);
}
