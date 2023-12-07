package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CCPhoneNum;
import com.mycompany.myapp.repository.CCPhoneNumRepository;
import com.mycompany.myapp.service.dto.CCPhoneNumDTO;
import com.mycompany.myapp.service.mapper.CCPhoneNumMapper;
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
 * Integration tests for the {@link CCPhoneNumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CCPhoneNumResourceIT {

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cc-phone-nums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CCPhoneNumRepository cCPhoneNumRepository;

    @Autowired
    private CCPhoneNumMapper cCPhoneNumMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCPhoneNumMockMvc;

    private CCPhoneNum cCPhoneNum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCPhoneNum createEntity(EntityManager em) {
        CCPhoneNum cCPhoneNum = new CCPhoneNum().phoneNum(DEFAULT_PHONE_NUM);
        return cCPhoneNum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCPhoneNum createUpdatedEntity(EntityManager em) {
        CCPhoneNum cCPhoneNum = new CCPhoneNum().phoneNum(UPDATED_PHONE_NUM);
        return cCPhoneNum;
    }

    @BeforeEach
    public void initTest() {
        cCPhoneNum = createEntity(em);
    }

    @Test
    @Transactional
    void createCCPhoneNum() throws Exception {
        int databaseSizeBeforeCreate = cCPhoneNumRepository.findAll().size();
        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);
        restCCPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO)))
            .andExpect(status().isCreated());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeCreate + 1);
        CCPhoneNum testCCPhoneNum = cCPhoneNumList.get(cCPhoneNumList.size() - 1);
        assertThat(testCCPhoneNum.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
    }

    @Test
    @Transactional
    void createCCPhoneNumWithExistingId() throws Exception {
        // Create the CCPhoneNum with an existing ID
        cCPhoneNum.setId(1L);
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        int databaseSizeBeforeCreate = cCPhoneNumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCPhoneNumRepository.findAll().size();
        // set the field null
        cCPhoneNum.setPhoneNum(null);

        // Create the CCPhoneNum, which fails.
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        restCCPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO)))
            .andExpect(status().isBadRequest());

        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCCPhoneNums() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        // Get all the cCPhoneNumList
        restCCPhoneNumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCPhoneNum.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)));
    }

    @Test
    @Transactional
    void getCCPhoneNum() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        // Get the cCPhoneNum
        restCCPhoneNumMockMvc
            .perform(get(ENTITY_API_URL_ID, cCPhoneNum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCPhoneNum.getId().intValue()))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM));
    }

    @Test
    @Transactional
    void getNonExistingCCPhoneNum() throws Exception {
        // Get the cCPhoneNum
        restCCPhoneNumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCCPhoneNum() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();

        // Update the cCPhoneNum
        CCPhoneNum updatedCCPhoneNum = cCPhoneNumRepository.findById(cCPhoneNum.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCCPhoneNum are not directly saved in db
        em.detach(updatedCCPhoneNum);
        updatedCCPhoneNum.phoneNum(UPDATED_PHONE_NUM);
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(updatedCCPhoneNum);

        restCCPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCPhoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isOk());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CCPhoneNum testCCPhoneNum = cCPhoneNumList.get(cCPhoneNumList.size() - 1);
        assertThat(testCCPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void putNonExistingCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCPhoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCCPhoneNumWithPatch() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();

        // Update the cCPhoneNum using partial update
        CCPhoneNum partialUpdatedCCPhoneNum = new CCPhoneNum();
        partialUpdatedCCPhoneNum.setId(cCPhoneNum.getId());

        partialUpdatedCCPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restCCPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CCPhoneNum testCCPhoneNum = cCPhoneNumList.get(cCPhoneNumList.size() - 1);
        assertThat(testCCPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void fullUpdateCCPhoneNumWithPatch() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();

        // Update the cCPhoneNum using partial update
        CCPhoneNum partialUpdatedCCPhoneNum = new CCPhoneNum();
        partialUpdatedCCPhoneNum.setId(cCPhoneNum.getId());

        partialUpdatedCCPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restCCPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
        CCPhoneNum testCCPhoneNum = cCPhoneNumList.get(cCPhoneNumList.size() - 1);
        assertThat(testCCPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void patchNonExistingCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cCPhoneNumDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCCPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = cCPhoneNumRepository.findAll().size();
        cCPhoneNum.setId(longCount.incrementAndGet());

        // Create the CCPhoneNum
        CCPhoneNumDTO cCPhoneNumDTO = cCPhoneNumMapper.toDto(cCPhoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cCPhoneNumDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCPhoneNum in the database
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCCPhoneNum() throws Exception {
        // Initialize the database
        cCPhoneNumRepository.saveAndFlush(cCPhoneNum);

        int databaseSizeBeforeDelete = cCPhoneNumRepository.findAll().size();

        // Delete the cCPhoneNum
        restCCPhoneNumMockMvc
            .perform(delete(ENTITY_API_URL_ID, cCPhoneNum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCPhoneNum> cCPhoneNumList = cCPhoneNumRepository.findAll();
        assertThat(cCPhoneNumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
