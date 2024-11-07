package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.UnidadFuncionalDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.UnidadFuncional}.
 */
public interface UnidadFuncionalService {
    /**
     * Save a unidadFuncional.
     *
     * @param unidadFuncionalDTO the entity to save.
     * @return the persisted entity.
     */
    UnidadFuncionalDTO save(UnidadFuncionalDTO unidadFuncionalDTO);

    /**
     * Updates a unidadFuncional.
     *
     * @param unidadFuncionalDTO the entity to update.
     * @return the persisted entity.
     */
    UnidadFuncionalDTO update(UnidadFuncionalDTO unidadFuncionalDTO);

    /**
     * Partially updates a unidadFuncional.
     *
     * @param unidadFuncionalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UnidadFuncionalDTO> partialUpdate(UnidadFuncionalDTO unidadFuncionalDTO);

    /**
     * Get all the unidadFuncionals.
     *
     * @return the list of entities.
     */
    List<UnidadFuncionalDTO> findAll();

    /**
     * Get the "id" unidadFuncional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnidadFuncionalDTO> findOne(Long id);

    /**
     * Delete the "id" unidadFuncional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
