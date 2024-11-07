package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.UnidadFuncionalAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.UnidadFuncional;
import com.medihelp.presupuesto.repository.UnidadFuncionalRepository;
import com.medihelp.presupuesto.service.dto.UnidadFuncionalDTO;
import com.medihelp.presupuesto.service.mapper.UnidadFuncionalMapper;
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
 * Integration tests for the {@link UnidadFuncionalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadFuncionalResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidad-funcionals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UnidadFuncionalRepository unidadFuncionalRepository;

    @Autowired
    private UnidadFuncionalMapper unidadFuncionalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadFuncionalMockMvc;

    private UnidadFuncional unidadFuncional;

    private UnidadFuncional insertedUnidadFuncional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadFuncional createEntity() {
        return new UnidadFuncional().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadFuncional createUpdatedEntity() {
        return new UnidadFuncional().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        unidadFuncional = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedUnidadFuncional != null) {
            unidadFuncionalRepository.delete(insertedUnidadFuncional);
            insertedUnidadFuncional = null;
        }
    }

    @Test
    @Transactional
    void createUnidadFuncional() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);
        var returnedUnidadFuncionalDTO = om.readValue(
            restUnidadFuncionalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unidadFuncionalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UnidadFuncionalDTO.class
        );

        // Validate the UnidadFuncional in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUnidadFuncional = unidadFuncionalMapper.toEntity(returnedUnidadFuncionalDTO);
        assertUnidadFuncionalUpdatableFieldsEquals(returnedUnidadFuncional, getPersistedUnidadFuncional(returnedUnidadFuncional));

        insertedUnidadFuncional = returnedUnidadFuncional;
    }

    @Test
    @Transactional
    void createUnidadFuncionalWithExistingId() throws Exception {
        // Create the UnidadFuncional with an existing ID
        unidadFuncional.setId(1L);
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadFuncionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unidadFuncionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidadFuncionals() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        // Get all the unidadFuncionalList
        restUnidadFuncionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadFuncional.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getUnidadFuncional() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        // Get the unidadFuncional
        restUnidadFuncionalMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadFuncional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadFuncional.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingUnidadFuncional() throws Exception {
        // Get the unidadFuncional
        restUnidadFuncionalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUnidadFuncional() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unidadFuncional
        UnidadFuncional updatedUnidadFuncional = unidadFuncionalRepository.findById(unidadFuncional.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUnidadFuncional are not directly saved in db
        em.detach(updatedUnidadFuncional);
        updatedUnidadFuncional.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(updatedUnidadFuncional);

        restUnidadFuncionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadFuncionalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(unidadFuncionalDTO))
            )
            .andExpect(status().isOk());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUnidadFuncionalToMatchAllProperties(updatedUnidadFuncional);
    }

    @Test
    @Transactional
    void putNonExistingUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadFuncionalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(unidadFuncionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(unidadFuncionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(unidadFuncionalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadFuncionalWithPatch() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unidadFuncional using partial update
        UnidadFuncional partialUpdatedUnidadFuncional = new UnidadFuncional();
        partialUpdatedUnidadFuncional.setId(unidadFuncional.getId());

        partialUpdatedUnidadFuncional.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);

        restUnidadFuncionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadFuncional.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUnidadFuncional))
            )
            .andExpect(status().isOk());

        // Validate the UnidadFuncional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUnidadFuncionalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUnidadFuncional, unidadFuncional),
            getPersistedUnidadFuncional(unidadFuncional)
        );
    }

    @Test
    @Transactional
    void fullUpdateUnidadFuncionalWithPatch() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the unidadFuncional using partial update
        UnidadFuncional partialUpdatedUnidadFuncional = new UnidadFuncional();
        partialUpdatedUnidadFuncional.setId(unidadFuncional.getId());

        partialUpdatedUnidadFuncional.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restUnidadFuncionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadFuncional.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUnidadFuncional))
            )
            .andExpect(status().isOk());

        // Validate the UnidadFuncional in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUnidadFuncionalUpdatableFieldsEquals(
            partialUpdatedUnidadFuncional,
            getPersistedUnidadFuncional(partialUpdatedUnidadFuncional)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadFuncionalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(unidadFuncionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(unidadFuncionalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadFuncional() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        unidadFuncional.setId(longCount.incrementAndGet());

        // Create the UnidadFuncional
        UnidadFuncionalDTO unidadFuncionalDTO = unidadFuncionalMapper.toDto(unidadFuncional);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadFuncionalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(unidadFuncionalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadFuncional in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadFuncional() throws Exception {
        // Initialize the database
        insertedUnidadFuncional = unidadFuncionalRepository.saveAndFlush(unidadFuncional);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the unidadFuncional
        restUnidadFuncionalMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadFuncional.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return unidadFuncionalRepository.count();
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

    protected UnidadFuncional getPersistedUnidadFuncional(UnidadFuncional unidadFuncional) {
        return unidadFuncionalRepository.findById(unidadFuncional.getId()).orElseThrow();
    }

    protected void assertPersistedUnidadFuncionalToMatchAllProperties(UnidadFuncional expectedUnidadFuncional) {
        assertUnidadFuncionalAllPropertiesEquals(expectedUnidadFuncional, getPersistedUnidadFuncional(expectedUnidadFuncional));
    }

    protected void assertPersistedUnidadFuncionalToMatchUpdatableProperties(UnidadFuncional expectedUnidadFuncional) {
        assertUnidadFuncionalAllUpdatablePropertiesEquals(expectedUnidadFuncional, getPersistedUnidadFuncional(expectedUnidadFuncional));
    }
}
