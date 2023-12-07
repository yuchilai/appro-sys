package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AccountsPayable;
import com.mycompany.myapp.repository.AccountsPayableRepository;
import com.mycompany.myapp.service.dto.AccountsPayableDTO;
import com.mycompany.myapp.service.mapper.AccountsPayableMapper;
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
 * Integration tests for the {@link AccountsPayableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountsPayableResourceIT {

    private static final String DEFAULT_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REP_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REP_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REP_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REP_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REP_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_REP_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REP_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_REP_PHONE_NUM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_USED_FOR_INVOICE = false;
    private static final Boolean UPDATED_IS_USED_FOR_INVOICE = true;

    private static final String ENTITY_API_URL = "/api/accounts-payables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountsPayableRepository accountsPayableRepository;

    @Autowired
    private AccountsPayableMapper accountsPayableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsPayableMockMvc;

    private AccountsPayable accountsPayable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountsPayable createEntity(EntityManager em) {
        AccountsPayable accountsPayable = new AccountsPayable()
            .deptName(DEFAULT_DEPT_NAME)
            .repLastName(DEFAULT_REP_LAST_NAME)
            .repFirstName(DEFAULT_REP_FIRST_NAME)
            .repEmail(DEFAULT_REP_EMAIL)
            .repPhoneNum(DEFAULT_REP_PHONE_NUM)
            .isUsedForInvoice(DEFAULT_IS_USED_FOR_INVOICE);
        return accountsPayable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountsPayable createUpdatedEntity(EntityManager em) {
        AccountsPayable accountsPayable = new AccountsPayable()
            .deptName(UPDATED_DEPT_NAME)
            .repLastName(UPDATED_REP_LAST_NAME)
            .repFirstName(UPDATED_REP_FIRST_NAME)
            .repEmail(UPDATED_REP_EMAIL)
            .repPhoneNum(UPDATED_REP_PHONE_NUM)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);
        return accountsPayable;
    }

    @BeforeEach
    public void initTest() {
        accountsPayable = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountsPayable() throws Exception {
        int databaseSizeBeforeCreate = accountsPayableRepository.findAll().size();
        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);
        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeCreate + 1);
        AccountsPayable testAccountsPayable = accountsPayableList.get(accountsPayableList.size() - 1);
        assertThat(testAccountsPayable.getDeptName()).isEqualTo(DEFAULT_DEPT_NAME);
        assertThat(testAccountsPayable.getRepLastName()).isEqualTo(DEFAULT_REP_LAST_NAME);
        assertThat(testAccountsPayable.getRepFirstName()).isEqualTo(DEFAULT_REP_FIRST_NAME);
        assertThat(testAccountsPayable.getRepEmail()).isEqualTo(DEFAULT_REP_EMAIL);
        assertThat(testAccountsPayable.getRepPhoneNum()).isEqualTo(DEFAULT_REP_PHONE_NUM);
        assertThat(testAccountsPayable.getIsUsedForInvoice()).isEqualTo(DEFAULT_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void createAccountsPayableWithExistingId() throws Exception {
        // Create the AccountsPayable with an existing ID
        accountsPayable.setId(1L);
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        int databaseSizeBeforeCreate = accountsPayableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDeptNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsPayableRepository.findAll().size();
        // set the field null
        accountsPayable.setDeptName(null);

        // Create the AccountsPayable, which fails.
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsPayableRepository.findAll().size();
        // set the field null
        accountsPayable.setRepLastName(null);

        // Create the AccountsPayable, which fails.
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsPayableRepository.findAll().size();
        // set the field null
        accountsPayable.setRepEmail(null);

        // Create the AccountsPayable, which fails.
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRepPhoneNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsPayableRepository.findAll().size();
        // set the field null
        accountsPayable.setRepPhoneNum(null);

        // Create the AccountsPayable, which fails.
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUsedForInvoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsPayableRepository.findAll().size();
        // set the field null
        accountsPayable.setIsUsedForInvoice(null);

        // Create the AccountsPayable, which fails.
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        restAccountsPayableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccountsPayables() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        // Get all the accountsPayableList
        restAccountsPayableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountsPayable.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptName").value(hasItem(DEFAULT_DEPT_NAME)))
            .andExpect(jsonPath("$.[*].repLastName").value(hasItem(DEFAULT_REP_LAST_NAME)))
            .andExpect(jsonPath("$.[*].repFirstName").value(hasItem(DEFAULT_REP_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].repEmail").value(hasItem(DEFAULT_REP_EMAIL)))
            .andExpect(jsonPath("$.[*].repPhoneNum").value(hasItem(DEFAULT_REP_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].isUsedForInvoice").value(hasItem(DEFAULT_IS_USED_FOR_INVOICE.booleanValue())));
    }

    @Test
    @Transactional
    void getAccountsPayable() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        // Get the accountsPayable
        restAccountsPayableMockMvc
            .perform(get(ENTITY_API_URL_ID, accountsPayable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountsPayable.getId().intValue()))
            .andExpect(jsonPath("$.deptName").value(DEFAULT_DEPT_NAME))
            .andExpect(jsonPath("$.repLastName").value(DEFAULT_REP_LAST_NAME))
            .andExpect(jsonPath("$.repFirstName").value(DEFAULT_REP_FIRST_NAME))
            .andExpect(jsonPath("$.repEmail").value(DEFAULT_REP_EMAIL))
            .andExpect(jsonPath("$.repPhoneNum").value(DEFAULT_REP_PHONE_NUM))
            .andExpect(jsonPath("$.isUsedForInvoice").value(DEFAULT_IS_USED_FOR_INVOICE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAccountsPayable() throws Exception {
        // Get the accountsPayable
        restAccountsPayableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountsPayable() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();

        // Update the accountsPayable
        AccountsPayable updatedAccountsPayable = accountsPayableRepository.findById(accountsPayable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAccountsPayable are not directly saved in db
        em.detach(updatedAccountsPayable);
        updatedAccountsPayable
            .deptName(UPDATED_DEPT_NAME)
            .repLastName(UPDATED_REP_LAST_NAME)
            .repFirstName(UPDATED_REP_FIRST_NAME)
            .repEmail(UPDATED_REP_EMAIL)
            .repPhoneNum(UPDATED_REP_PHONE_NUM)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(updatedAccountsPayable);

        restAccountsPayableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsPayableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
        AccountsPayable testAccountsPayable = accountsPayableList.get(accountsPayableList.size() - 1);
        assertThat(testAccountsPayable.getDeptName()).isEqualTo(UPDATED_DEPT_NAME);
        assertThat(testAccountsPayable.getRepLastName()).isEqualTo(UPDATED_REP_LAST_NAME);
        assertThat(testAccountsPayable.getRepFirstName()).isEqualTo(UPDATED_REP_FIRST_NAME);
        assertThat(testAccountsPayable.getRepEmail()).isEqualTo(UPDATED_REP_EMAIL);
        assertThat(testAccountsPayable.getRepPhoneNum()).isEqualTo(UPDATED_REP_PHONE_NUM);
        assertThat(testAccountsPayable.getIsUsedForInvoice()).isEqualTo(UPDATED_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void putNonExistingAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsPayableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountsPayableWithPatch() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();

        // Update the accountsPayable using partial update
        AccountsPayable partialUpdatedAccountsPayable = new AccountsPayable();
        partialUpdatedAccountsPayable.setId(accountsPayable.getId());

        partialUpdatedAccountsPayable.repFirstName(UPDATED_REP_FIRST_NAME).repPhoneNum(UPDATED_REP_PHONE_NUM);

        restAccountsPayableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountsPayable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountsPayable))
            )
            .andExpect(status().isOk());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
        AccountsPayable testAccountsPayable = accountsPayableList.get(accountsPayableList.size() - 1);
        assertThat(testAccountsPayable.getDeptName()).isEqualTo(DEFAULT_DEPT_NAME);
        assertThat(testAccountsPayable.getRepLastName()).isEqualTo(DEFAULT_REP_LAST_NAME);
        assertThat(testAccountsPayable.getRepFirstName()).isEqualTo(UPDATED_REP_FIRST_NAME);
        assertThat(testAccountsPayable.getRepEmail()).isEqualTo(DEFAULT_REP_EMAIL);
        assertThat(testAccountsPayable.getRepPhoneNum()).isEqualTo(UPDATED_REP_PHONE_NUM);
        assertThat(testAccountsPayable.getIsUsedForInvoice()).isEqualTo(DEFAULT_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void fullUpdateAccountsPayableWithPatch() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();

        // Update the accountsPayable using partial update
        AccountsPayable partialUpdatedAccountsPayable = new AccountsPayable();
        partialUpdatedAccountsPayable.setId(accountsPayable.getId());

        partialUpdatedAccountsPayable
            .deptName(UPDATED_DEPT_NAME)
            .repLastName(UPDATED_REP_LAST_NAME)
            .repFirstName(UPDATED_REP_FIRST_NAME)
            .repEmail(UPDATED_REP_EMAIL)
            .repPhoneNum(UPDATED_REP_PHONE_NUM)
            .isUsedForInvoice(UPDATED_IS_USED_FOR_INVOICE);

        restAccountsPayableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountsPayable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountsPayable))
            )
            .andExpect(status().isOk());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
        AccountsPayable testAccountsPayable = accountsPayableList.get(accountsPayableList.size() - 1);
        assertThat(testAccountsPayable.getDeptName()).isEqualTo(UPDATED_DEPT_NAME);
        assertThat(testAccountsPayable.getRepLastName()).isEqualTo(UPDATED_REP_LAST_NAME);
        assertThat(testAccountsPayable.getRepFirstName()).isEqualTo(UPDATED_REP_FIRST_NAME);
        assertThat(testAccountsPayable.getRepEmail()).isEqualTo(UPDATED_REP_EMAIL);
        assertThat(testAccountsPayable.getRepPhoneNum()).isEqualTo(UPDATED_REP_PHONE_NUM);
        assertThat(testAccountsPayable.getIsUsedForInvoice()).isEqualTo(UPDATED_IS_USED_FOR_INVOICE);
    }

    @Test
    @Transactional
    void patchNonExistingAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountsPayableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountsPayable() throws Exception {
        int databaseSizeBeforeUpdate = accountsPayableRepository.findAll().size();
        accountsPayable.setId(longCount.incrementAndGet());

        // Create the AccountsPayable
        AccountsPayableDTO accountsPayableDTO = accountsPayableMapper.toDto(accountsPayable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsPayableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountsPayableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountsPayable in the database
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountsPayable() throws Exception {
        // Initialize the database
        accountsPayableRepository.saveAndFlush(accountsPayable);

        int databaseSizeBeforeDelete = accountsPayableRepository.findAll().size();

        // Delete the accountsPayable
        restAccountsPayableMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountsPayable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountsPayable> accountsPayableList = accountsPayableRepository.findAll();
        assertThat(accountsPayableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
