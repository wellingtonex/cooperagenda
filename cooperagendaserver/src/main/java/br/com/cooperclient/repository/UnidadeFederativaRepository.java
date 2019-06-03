package br.com.cooperclient.repository;

import br.com.cooperclient.domain.UnidadeFederativa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeFederativa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeFederativaRepository extends JpaRepository<UnidadeFederativa, Long> {

}
