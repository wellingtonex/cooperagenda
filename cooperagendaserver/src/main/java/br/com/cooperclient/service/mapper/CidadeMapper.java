package br.com.cooperclient.service.mapper;

import br.com.cooperclient.domain.*;
import br.com.cooperclient.service.dto.CidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnidadeFederativaMapper.class})
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {

    @Mapping(source = "uf.id", target = "ufId")
    CidadeDTO toDto(Cidade cidade);

    @Mapping(source = "ufId", target = "uf")
    Cidade toEntity(CidadeDTO cidadeDTO);

    default Cidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cidade cidade = new Cidade();
        cidade.setId(id);
        return cidade;
    }
}
