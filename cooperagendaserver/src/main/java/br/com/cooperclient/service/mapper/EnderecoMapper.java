package br.com.cooperclient.service.mapper;

import br.com.cooperclient.domain.*;
import br.com.cooperclient.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CidadeMapper.class})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {

    @Mapping(source = "cidade.id", target = "cidadeId")
    EnderecoDTO toDto(Endereco endereco);

    @Mapping(source = "cidadeId", target = "cidade")
    @Mapping(target = "cliente", ignore = true)
    Endereco toEntity(EnderecoDTO enderecoDTO);

    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
