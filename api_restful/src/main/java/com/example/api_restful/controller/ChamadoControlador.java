package com.example.api_restful.controller;

import com.example.api_restful.dto.ChamadoDTO;
import com.example.api_restful.dto.ComentarioDTO;
import com.example.api_restful.service.ChamadoServico;
import com.example.api_restful.service.ComentarioServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@Tag(name = "Chamados", description = "API para gerenciamento de chamados de suporte")
@SecurityRequirement(name = "bearerAuth")
public class ChamadoControlador {

    private final ChamadoServico chamadoServico;
    private final ComentarioServico comentarioServico;

    public ChamadoControlador(ChamadoServico chamadoServico, ComentarioServico comentarioServico) {
        this.chamadoServico = chamadoServico;
        this.comentarioServico = comentarioServico;
    }

    @Operation(summary = "Busca um chamado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Long id) {
        ChamadoDTO chamadoDTO = chamadoServico.findById(id);
        return ResponseEntity.ok(chamadoDTO);
    }

    @Operation(summary = "Lista todos os chamados")
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<ChamadoDTO> list = chamadoServico.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Cria um novo chamado")
    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO novoChamado = chamadoServico.create(chamadoDTO);
        return new ResponseEntity<>(novoChamado, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um chamado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Long id, @Valid @RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO chamadoAtualizado = chamadoServico.update(id, chamadoDTO);
        return ResponseEntity.ok(chamadoAtualizado);
    }

    @Operation(summary = "Deleta um chamado (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Chamado deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (apenas ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chamadoServico.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista os comentários de um chamado")
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioDTO>> findComentariosByChamadoId(@PathVariable Long id) {
        List<ComentarioDTO> comentarios = comentarioServico.findByChamadoId(id);
        return ResponseEntity.ok(comentarios);
    }

    @Operation(summary = "Adiciona um novo comentário a um chamado")
    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioDTO> addComentario(@PathVariable Long id, @Valid @RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO novoComentario = comentarioServico.addComentario(id, comentarioDTO);
        return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
    }
}
