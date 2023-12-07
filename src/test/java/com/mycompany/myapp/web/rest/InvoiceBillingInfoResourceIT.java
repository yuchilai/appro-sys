package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.InvoiceBillingInfo;
import com.mycompany.myapp.repository.InvoiceBillingInfoRepository;
import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
import com.mycompany.myapp.service.mapper.InvoiceBillingInfoMapper;
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
 * Integration tests for the {@link InvoiceBillingInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceBillingInfoResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "s@7.q!78M";
    private static final String UPDATED_EMAIL = "F]@GjIJ.j";

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/invoice-billing-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InvoiceBillingInfoRepository invoiceBillingInfoRepository;

    @Autowired
    private InvoiceBillingInfoMapper invoiceBillingInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceBillingInfoMockMvc;

    private InvoiceBillingInfo invoiceBillingInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceBillingInfo createEntity(EntityManager em) {
        InvoiceBillingInfo invoiceBillingInfo = new InvoiceBillingInfo()
            .firstName(DEFAULT_FIRST_NAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .phoneNum(DEFAULT_PHONE_NUM)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .county(DEFAULT_COUNTY)
            .zipCode(DEFAULT_ZIP_CODE)
            .country(DEFAULT_COUNTRY);
        return invoiceBillingInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceBillingInfo createUpdatedEntity(EntityManager em) {
        InvoiceBillingInfo invoiceBillingInfo = new InvoiceBillingInfo()
            .firstName(UPDATED_FIRST_NAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);
        return invoiceBillingInfo;
    }

    @BeforeEach
    public void initTest() {
        invoiceBillingInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeCreate = invoiceBillingInfoRepository.findAll().size();
        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);
        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceBillingInfo testInvoiceBillingInfo = invoiceBillingInfoList.get(invoiceBillingInfoList.size() - 1);
        assertThat(testInvoiceBillingInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testInvoiceBillingInfo.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testInvoiceBillingInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInvoiceBillingInfo.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
        assertThat(testInvoiceBillingInfo.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testInvoiceBillingInfo.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testInvoiceBillingInfo.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testInvoiceBillingInfo.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testInvoiceBillingInfo.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testInvoiceBillingInfo.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testInvoiceBillingInfo.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createInvoiceBillingInfoWithExistingId() throws Exception {
        // Create the InvoiceBillingInfo with an existing ID
        invoiceBillingInfo.setId(1L);
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        int databaseSizeBeforeCreate = invoiceBillingInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setFirstName(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setLastname(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setPhoneNum(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setAddress1(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setCity(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setState(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceBillingInfoRepository.findAll().size();
        // set the field null
        invoiceBillingInfo.setZipCode(null);

        // Create the InvoiceBillingInfo, which fails.
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInvoiceBillingInfos() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        // Get all the invoiceBillingInfoList
        restInvoiceBillingInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceBillingInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
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
    void getInvoiceBillingInfo() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        // Get the invoiceBillingInfo
        restInvoiceBillingInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceBillingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceBillingInfo.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM))
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
    void getNonExistingInvoiceBillingInfo() throws Exception {
        // Get the invoiceBillingInfo
        restInvoiceBillingInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoiceBillingInfo() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();

        // Update the invoiceBillingInfo
        InvoiceBillingInfo updatedInvoiceBillingInfo = invoiceBillingInfoRepository.findById(invoiceBillingInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInvoiceBillingInfo are not directly saved in db
        em.detach(updatedInvoiceBillingInfo);
        updatedInvoiceBillingInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(updatedInvoiceBillingInfo);

        restInvoiceBillingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceBillingInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
        InvoiceBillingInfo testInvoiceBillingInfo = invoiceBillingInfoList.get(invoiceBillingInfoList.size() - 1);
        assertThat(testInvoiceBillingInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInvoiceBillingInfo.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testInvoiceBillingInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvoiceBillingInfo.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testInvoiceBillingInfo.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testInvoiceBillingInfo.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testInvoiceBillingInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testInvoiceBillingInfo.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testInvoiceBillingInfo.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testInvoiceBillingInfo.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testInvoiceBillingInfo.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceBillingInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceBillingInfoWithPatch() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();

        // Update the invoiceBillingInfo using partial update
        InvoiceBillingInfo partialUpdatedInvoiceBillingInfo = new InvoiceBillingInfo();
        partialUpdatedInvoiceBillingInfo.setId(invoiceBillingInfo.getId());

        partialUpdatedInvoiceBillingInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY);

        restInvoiceBillingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceBillingInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceBillingInfo))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
        InvoiceBillingInfo testInvoiceBillingInfo = invoiceBillingInfoList.get(invoiceBillingInfoList.size() - 1);
        assertThat(testInvoiceBillingInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInvoiceBillingInfo.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testInvoiceBillingInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvoiceBillingInfo.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testInvoiceBillingInfo.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testInvoiceBillingInfo.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testInvoiceBillingInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testInvoiceBillingInfo.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testInvoiceBillingInfo.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testInvoiceBillingInfo.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testInvoiceBillingInfo.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateInvoiceBillingInfoWithPatch() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();

        // Update the invoiceBillingInfo using partial update
        InvoiceBillingInfo partialUpdatedInvoiceBillingInfo = new InvoiceBillingInfo();
        partialUpdatedInvoiceBillingInfo.setId(invoiceBillingInfo.getId());

        partialUpdatedInvoiceBillingInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .county(UPDATED_COUNTY)
            .zipCode(UPDATED_ZIP_CODE)
            .country(UPDATED_COUNTRY);

        restInvoiceBillingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceBillingInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceBillingInfo))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
        InvoiceBillingInfo testInvoiceBillingInfo = invoiceBillingInfoList.get(invoiceBillingInfoList.size() - 1);
        assertThat(testInvoiceBillingInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInvoiceBillingInfo.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testInvoiceBillingInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvoiceBillingInfo.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testInvoiceBillingInfo.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testInvoiceBillingInfo.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testInvoiceBillingInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testInvoiceBillingInfo.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testInvoiceBillingInfo.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testInvoiceBillingInfo.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testInvoiceBillingInfo.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceBillingInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = invoiceBillingInfoRepository.findAll().size();
        invoiceBillingInfo.setId(longCount.incrementAndGet());

        // Create the InvoiceBillingInfo
        InvoiceBillingInfoDTO invoiceBillingInfoDTO = invoiceBillingInfoMapper.toDto(invoiceBillingInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceBillingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceBillingInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceBillingInfo in the database
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceBillingInfo() throws Exception {
        // Initialize the database
        invoiceBillingInfoRepository.saveAndFlush(invoiceBillingInfo);

        int databaseSizeBeforeDelete = invoiceBillingInfoRepository.findAll().size();

        // Delete the invoiceBillingInfo
        restInvoiceBillingInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceBillingInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceBillingInfo> invoiceBillingInfoList = invoiceBillingInfoRepository.findAll();
        assertThat(invoiceBillingInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
