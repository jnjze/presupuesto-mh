package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.RubroAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.Rubro;
import com.medihelp.presupuesto.repository.RubroRepository;
import com.medihelp.presupuesto.service.dto.RubroDTO;
import com.medihelp.presupuesto.service.mapper.RubroMapper;
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
 * Integration tests for the {@link RubroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RubroResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rubros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private RubroMapper rubroMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRubroMockMvc;

    private Rubro rubro;

    private Rubro insertedRubro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubro createEntity() {
        return new Rubro().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubro createUpdatedEntity() {
        return new Rubro().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        rubro = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRubro != null) {
            rubroRepository.delete(insertedRubro);
            insertedRubro = null;
        }
    }

    @Test
    @Transactional
    void createRubro() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);
        var returnedRubroDTO = om.readValue(
            restRubroMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rubroDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RubroDTO.class
        );

        // Validate the Rubro in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRubro = rubroMapper.toEntity(returnedRubroDTO);
        assertRubroUpdatableFieldsEquals(returnedRubro, getPersistedRubro(returnedRubro));

        insertedRubro = returnedRubro;
    }

    @Test
    @Transactional
    void createRubroWithExistingId() throws Exception {
        // Create the Rubro with an existing ID
        rubro.setId(1L);
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rubroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRubros() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        // Get all the rubroList
        restRubroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubro.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getRubro() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        // Get the rubro
        restRubroMockMvc
            .perform(get(ENTITY_API_URL_ID, rubro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rubro.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingRubro() throws Exception {
        // Get the rubro
        restRubroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRubro() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rubro
        Rubro updatedRubro = rubroRepository.findById(rubro.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRubro are not directly saved in db
        em.detach(updatedRubro);
        updatedRubro.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        RubroDTO rubroDTO = rubroMapper.toDto(updatedRubro);

        restRubroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rubroDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rubroDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRubroToMatchAllProperties(updatedRubro);
    }

    @Test
    @Transactional
    void putNonExistingRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rubroDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rubroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rubroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rubroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRubroWithPatch() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rubro using partial update
        Rubro partialUpdatedRubro = new Rubro();
        partialUpdatedRubro.setId(rubro.getId());

        partialUpdatedRubro.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restRubroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRubro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRubro))
            )
            .andExpect(status().isOk());

        // Validate the Rubro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRubroUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRubro, rubro), getPersistedRubro(rubro));
    }

    @Test
    @Transactional
    void fullUpdateRubroWithPatch() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rubro using partial update
        Rubro partialUpdatedRubro = new Rubro();
        partialUpdatedRubro.setId(rubro.getId());

        partialUpdatedRubro.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restRubroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRubro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRubro))
            )
            .andExpect(status().isOk());

        // Validate the Rubro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRubroUpdatableFieldsEquals(partialUpdatedRubro, getPersistedRubro(partialUpdatedRubro));
    }

    @Test
    @Transactional
    void patchNonExistingRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rubroDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rubroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rubroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRubro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rubro.setId(longCount.incrementAndGet());

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRubroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rubroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rubro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRubro() throws Exception {
        // Initialize the database
        insertedRubro = rubroRepository.saveAndFlush(rubro);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rubro
        restRubroMockMvc
            .perform(delete(ENTITY_API_URL_ID, rubro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rubroRepository.count();
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

    protected Rubro getPersistedRubro(Rubro rubro) {
        return rubroRepository.findById(rubro.getId()).orElseThrow();
    }

    protected void assertPersistedRubroToMatchAllProperties(Rubro expectedRubro) {
        assertRubroAllPropertiesEquals(expectedRubro, getPersistedRubro(expectedRubro));
    }

    protected void assertPersistedRubroToMatchUpdatableProperties(Rubro expectedRubro) {
        assertRubroAllUpdatablePropertiesEquals(expectedRubro, getPersistedRubro(expectedRubro));
    }
}
