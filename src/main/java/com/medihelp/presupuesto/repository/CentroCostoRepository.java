package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.CentroCosto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroCosto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroCostoRepository extends JpaRepository<CentroCosto, Long> {}
