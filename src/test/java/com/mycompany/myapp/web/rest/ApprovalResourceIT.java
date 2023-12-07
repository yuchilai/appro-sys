package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Approval;
import com.mycompany.myapp.repository.ApprovalRepository;
import com.mycompany.myapp.service.dto.ApprovalDTO;
import com.mycompany.myapp.service.mapper.ApprovalMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ApprovalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalResourceIT {

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Instant DEFAULT_APPROVAL_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVAL_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalMockMvc;

    private Approval approval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createEntity(EntityManager em) {
        Approval approval = new Approval()
            .approved(DEFAULT_APPROVED)
            .approvalDateTime(DEFAULT_APPROVAL_DATE_TIME)
            .comments(DEFAULT_COMMENTS);
        return approval;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createUpdatedEntity(EntityManager em) {
        Approval approval = new Approval()
            .approved(UPDATED_APPROVED)
            .approvalDateTime(UPDATED_APPROVAL_DATE_TIME)
            .comments(UPDATED_COMMENTS);
        return approval;
    }

    @BeforeEach
    public void initTest() {
        approval = createEntity(em);
    }

    @Test
    @Transactional
    void createApproval() throws Exception {
        int databaseSizeBeforeCreate = approvalRepository.findAll().size();
        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isCreated());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate + 1);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testApproval.getApprovalDateTime()).isEqualTo(DEFAULT_APPROVAL_DATE_TIME);
        assertThat(testApproval.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createApprovalWithExistingId() throws Exception {
        // Create the Approval with an existing ID
        approval.setId(1L);
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        int databaseSizeBeforeCreate = approvalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = approvalRepository.findAll().size();
        // set the field null
        approval.setApproved(null);

        // Create the Approval, which fails.
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApprovalDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = approvalRepository.findAll().size();
        // set the field null
        approval.setApprovalDateTime(null);

        // Create the Approval, which fails.
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApprovals() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approval.getId().intValue())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].approvalDateTime").value(hasItem(DEFAULT_APPROVAL_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    void getApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get the approval
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL_ID, approval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approval.getId().intValue()))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.approvalDateTime").value(DEFAULT_APPROVAL_DATE_TIME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApproval() throws Exception {
        // Get the approval
        restApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval
        Approval updatedApproval = approvalRepository.findById(approval.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApproval are not directly saved in db
        em.detach(updatedApproval);
        updatedApproval.approved(UPDATED_APPROVED).approvalDateTime(UPDATED_APPROVAL_DATE_TIME).comments(UPDATED_COMMENTS);
        ApprovalDTO approvalDTO = approvalMapper.toDto(updatedApproval);

        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testApproval.getApprovalDateTime()).isEqualTo(UPDATED_APPROVAL_DATE_TIME);
        assertThat(testApproval.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval.approved(UPDATED_APPROVED).comments(UPDATED_COMMENTS);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testApproval.getApprovalDateTime()).isEqualTo(DEFAULT_APPROVAL_DATE_TIME);
        assertThat(testApproval.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval.approved(UPDATED_APPROVED).approvalDateTime(UPDATED_APPROVAL_DATE_TIME).comments(UPDATED_COMMENTS);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testApproval.getApprovalDateTime()).isEqualTo(UPDATED_APPROVAL_DATE_TIME);
        assertThat(testApproval.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(longCount.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeDelete = approvalRepository.findAll().size();

        // Delete the approval
        restApprovalMockMvc
            .perform(delete(ENTITY_API_URL_ID, approval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
