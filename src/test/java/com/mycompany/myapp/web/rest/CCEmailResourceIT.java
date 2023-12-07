package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CCEmail;
import com.mycompany.myapp.repository.CCEmailRepository;
import com.mycompany.myapp.service.dto.CCEmailDTO;
import com.mycompany.myapp.service.mapper.CCEmailMapper;
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
 * Integration tests for the {@link CCEmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CCEmailResourceIT {

    private static final String DEFAULT_EMAIL = "Anz.X@(w=&I.OmM0Se";
    private static final String UPDATED_EMAIL = "N-@q1{$$).Z";

    private static final String ENTITY_API_URL = "/api/cc-emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CCEmailRepository cCEmailRepository;

    @Autowired
    private CCEmailMapper cCEmailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCEmailMockMvc;

    private CCEmail cCEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCEmail createEntity(EntityManager em) {
        CCEmail cCEmail = new CCEmail().email(DEFAULT_EMAIL);
        return cCEmail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCEmail createUpdatedEntity(EntityManager em) {
        CCEmail cCEmail = new CCEmail().email(UPDATED_EMAIL);
        return cCEmail;
    }

    @BeforeEach
    public void initTest() {
        cCEmail = createEntity(em);
    }

    @Test
    @Transactional
    void createCCEmail() throws Exception {
        int databaseSizeBeforeCreate = cCEmailRepository.findAll().size();
        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);
        restCCEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeCreate + 1);
        CCEmail testCCEmail = cCEmailList.get(cCEmailList.size() - 1);
        assertThat(testCCEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createCCEmailWithExistingId() throws Exception {
        // Create the CCEmail with an existing ID
        cCEmail.setId(1L);
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        int databaseSizeBeforeCreate = cCEmailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCEmailRepository.findAll().size();
        // set the field null
        cCEmail.setEmail(null);

        // Create the CCEmail, which fails.
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        restCCEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCEmailDTO)))
            .andExpect(status().isBadRequest());

        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCCEmails() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        // Get all the cCEmailList
        restCCEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getCCEmail() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        // Get the cCEmail
        restCCEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, cCEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCEmail.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingCCEmail() throws Exception {
        // Get the cCEmail
        restCCEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCCEmail() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();

        // Update the cCEmail
        CCEmail updatedCCEmail = cCEmailRepository.findById(cCEmail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCCEmail are not directly saved in db
        em.detach(updatedCCEmail);
        updatedCCEmail.email(UPDATED_EMAIL);
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(updatedCCEmail);

        restCCEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isOk());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
        CCEmail testCCEmail = cCEmailList.get(cCEmailList.size() - 1);
        assertThat(testCCEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCEmailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCCEmailWithPatch() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();

        // Update the cCEmail using partial update
        CCEmail partialUpdatedCCEmail = new CCEmail();
        partialUpdatedCCEmail.setId(cCEmail.getId());

        partialUpdatedCCEmail.email(UPDATED_EMAIL);

        restCCEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCEmail))
            )
            .andExpect(status().isOk());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
        CCEmail testCCEmail = cCEmailList.get(cCEmailList.size() - 1);
        assertThat(testCCEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateCCEmailWithPatch() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();

        // Update the cCEmail using partial update
        CCEmail partialUpdatedCCEmail = new CCEmail();
        partialUpdatedCCEmail.setId(cCEmail.getId());

        partialUpdatedCCEmail.email(UPDATED_EMAIL);

        restCCEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCEmail))
            )
            .andExpect(status().isOk());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
        CCEmail testCCEmail = cCEmailList.get(cCEmailList.size() - 1);
        assertThat(testCCEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cCEmailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCCEmail() throws Exception {
        int databaseSizeBeforeUpdate = cCEmailRepository.findAll().size();
        cCEmail.setId(longCount.incrementAndGet());

        // Create the CCEmail
        CCEmailDTO cCEmailDTO = cCEmailMapper.toDto(cCEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCEmailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cCEmailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCEmail in the database
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCCEmail() throws Exception {
        // Initialize the database
        cCEmailRepository.saveAndFlush(cCEmail);

        int databaseSizeBeforeDelete = cCEmailRepository.findAll().size();

        // Delete the cCEmail
        restCCEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, cCEmail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCEmail> cCEmailList = cCEmailRepository.findAll();
        assertThat(cCEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
