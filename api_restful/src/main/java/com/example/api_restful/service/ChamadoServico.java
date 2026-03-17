package com.example.api_restful.service;

import com.example.api_restful.dto.ChamadoDTO;
import java.util.List;

public interface ChamadoServico {

    ChamadoDTO findById(Long id);

    List<ChamadoDTO> findAll();

    ChamadoDTO create(ChamadoDTO chamadoDTO);

    ChamadoDTO update(Long id, ChamadoDTO chamadoDTO);

    void delete(Long id);
}
