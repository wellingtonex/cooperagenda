package br.com.cooperclient.service.impl;

import br.com.cooperclient.service.UnidadeFederativaService;
import br.com.cooperclient.domain.UnidadeFederativa;
import br.com.cooperclient.repository.UnidadeFederativaRepository;
import br.com.cooperclient.service.dto.UnidadeFederativaDTO;
import br.com.cooperclient.service.mapper.UnidadeFederativaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnidadeFederativaServiceImpl implements UnidadeFederativaService {

    private final Logger log = LoggerFactory.getLogger(UnidadeFederativaServiceImpl.class);

    private final UnidadeFederativaRepository unidadeFederativaRepository;

    private final UnidadeFederativaMapper unidadeFederativaMapper;

    public UnidadeFederativaServiceImpl(UnidadeFederativaRepository unidadeFederativaRepository, UnidadeFederativaMapper unidadeFederativaMapper) {
        this.unidadeFederativaRepository = unidadeFederativaRepository;
        this.unidadeFederativaMapper = unidadeFederativaMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadeFederativaDTO> findAll() {
        log.debug("Request to get all UnidadeFederativas");
        return unidadeFederativaRepository.findAll().stream()
            .map(unidadeFederativaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
