package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.TipoRecursoRepository;
import com.medihelp.presupuesto.service.TipoRecursoService;
import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.TipoRecurso}.
 */
@RestController
@RequestMapping("/api/tipo-recursos")
public class TipoRecursoResource {

    private static final Logger LOG = LoggerFactory.getLogger(TipoRecursoResource.class);

    private static final String ENTITY_NAME = "tipoRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoRecursoService tipoRecursoService;

    private final TipoRecursoRepository tipoRecursoRepository;

    public TipoRecursoResource(TipoRecursoService tipoRecursoService, TipoRecursoRepository tipoRecursoRepository) {
        this.tipoRecursoService = tipoRecursoService;
        this.tipoRecursoRepository = tipoRecursoRepository;
    }

    /**
     * {@code POST  /tipo-recursos} : Create a new tipoRecurso.
     *
     * @param tipoRecursoDTO the tipoRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoRecursoDTO, or with status {@code 400 (Bad Request)} if the tipoRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TipoRecursoDTO> createTipoRecurso(@RequestBody TipoRecursoDTO tipoRecursoDTO) throws URISyntaxException {
        LOG.debug("REST request to save TipoRecurso : {}", tipoRecursoDTO);
        if (tipoRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tipoRecursoDTO = tipoRecursoService.save(tipoRecursoDTO);
        return ResponseEntity.created(new URI("/api/tipo-recursos/" + tipoRecursoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tipoRecursoDTO.getId().toString()))
            .body(tipoRecursoDTO);
    }

    /**
     * {@code PUT  /tipo-recursos/:id} : Updates an existing tipoRecurso.
     *
     * @param id the id of the tipoRecursoDTO to save.
     * @param tipoRecursoDTO the tipoRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoRecursoDTO> updateTipoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecursoDTO tipoRecursoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TipoRecurso : {}, {}", id, tipoRecursoDTO);
        if (tipoRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tipoRecursoDTO = tipoRecursoService.update(tipoRecursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoRecursoDTO.getId().toString()))
            .body(tipoRecursoDTO);
    }

    /**
     * {@code PATCH  /tipo-recursos/:id} : Partial updates given fields of an existing tipoRecurso, field will ignore if it is null
     *
     * @param id the id of the tipoRecursoDTO to save.
     * @param tipoRecursoDTO the tipoRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoRecursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoRecursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoRecursoDTO> partialUpdateTipoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecursoDTO tipoRecursoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TipoRecurso partially : {}, {}", id, tipoRecursoDTO);
        if (tipoRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoRecursoDTO> result = tipoRecursoService.partialUpdate(tipoRecursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoRecursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-recursos} : get all the tipoRecursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRecursos in body.
     */
    @GetMapping("")
    public List<TipoRecursoDTO> getAllTipoRecursos() {
        LOG.debug("REST request to get all TipoRecursos");
        return tipoRecursoService.findAll();
    }

    /**
     * {@code GET  /tipo-recursos/:id} : get the "id" tipoRecurso.
     *
     * @param id the id of the tipoRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoRecursoDTO> getTipoRecurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TipoRecurso : {}", id);
        Optional<TipoRecursoDTO> tipoRecursoDTO = tipoRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRecursoDTO);
    }

    /**
     * {@code DELETE  /tipo-recursos/:id} : delete the "id" tipoRecurso.
     *
     * @param id the id of the tipoRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoRecurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TipoRecurso : {}", id);
        tipoRecursoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
