package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.UnidadFuncional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UnidadFuncional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadFuncionalRepository extends JpaRepository<UnidadFuncional, Long> {}
