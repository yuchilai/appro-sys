package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PhoneNum;
import com.mycompany.myapp.repository.PhoneNumRepository;
import com.mycompany.myapp.service.dto.PhoneNumDTO;
import com.mycompany.myapp.service.mapper.PhoneNumMapper;
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
 * Integration tests for the {@link PhoneNumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhoneNumResourceIT {

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phone-nums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhoneNumRepository phoneNumRepository;

    @Autowired
    private PhoneNumMapper phoneNumMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneNumMockMvc;

    private PhoneNum phoneNum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNum createEntity(EntityManager em) {
        PhoneNum phoneNum = new PhoneNum().phoneNum(DEFAULT_PHONE_NUM);
        return phoneNum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNum createUpdatedEntity(EntityManager em) {
        PhoneNum phoneNum = new PhoneNum().phoneNum(UPDATED_PHONE_NUM);
        return phoneNum;
    }

    @BeforeEach
    public void initTest() {
        phoneNum = createEntity(em);
    }

    @Test
    @Transactional
    void createPhoneNum() throws Exception {
        int databaseSizeBeforeCreate = phoneNumRepository.findAll().size();
        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);
        restPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneNumDTO)))
            .andExpect(status().isCreated());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNum testPhoneNum = phoneNumList.get(phoneNumList.size() - 1);
        assertThat(testPhoneNum.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
    }

    @Test
    @Transactional
    void createPhoneNumWithExistingId() throws Exception {
        // Create the PhoneNum with an existing ID
        phoneNum.setId(1L);
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        int databaseSizeBeforeCreate = phoneNumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneNumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumRepository.findAll().size();
        // set the field null
        phoneNum.setPhoneNum(null);

        // Create the PhoneNum, which fails.
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        restPhoneNumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneNumDTO)))
            .andExpect(status().isBadRequest());

        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhoneNums() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        // Get all the phoneNumList
        restPhoneNumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNum.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)));
    }

    @Test
    @Transactional
    void getPhoneNum() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        // Get the phoneNum
        restPhoneNumMockMvc
            .perform(get(ENTITY_API_URL_ID, phoneNum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNum.getId().intValue()))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM));
    }

    @Test
    @Transactional
    void getNonExistingPhoneNum() throws Exception {
        // Get the phoneNum
        restPhoneNumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhoneNum() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();

        // Update the phoneNum
        PhoneNum updatedPhoneNum = phoneNumRepository.findById(phoneNum.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPhoneNum are not directly saved in db
        em.detach(updatedPhoneNum);
        updatedPhoneNum.phoneNum(UPDATED_PHONE_NUM);
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(updatedPhoneNum);

        restPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
        PhoneNum testPhoneNum = phoneNumList.get(phoneNumList.size() - 1);
        assertThat(testPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void putNonExistingPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phoneNumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneNumDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhoneNumWithPatch() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();

        // Update the phoneNum using partial update
        PhoneNum partialUpdatedPhoneNum = new PhoneNum();
        partialUpdatedPhoneNum.setId(phoneNum.getId());

        partialUpdatedPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
        PhoneNum testPhoneNum = phoneNumList.get(phoneNumList.size() - 1);
        assertThat(testPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void fullUpdatePhoneNumWithPatch() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();

        // Update the phoneNum using partial update
        PhoneNum partialUpdatedPhoneNum = new PhoneNum();
        partialUpdatedPhoneNum.setId(phoneNum.getId());

        partialUpdatedPhoneNum.phoneNum(UPDATED_PHONE_NUM);

        restPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoneNum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoneNum))
            )
            .andExpect(status().isOk());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
        PhoneNum testPhoneNum = phoneNumList.get(phoneNumList.size() - 1);
        assertThat(testPhoneNum.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    void patchNonExistingPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phoneNumDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhoneNum() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumRepository.findAll().size();
        phoneNum.setId(longCount.incrementAndGet());

        // Create the PhoneNum
        PhoneNumDTO phoneNumDTO = phoneNumMapper.toDto(phoneNum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneNumMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phoneNumDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhoneNum in the database
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhoneNum() throws Exception {
        // Initialize the database
        phoneNumRepository.saveAndFlush(phoneNum);

        int databaseSizeBeforeDelete = phoneNumRepository.findAll().size();

        // Delete the phoneNum
        restPhoneNumMockMvc
            .perform(delete(ENTITY_API_URL_ID, phoneNum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneNum> phoneNumList = phoneNumRepository.findAll();
        assertThat(phoneNumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
