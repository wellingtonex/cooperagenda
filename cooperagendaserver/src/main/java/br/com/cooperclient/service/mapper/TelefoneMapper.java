package br.com.cooperclient.service.mapper;

import br.com.cooperclient.domain.*;
import br.com.cooperclient.service.dto.TelefoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {

    @Mapping(source = "cliente.id", target = "clienteId")
    TelefoneDTO toDto(Telefone telefone);

    @Mapping(source = "clienteId", target = "cliente")
    Telefone toEntity(TelefoneDTO telefoneDTO);

    default Telefone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telefone telefone = new Telefone();
        telefone.setId(id);
        return telefone;
    }
}
