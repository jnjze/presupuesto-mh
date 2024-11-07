package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.RubroRepository;
import com.medihelp.presupuesto.service.RubroService;
import com.medihelp.presupuesto.service.dto.RubroDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.Rubro}.
 */
@RestController
@RequestMapping("/api/rubros")
public class RubroResource {

    private static final Logger LOG = LoggerFactory.getLogger(RubroResource.class);

    private static final String ENTITY_NAME = "rubro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RubroService rubroService;

    private final RubroRepository rubroRepository;

    public RubroResource(RubroService rubroService, RubroRepository rubroRepository) {
        this.rubroService = rubroService;
        this.rubroRepository = rubroRepository;
    }

    /**
     * {@code POST  /rubros} : Create a new rubro.
     *
     * @param rubroDTO the rubroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rubroDTO, or with status {@code 400 (Bad Request)} if the rubro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RubroDTO> createRubro(@RequestBody RubroDTO rubroDTO) throws URISyntaxException {
        LOG.debug("REST request to save Rubro : {}", rubroDTO);
        if (rubroDTO.getId() != null) {
            throw new BadRequestAlertException("A new rubro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rubroDTO = rubroService.save(rubroDTO);
        return ResponseEntity.created(new URI("/api/rubros/" + rubroDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rubroDTO.getId().toString()))
            .body(rubroDTO);
    }

    /**
     * {@code PUT  /rubros/:id} : Updates an existing rubro.
     *
     * @param id the id of the rubroDTO to save.
     * @param rubroDTO the rubroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rubroDTO,
     * or with status {@code 400 (Bad Request)} if the rubroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rubroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RubroDTO> updateRubro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RubroDTO rubroDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Rubro : {}, {}", id, rubroDTO);
        if (rubroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rubroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rubroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rubroDTO = rubroService.update(rubroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rubroDTO.getId().toString()))
            .body(rubroDTO);
    }

    /**
     * {@code PATCH  /rubros/:id} : Partial updates given fields of an existing rubro, field will ignore if it is null
     *
     * @param id the id of the rubroDTO to save.
     * @param rubroDTO the rubroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rubroDTO,
     * or with status {@code 400 (Bad Request)} if the rubroDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rubroDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rubroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RubroDTO> partialUpdateRubro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RubroDTO rubroDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Rubro partially : {}, {}", id, rubroDTO);
        if (rubroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rubroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rubroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RubroDTO> result = rubroService.partialUpdate(rubroDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rubroDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rubros} : get all the rubros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rubros in body.
     */
    @GetMapping("")
    public List<RubroDTO> getAllRubros() {
        LOG.debug("REST request to get all Rubros");
        return rubroService.findAll();
    }

    /**
     * {@code GET  /rubros/:id} : get the "id" rubro.
     *
     * @param id the id of the rubroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rubroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RubroDTO> getRubro(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Rubro : {}", id);
        Optional<RubroDTO> rubroDTO = rubroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubroDTO);
    }

    /**
     * {@code DELETE  /rubros/:id} : delete the "id" rubro.
     *
     * @param id the id of the rubroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRubro(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Rubro : {}", id);
        rubroService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
