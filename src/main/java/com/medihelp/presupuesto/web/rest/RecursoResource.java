package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.RecursoRepository;
import com.medihelp.presupuesto.service.RecursoService;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.Recurso}.
 */
@RestController
@RequestMapping("/api/recursos")
public class RecursoResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecursoResource.class);

    private static final String ENTITY_NAME = "recurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursoService recursoService;

    private final RecursoRepository recursoRepository;

    public RecursoResource(RecursoService recursoService, RecursoRepository recursoRepository) {
        this.recursoService = recursoService;
        this.recursoRepository = recursoRepository;
    }

    /**
     * {@code POST  /recursos} : Create a new recurso.
     *
     * @param recursoDTO the recursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursoDTO, or with status {@code 400 (Bad Request)} if the recurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecursoDTO> createRecurso(@RequestBody RecursoDTO recursoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recurso : {}", recursoDTO);
        if (recursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recursoDTO = recursoService.save(recursoDTO);
        return ResponseEntity.created(new URI("/api/recursos/" + recursoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, recursoDTO.getId().toString()))
            .body(recursoDTO);
    }

    /**
     * {@code PUT  /recursos/:id} : Updates an existing recurso.
     *
     * @param id the id of the recursoDTO to save.
     * @param recursoDTO the recursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoDTO,
     * or with status {@code 400 (Bad Request)} if the recursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> updateRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecursoDTO recursoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recurso : {}, {}", id, recursoDTO);
        if (recursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recursoDTO = recursoService.update(recursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recursoDTO.getId().toString()))
            .body(recursoDTO);
    }

    /**
     * {@code PATCH  /recursos/:id} : Partial updates given fields of an existing recurso, field will ignore if it is null
     *
     * @param id the id of the recursoDTO to save.
     * @param recursoDTO the recursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoDTO,
     * or with status {@code 400 (Bad Request)} if the recursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecursoDTO> partialUpdateRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecursoDTO recursoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recurso partially : {}, {}", id, recursoDTO);
        if (recursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecursoDTO> result = recursoService.partialUpdate(recursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recursos} : get all the recursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursos in body.
     */
    @GetMapping("")
    public List<RecursoDTO> getAllRecursos() {
        LOG.debug("REST request to get all Recursos");
        return recursoService.findAll();
    }

    /**
     * {@code GET  /recursos/:id} : get the "id" recurso.
     *
     * @param id the id of the recursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> getRecurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recurso : {}", id);
        Optional<RecursoDTO> recursoDTO = recursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursoDTO);
    }

    /**
     * {@code DELETE  /recursos/:id} : delete the "id" recurso.
     *
     * @param id the id of the recursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recurso : {}", id);
        recursoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
