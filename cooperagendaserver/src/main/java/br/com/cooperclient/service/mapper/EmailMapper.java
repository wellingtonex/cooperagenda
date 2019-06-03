package br.com.cooperclient.service.mapper;

import br.com.cooperclient.domain.*;
import br.com.cooperclient.service.dto.EmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {

    @Mapping(source = "cliente.id", target = "clienteId")
    EmailDTO toDto(Email email);

    @Mapping(source = "clienteId", target = "cliente")
    Email toEntity(EmailDTO emailDTO);

    default Email fromId(Long id) {
        if (id == null) {
            return null;
        }
        Email email = new Email();
        email.setId(id);
        return email;
    }
}
