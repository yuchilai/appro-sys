package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CCAddress;
import com.mycompany.myapp.repository.CCAddressRepository;
import com.mycompany.myapp.service.dto.CCAddressDTO;
import com.mycompany.myapp.service.mapper.CCAddressMapper;
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
 * Integration tests for the {@link CCAddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CCAddressResourceIT {

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

    private static final Boolean DEFAULT_IS_USED_FOR_INVOICE = false;
    private static final Boolean UPDATED_IS_USED_FOR_INVOICE = true;

    private static final String ENTITY_API_URL = "/api/cc-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CCAddressRepository cCAddressRepository;

    @Autowired
    private CCAddressMapper cCAddressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCAddressMockMvc;

    private CCAddress cCAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCAddress createEntity(EntityManager em) {
        CCAddress cCAddress = new CCAddress()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .county(DEFAULT_COUNTY)
            .zipCode(DEFAULT_ZIP_CODE)
            .country(DEFAULT_COUNTRY)
            .isUsedForInvoice(DEFAULT_IS_USED_FOR_INVOICE);
        return cCAddress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCAddress createUpdatedEntity(EntityManager em) {
        CCAddress cCAddress = new CCAddress()
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);
        return cCAddress;
    }

    @BeforeEach
    public void initTest() {
        cCAddress = createEntity(em);
    }

    @Test
    @Transactional
    void createCCAddress() throws Exception {
        int databaseSizeBeforeCreate = cCAddressRepository.findAll().size();
        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);
        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CCAddress testCCAddress = cCAddressList.get(cCAddressList.size() - 1);
        assertThat(testCCAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCCAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCCAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCCAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCCAddress.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testCCAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testCCAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCCAddress.getIsUsedForInvoice()).isEqualTo(DEFAULT_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void createCCAddressWithExistingId() throws Exception {
        // Create the CCAddress with an existing ID
        cCAddress.setId(1L);
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        int databaseSizeBeforeCreate = cCAddressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = cCAddressRepository.findAll().size();
        // set the field null
        cCAddress.setAddress1(null);

        // Create the CCAddress, which fails.
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCAddressRepository.findAll().size();
        // set the field null
        cCAddress.setCity(null);

        // Create the CCAddress, which fails.
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCAddressRepository.findAll().size();
        // set the field null
        cCAddress.setState(null);

        // Create the CCAddress, which fails.
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCAddressRepository.findAll().size();
        // set the field null
        cCAddress.setZipCode(null);

        // Create the CCAddress, which fails.
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUsedForInvoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCAddressRepository.findAll().size();
        // set the field null
        cCAddress.setIsUsedForInvoice(null);

        // Create the CCAddress, which fails.
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        restCCAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCCAddresses() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        // Get all the cCAddressList
        restCCAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].isUsedForInvoice").value(hasItem(DEFAULT_IS_USED_FOR_INVOICE.booleanValue())));
    }

    @Test
    @Transactional
    void getCCAddress() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        // Get the cCAddress
        restCCAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, cCAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.isUsedForInvoice").value(DEFAULT_IS_USED_FOR_INVOICE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCCAddress() throws Exception {
        // Get the cCAddress
        restCCAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCCAddress() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();

        // Update the cCAddress
        CCAddress updatedCCAddress = cCAddressRepository.findById(cCAddress.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCCAddress are not directly saved in db
        em.detach(updatedCCAddress);
        updatedCCAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(updatedCCAddress);

        restCCAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCAddressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isOk());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
        CCAddress testCCAddress = cCAddressList.get(cCAddressList.size() - 1);
        assertThat(testCCAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCCAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCCAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCCAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCCAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCCAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCCAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCCAddress.getIsUsedForInvoice()).isEqualTo(UPDATED_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void putNonExistingCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cCAddressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cCAddressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCCAddressWithPatch() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();

        // Update the cCAddress using partial update
        CCAddress partialUpdatedCCAddress = new CCAddress();
        partialUpdatedCCAddress.setId(cCAddress.getId());

        partialUpdatedCCAddress
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);

        restCCAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCAddress))
            )
            .andExpect(status().isOk());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
        CCAddress testCCAddress = cCAddressList.get(cCAddressList.size() - 1);
        assertThat(testCCAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCCAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCCAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCCAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCCAddress.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testCCAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCCAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCCAddress.getIsUsedForInvoice()).isEqualTo(UPDATED_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void fullUpdateCCAddressWithPatch() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();

        // Update the cCAddress using partial update
        CCAddress partialUpdatedCCAddress = new CCAddress();
        partialUpdatedCCAddress.setId(cCAddress.getId());

        partialUpdatedCCAddress
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);

        restCCAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCAddress))
            )
            .andExpect(status().isOk());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
        CCAddress testCCAddress = cCAddressList.get(cCAddressList.size() - 1);
        assertThat(testCCAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCCAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCCAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCCAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCCAddress.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCCAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCCAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCCAddress.getIsUsedForInvoice()).isEqualTo(UPDATED_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void patchNonExistingCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cCAddressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCCAddress() throws Exception {
        int databaseSizeBeforeUpdate = cCAddressRepository.findAll().size();
        cCAddress.setId(longCount.incrementAndGet());

        // Create the CCAddress
        CCAddressDTO cCAddressDTO = cCAddressMapper.toDto(cCAddress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCCAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cCAddressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCAddress in the database
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCCAddress() throws Exception {
        // Initialize the database
        cCAddressRepository.saveAndFlush(cCAddress);

        int databaseSizeBeforeDelete = cCAddressRepository.findAll().size();

        // Delete the cCAddress
        restCCAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, cCAddress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCAddress> cCAddressList = cCAddressRepository.findAll();
        assertThat(cCAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
