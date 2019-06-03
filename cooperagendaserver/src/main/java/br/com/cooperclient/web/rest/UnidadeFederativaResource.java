package br.com.cooperclient.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperclient.service.UnidadeFederativaService;
import br.com.cooperclient.service.dto.UnidadeFederativaDTO;

/**
 * REST controller for managing {@link br.com.cooperclient.domain.UnidadeFederativa}.
 */
@RestController
@RequestMapping("/api/unidade-federativas")
public class UnidadeFederativaResource {

    private final UnidadeFederativaService unidadeFederativaService;

    public UnidadeFederativaResource(UnidadeFederativaService unidadeFederativaService) {
        this.unidadeFederativaService = unidadeFederativaService;
    }

    @GetMapping
    public List<UnidadeFederativaDTO> getAllUnidadeFederativas() {        
        return unidadeFederativaService.findAll();
    }
}
