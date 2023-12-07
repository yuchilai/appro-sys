package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CIAddress;
import com.mycompany.myapp.repository.CIAddressRepository;
import com.mycompany.myapp.service.dto.CIAddressDTO;
import com.mycompany.myapp.service.mapper.CIAddressMapper;
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
 * Integration tests for the {@link CIAddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CIAddressResourceIT {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ci-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CIAddressRepository cIAddressRepository;

    @Autowired
    private CIAddressMapper cIAddressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCIAddressMockMvc;

    private CIAddress cIAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIAddress createEntity(EntityManager em) {
        CIAddress cIAddress = new CIAddress()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .county(DEFAULT_COUNTY)
            .zipCode(DEFAULT_ZIP_CODE)
            .country(DEFAULT_COUNTRY);
        return cIAddress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIAddress createUpdatedEntity(EntityManager em) {
        CIAddress cIAddress = new CIAddress()
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);
        return cIAddress;
    }

    @BeforeEach
    public void initTest() {
        cIAddress = createEntity(em);
    }

    @Test
    @Transactional
    void createCIAddress() throws Exception {
        int databaseSizeBeforeCreate = cIAddressRepository.findAll().size();
        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);
        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CIAddress testCIAddress = cIAddressList.get(cIAddressList.size() - 1);
        assertThat(testCIAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCIAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCIAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCIAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCIAddress.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testCIAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testCIAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createCIAddressWithExistingId() throws Exception {
        // Create the CIAddress with an existing ID
        cIAddress.setId(1L);
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        int databaseSizeBeforeCreate = cIAddressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = cIAddressRepository.findAll().size();
        // set the field null
        cIAddress.setAddress1(null);

        // Create the CIAddress, which fails.
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIAddressRepository.findAll().size();
        // set the field null
        cIAddress.setCity(null);

        // Create the CIAddress, which fails.
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIAddressRepository.findAll().size();
        // set the field null
        cIAddress.setState(null);

        // Create the CIAddress, which fails.
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIAddressRepository.findAll().size();
        // set the field null
        cIAddress.setZipCode(null);

        // Create the CIAddress, which fails.
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        restCIAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCIAddresses() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        // Get all the cIAddressList
        restCIAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getCIAddress() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        // Get the cIAddress
        restCIAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, cIAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getNonExistingCIAddress() throws Exception {
        // Get the cIAddress
        restCIAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCIAddress() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();

        // Update the cIAddress
        CIAddress updatedCIAddress = cIAddressRepository.findById(cIAddress.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCIAddress are not directly saved in db
        em.detach(updatedCIAddress);
        updatedCIAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(updatedCIAddress);

        restCIAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIAddressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isOk());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
        CIAddress testCIAddress = cIAddressList.get(cIAddressList.size() - 1);
        assertThat(testCIAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCIAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCIAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCIAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCIAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCIAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCIAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cIAddressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cIAddressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCIAddressWithPatch() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();

        // Update the cIAddress using partial update
        CIAddress partialUpdatedCIAddress = new CIAddress();
        partialUpdatedCIAddress.setId(cIAddress.getId());

        partialUpdatedCIAddress.state(UPDATED_STATE).county(UPDATED_COUNTY);

        restCIAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIAddress))
            )
            .andExpect(status().isOk());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
        CIAddress testCIAddress = cIAddressList.get(cIAddressList.size() - 1);
        assertThat(testCIAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCIAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCIAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCIAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCIAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCIAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testCIAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateCIAddressWithPatch() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();

        // Update the cIAddress using partial update
        CIAddress partialUpdatedCIAddress = new CIAddress();
        partialUpdatedCIAddress.setId(cIAddress.getId());

        partialUpdatedCIAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);

        restCIAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCIAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCIAddress))
            )
            .andExpect(status().isOk());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
        CIAddress testCIAddress = cIAddressList.get(cIAddressList.size() - 1);
        assertThat(testCIAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCIAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCIAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCIAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCIAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCIAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCIAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cIAddressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCIAddress() throws Exception {
        int databaseSizeBeforeUpdate = cIAddressRepository.findAll().size();
        cIAddress.setId(longCount.incrementAndGet());

        // Create the CIAddress
        CIAddressDTO cIAddressDTO = cIAddressMapper.toDto(cIAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCIAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cIAddressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CIAddress in the database
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCIAddress() throws Exception {
        // Initialize the database
        cIAddressRepository.saveAndFlush(cIAddress);

        int databaseSizeBeforeDelete = cIAddressRepository.findAll().size();

        // Delete the cIAddress
        restCIAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, cIAddress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIAddress> cIAddressList = cIAddressRepository.findAll();
        assertThat(cIAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
