package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HourlyRate;
import com.mycompany.myapp.repository.HourlyRateRepository;
import com.mycompany.myapp.service.dto.HourlyRateDTO;
import com.mycompany.myapp.service.mapper.HourlyRateMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link HourlyRateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HourlyRateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(1);

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final String ENTITY_API_URL = "/api/hourly-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HourlyRateRepository hourlyRateRepository;

    @Autowired
    private HourlyRateMapper hourlyRateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHourlyRateMockMvc;

    private HourlyRate hourlyRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HourlyRate createEntity(EntityManager em) {
        HourlyRate hourlyRate = new HourlyRate().name(DEFAULT_NAME).rate(DEFAULT_RATE).isDefault(DEFAULT_IS_DEFAULT);
        return hourlyRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HourlyRate createUpdatedEntity(EntityManager em) {
        HourlyRate hourlyRate = new HourlyRate().name(UPDATED_NAME).rate(UPDATED_RATE).isDefault(UPDATED_IS_DEFAULT);
        return hourlyRate;
    }

    @BeforeEach
    public void initTest() {
        hourlyRate = createEntity(em);
    }

    @Test
    @Transactional
    void createHourlyRate() throws Exception {
        int databaseSizeBeforeCreate = hourlyRateRepository.findAll().size();
        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);
        restHourlyRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeCreate + 1);
        HourlyRate testHourlyRate = hourlyRateList.get(hourlyRateList.size() - 1);
        assertThat(testHourlyRate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHourlyRate.getRate()).isEqualByComparingTo(DEFAULT_RATE);
        assertThat(testHourlyRate.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
    }

    @Test
    @Transactional
    void createHourlyRateWithExistingId() throws Exception {
        // Create the HourlyRate with an existing ID
        hourlyRate.setId(1L);
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        int databaseSizeBeforeCreate = hourlyRateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHourlyRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hourlyRateRepository.findAll().size();
        // set the field null
        hourlyRate.setName(null);

        // Create the HourlyRate, which fails.
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        restHourlyRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isBadRequest());

        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hourlyRateRepository.findAll().size();
        // set the field null
        hourlyRate.setRate(null);

        // Create the HourlyRate, which fails.
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        restHourlyRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isBadRequest());

        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = hourlyRateRepository.findAll().size();
        // set the field null
        hourlyRate.setIsDefault(null);

        // Create the HourlyRate, which fails.
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        restHourlyRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isBadRequest());

        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHourlyRates() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        // Get all the hourlyRateList
        restHourlyRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hourlyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(sameNumber(DEFAULT_RATE))))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    void getHourlyRate() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        // Get the hourlyRate
        restHourlyRateMockMvc
            .perform(get(ENTITY_API_URL_ID, hourlyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hourlyRate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rate").value(sameNumber(DEFAULT_RATE)))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingHourlyRate() throws Exception {
        // Get the hourlyRate
        restHourlyRateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHourlyRate() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();

        // Update the hourlyRate
        HourlyRate updatedHourlyRate = hourlyRateRepository.findById(hourlyRate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHourlyRate are not directly saved in db
        em.detach(updatedHourlyRate);
        updatedHourlyRate.name(UPDATED_NAME).rate(UPDATED_RATE).isDefault(UPDATED_IS_DEFAULT);
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(updatedHourlyRate);

        restHourlyRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hourlyRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
        HourlyRate testHourlyRate = hourlyRateList.get(hourlyRateList.size() - 1);
        assertThat(testHourlyRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHourlyRate.getRate()).isEqualByComparingTo(UPDATED_RATE);
        assertThat(testHourlyRate.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    void putNonExistingHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hourlyRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHourlyRateWithPatch() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();

        // Update the hourlyRate using partial update
        HourlyRate partialUpdatedHourlyRate = new HourlyRate();
        partialUpdatedHourlyRate.setId(hourlyRate.getId());

        partialUpdatedHourlyRate.isDefault(UPDATED_IS_DEFAULT);

        restHourlyRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHourlyRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHourlyRate))
            )
            .andExpect(status().isOk());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
        HourlyRate testHourlyRate = hourlyRateList.get(hourlyRateList.size() - 1);
        assertThat(testHourlyRate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHourlyRate.getRate()).isEqualByComparingTo(DEFAULT_RATE);
        assertThat(testHourlyRate.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    void fullUpdateHourlyRateWithPatch() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();

        // Update the hourlyRate using partial update
        HourlyRate partialUpdatedHourlyRate = new HourlyRate();
        partialUpdatedHourlyRate.setId(hourlyRate.getId());

        partialUpdatedHourlyRate.name(UPDATED_NAME).rate(UPDATED_RATE).isDefault(UPDATED_IS_DEFAULT);

        restHourlyRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHourlyRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHourlyRate))
            )
            .andExpect(status().isOk());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
        HourlyRate testHourlyRate = hourlyRateList.get(hourlyRateList.size() - 1);
        assertThat(testHourlyRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHourlyRate.getRate()).isEqualByComparingTo(UPDATED_RATE);
        assertThat(testHourlyRate.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    void patchNonExistingHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hourlyRateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHourlyRate() throws Exception {
        int databaseSizeBeforeUpdate = hourlyRateRepository.findAll().size();
        hourlyRate.setId(longCount.incrementAndGet());

        // Create the HourlyRate
        HourlyRateDTO hourlyRateDTO = hourlyRateMapper.toDto(hourlyRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHourlyRateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hourlyRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HourlyRate in the database
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHourlyRate() throws Exception {
        // Initialize the database
        hourlyRateRepository.saveAndFlush(hourlyRate);

        int databaseSizeBeforeDelete = hourlyRateRepository.findAll().size();

        // Delete the hourlyRate
        restHourlyRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, hourlyRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HourlyRate> hourlyRateList = hourlyRateRepository.findAll();
        assertThat(hourlyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
