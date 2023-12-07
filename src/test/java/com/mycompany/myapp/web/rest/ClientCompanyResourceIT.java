package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.repository.ClientCompanyRepository;
import com.mycompany.myapp.service.ClientCompanyService;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.mapper.ClientCompanyMapper;
import jakarta.persistence.EntityManager;
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

/**
 * Integration tests for the {@link ClientCompanyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClientCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_PREFIX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Mock
    private ClientCompanyRepository clientCompanyRepositoryMock;

    @Autowired
    private ClientCompanyMapper clientCompanyMapper;

    @Mock
    private ClientCompanyService clientCompanyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientCompanyMockMvc;

    private ClientCompany clientCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientCompany createEntity(EntityManager em) {
        ClientCompany clientCompany = new ClientCompany().name(DEFAULT_NAME).invoicePrefix(DEFAULT_INVOICE_PREFIX);
        return clientCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientCompany createUpdatedEntity(EntityManager em) {
        ClientCompany clientCompany = new ClientCompany().name(UPDATED_NAME).invoicePrefix(UPDATED_INVOICE_PREFIX);
        return clientCompany;
    }

    @BeforeEach
    public void initTest() {
        clientCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createClientCompany() throws Exception {
        int databaseSizeBeforeCreate = clientCompanyRepository.findAll().size();
        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);
        restClientCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        ClientCompany testClientCompany = clientCompanyList.get(clientCompanyList.size() - 1);
        assertThat(testClientCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClientCompany.getInvoicePrefix()).isEqualTo(DEFAULT_INVOICE_PREFIX);
    }

    @Test
    @Transactional
    void createClientCompanyWithExistingId() throws Exception {
        // Create the ClientCompany with an existing ID
        clientCompany.setId(1L);
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        int databaseSizeBeforeCreate = clientCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCompanyRepository.findAll().size();
        // set the field null
        clientCompany.setName(null);

        // Create the ClientCompany, which fails.
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        restClientCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInvoicePrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCompanyRepository.findAll().size();
        // set the field null
        clientCompany.setInvoicePrefix(null);

        // Create the ClientCompany, which fails.
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        restClientCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientCompanies() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        // Get all the clientCompanyList
        restClientCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].invoicePrefix").value(hasItem(DEFAULT_INVOICE_PREFIX)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientCompaniesWithEagerRelationshipsIsEnabled() throws Exception {
        when(clientCompanyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClientCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clientCompanyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientCompaniesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clientCompanyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClientCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(clientCompanyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getClientCompany() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        // Get the clientCompany
        restClientCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, clientCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.invoicePrefix").value(DEFAULT_INVOICE_PREFIX));
    }

    @Test
    @Transactional
    void getNonExistingClientCompany() throws Exception {
        // Get the clientCompany
        restClientCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientCompany() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();

        // Update the clientCompany
        ClientCompany updatedClientCompany = clientCompanyRepository.findById(clientCompany.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClientCompany are not directly saved in db
        em.detach(updatedClientCompany);
        updatedClientCompany.name(UPDATED_NAME).invoicePrefix(UPDATED_INVOICE_PREFIX);
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(updatedClientCompany);

        restClientCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
        ClientCompany testClientCompany = clientCompanyList.get(clientCompanyList.size() - 1);
        assertThat(testClientCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientCompany.getInvoicePrefix()).isEqualTo(UPDATED_INVOICE_PREFIX);
    }

    @Test
    @Transactional
    void putNonExistingClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientCompanyWithPatch() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();

        // Update the clientCompany using partial update
        ClientCompany partialUpdatedClientCompany = new ClientCompany();
        partialUpdatedClientCompany.setId(clientCompany.getId());

        partialUpdatedClientCompany.name(UPDATED_NAME);

        restClientCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientCompany))
            )
            .andExpect(status().isOk());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
        ClientCompany testClientCompany = clientCompanyList.get(clientCompanyList.size() - 1);
        assertThat(testClientCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientCompany.getInvoicePrefix()).isEqualTo(DEFAULT_INVOICE_PREFIX);
    }

    @Test
    @Transactional
    void fullUpdateClientCompanyWithPatch() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();

        // Update the clientCompany using partial update
        ClientCompany partialUpdatedClientCompany = new ClientCompany();
        partialUpdatedClientCompany.setId(clientCompany.getId());

        partialUpdatedClientCompany.name(UPDATED_NAME).invoicePrefix(UPDATED_INVOICE_PREFIX);

        restClientCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientCompany))
            )
            .andExpect(status().isOk());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
        ClientCompany testClientCompany = clientCompanyList.get(clientCompanyList.size() - 1);
        assertThat(testClientCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientCompany.getInvoicePrefix()).isEqualTo(UPDATED_INVOICE_PREFIX);
    }

    @Test
    @Transactional
    void patchNonExistingClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientCompany() throws Exception {
        int databaseSizeBeforeUpdate = clientCompanyRepository.findAll().size();
        clientCompany.setId(longCount.incrementAndGet());

        // Create the ClientCompany
        ClientCompanyDTO clientCompanyDTO = clientCompanyMapper.toDto(clientCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientCompany in the database
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientCompany() throws Exception {
        // Initialize the database
        clientCompanyRepository.saveAndFlush(clientCompany);

        int databaseSizeBeforeDelete = clientCompanyRepository.findAll().size();

        // Delete the clientCompany
        restClientCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientCompany> clientCompanyList = clientCompanyRepository.findAll();
        assertThat(clientCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
