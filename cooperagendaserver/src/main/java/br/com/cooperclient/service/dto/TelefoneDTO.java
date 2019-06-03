package br.com.cooperclient.service.dto;
import java.io.Serializable;
import java.util.Objects;
import br.com.cooperclient.domain.enumeration.TipoTefeone;

/**
 * A DTO for the {@link br.com.cooperclient.domain.Telefone} entity.
 */
public class TelefoneDTO implements Serializable {

    private Long id;

    private String numero;

    private TipoTefeone tipo;


    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTefeone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTefeone tipo) {
        this.tipo = tipo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelefoneDTO telefoneDTO = (TelefoneDTO) o;
        if (telefoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telefoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", cliente=" + getClienteId() +
            "}";
    }
}
