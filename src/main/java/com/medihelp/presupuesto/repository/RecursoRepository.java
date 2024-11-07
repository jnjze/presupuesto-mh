package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.Recurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {}
