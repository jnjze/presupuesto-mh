package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.TipoRecurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRecursoRepository extends JpaRepository<TipoRecurso, Long> {}
