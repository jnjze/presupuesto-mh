package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Plan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {}
