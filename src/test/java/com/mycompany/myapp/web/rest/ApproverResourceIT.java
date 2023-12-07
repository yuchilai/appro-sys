package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.repository.ApproverRepository;
import com.mycompany.myapp.service.ApproverService;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.mapper.ApproverMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link ApproverResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApproverResourceIT {

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_ASSIGNED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGNED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproverRepository approverRepository;

    @Mock
    private ApproverRepository approverRepositoryMock;

    @Autowired
    private ApproverMapper approverMapper;

    @Mock
    private ApproverService approverServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproverMockMvc;

    private Approver approver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createEntity(EntityManager em) {
        Approver approver = new Approver()
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE)
            .assignedDate(DEFAULT_ASSIGNED_DATE);
        return approver;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createUpdatedEntity(EntityManager em) {
        Approver approver = new Approver()
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .assignedDate(UPDATED_ASSIGNED_DATE);
        return approver;
    }

    @BeforeEach
    public void initTest() {
        approver = createEntity(em);
    }

    @Test
    @Transactional
    void createApprover() throws Exception {
        int databaseSizeBeforeCreate = approverRepository.findAll().size();
        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isCreated());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate + 1);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testApprover.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testApprover.getAssignedDate()).isEqualTo(DEFAULT_ASSIGNED_DATE);
    }

    @Test
    @Transactional
    void createApproverWithExistingId() throws Exception {
        // Create the Approver with an existing ID
        approver.setId(1L);
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        int databaseSizeBeforeCreate = approverRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssignedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = approverRepository.findAll().size();
        // set the field null
        approver.setAssignedDate(null);

        // Create the Approver, which fails.
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isBadRequest());

        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApprovers() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approver.getId().intValue())))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))))
            .andExpect(jsonPath("$.[*].assignedDate").value(hasItem(DEFAULT_ASSIGNED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApproversWithEagerRelationshipsIsEnabled() throws Exception {
        when(approverServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApproverMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(approverServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApproversWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(approverServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApproverMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(approverRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get the approver
        restApproverMockMvc
            .perform(get(ENTITY_API_URL_ID, approver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approver.getId().intValue()))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.assignedDate").value(DEFAULT_ASSIGNED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApprover() throws Exception {
        // Get the approver
        restApproverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver
        Approver updatedApprover = approverRepository.findById(approver.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApprover are not directly saved in db
        em.detach(updatedApprover);
        updatedApprover
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .assignedDate(UPDATED_ASSIGNED_DATE);
        ApproverDTO approverDTO = approverMapper.toDto(updatedApprover);

        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testApprover.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testApprover.getAssignedDate()).isEqualTo(UPDATED_ASSIGNED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approverDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover.signature(UPDATED_SIGNATURE).signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testApprover.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testApprover.getAssignedDate()).isEqualTo(DEFAULT_ASSIGNED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .assignedDate(UPDATED_ASSIGNED_DATE);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testApprover.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testApprover.getAssignedDate()).isEqualTo(UPDATED_ASSIGNED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approverDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(longCount.incrementAndGet());

        // Create the Approver
        ApproverDTO approverDTO = approverMapper.toDto(approver);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approverDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeDelete = approverRepository.findAll().size();

        // Delete the approver
        restApproverMockMvc
            .perform(delete(ENTITY_API_URL_ID, approver.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
