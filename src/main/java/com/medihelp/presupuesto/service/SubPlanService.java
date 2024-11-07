package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.SubPlanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.SubPlan}.
 */
public interface SubPlanService {
    /**
     * Save a subPlan.
     *
     * @param subPlanDTO the entity to save.
     * @return the persisted entity.
     */
    SubPlanDTO save(SubPlanDTO subPlanDTO);

    /**
     * Updates a subPlan.
     *
     * @param subPlanDTO the entity to update.
     * @return the persisted entity.
     */
    SubPlanDTO update(SubPlanDTO subPlanDTO);

    /**
     * Partially updates a subPlan.
     *
     * @param subPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubPlanDTO> partialUpdate(SubPlanDTO subPlanDTO);

    /**
     * Get all the subPlans.
     *
     * @return the list of entities.
     */
    List<SubPlanDTO> findAll();

    List<SubPlanDTO> findByPlanId(Long planId);

    /**
     * Get the "id" subPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubPlanDTO> findOne(Long id);

    /**
     * Delete the "id" subPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
