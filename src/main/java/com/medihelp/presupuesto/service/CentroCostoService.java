package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.service.dto.CentroCostoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medihelp.presupuesto.domain.CentroCosto}.
 */
public interface CentroCostoService {
    /**
     * Save a centroCosto.
     *
     * @param centroCostoDTO the entity to save.
     * @return the persisted entity.
     */
    CentroCostoDTO save(CentroCostoDTO centroCostoDTO);

    /**
     * Updates a centroCosto.
     *
     * @param centroCostoDTO the entity to update.
     * @return the persisted entity.
     */
    CentroCostoDTO update(CentroCostoDTO centroCostoDTO);

    /**
     * Partially updates a centroCosto.
     *
     * @param centroCostoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CentroCostoDTO> partialUpdate(CentroCostoDTO centroCostoDTO);

    /**
     * Get all the centroCostos.
     *
     * @return the list of entities.
     */
    List<CentroCostoDTO> findAll();

    /**
     * Get the "id" centroCosto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentroCostoDTO> findOne(Long id);

    /**
     * Delete the "id" centroCosto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
