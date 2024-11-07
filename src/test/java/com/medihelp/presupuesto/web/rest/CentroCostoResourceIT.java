package com.medihelp.presupuesto.web.rest;

import static com.medihelp.presupuesto.domain.CentroCostoAsserts.*;
import static com.medihelp.presupuesto.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medihelp.presupuesto.IntegrationTest;
import com.medihelp.presupuesto.domain.CentroCosto;
import com.medihelp.presupuesto.repository.CentroCostoRepository;
import com.medihelp.presupuesto.service.dto.CentroCostoDTO;
import com.medihelp.presupuesto.service.mapper.CentroCostoMapper;
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
 * Integration tests for the {@link CentroCostoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentroCostoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-costos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentroCostoRepository centroCostoRepository;

    @Autowired
    private CentroCostoMapper centroCostoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroCostoMockMvc;

    private CentroCosto centroCosto;

    private CentroCosto insertedCentroCosto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroCosto createEntity() {
        return new CentroCosto().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroCosto createUpdatedEntity() {
        return new CentroCosto().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        centroCosto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentroCosto != null) {
            centroCostoRepository.delete(insertedCentroCosto);
            insertedCentroCosto = null;
        }
    }

    @Test
    @Transactional
    void createCentroCosto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);
        var returnedCentroCostoDTO = om.readValue(
            restCentroCostoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroCostoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentroCostoDTO.class
        );

        // Validate the CentroCosto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCentroCosto = centroCostoMapper.toEntity(returnedCentroCostoDTO);
        assertCentroCostoUpdatableFieldsEquals(returnedCentroCosto, getPersistedCentroCosto(returnedCentroCosto));

        insertedCentroCosto = returnedCentroCosto;
    }

    @Test
    @Transactional
    void createCentroCostoWithExistingId() throws Exception {
        // Create the CentroCosto with an existing ID
        centroCosto.setId(1L);
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroCostoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroCostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCentroCostos() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        // Get all the centroCostoList
        restCentroCostoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroCosto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getCentroCosto() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        // Get the centroCosto
        restCentroCostoMockMvc
            .perform(get(ENTITY_API_URL_ID, centroCosto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroCosto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingCentroCosto() throws Exception {
        // Get the centroCosto
        restCentroCostoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroCosto() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroCosto
        CentroCosto updatedCentroCosto = centroCostoRepository.findById(centroCosto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentroCosto are not directly saved in db
        em.detach(updatedCentroCosto);
        updatedCentroCosto.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(updatedCentroCosto);

        restCentroCostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroCostoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroCostoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentroCostoToMatchAllProperties(updatedCentroCosto);
    }

    @Test
    @Transactional
    void putNonExistingCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroCostoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroCostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroCostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroCostoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroCostoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroCosto using partial update
        CentroCosto partialUpdatedCentroCosto = new CentroCosto();
        partialUpdatedCentroCosto.setId(centroCosto.getId());

        partialUpdatedCentroCosto.nombre(UPDATED_NOMBRE);

        restCentroCostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroCosto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroCosto))
            )
            .andExpect(status().isOk());

        // Validate the CentroCosto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroCostoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentroCosto, centroCosto),
            getPersistedCentroCosto(centroCosto)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentroCostoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroCosto using partial update
        CentroCosto partialUpdatedCentroCosto = new CentroCosto();
        partialUpdatedCentroCosto.setId(centroCosto.getId());

        partialUpdatedCentroCosto.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCentroCostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroCosto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroCosto))
            )
            .andExpect(status().isOk());

        // Validate the CentroCosto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroCostoUpdatableFieldsEquals(partialUpdatedCentroCosto, getPersistedCentroCosto(partialUpdatedCentroCosto));
    }

    @Test
    @Transactional
    void patchNonExistingCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroCostoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroCostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroCostoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroCosto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroCosto.setId(longCount.incrementAndGet());

        // Create the CentroCosto
        CentroCostoDTO centroCostoDTO = centroCostoMapper.toDto(centroCosto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroCostoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centroCostoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroCosto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroCosto() throws Exception {
        // Initialize the database
        insertedCentroCosto = centroCostoRepository.saveAndFlush(centroCosto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centroCosto
        restCentroCostoMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroCosto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centroCostoRepository.count();
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

    protected CentroCosto getPersistedCentroCosto(CentroCosto centroCosto) {
        return centroCostoRepository.findById(centroCosto.getId()).orElseThrow();
    }

    protected void assertPersistedCentroCostoToMatchAllProperties(CentroCosto expectedCentroCosto) {
        assertCentroCostoAllPropertiesEquals(expectedCentroCosto, getPersistedCentroCosto(expectedCentroCosto));
    }

    protected void assertPersistedCentroCostoToMatchUpdatableProperties(CentroCosto expectedCentroCosto) {
        assertCentroCostoAllUpdatablePropertiesEquals(expectedCentroCosto, getPersistedCentroCosto(expectedCentroCosto));
    }
}
