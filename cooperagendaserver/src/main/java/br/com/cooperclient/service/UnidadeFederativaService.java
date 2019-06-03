package br.com.cooperclient.service;

import br.com.cooperclient.service.dto.UnidadeFederativaDTO;

import java.util.List;
import java.util.Optional;

public interface UnidadeFederativaService {

    List<UnidadeFederativaDTO> findAll();
}
