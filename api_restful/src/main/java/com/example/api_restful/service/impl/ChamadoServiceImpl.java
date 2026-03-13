package com.example.api_restful.service.impl;

import com.example.api_restful.dto.ChamadoDTO;
import com.example.api_restful.exception.ResourceNotFoundException;
import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Usuario;
import com.example.api_restful.repository.ChamadoRepository;
import com.example.api_restful.repository.UsuarioRepository;
import com.example.api_restful.service.ChamadoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoServiceImpl implements ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    public ChamadoServiceImpl(ChamadoRepository chamadoRepository, UsuarioRepository usuarioRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ChamadoDTO findById(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));
        return toDTO(chamado);
    }

    @Override
    public List<ChamadoDTO> findAll() {
        return chamadoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ChamadoDTO create(ChamadoDTO chamadoDTO) {
        Chamado chamado = fromDTO(chamadoDTO);
        // Aqui, em um cenário real, pegaríamos o usuário logado para ser o solicitante.
        // Por enquanto, vamos buscar o usuário pelo ID fornecido no DTO.
        Usuario solicitante = usuarioRepository.findById(chamadoDTO.getSolicitanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Solicitante não encontrado com o ID: " + chamadoDTO.getSolicitanteId()));
        chamado.setSolicitante(solicitante);
        
        Chamado novoChamado = chamadoRepository.save(chamado);
        return toDTO(novoChamado);
    }

    @Override
    public ChamadoDTO update(Long id, ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));

        chamado.setPrioridade(chamadoDTO.getPrioridade());
        chamado.setStatus(chamadoDTO.getStatus());
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setDescricao(chamadoDTO.getDescricao());

        if (chamadoDTO.getTecnicoId() != null) {
            Usuario tecnico = usuarioRepository.findById(chamadoDTO.getTecnicoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado com o ID: " + chamadoDTO.getTecnicoId()));
            chamado.setTecnico(tecnico);
        }

        if (chamadoDTO.getStatus().equals(com.example.api_restful.model.enums.Status.FECHADO)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        Chamado chamadoAtualizado = chamadoRepository.save(chamado);
        return toDTO(chamadoAtualizado);
    }

    // --- Métodos de conversão ---

    private ChamadoDTO toDTO(Chamado chamado) {
        ChamadoDTO dto = new ChamadoDTO();
        dto.setId(chamado.getId());
        dto.setDataAbertura(chamado.getDataAbertura());
        dto.setDataFechamento(chamado.getDataFechamento());
        dto.setPrioridade(chamado.getPrioridade());
        dto.setStatus(chamado.getStatus());
        dto.setTitulo(chamado.getTitulo());
        dto.setDescricao(chamado.getDescricao());
        dto.setSolicitanteId(chamado.getSolicitante().getId());
        dto.setNomeSolicitante(chamado.getSolicitante().getNome());
        if (chamado.getTecnico() != null) {
            dto.setTecnicoId(chamado.getTecnico().getId());
            dto.setNomeTecnico(chamado.getTecnico().getNome());
        }
        return dto;
    }

    private Chamado fromDTO(ChamadoDTO dto) {
        Chamado chamado = new Chamado();
        chamado.setId(dto.getId());
        chamado.setDataAbertura(dto.getDataAbertura());
        chamado.setDataFechamento(dto.getDataFechamento());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        return chamado;
    }
}
