package com.example.api_restful.controller;

import com.example.api_restful.dto.ChamadoDTO;
import com.example.api_restful.service.ChamadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@Tag(name = "Chamados", description = "API para gerenciamento de chamados de suporte")
@SecurityRequirement(name = "bearerAuth")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @Operation(summary = "Busca um chamado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Long id) {
        ChamadoDTO chamadoDTO = chamadoService.findById(id);
        return ResponseEntity.ok(chamadoDTO);
    }

    @Operation(summary = "Lista todos os chamados")
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<ChamadoDTO> list = chamadoService.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Cria um novo chamado")
    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO novoChamado = chamadoService.create(chamadoDTO);
        return new ResponseEntity<>(novoChamado, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um chamado existente")
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Long id, @Valid @RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO chamadoAtualizado = chamadoService.update(id, chamadoDTO);
        return ResponseEntity.ok(chamadoAtualizado);
    }
}
