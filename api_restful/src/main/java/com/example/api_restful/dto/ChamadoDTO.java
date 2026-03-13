package com.example.api_restful.dto;

import com.example.api_restful.model.enums.Prioridade;
import com.example.api_restful.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChamadoDTO {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @NotNull(message = "A prioridade não pode ser nula.")
    private Prioridade prioridade;

    @NotNull(message = "O status não pode ser nulo.")
    private Status status;

    @NotBlank(message = "O título não pode estar em branco.")
    private String titulo;

    @NotBlank(message = "A descrição não pode estar em branco.")
    private String descricao;

    private Long solicitanteId;
    private String nomeSolicitante;

    private Long tecnicoId;
    private String nomeTecnico;
}
