package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CIPhoneNum;
import com.mycompany.myapp.repository.CIPhoneNumRepository;
import com.mycompany.myapp.service.dto.CIPhoneNumDTO;
import com.mycompany.myapp.service.mapper.CIPhoneNumMapper;
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
 * Integration tests for the {@link CIPhoneNumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CIPhoneNumResourceIT {

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ci-phone-nums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CIPhoneNumRepository cIPhoneNumRepository;

    @Autowired
    private CIPhoneNumMapper cIPhoneNumMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCIPhoneNumMockMvc;

    private CIPhoneNum cIPhoneNum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIPhoneNum createEntity(EntityManager em) {
        CIPhoneNum cIPhoneNum = new CIPhoneNum().phoneNum(DEFAULT_PHONE_NUM);
        return cIPhoneNum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIPhoneNum createUpdatedEntity(EntityManager em) {
        CIPhoneNum cIPhoneNum = new CIPhoneNum().phoneNum(UPDATED_PHONE_NUM);
        return cIPhoneNum;
    }

    @BeforeEach
    public void initTest() {
        cIPhoneNum = createEntity(em);
    }

    @Test
    @Transactional
    void createCIPhoneNum() throws Exception {
        int databaseSizeBeforeCreate = cIPhoneNumRepository.findAll().size();
        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);
        restCIPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO)))
            .andExpect(status().isCreated());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeCreate + 1);
        CIPhoneNum testCIPhoneNum = cIPhoneNumList.get(cIPhoneNumList.size() - 1);
        assertThat(testCIPhoneNum.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
    }

    @Test
    @Transactional
    void createCIPhoneNumWithExistingId() throws Exception {
        // Create the CIPhoneNum with an existing ID
        cIPhoneNum.setId(1L);
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        int databaseSizeBeforeCreate = cIPhoneNumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIPhoneNumRepository.findAll().size();
        // set the field null
        cIPhoneNum.setPhoneNum(null);

        // Create the CIPhoneNum, which fails.
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        restCIPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO)))
            .andExpect(status().isBadRequest());

        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCIPhoneNums() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        // Get all the cIPhoneNumList
        restCIPhoneNumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIPhoneNum.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)));
    }

    @Test
    @Transactional
    void getCIPhoneNum() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        // Get the cIPhoneNum
        restCIPhoneNumMockMvc
            .perform(get(ENTITY_API_URL_ID, cIPhoneNum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIPhoneNum.getId().intValue()))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM));
    }

    @Test
    @Transactional
    void getNonExistingCIPhoneNum() throws Exception {
        // Get the cIPhoneNum
        restCIPhoneNumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCIPhoneNum() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();

        // Update the cIPhoneNum
        CIPhoneNum updatedCIPhoneNum = cIPhoneNumRepository.findById(cIPhoneNum.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCIPhoneNum are not directly saved in db
        em.detach(updatedCIPhoneNum);
        updatedCIPhoneNum.phoneNum(UPDATED_PHONE_NUM);
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(updatedCIPhoneNum);

        restCIPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIPhoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isOk());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CIPhoneNum testCIPhoneNum = cIPhoneNumList.get(cIPhoneNumList.size() - 1);
        assertThat(testCIPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void putNonExistingCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIPhoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCIPhoneNumWithPatch() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();

        // Update the cIPhoneNum using partial update
        CIPhoneNum partialUpdatedCIPhoneNum = new CIPhoneNum();
        partialUpdatedCIPhoneNum.setId(cIPhoneNum.getId());

        partialUpdatedCIPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restCIPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CIPhoneNum testCIPhoneNum = cIPhoneNumList.get(cIPhoneNumList.size() - 1);
        assertThat(testCIPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void fullUpdateCIPhoneNumWithPatch() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();

        // Update the cIPhoneNum using partial update
        CIPhoneNum partialUpdatedCIPhoneNum = new CIPhoneNum();
        partialUpdatedCIPhoneNum.setId(cIPhoneNum.getId());

        partialUpdatedCIPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restCIPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CIPhoneNum testCIPhoneNum = cIPhoneNumList.get(cIPhoneNumList.size() - 1);
        assertThat(testCIPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void patchNonExistingCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cIPhoneNumDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCIPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cIPhoneNumRepository.findAll().size();
        cIPhoneNum.setId(longCount.incrementAndGet());

        // Create the CIPhoneNum
        CIPhoneNumDTO cIPhoneNumDTO = cIPhoneNumMapper.toDto(cIPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cIPhoneNumDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIPhoneNum in the database
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCIPhoneNum() throws Exception {
        // Initialize the database
        cIPhoneNumRepository.saveAndFlush(cIPhoneNum);

        int databaseSizeBeforeDelete = cIPhoneNumRepository.findAll().size();

        // Delete the cIPhoneNum
        restCIPhoneNumMockMvc
            .perform(delete(ENTITY_API_URL_ID, cIPhoneNum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIPhoneNum> cIPhoneNumList = cIPhoneNumRepository.findAll();
        assertThat(cIPhoneNumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
