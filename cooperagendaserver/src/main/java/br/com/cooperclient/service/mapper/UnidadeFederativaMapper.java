package br.com.cooperclient.service.mapper;

import br.com.cooperclient.domain.*;
import br.com.cooperclient.service.dto.UnidadeFederativaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadeFederativa} and its DTO {@link UnidadeFederativaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadeFederativaMapper extends EntityMapper<UnidadeFederativaDTO, UnidadeFederativa> {



    default UnidadeFederativa fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeFederativa unidadeFederativa = new UnidadeFederativa();
        unidadeFederativa.setId(id);
        return unidadeFederativa;
    }
}
