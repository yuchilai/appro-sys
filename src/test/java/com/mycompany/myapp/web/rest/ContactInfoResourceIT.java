package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.repository.ContactInfoRepository;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import com.mycompany.myapp.service.mapper.ContactInfoMapper;
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
 * Integration tests for the {@link ContactInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactInfoResourceIT {

    private static final String DEFAULT_POSITSION = "AAAAAAAAAA";
    private static final String UPDATED_POSITSION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contact-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactInfoMockMvc;

    private ContactInfo contactInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().positsion(DEFAULT_POSITSION).firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME);
        return contactInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createUpdatedEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().positsion(UPDATED_POSITSION).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);
        return contactInfo;
    }

    @BeforeEach
    public void initTest() {
        contactInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createContactInfo() throws Exception {
        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();
        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPositsion()).isEqualTo(DEFAULT_POSITSION);
        assertThat(testContactInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContactInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void createContactInfoWithExistingId() throws Exception {
        // Create the ContactInfo with an existing ID
        contactInfo.setId(1L);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositsionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInfoRepository.findAll().size();
        // set the field null
        contactInfo.setPositsion(null);

        // Create the ContactInfo, which fails.
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInfoRepository.findAll().size();
        // set the field null
        contactInfo.setLastName(null);

        // Create the ContactInfo, which fails.
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactInfos() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].positsion").value(hasItem(DEFAULT_POSITSION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @Test
    @Transactional
    void getContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get the contactInfo
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, contactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactInfo.getId().intValue()))
            .andExpect(jsonPath("$.positsion").value(DEFAULT_POSITSION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingContactInfo() throws Exception {
        // Get the contactInfo
        restContactInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo
        ContactInfo updatedContactInfo = contactInfoRepository.findById(contactInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContactInfo are not directly saved in db
        em.detach(updatedContactInfo);
        updatedContactInfo.positsion(UPDATED_POSITSION).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(updatedContactInfo);

        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPositsion()).isEqualTo(UPDATED_POSITSION);
        assertThat(testContactInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPositsion()).isEqualTo(DEFAULT_POSITSION);
        assertThat(testContactInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.positsion(UPDATED_POSITSION).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPositsion()).isEqualTo(UPDATED_POSITSION);
        assertThat(testContactInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(longCount.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeDelete = contactInfoRepository.findAll().size();

        // Delete the contactInfo
        restContactInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
