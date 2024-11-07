package com.medihelp.presupuesto.web.rest;

import com.medihelp.presupuesto.repository.PlanRepository;
import com.medihelp.presupuesto.service.PlanService;
import com.medihelp.presupuesto.service.dto.PlanDTO;
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
 * REST controller for managing {@link com.medihelp.presupuesto.domain.Plan}.
 */
@RestController
@RequestMapping("/api/plans")
public class PlanResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "plan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanService planService;

    private final PlanRepository planRepository;

    public PlanResource(PlanService planService, PlanRepository planRepository) {
        this.planService = planService;
        this.planRepository = planRepository;
    }

    /**
     * {@code POST  /plans} : Create a new plan.
     *
     * @param planDTO the planDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planDTO, or with status {@code 400 (Bad Request)} if the plan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO planDTO) throws URISyntaxException {
        LOG.debug("REST request to save Plan : {}", planDTO);
        if (planDTO.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planDTO = planService.save(planDTO);
        return ResponseEntity.created(new URI("/api/plans/" + planDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, planDTO.getId().toString()))
            .body(planDTO);
    }

    /**
     * {@code PUT  /plans/:id} : Updates an existing plan.
     *
     * @param id the id of the planDTO to save.
     * @param planDTO the planDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planDTO,
     * or with status {@code 400 (Bad Request)} if the planDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable(value = "id", required = false) final Long id, @RequestBody PlanDTO planDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Plan : {}, {}", id, planDTO);
        if (planDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planDTO = planService.update(planDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planDTO.getId().toString()))
            .body(planDTO);
    }

    /**
     * {@code PATCH  /plans/:id} : Partial updates given fields of an existing plan, field will ignore if it is null
     *
     * @param id the id of the planDTO to save.
     * @param planDTO the planDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planDTO,
     * or with status {@code 400 (Bad Request)} if the planDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanDTO> partialUpdatePlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanDTO planDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Plan partially : {}, {}", id, planDTO);
        if (planDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanDTO> result = planService.partialUpdate(planDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plans} : get all the plans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("")
    public List<PlanDTO> getAllPlans() {
        LOG.debug("REST request to get all Plans");
        return planService.findAll();
    }

    /**
     * {@code GET  /plans/:id} : get the "id" plan.
     *
     * @param id the id of the planDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Plan : {}", id);
        Optional<PlanDTO> planDTO = planService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planDTO);
    }

    /**
     * {@code DELETE  /plans/:id} : delete the "id" plan.
     *
     * @param id the id of the planDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Plan : {}", id);
        planService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
