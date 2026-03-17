package com.example.api_restful.service.impl;

import com.example.api_restful.dto.ComentarioDTO;
import com.example.api_restful.exception.ResourceNotFoundException;
import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Comentario;
import com.example.api_restful.model.Usuario;
import com.example.api_restful.repository.ChamadoRepositorio;
import com.example.api_restful.repository.ComentarioRepositorio;
import com.example.api_restful.security.UtilDoContextoDeSeguranca;
import com.example.api_restful.service.ComentarioServico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicoImpl implements ComentarioServico {

    private final ComentarioRepositorio comentarioRepositorio;
    private final ChamadoRepositorio chamadoRepositorio;

    public ComentarioServicoImpl(ComentarioRepositorio comentarioRepositorio, ChamadoRepositorio chamadoRepositorio) {
        this.comentarioRepositorio = comentarioRepositorio;
        this.chamadoRepositorio = chamadoRepositorio;
    }

    @Override
    public List<ComentarioDTO> findByChamadoId(Long chamadoId) {
        Chamado chamado = chamadoRepositorio.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + chamadoId));
        return comentarioRepositorio.findByChamado(chamado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO addComentario(Long chamadoId, ComentarioDTO comentarioDTO) {
        Chamado chamado = chamadoRepositorio.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + chamadoId));
        Usuario autor = UtilDoContextoDeSeguranca.getAuthenticatedUser();

        Comentario comentario = fromDTO(comentarioDTO);
        comentario.setChamado(chamado);
        comentario.setAutor(autor);

        Comentario novoComentario = comentarioRepositorio.save(comentario);
        return toDTO(novoComentario);
    }

    private ComentarioDTO toDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setTexto(comentario.getTexto());
        dto.setDataCriacao(comentario.getDataCriacao());
        dto.setAutorId(comentario.getAutor().getId());
        dto.setNomeAutor(comentario.getAutor().getNome());
        dto.setChamadoId(comentario.getChamado().getId());
        return dto;
    }

    private Comentario fromDTO(ComentarioDTO dto) {
        Comentario comentario = new Comentario();
        comentario.setId(dto.getId());
        comentario.setTexto(dto.getTexto());
        comentario.setDataCriacao(dto.getDataCriacao());
        return comentario;
    }
}
