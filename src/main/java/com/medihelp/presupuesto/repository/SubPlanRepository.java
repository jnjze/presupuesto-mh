package com.medihelp.presupuesto.repository;

import com.medihelp.presupuesto.domain.SubPlan;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubPlanRepository extends JpaRepository<SubPlan, Long> {
    List<SubPlan> findByPlanId(Long planId);
}
