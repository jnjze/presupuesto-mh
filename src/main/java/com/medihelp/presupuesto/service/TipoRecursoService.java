package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.TipoRecurso}.
 */
public interface TipoRecursoService {
    /**
     * Save a tipoRecurso.
     *
     * @param tipoRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoRecursoDTO save(TipoRecursoDTO tipoRecursoDTO);

    /**
     * Updates a tipoRecurso.
     *
     * @param tipoRecursoDTO the entity to update.
     * @return the persisted entity.
     */
    TipoRecursoDTO update(TipoRecursoDTO tipoRecursoDTO);

    /**
     * Partially updates a tipoRecurso.
     *
     * @param tipoRecursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoRecursoDTO> partialUpdate(TipoRecursoDTO tipoRecursoDTO);

    /**
     * Get all the tipoRecursos.
     *
     * @return the list of entities.
     */
    List<TipoRecursoDTO> findAll();

    /**
     * Get the "id" tipoRecurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoRecursoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoRecurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
