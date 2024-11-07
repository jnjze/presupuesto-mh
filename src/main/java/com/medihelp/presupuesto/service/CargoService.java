package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.CargoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.Plan}.
 */
public interface CargoService {
    /**
     * Save a plan.
     *
     * @param CargoDTO the entity to save.
     * @return the persisted entity.
     */
    CargoDTO save(CargoDTO CargoDTO);

    /**
     * Updates a plan.
     *
     * @param CargoDTO the entity to update.
     * @return the persisted entity.
     */
    CargoDTO update(CargoDTO CargoDTO);

    /**
     * Partially updates a plan.
     *
     * @param CargoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CargoDTO> partialUpdate(CargoDTO CargoDTO);

    /**
     * Get all the plans.
     *
     * @return the list of entities.
     */
    List<CargoDTO> findAll();

    /**
     * Get the "id" plan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CargoDTO> findOne(Long id);

    /**
     * Delete the "id" plan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
