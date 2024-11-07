package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.RubroDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.Rubro}.
 */
public interface RubroService {
    /**
     * Save a rubro.
     *
     * @param rubroDTO the entity to save.
     * @return the persisted entity.
     */
    RubroDTO save(RubroDTO rubroDTO);

    /**
     * Updates a rubro.
     *
     * @param rubroDTO the entity to update.
     * @return the persisted entity.
     */
    RubroDTO update(RubroDTO rubroDTO);

    /**
     * Partially updates a rubro.
     *
     * @param rubroDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RubroDTO> partialUpdate(RubroDTO rubroDTO);

    /**
     * Get all the rubros.
     *
     * @return the list of entities.
     */
    List<RubroDTO> findAll();

    /**
     * Get the "id" rubro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RubroDTO> findOne(Long id);

    /**
     * Delete the "id" rubro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
