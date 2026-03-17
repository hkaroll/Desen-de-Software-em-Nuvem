package com.example.api_restful.service.impl;

import com.example.api_restful.dto.ComentarioDTO;
import com.example.api_restful.exception.ResourceNotFoundException;
import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Comentario;
import com.example.api_restful.model.Usuario;
import com.example.api_restful.repository.ChamadoRepository;
import com.example.api_restful.repository.ComentarioRepository;
import com.example.api_restful.security.SecurityContextUtil;
import com.example.api_restful.service.ComentarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository, ChamadoRepository chamadoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.chamadoRepository = chamadoRepository;
    }

    @Override
    public List<ComentarioDTO> findByChamadoId(Long chamadoId) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + chamadoId));
        return comentarioRepository.findByChamado(chamado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO addComentario(Long chamadoId, ComentarioDTO comentarioDTO) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + chamadoId));
        Usuario autor = SecurityContextUtil.getAuthenticatedUser(); // O autor é o usuário logado

        Comentario comentario = fromDTO(comentarioDTO);
        comentario.setChamado(chamado);
        comentario.setAutor(autor);

        Comentario novoComentario = comentarioRepository.save(comentario);
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
