package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProjectService;
import com.mycompany.myapp.repository.ProjectServiceRepository;
import com.mycompany.myapp.service.dto.ProjectServiceDTO;
import com.mycompany.myapp.service.mapper.ProjectServiceMapper;
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
 * Integration tests for the {@link ProjectServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FEE = new BigDecimal(0);
    private static final BigDecimal UPDATED_FEE = new BigDecimal(1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY_LENGTH = 1;
    private static final Integer UPDATED_DAY_LENGTH = 2;

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectServiceRepository projectServiceRepository;

    @Autowired
    private ProjectServiceMapper projectServiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectServiceMockMvc;

    private ProjectService projectService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectService createEntity(EntityManager em) {
        ProjectService projectService = new ProjectService()
            .name(DEFAULT_NAME)
            .fee(DEFAULT_FEE)
            .description(DEFAULT_DESCRIPTION)
            .dayLength(DEFAULT_DAY_LENGTH)
            .extra(DEFAULT_EXTRA);
        return projectService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectService createUpdatedEntity(EntityManager em) {
        ProjectService projectService = new ProjectService()
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .description(UPDATED_DESCRIPTION)
            .dayLength(UPDATED_DAY_LENGTH)
            .extra(UPDATED_EXTRA);
        return projectService;
    }

    @BeforeEach
    public void initTest() {
        projectService = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectService() throws Exception {
        int databaseSizeBeforeCreate = projectServiceRepository.findAll().size();
        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);
        restProjectServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectService testProjectService = projectServiceList.get(projectServiceList.size() - 1);
        assertThat(testProjectService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectService.getFee()).isEqualByComparingTo(DEFAULT_FEE);
        assertThat(testProjectService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectService.getDayLength()).isEqualTo(DEFAULT_DAY_LENGTH);
        assertThat(testProjectService.getExtra()).isEqualTo(DEFAULT_EXTRA);
    }

    @Test
    @Transactional
    void createProjectServiceWithExistingId() throws Exception {
        // Create the ProjectService with an existing ID
        projectService.setId(1L);
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        int databaseSizeBeforeCreate = projectServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectServiceRepository.findAll().size();
        // set the field null
        projectService.setName(null);

        // Create the ProjectService, which fails.
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        restProjectServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectServiceRepository.findAll().size();
        // set the field null
        projectService.setFee(null);

        // Create the ProjectService, which fails.
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        restProjectServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectServices() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        // Get all the projectServiceList
        restProjectServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(sameNumber(DEFAULT_FEE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dayLength").value(hasItem(DEFAULT_DAY_LENGTH)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())));
    }

    @Test
    @Transactional
    void getProjectService() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        // Get the projectService
        restProjectServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, projectService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fee").value(sameNumber(DEFAULT_FEE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dayLength").value(DEFAULT_DAY_LENGTH))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProjectService() throws Exception {
        // Get the projectService
        restProjectServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectService() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();

        // Update the projectService
        ProjectService updatedProjectService = projectServiceRepository.findById(projectService.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectService are not directly saved in db
        em.detach(updatedProjectService);
        updatedProjectService
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .description(UPDATED_DESCRIPTION)
            .dayLength(UPDATED_DAY_LENGTH)
            .extra(UPDATED_EXTRA);
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(updatedProjectService);

        restProjectServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
        ProjectService testProjectService = projectServiceList.get(projectServiceList.size() - 1);
        assertThat(testProjectService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectService.getFee()).isEqualByComparingTo(UPDATED_FEE);
        assertThat(testProjectService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectService.getDayLength()).isEqualTo(UPDATED_DAY_LENGTH);
        assertThat(testProjectService.getExtra()).isEqualTo(UPDATED_EXTRA);
    }

    @Test
    @Transactional
    void putNonExistingProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectServiceWithPatch() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();

        // Update the projectService using partial update
        ProjectService partialUpdatedProjectService = new ProjectService();
        partialUpdatedProjectService.setId(projectService.getId());

        partialUpdatedProjectService.name(UPDATED_NAME).dayLength(UPDATED_DAY_LENGTH).extra(UPDATED_EXTRA);

        restProjectServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectService))
            )
            .andExpect(status().isOk());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
        ProjectService testProjectService = projectServiceList.get(projectServiceList.size() - 1);
        assertThat(testProjectService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectService.getFee()).isEqualByComparingTo(DEFAULT_FEE);
        assertThat(testProjectService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectService.getDayLength()).isEqualTo(UPDATED_DAY_LENGTH);
        assertThat(testProjectService.getExtra()).isEqualTo(UPDATED_EXTRA);
    }

    @Test
    @Transactional
    void fullUpdateProjectServiceWithPatch() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();

        // Update the projectService using partial update
        ProjectService partialUpdatedProjectService = new ProjectService();
        partialUpdatedProjectService.setId(projectService.getId());

        partialUpdatedProjectService
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .description(UPDATED_DESCRIPTION)
            .dayLength(UPDATED_DAY_LENGTH)
            .extra(UPDATED_EXTRA);

        restProjectServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectService))
            )
            .andExpect(status().isOk());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
        ProjectService testProjectService = projectServiceList.get(projectServiceList.size() - 1);
        assertThat(testProjectService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectService.getFee()).isEqualByComparingTo(UPDATED_FEE);
        assertThat(testProjectService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectService.getDayLength()).isEqualTo(UPDATED_DAY_LENGTH);
        assertThat(testProjectService.getExtra()).isEqualTo(UPDATED_EXTRA);
    }

    @Test
    @Transactional
    void patchNonExistingProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectService() throws Exception {
        int databaseSizeBeforeUpdate = projectServiceRepository.findAll().size();
        projectService.setId(longCount.incrementAndGet());

        // Create the ProjectService
        ProjectServiceDTO projectServiceDTO = projectServiceMapper.toDto(projectService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectService in the database
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectService() throws Exception {
        // Initialize the database
        projectServiceRepository.saveAndFlush(projectService);

        int databaseSizeBeforeDelete = projectServiceRepository.findAll().size();

        // Delete the projectService
        restProjectServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectService> projectServiceList = projectServiceRepository.findAll();
        assertThat(projectServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
