package br.com.cooperclient.service;

import br.com.cooperclient.service.dto.ClienteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.cooperclient.domain.Cliente}.
 */
public interface ClienteService {

    ClienteDTO save(ClienteDTO clienteDTO);
    Page<ClienteDTO> findAll(Pageable pageable);
    Optional<ClienteDTO> findOne(Long id);
    void delete(Long id);
}
