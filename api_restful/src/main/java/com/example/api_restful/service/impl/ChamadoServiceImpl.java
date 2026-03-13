package com.example.api_restful.service.impl;

import com.example.api_restful.dto.ChamadoDTO;
import com.example.api_restful.exception.AccessDeniedException;
import com.example.api_restful.exception.ResourceNotFoundException;
import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Usuario;
import com.example.api_restful.model.enums.Perfil;
import com.example.api_restful.repository.ChamadoRepository;
import com.example.api_restful.repository.UsuarioRepository;
import com.example.api_restful.security.SecurityContextUtil;
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
        Usuario usuarioLogado = SecurityContextUtil.getAuthenticatedUser();
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));

        if (!usuarioLogado.getPerfis().contains(Perfil.ADMIN) && !chamado.getSolicitante().equals(usuarioLogado)) {
            throw new AccessDeniedException("Acesso negado: Você não tem permissão para visualizar este chamado.");
        }

        return toDTO(chamado);
    }

    @Override
    public List<ChamadoDTO> findAll() {
        Usuario usuarioLogado = SecurityContextUtil.getAuthenticatedUser();

        if (usuarioLogado.getPerfis().contains(Perfil.ADMIN)) {
            return chamadoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            return chamadoRepository.findBySolicitante(usuarioLogado).stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    @Override
    public ChamadoDTO create(ChamadoDTO chamadoDTO) {
        Usuario solicitante = SecurityContextUtil.getAuthenticatedUser();
        Chamado chamado = fromDTO(chamadoDTO);
        chamado.setSolicitante(solicitante);
        
        Chamado novoChamado = chamadoRepository.save(chamado);
        return toDTO(novoChamado);
    }

    @Override
    public ChamadoDTO update(Long id, ChamadoDTO chamadoDTO) {
        Usuario usuarioLogado = SecurityContextUtil.getAuthenticatedUser();
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));

        if (!usuarioLogado.getPerfis().contains(Perfil.ADMIN) && !chamado.getSolicitante().equals(usuarioLogado)) {
            throw new AccessDeniedException("Acesso negado: Você não tem permissão para atualizar este chamado.");
        }

        chamado.setPrioridade(chamadoDTO.getPrioridade());
        chamado.setStatus(chamadoDTO.getStatus());
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setDescricao(chamadoDTO.getDescricao());

        if (usuarioLogado.getPerfis().contains(Perfil.ADMIN) && chamadoDTO.getTecnicoId() != null) {
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
