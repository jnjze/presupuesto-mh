package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.PresupuestoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.Presupuesto}.
 */
public interface PresupuestoService {
    /**
     * Save a presupuesto.
     *
     * @param presupuestoDTO the entity to save.
     * @return the persisted entity.
     */
    PresupuestoDTO save(PresupuestoDTO presupuestoDTO);

    /**
     * Updates a presupuesto.
     *
     * @param presupuestoDTO the entity to update.
     * @return the persisted entity.
     */
    PresupuestoDTO update(PresupuestoDTO presupuestoDTO);

    /**
     * Partially updates a presupuesto.
     *
     * @param presupuestoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PresupuestoDTO> partialUpdate(PresupuestoDTO presupuestoDTO);

    /**
     * Get all the presupuestos.
     *
     * @return the list of entities.
     */
    List<PresupuestoDTO> findAll();

    /**
     * Get the "id" presupuesto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PresupuestoDTO> findOne(Long id);

    /**
     * Delete the "id" presupuesto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
