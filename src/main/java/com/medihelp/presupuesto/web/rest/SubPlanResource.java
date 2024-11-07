package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.SubPlanRepository;
import com.medihelp.presupuesto.service.SubPlanService;
import com.medihelp.presupuesto.service.dto.SubPlanDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.SubPlan}.
 */
@RestController
@RequestMapping("/api/sub-plans")
public class SubPlanResource {

    private static final Logger LOG = LoggerFactory.getLogger(SubPlanResource.class);

    private static final String ENTITY_NAME = "subPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubPlanService subPlanService;

    private final SubPlanRepository subPlanRepository;

    public SubPlanResource(SubPlanService subPlanService, SubPlanRepository subPlanRepository) {
        this.subPlanService = subPlanService;
        this.subPlanRepository = subPlanRepository;
    }

    /**
     * {@code POST  /sub-plans} : Create a new subPlan.
     *
     * @param subPlanDTO the subPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subPlanDTO, or with status {@code 400 (Bad Request)} if the subPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubPlanDTO> createSubPlan(@RequestBody SubPlanDTO subPlanDTO) throws URISyntaxException {
        LOG.debug("REST request to save SubPlan : {}", subPlanDTO);
        if (subPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new subPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subPlanDTO = subPlanService.save(subPlanDTO);
        return ResponseEntity.created(new URI("/api/sub-plans/" + subPlanDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subPlanDTO.getId().toString()))
            .body(subPlanDTO);
    }

    /**
     * {@code PUT  /sub-plans/:id} : Updates an existing subPlan.
     *
     * @param id the id of the subPlanDTO to save.
     * @param subPlanDTO the subPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subPlanDTO,
     * or with status {@code 400 (Bad Request)} if the subPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubPlanDTO> updateSubPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubPlanDTO subPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SubPlan : {}, {}", id, subPlanDTO);
        if (subPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subPlanDTO = subPlanService.update(subPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subPlanDTO.getId().toString()))
            .body(subPlanDTO);
    }

    /**
     * {@code PATCH  /sub-plans/:id} : Partial updates given fields of an existing subPlan, field will ignore if it is null
     *
     * @param id the id of the subPlanDTO to save.
     * @param subPlanDTO the subPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subPlanDTO,
     * or with status {@code 400 (Bad Request)} if the subPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubPlanDTO> partialUpdateSubPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubPlanDTO subPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SubPlan partially : {}, {}", id, subPlanDTO);
        if (subPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubPlanDTO> result = subPlanService.partialUpdate(subPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-plans} : get all the subPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subPlans in body.
     */
    @GetMapping("")
    public List<SubPlanDTO> getAllSubPlans() {
        LOG.debug("REST request to get all SubPlans");
        return subPlanService.findAll();
    }

    /**
     * {@code GET  /sub-plans} : get all the subPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subPlans in body.
     */
    @GetMapping(value = "/plan/{planId}")
    public List<SubPlanDTO> getSubPlansByPlanId(@PathVariable(value = "planId") final Long planId) {
        LOG.debug("REST request to get all SubPlans");
        return subPlanService.findByPlanId(planId);
    }

    /**
     * {@code GET  /sub-plans/:id} : get the "id" subPlan.
     *
     * @param id the id of the subPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubPlanDTO> getSubPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SubPlan : {}", id);
        Optional<SubPlanDTO> subPlanDTO = subPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subPlanDTO);
    }

    /**
     * {@code DELETE  /sub-plans/:id} : delete the "id" subPlan.
     *
     * @param id the id of the subPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SubPlan : {}", id);
        subPlanService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
