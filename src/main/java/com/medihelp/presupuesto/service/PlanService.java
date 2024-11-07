package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.PlanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.Plan}.
 */
public interface PlanService {
    /**
     * Save a plan.
     *
     * @param planDTO the entity to save.
     * @return the persisted entity.
     */
    PlanDTO save(PlanDTO planDTO);

    /**
     * Updates a plan.
     *
     * @param planDTO the entity to update.
     * @return the persisted entity.
     */
    PlanDTO update(PlanDTO planDTO);

    /**
     * Partially updates a plan.
     *
     * @param planDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanDTO> partialUpdate(PlanDTO planDTO);

    /**
     * Get all the plans.
     *
     * @return the list of entities.
     */
    List<PlanDTO> findAll();

    /**
     * Get the "id" plan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanDTO> findOne(Long id);

    /**
     * Delete the "id" plan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
