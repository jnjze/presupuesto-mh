package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.CentroCostoRepository;
import com.medihelp.presupuesto.service.CentroCostoService;
import com.medihelp.presupuesto.service.dto.CentroCostoDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.CentroCosto}.
 */
@RestController
@RequestMapping("/api/centro-costos")
public class CentroCostoResource {

    private static final Logger LOG = LoggerFactory.getLogger(CentroCostoResource.class);

    private static final String ENTITY_NAME = "centroCosto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroCostoService centroCostoService;

    private final CentroCostoRepository centroCostoRepository;

    public CentroCostoResource(CentroCostoService centroCostoService, CentroCostoRepository centroCostoRepository) {
        this.centroCostoService = centroCostoService;
        this.centroCostoRepository = centroCostoRepository;
    }

    /**
     * {@code POST  /centro-costos} : Create a new centroCosto.
     *
     * @param centroCostoDTO the centroCostoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroCostoDTO, or with status {@code 400 (Bad Request)} if the centroCosto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentroCostoDTO> createCentroCosto(@RequestBody CentroCostoDTO centroCostoDTO) throws URISyntaxException {
        LOG.debug("REST request to save CentroCosto : {}", centroCostoDTO);
        if (centroCostoDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroCosto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centroCostoDTO = centroCostoService.save(centroCostoDTO);
        return ResponseEntity.created(new URI("/api/centro-costos/" + centroCostoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, centroCostoDTO.getId().toString()))
            .body(centroCostoDTO);
    }

    /**
     * {@code PUT  /centro-costos/:id} : Updates an existing centroCosto.
     *
     * @param id the id of the centroCostoDTO to save.
     * @param centroCostoDTO the centroCostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroCostoDTO,
     * or with status {@code 400 (Bad Request)} if the centroCostoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroCostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentroCostoDTO> updateCentroCosto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentroCostoDTO centroCostoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CentroCosto : {}, {}", id, centroCostoDTO);
        if (centroCostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroCostoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroCostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centroCostoDTO = centroCostoService.update(centroCostoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centroCostoDTO.getId().toString()))
            .body(centroCostoDTO);
    }

    /**
     * {@code PATCH  /centro-costos/:id} : Partial updates given fields of an existing centroCosto, field will ignore if it is null
     *
     * @param id the id of the centroCostoDTO to save.
     * @param centroCostoDTO the centroCostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroCostoDTO,
     * or with status {@code 400 (Bad Request)} if the centroCostoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centroCostoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroCostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroCostoDTO> partialUpdateCentroCosto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentroCostoDTO centroCostoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CentroCosto partially : {}, {}", id, centroCostoDTO);
        if (centroCostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroCostoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroCostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroCostoDTO> result = centroCostoService.partialUpdate(centroCostoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centroCostoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-costos} : get all the centroCostos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroCostos in body.
     */
    @GetMapping("")
    public List<CentroCostoDTO> getAllCentroCostos() {
        LOG.debug("REST request to get all CentroCostos");
        return centroCostoService.findAll();
    }

    /**
     * {@code GET  /centro-costos/:id} : get the "id" centroCosto.
     *
     * @param id the id of the centroCostoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroCostoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroCostoDTO> getCentroCosto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CentroCosto : {}", id);
        Optional<CentroCostoDTO> centroCostoDTO = centroCostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroCostoDTO);
    }

    /**
     * {@code DELETE  /centro-costos/:id} : delete the "id" centroCosto.
     *
     * @param id the id of the centroCostoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentroCosto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CentroCosto : {}", id);
        centroCostoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
