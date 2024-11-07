package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.Presupuesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Presupuesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {}
