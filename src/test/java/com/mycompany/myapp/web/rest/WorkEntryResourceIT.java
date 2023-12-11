package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.domain.enumeration.WorkStatus;
import com.mycompany.myapp.repository.WorkEntryRepository;
import com.mycompany.myapp.service.WorkEntryService;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import com.mycompany.myapp.service.mapper.WorkEntryMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link WorkEntryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WorkEntryResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;

    private static final WorkStatus DEFAULT_STATUS = WorkStatus.SUBMITTED;
    private static final WorkStatus UPDATED_STATUS = WorkStatus.APPROVED;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(1);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_APPROVAL_KEY_REGENERATED_DAYS = 1;
    private static final Integer UPDATED_APPROVAL_KEY_REGENERATED_DAYS = 2;

    private static final Instant DEFAULT_APPROVAL_KEY_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVAL_KEY_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_APPROVAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVAL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_APPROVAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_APPROVAL_KEY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkEntryRepository workEntryRepository;

    @Mock
    private WorkEntryRepository workEntryRepositoryMock;

    @Autowired
    private WorkEntryMapper workEntryMapper;

    @Mock
    private WorkEntryService workEntryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEntryMockMvc;

    private WorkEntry workEntry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEntry createEntity(EntityManager em) {
        WorkEntry workEntry = new WorkEntry()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .hours(DEFAULT_HOURS)
            .status(DEFAULT_STATUS)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .comment(DEFAULT_COMMENT)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .fileSize(DEFAULT_FILE_SIZE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .approvalKeyRegeneratedDays(DEFAULT_APPROVAL_KEY_REGENERATED_DAYS)
            .approvalKeyCreatedDate(DEFAULT_APPROVAL_KEY_CREATED_DATE)
            .approvalKey(DEFAULT_APPROVAL_KEY)
            .batchApprovalKey(DEFAULT_BATCH_APPROVAL_KEY);
        return workEntry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEntry createUpdatedEntity(EntityManager em) {
        WorkEntry workEntry = new WorkEntry()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .hours(UPDATED_HOURS)
            .status(UPDATED_STATUS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .comment(UPDATED_COMMENT)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .approvalKeyRegeneratedDays(UPDATED_APPROVAL_KEY_REGENERATED_DAYS)
            .approvalKeyCreatedDate(UPDATED_APPROVAL_KEY_CREATED_DATE)
            .approvalKey(UPDATED_APPROVAL_KEY)
            .batchApprovalKey(UPDATED_BATCH_APPROVAL_KEY);
        return workEntry;
    }

    @BeforeEach
    public void initTest() {
        workEntry = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkEntry() throws Exception {
        int databaseSizeBeforeCreate = workEntryRepository.findAll().size();
        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);
        restWorkEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWorkEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWorkEntry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkEntry.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWorkEntry.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testWorkEntry.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorkEntry.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkEntry.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testWorkEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testWorkEntry.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testWorkEntry.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testWorkEntry.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testWorkEntry.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testWorkEntry.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testWorkEntry.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWorkEntry.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testWorkEntry.getApprovalKeyRegeneratedDays()).isEqualTo(DEFAULT_APPROVAL_KEY_REGENERATED_DAYS);
        assertThat(testWorkEntry.getApprovalKeyCreatedDate()).isEqualTo(DEFAULT_APPROVAL_KEY_CREATED_DATE);
        assertThat(testWorkEntry.getApprovalKey()).isEqualTo(DEFAULT_APPROVAL_KEY);
        assertThat(testWorkEntry.getBatchApprovalKey()).isEqualTo(DEFAULT_BATCH_APPROVAL_KEY);
    }

    @Test
    @Transactional
    void createWorkEntryWithExistingId() throws Exception {
        // Create the WorkEntry with an existing ID
        workEntry.setId(1L);
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        int databaseSizeBeforeCreate = workEntryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEntryRepository.findAll().size();
        // set the field null
        workEntry.setTitle(null);

        // Create the WorkEntry, which fails.
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        restWorkEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isBadRequest());

        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEntryRepository.findAll().size();
        // set the field null
        workEntry.setDate(null);

        // Create the WorkEntry, which fails.
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        restWorkEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isBadRequest());

        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = workEntryRepository.findAll().size();
        // set the field null
        workEntry.setStatus(null);

        // Create the WorkEntry, which fails.
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        restWorkEntryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isBadRequest());

        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkEntries() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        // Get all the workEntryList
        restWorkEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvalKeyRegeneratedDays").value(hasItem(DEFAULT_APPROVAL_KEY_REGENERATED_DAYS)))
            .andExpect(jsonPath("$.[*].approvalKeyCreatedDate").value(hasItem(DEFAULT_APPROVAL_KEY_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvalKey").value(hasItem(DEFAULT_APPROVAL_KEY)))
            .andExpect(jsonPath("$.[*].batchApprovalKey").value(hasItem(DEFAULT_BATCH_APPROVAL_KEY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkEntriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(workEntryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkEntryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workEntryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkEntriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(workEntryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkEntryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(workEntryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        // Get the workEntry
        restWorkEntryMockMvc
            .perform(get(ENTITY_API_URL_ID, workEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEntry.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.approvalKeyRegeneratedDays").value(DEFAULT_APPROVAL_KEY_REGENERATED_DAYS))
            .andExpect(jsonPath("$.approvalKeyCreatedDate").value(DEFAULT_APPROVAL_KEY_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.approvalKey").value(DEFAULT_APPROVAL_KEY))
            .andExpect(jsonPath("$.batchApprovalKey").value(DEFAULT_BATCH_APPROVAL_KEY));
    }

    @Test
    @Transactional
    void getNonExistingWorkEntry() throws Exception {
        // Get the workEntry
        restWorkEntryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();

        // Update the workEntry
        WorkEntry updatedWorkEntry = workEntryRepository.findById(workEntry.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWorkEntry are not directly saved in db
        em.detach(updatedWorkEntry);
        updatedWorkEntry
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .hours(UPDATED_HOURS)
            .status(UPDATED_STATUS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .comment(UPDATED_COMMENT)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .approvalKeyRegeneratedDays(UPDATED_APPROVAL_KEY_REGENERATED_DAYS)
            .approvalKeyCreatedDate(UPDATED_APPROVAL_KEY_CREATED_DATE)
            .approvalKey(UPDATED_APPROVAL_KEY)
            .batchApprovalKey(UPDATED_BATCH_APPROVAL_KEY);
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(updatedWorkEntry);

        restWorkEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkEntry.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWorkEntry.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testWorkEntry.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkEntry.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkEntry.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testWorkEntry.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testWorkEntry.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testWorkEntry.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testWorkEntry.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testWorkEntry.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testWorkEntry.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testWorkEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkEntry.getApprovalKeyRegeneratedDays()).isEqualTo(UPDATED_APPROVAL_KEY_REGENERATED_DAYS);
        assertThat(testWorkEntry.getApprovalKeyCreatedDate()).isEqualTo(UPDATED_APPROVAL_KEY_CREATED_DATE);
        assertThat(testWorkEntry.getApprovalKey()).isEqualTo(UPDATED_APPROVAL_KEY);
        assertThat(testWorkEntry.getBatchApprovalKey()).isEqualTo(UPDATED_BATCH_APPROVAL_KEY);
    }

    @Test
    @Transactional
    void putNonExistingWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkEntryWithPatch() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();

        // Update the workEntry using partial update
        WorkEntry partialUpdatedWorkEntry = new WorkEntry();
        partialUpdatedWorkEntry.setId(workEntry.getId());

        partialUpdatedWorkEntry
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .endTime(UPDATED_END_TIME)
            .hours(UPDATED_HOURS)
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .approvalKeyCreatedDate(UPDATED_APPROVAL_KEY_CREATED_DATE);

        restWorkEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEntry))
            )
            .andExpect(status().isOk());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkEntry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkEntry.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWorkEntry.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testWorkEntry.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkEntry.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkEntry.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testWorkEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testWorkEntry.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testWorkEntry.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testWorkEntry.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testWorkEntry.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testWorkEntry.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testWorkEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkEntry.getApprovalKeyRegeneratedDays()).isEqualTo(DEFAULT_APPROVAL_KEY_REGENERATED_DAYS);
        assertThat(testWorkEntry.getApprovalKeyCreatedDate()).isEqualTo(UPDATED_APPROVAL_KEY_CREATED_DATE);
        assertThat(testWorkEntry.getApprovalKey()).isEqualTo(DEFAULT_APPROVAL_KEY);
        assertThat(testWorkEntry.getBatchApprovalKey()).isEqualTo(DEFAULT_BATCH_APPROVAL_KEY);
    }

    @Test
    @Transactional
    void fullUpdateWorkEntryWithPatch() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();

        // Update the workEntry using partial update
        WorkEntry partialUpdatedWorkEntry = new WorkEntry();
        partialUpdatedWorkEntry.setId(workEntry.getId());

        partialUpdatedWorkEntry
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .hours(UPDATED_HOURS)
            .status(UPDATED_STATUS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .comment(UPDATED_COMMENT)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .approvalKeyRegeneratedDays(UPDATED_APPROVAL_KEY_REGENERATED_DAYS)
            .approvalKeyCreatedDate(UPDATED_APPROVAL_KEY_CREATED_DATE)
            .approvalKey(UPDATED_APPROVAL_KEY)
            .batchApprovalKey(UPDATED_BATCH_APPROVAL_KEY);

        restWorkEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkEntry))
            )
            .andExpect(status().isOk());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkEntry.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWorkEntry.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testWorkEntry.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkEntry.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkEntry.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testWorkEntry.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testWorkEntry.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testWorkEntry.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testWorkEntry.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testWorkEntry.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testWorkEntry.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testWorkEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkEntry.getApprovalKeyRegeneratedDays()).isEqualTo(UPDATED_APPROVAL_KEY_REGENERATED_DAYS);
        assertThat(testWorkEntry.getApprovalKeyCreatedDate()).isEqualTo(UPDATED_APPROVAL_KEY_CREATED_DATE);
        assertThat(testWorkEntry.getApprovalKey()).isEqualTo(UPDATED_APPROVAL_KEY);
        assertThat(testWorkEntry.getBatchApprovalKey()).isEqualTo(UPDATED_BATCH_APPROVAL_KEY);
    }

    @Test
    @Transactional
    void patchNonExistingWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workEntryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();
        workEntry.setId(longCount.incrementAndGet());

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.toDto(workEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkEntryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workEntryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        int databaseSizeBeforeDelete = workEntryRepository.findAll().size();

        // Delete the workEntry
        restWorkEntryMockMvc
            .perform(delete(ENTITY_API_URL_ID, workEntry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
