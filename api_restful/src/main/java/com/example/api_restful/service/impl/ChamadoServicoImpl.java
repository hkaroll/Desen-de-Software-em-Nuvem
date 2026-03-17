package com.example.api_restful.service.impl;

import com.example.api_restful.dto.ChamadoDTO;
import com.example.api_restful.exception.AccessDeniedException;
import com.example.api_restful.exception.ResourceNotFoundException;
import com.example.api_restful.model.Chamado;
import com.example.api_restful.model.Usuario;
import com.example.api_restful.model.enums.Perfil;
import com.example.api_restful.repository.ChamadoRepositorio;
import com.example.api_restful.repository.UsuarioRepositorio;
import com.example.api_restful.security.UtilDoContextoDeSeguranca;
import com.example.api_restful.service.ChamadoServico;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoServicoImpl implements ChamadoServico {

    private final ChamadoRepositorio chamadoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public ChamadoServicoImpl(ChamadoRepositorio chamadoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.chamadoRepositorio = chamadoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public ChamadoDTO findById(Long id) {
        Chamado chamado = chamadoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));
        validaPermissaoSobreChamado(chamado, "visualizar");
        return toDTO(chamado);
    }

    @Override
    public List<ChamadoDTO> findAll() {
        Usuario usuarioLogado = UtilDoContextoDeSeguranca.getAuthenticatedUser();
        if (usuarioLogado.getPerfis().contains(Perfil.ADMIN)) {
            return chamadoRepositorio.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            return chamadoRepositorio.findBySolicitante(usuarioLogado).stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    @Override
    public ChamadoDTO create(ChamadoDTO chamadoDTO) {
        Usuario solicitante = UtilDoContextoDeSeguranca.getAuthenticatedUser();
        Chamado chamado = fromDTO(chamadoDTO);
        chamado.setSolicitante(solicitante);
        Chamado novoChamado = chamadoRepositorio.save(chamado);
        return toDTO(novoChamado);
    }

    @Override
    public ChamadoDTO update(Long id, ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));
        validaPermissaoSobreChamado(chamado, "atualizar");

        chamado.setPrioridade(chamadoDTO.getPrioridade());
        chamado.setStatus(chamadoDTO.getStatus());
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setDescricao(chamadoDTO.getDescricao());

        Usuario usuarioLogado = UtilDoContextoDeSeguranca.getAuthenticatedUser();
        if (usuarioLogado.getPerfis().contains(Perfil.ADMIN) && chamadoDTO.getTecnicoId() != null) {
            Usuario tecnico = usuarioRepositorio.findById(chamadoDTO.getTecnicoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado com o ID: " + chamadoDTO.getTecnicoId()));
            chamado.setTecnico(tecnico);
        }

        if (chamadoDTO.getStatus().equals(com.example.api_restful.model.enums.Status.FECHADO)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        Chamado chamadoAtualizado = chamadoRepositorio.save(chamado);
        return toDTO(chamadoAtualizado);
    }

    @Override
    public void delete(Long id) {
        chamadoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado com o ID: " + id));
        chamadoRepositorio.deleteById(id);
    }

    private void validaPermissaoSobreChamado(Chamado chamado, String acao) {
        Usuario usuarioLogado = UtilDoContextoDeSeguranca.getAuthenticatedUser();
        if (!usuarioLogado.getPerfis().contains(Perfil.ADMIN) && !chamado.getSolicitante().equals(usuarioLogado)) {
            throw new AccessDeniedException("Acesso negado: Você não tem permissão para " + acao + " este chamado.");
        }
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
