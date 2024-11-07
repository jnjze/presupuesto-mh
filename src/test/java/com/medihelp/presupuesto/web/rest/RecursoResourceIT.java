package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.RecursoAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static com.medihelp.presupuesto.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.Recurso;
import com.medihelp.presupuesto.repository.RecursoRepository;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
import com.medihelp.presupuesto.service.mapper.RecursoMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link RecursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecursoResourceIT {

    private static final String DEFAULT_MES = "AAAAAAAAAA";
    private static final String UPDATED_MES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoMapper recursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoMockMvc;

    private Recurso recurso;

    private Recurso insertedRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createEntity() {
        return new Recurso().mes(DEFAULT_MES).valor(DEFAULT_VALOR).observacion(DEFAULT_OBSERVACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createUpdatedEntity() {
        return new Recurso().mes(UPDATED_MES).valor(UPDATED_VALOR).observacion(UPDATED_OBSERVACION);
    }

    @BeforeEach
    public void initTest() {
        recurso = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecurso != null) {
            recursoRepository.delete(insertedRecurso);
            insertedRecurso = null;
        }
    }

    @Test
    @Transactional
    void createRecurso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);
        var returnedRecursoDTO = om.readValue(
            restRecursoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recursoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecursoDTO.class
        );

        // Validate the Recurso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecurso = recursoMapper.toEntity(returnedRecursoDTO);
        assertRecursoUpdatableFieldsEquals(returnedRecurso, getPersistedRecurso(returnedRecurso));

        insertedRecurso = returnedRecurso;
    }

    @Test
    @Transactional
    void createRecursoWithExistingId() throws Exception {
        // Create the Recurso with an existing ID
        recurso.setId(1L);
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecursos() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList
        restRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)));
    }

    @Test
    @Transactional
    void getRecurso() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        // Get the recurso
        restRecursoMockMvc
            .perform(get(ENTITY_API_URL_ID, recurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurso.getId().intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION));
    }

    @Test
    @Transactional
    void getNonExistingRecurso() throws Exception {
        // Get the recurso
        restRecursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecurso() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recurso
        Recurso updatedRecurso = recursoRepository.findById(recurso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecurso are not directly saved in db
        em.detach(updatedRecurso);
        updatedRecurso.mes(UPDATED_MES).valor(UPDATED_VALOR).observacion(UPDATED_OBSERVACION);
        RecursoDTO recursoDTO = recursoMapper.toDto(updatedRecurso);

        restRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recursoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecursoToMatchAllProperties(updatedRecurso);
    }

    @Test
    @Transactional
    void putNonExistingRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recursoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recurso using partial update
        Recurso partialUpdatedRecurso = new Recurso();
        partialUpdatedRecurso.setId(recurso.getId());

        partialUpdatedRecurso.observacion(UPDATED_OBSERVACION);

        restRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecurso))
            )
            .andExpect(status().isOk());

        // Validate the Recurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecursoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRecurso, recurso), getPersistedRecurso(recurso));
    }

    @Test
    @Transactional
    void fullUpdateRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recurso using partial update
        Recurso partialUpdatedRecurso = new Recurso();
        partialUpdatedRecurso.setId(recurso.getId());

        partialUpdatedRecurso.mes(UPDATED_MES).valor(UPDATED_VALOR).observacion(UPDATED_OBSERVACION);

        restRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecurso))
            )
            .andExpect(status().isOk());

        // Validate the Recurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecursoUpdatableFieldsEquals(partialUpdatedRecurso, getPersistedRecurso(partialUpdatedRecurso));
    }

    @Test
    @Transactional
    void patchNonExistingRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recurso.setId(longCount.incrementAndGet());

        // Create the Recurso
        RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecurso() throws Exception {
        // Initialize the database
        insertedRecurso = recursoRepository.saveAndFlush(recurso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recurso
        restRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, recurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recursoRepository.count();
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

    protected Recurso getPersistedRecurso(Recurso recurso) {
        return recursoRepository.findById(recurso.getId()).orElseThrow();
    }

    protected void assertPersistedRecursoToMatchAllProperties(Recurso expectedRecurso) {
        assertRecursoAllPropertiesEquals(expectedRecurso, getPersistedRecurso(expectedRecurso));
    }

    protected void assertPersistedRecursoToMatchUpdatableProperties(Recurso expectedRecurso) {
        assertRecursoAllUpdatablePropertiesEquals(expectedRecurso, getPersistedRecurso(expectedRecurso));
    }
}
