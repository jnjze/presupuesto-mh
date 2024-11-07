package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.Rubro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rubro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long> {}
