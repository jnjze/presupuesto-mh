package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.SubPlanAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.SubPlan;
import com.medihelp.presupuesto.repository.SubPlanRepository;
import com.medihelp.presupuesto.service.dto.SubPlanDTO;
import com.medihelp.presupuesto.service.mapper.SubPlanMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubPlanResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubPlanRepository subPlanRepository;

    @Autowired
    private SubPlanMapper subPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubPlanMockMvc;

    private SubPlan subPlan;

    private SubPlan insertedSubPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubPlan createEntity() {
        return new SubPlan().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubPlan createUpdatedEntity() {
        return new SubPlan().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        subPlan = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubPlan != null) {
            subPlanRepository.delete(insertedSubPlan);
            insertedSubPlan = null;
        }
    }

    @Test
    @Transactional
    void createSubPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);
        var returnedSubPlanDTO = om.readValue(
            restSubPlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subPlanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubPlanDTO.class
        );

        // Validate the SubPlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSubPlan = subPlanMapper.toEntity(returnedSubPlanDTO);
        assertSubPlanUpdatableFieldsEquals(returnedSubPlan, getPersistedSubPlan(returnedSubPlan));

        insertedSubPlan = returnedSubPlan;
    }

    @Test
    @Transactional
    void createSubPlanWithExistingId() throws Exception {
        // Create the SubPlan with an existing ID
        subPlan.setId(1L);
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubPlans() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        // Get all the subPlanList
        restSubPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getSubPlan() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        // Get the subPlan
        restSubPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, subPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subPlan.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingSubPlan() throws Exception {
        // Get the subPlan
        restSubPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubPlan() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subPlan
        SubPlan updatedSubPlan = subPlanRepository.findById(subPlan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubPlan are not directly saved in db
        em.detach(updatedSubPlan);
        updatedSubPlan.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(updatedSubPlan);

        restSubPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subPlanDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubPlanToMatchAllProperties(updatedSubPlan);
    }

    @Test
    @Transactional
    void putNonExistingSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subPlanDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubPlanWithPatch() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subPlan using partial update
        SubPlan partialUpdatedSubPlan = new SubPlan();
        partialUpdatedSubPlan.setId(subPlan.getId());

        restSubPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubPlan))
            )
            .andExpect(status().isOk());

        // Validate the SubPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubPlanUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSubPlan, subPlan), getPersistedSubPlan(subPlan));
    }

    @Test
    @Transactional
    void fullUpdateSubPlanWithPatch() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subPlan using partial update
        SubPlan partialUpdatedSubPlan = new SubPlan();
        partialUpdatedSubPlan.setId(subPlan.getId());

        partialUpdatedSubPlan.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restSubPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubPlan))
            )
            .andExpect(status().isOk());

        // Validate the SubPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubPlanUpdatableFieldsEquals(partialUpdatedSubPlan, getPersistedSubPlan(partialUpdatedSubPlan));
    }

    @Test
    @Transactional
    void patchNonExistingSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subPlan.setId(longCount.incrementAndGet());

        // Create the SubPlan
        SubPlanDTO subPlanDTO = subPlanMapper.toDto(subPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubPlan() throws Exception {
        // Initialize the database
        insertedSubPlan = subPlanRepository.saveAndFlush(subPlan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subPlan
        restSubPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, subPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subPlanRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SubPlan getPersistedSubPlan(SubPlan subPlan) {
        return subPlanRepository.findById(subPlan.getId()).orElseThrow();
    }

    protected void assertPersistedSubPlanToMatchAllProperties(SubPlan expectedSubPlan) {
        assertSubPlanAllPropertiesEquals(expectedSubPlan, getPersistedSubPlan(expectedSubPlan));
    }

    protected void assertPersistedSubPlanToMatchUpdatableProperties(SubPlan expectedSubPlan) {
        assertSubPlanAllUpdatablePropertiesEquals(expectedSubPlan, getPersistedSubPlan(expectedSubPlan));
    }
}
