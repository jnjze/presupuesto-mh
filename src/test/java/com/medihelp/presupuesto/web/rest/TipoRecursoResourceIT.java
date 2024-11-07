package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.TipoRecursoAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.TipoRecurso;
import com.medihelp.presupuesto.repository.TipoRecursoRepository;
import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
import com.medihelp.presupuesto.service.mapper.TipoRecursoMapper;
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
 * Integration tests for the {@link TipoRecursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoRecursoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-recursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TipoRecursoRepository tipoRecursoRepository;

    @Autowired
    private TipoRecursoMapper tipoRecursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoRecursoMockMvc;

    private TipoRecurso tipoRecurso;

    private TipoRecurso insertedTipoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createEntity() {
        return new TipoRecurso().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createUpdatedEntity() {
        return new TipoRecurso().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        tipoRecurso = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTipoRecurso != null) {
            tipoRecursoRepository.delete(insertedTipoRecurso);
            insertedTipoRecurso = null;
        }
    }

    @Test
    @Transactional
    void createTipoRecurso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);
        var returnedTipoRecursoDTO = om.readValue(
            restTipoRecursoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoRecursoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TipoRecursoDTO.class
        );

        // Validate the TipoRecurso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTipoRecurso = tipoRecursoMapper.toEntity(returnedTipoRecursoDTO);
        assertTipoRecursoUpdatableFieldsEquals(returnedTipoRecurso, getPersistedTipoRecurso(returnedTipoRecurso));

        insertedTipoRecurso = returnedTipoRecurso;
    }

    @Test
    @Transactional
    void createTipoRecursoWithExistingId() throws Exception {
        // Create the TipoRecurso with an existing ID
        tipoRecurso.setId(1L);
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoRecursos() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getTipoRecurso() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get the tipoRecurso
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTipoRecurso() throws Exception {
        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoRecurso() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoRecurso
        TipoRecurso updatedTipoRecurso = tipoRecursoRepository.findById(tipoRecurso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTipoRecurso are not directly saved in db
        em.detach(updatedTipoRecurso);
        updatedTipoRecurso.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(updatedTipoRecurso);

        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoRecursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoRecursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTipoRecursoToMatchAllProperties(updatedTipoRecurso);
    }

    @Test
    @Transactional
    void putNonExistingTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoRecursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoRecursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoRecurso using partial update
        TipoRecurso partialUpdatedTipoRecurso = new TipoRecurso();
        partialUpdatedTipoRecurso.setId(tipoRecurso.getId());

        partialUpdatedTipoRecurso.nombre(UPDATED_NOMBRE);

        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoRecursoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTipoRecurso, tipoRecurso),
            getPersistedTipoRecurso(tipoRecurso)
        );
    }

    @Test
    @Transactional
    void fullUpdateTipoRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoRecurso using partial update
        TipoRecurso partialUpdatedTipoRecurso = new TipoRecurso();
        partialUpdatedTipoRecurso.setId(tipoRecurso.getId());

        partialUpdatedTipoRecurso.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoRecursoUpdatableFieldsEquals(partialUpdatedTipoRecurso, getPersistedTipoRecurso(partialUpdatedTipoRecurso));
    }

    @Test
    @Transactional
    void patchNonExistingTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoRecursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoRecurso.setId(longCount.incrementAndGet());

        // Create the TipoRecurso
        TipoRecursoDTO tipoRecursoDTO = tipoRecursoMapper.toDto(tipoRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tipoRecursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoRecurso() throws Exception {
        // Initialize the database
        insertedTipoRecurso = tipoRecursoRepository.saveAndFlush(tipoRecurso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tipoRecurso
        restTipoRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoRecurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tipoRecursoRepository.count();
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

    protected TipoRecurso getPersistedTipoRecurso(TipoRecurso tipoRecurso) {
        return tipoRecursoRepository.findById(tipoRecurso.getId()).orElseThrow();
    }

    protected void assertPersistedTipoRecursoToMatchAllProperties(TipoRecurso expectedTipoRecurso) {
        assertTipoRecursoAllPropertiesEquals(expectedTipoRecurso, getPersistedTipoRecurso(expectedTipoRecurso));
    }

    protected void assertPersistedTipoRecursoToMatchUpdatableProperties(TipoRecurso expectedTipoRecurso) {
        assertTipoRecursoAllUpdatablePropertiesEquals(expectedTipoRecurso, getPersistedTipoRecurso(expectedTipoRecurso));
    }
}
