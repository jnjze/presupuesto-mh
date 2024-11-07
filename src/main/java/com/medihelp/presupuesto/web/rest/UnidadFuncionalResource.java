package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.UnidadFuncionalRepository;
import com.medihelp.presupuesto.service.UnidadFuncionalService;
import com.medihelp.presupuesto.service.dto.UnidadFuncionalDTO;
import com.medihelp.presupuesto.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.medihelp.presupuesto.domain.UnidadFuncional}.
 */
@RestController
@RequestMapping("/api/unidad-funcionals")
public class UnidadFuncionalResource {

    private static final Logger LOG = LoggerFactory.getLogger(UnidadFuncionalResource.class);

    private static final String ENTITY_NAME = "unidadFuncional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadFuncionalService unidadFuncionalService;

    private final UnidadFuncionalRepository unidadFuncionalRepository;

    public UnidadFuncionalResource(UnidadFuncionalService unidadFuncionalService, UnidadFuncionalRepository unidadFuncionalRepository) {
        this.unidadFuncionalService = unidadFuncionalService;
        this.unidadFuncionalRepository = unidadFuncionalRepository;
    }

    /**
     * {@code POST  /unidad-funcionals} : Create a new unidadFuncional.
     *
     * @param unidadFuncionalDTO the unidadFuncionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadFuncionalDTO, or with status {@code 400 (Bad Request)} if the unidadFuncional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UnidadFuncionalDTO> createUnidadFuncional(@RequestBody UnidadFuncionalDTO unidadFuncionalDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save UnidadFuncional : {}", unidadFuncionalDTO);
        if (unidadFuncionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadFuncional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        unidadFuncionalDTO = unidadFuncionalService.save(unidadFuncionalDTO);
        return ResponseEntity.created(new URI("/api/unidad-funcionals/" + unidadFuncionalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, unidadFuncionalDTO.getId().toString()))
            .body(unidadFuncionalDTO);
    }

    /**
     * {@code PUT  /unidad-funcionals/:id} : Updates an existing unidadFuncional.
     *
     * @param id the id of the unidadFuncionalDTO to save.
     * @param unidadFuncionalDTO the unidadFuncionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadFuncionalDTO,
     * or with status {@code 400 (Bad Request)} if the unidadFuncionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadFuncionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UnidadFuncionalDTO> updateUnidadFuncional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadFuncionalDTO unidadFuncionalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UnidadFuncional : {}, {}", id, unidadFuncionalDTO);
        if (unidadFuncionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadFuncionalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadFuncionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        unidadFuncionalDTO = unidadFuncionalService.update(unidadFuncionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadFuncionalDTO.getId().toString()))
            .body(unidadFuncionalDTO);
    }

    /**
     * {@code PATCH  /unidad-funcionals/:id} : Partial updates given fields of an existing unidadFuncional, field will ignore if it is null
     *
     * @param id the id of the unidadFuncionalDTO to save.
     * @param unidadFuncionalDTO the unidadFuncionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadFuncionalDTO,
     * or with status {@code 400 (Bad Request)} if the unidadFuncionalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the unidadFuncionalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidadFuncionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnidadFuncionalDTO> partialUpdateUnidadFuncional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadFuncionalDTO unidadFuncionalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UnidadFuncional partially : {}, {}", id, unidadFuncionalDTO);
        if (unidadFuncionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadFuncionalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadFuncionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnidadFuncionalDTO> result = unidadFuncionalService.partialUpdate(unidadFuncionalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadFuncionalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /unidad-funcionals} : get all the unidadFuncionals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadFuncionals in body.
     */
    @GetMapping("")
    public List<UnidadFuncionalDTO> getAllUnidadFuncionals() {
        LOG.debug("REST request to get all UnidadFuncionals");
        return unidadFuncionalService.findAll();
    }

    /**
     * {@code GET  /unidad-funcionals/:id} : get the "id" unidadFuncional.
     *
     * @param id the id of the unidadFuncionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadFuncionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UnidadFuncionalDTO> getUnidadFuncional(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UnidadFuncional : {}", id);
        Optional<UnidadFuncionalDTO> unidadFuncionalDTO = unidadFuncionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadFuncionalDTO);
    }

    /**
     * {@code DELETE  /unidad-funcionals/:id} : delete the "id" unidadFuncional.
     *
     * @param id the id of the unidadFuncionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnidadFuncional(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UnidadFuncional : {}", id);
        unidadFuncionalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
