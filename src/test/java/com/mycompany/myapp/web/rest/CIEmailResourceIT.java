package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CIEmail;
import com.mycompany.myapp.repository.CIEmailRepository;
import com.mycompany.myapp.service.dto.CIEmailDTO;
import com.mycompany.myapp.service.mapper.CIEmailMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CIEmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CIEmailResourceIT {

    private static final String DEFAULT_EMAIL = "6~@m.:i";
    private static final String UPDATED_EMAIL = "I]VN=@?.}Ly47";

    private static final String ENTITY_API_URL = "/api/ci-emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CIEmailRepository cIEmailRepository;

    @Autowired
    private CIEmailMapper cIEmailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCIEmailMockMvc;

    private CIEmail cIEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIEmail createEntity(EntityManager em) {
        CIEmail cIEmail = new CIEmail().email(DEFAULT_EMAIL);
        return cIEmail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIEmail createUpdatedEntity(EntityManager em) {
        CIEmail cIEmail = new CIEmail().email(UPDATED_EMAIL);
        return cIEmail;
    }

    @BeforeEach
    public void initTest() {
        cIEmail = createEntity(em);
    }

    @Test
    @Transactional
    void createCIEmail() throws Exception {
        int databaseSizeBeforeCreate = cIEmailRepository.findAll().size();
        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);
        restCIEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeCreate + 1);
        CIEmail testCIEmail = cIEmailList.get(cIEmailList.size() - 1);
        assertThat(testCIEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createCIEmailWithExistingId() throws Exception {
        // Create the CIEmail with an existing ID
        cIEmail.setId(1L);
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        int databaseSizeBeforeCreate = cIEmailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIEmailRepository.findAll().size();
        // set the field null
        cIEmail.setEmail(null);

        // Create the CIEmail, which fails.
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        restCIEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIEmailDTO)))
            .andExpect(status().isBadRequest());

        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCIEmails() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        // Get all the cIEmailList
        restCIEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getCIEmail() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        // Get the cIEmail
        restCIEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, cIEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIEmail.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingCIEmail() throws Exception {
        // Get the cIEmail
        restCIEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCIEmail() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();

        // Update the cIEmail
        CIEmail updatedCIEmail = cIEmailRepository.findById(cIEmail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCIEmail are not directly saved in db
        em.detach(updatedCIEmail);
        updatedCIEmail.email(UPDATED_EMAIL);
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(updatedCIEmail);

        restCIEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isOk());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
        CIEmail testCIEmail = cIEmailList.get(cIEmailList.size() - 1);
        assertThat(testCIEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIEmailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCIEmailWithPatch() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();

        // Update the cIEmail using partial update
        CIEmail partialUpdatedCIEmail = new CIEmail();
        partialUpdatedCIEmail.setId(cIEmail.getId());

        restCIEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIEmail))
            )
            .andExpect(status().isOk());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
        CIEmail testCIEmail = cIEmailList.get(cIEmailList.size() - 1);
        assertThat(testCIEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateCIEmailWithPatch() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();

        // Update the cIEmail using partial update
        CIEmail partialUpdatedCIEmail = new CIEmail();
        partialUpdatedCIEmail.setId(cIEmail.getId());

        partialUpdatedCIEmail.email(UPDATED_EMAIL);

        restCIEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIEmail))
            )
            .andExpect(status().isOk());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
        CIEmail testCIEmail = cIEmailList.get(cIEmailList.size() - 1);
        assertThat(testCIEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cIEmailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCIEmail() throws Exception {
        int databaseSizeBeforeUpdate = cIEmailRepository.findAll().size();
        cIEmail.setId(longCount.incrementAndGet());

        // Create the CIEmail
        CIEmailDTO cIEmailDTO = cIEmailMapper.toDto(cIEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIEmailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cIEmailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIEmail in the database
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCIEmail() throws Exception {
        // Initialize the database
        cIEmailRepository.saveAndFlush(cIEmail);

        int databaseSizeBeforeDelete = cIEmailRepository.findAll().size();

        // Delete the cIEmail
        restCIEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, cIEmail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIEmail> cIEmailList = cIEmailRepository.findAll();
        assertThat(cIEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
