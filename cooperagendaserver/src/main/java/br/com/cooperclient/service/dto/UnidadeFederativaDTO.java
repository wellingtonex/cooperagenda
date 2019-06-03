package br.com.cooperclient.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.cooperclient.domain.UnidadeFederativa} entity.
 */
public class UnidadeFederativaDTO implements Serializable {

    private Long id;

    @NotNull
    private String sigla;

    @NotNull
    private String nome;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeFederativaDTO unidadeFederativaDTO = (UnidadeFederativaDTO) o;
        if (unidadeFederativaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeFederativaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeFederativaDTO{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
