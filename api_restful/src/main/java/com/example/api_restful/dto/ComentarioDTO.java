package com.example.api_restful.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioDTO {

    private Long id;

    @NotBlank(message = "O texto do comentário não pode estar em branco.")
    private String texto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    private Long autorId;
    private String nomeAutor;

    private Long chamadoId;
}
