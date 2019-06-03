package br.com.cooperclient.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperclient.service.CidadeService;
import br.com.cooperclient.service.dto.CidadeDTO;

@RestController
@RequestMapping("/api/cidades")
public class CidadeResource {

    private final CidadeService cidadeService;

    public CidadeResource(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<CidadeDTO> getAllCidades() {
        return cidadeService.findAll();
    }
}
