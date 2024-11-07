package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.RecursoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.Recurso}.
 */
public interface RecursoService {
    /**
     * Save a recurso.
     *
     * @param recursoDTO the entity to save.
     * @return the persisted entity.
     */
    RecursoDTO save(RecursoDTO recursoDTO);

    /**
     * Updates a recurso.
     *
     * @param recursoDTO the entity to update.
     * @return the persisted entity.
     */
    RecursoDTO update(RecursoDTO recursoDTO);

    /**
     * Partially updates a recurso.
     *
     * @param recursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecursoDTO> partialUpdate(RecursoDTO recursoDTO);

    /**
     * Get all the recursos.
     *
     * @return the list of entities.
     */
    List<RecursoDTO> findAll();

    /**
     * Get the "id" recurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecursoDTO> findOne(Long id);

    /**
     * Delete the "id" recurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
