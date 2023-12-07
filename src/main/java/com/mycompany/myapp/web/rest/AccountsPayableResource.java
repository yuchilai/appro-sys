package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AccountsPayableRepository;
import com.mycompany.myapp.service.AccountsPayableService;
import com.mycompany.myapp.service.dto.AccountsPayableDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AccountsPayable}.
 */
@RestController
@RequestMapping("/api/accounts-payables")
public class AccountsPayableResource {

    private final Logger log = LoggerFactory.getLogger(AccountsPayableResource.class);

    private static final String ENTITY_NAME = "accountsPayable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountsPayableService accountsPayableService;

    private final AccountsPayableRepository accountsPayableRepository;

    public AccountsPayableResource(AccountsPayableService accountsPayableService, AccountsPayableRepository accountsPayableRepository) {
        this.accountsPayableService = accountsPayableService;
        this.accountsPayableRepository = accountsPayableRepository;
    }

    /**
     * {@code POST  /accounts-payables} : Create a new accountsPayable.
     *
     * @param accountsPayableDTO the accountsPayableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountsPayableDTO, or with status {@code 400 (Bad Request)} if the accountsPayable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AccountsPayableDTO> createAccountsPayable(@Valid @RequestBody AccountsPayableDTO accountsPayableDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccountsPayable : {}", accountsPayableDTO);
        if (accountsPayableDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountsPayable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountsPayableDTO result = accountsPayableService.save(accountsPayableDTO);
        return ResponseEntity
            .created(new URI("/api/accounts-payables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accounts-payables/:id} : Updates an existing accountsPayable.
     *
     * @param id the id of the accountsPayableDTO to save.
     * @param accountsPayableDTO the accountsPayableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountsPayableDTO,
     * or with status {@code 400 (Bad Request)} if the accountsPayableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountsPayableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountsPayableDTO> updateAccountsPayable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccountsPayableDTO accountsPayableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccountsPayable : {}, {}", id, accountsPayableDTO);
        if (accountsPayableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountsPayableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountsPayableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountsPayableDTO result = accountsPayableService.update(accountsPayableDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountsPayableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accounts-payables/:id} : Partial updates given fields of an existing accountsPayable, field will ignore if it is null
     *
     * @param id the id of the accountsPayableDTO to save.
     * @param accountsPayableDTO the accountsPayableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountsPayableDTO,
     * or with status {@code 400 (Bad Request)} if the accountsPayableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accountsPayableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountsPayableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountsPayableDTO> partialUpdateAccountsPayable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccountsPayableDTO accountsPayableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountsPayable partially : {}, {}", id, accountsPayableDTO);
        if (accountsPayableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountsPayableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountsPayableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountsPayableDTO> result = accountsPayableService.partialUpdate(accountsPayableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountsPayableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /accounts-payables} : get all the accountsPayables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountsPayables in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AccountsPayableDTO>> getAllAccountsPayables(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AccountsPayables");
        Page<AccountsPayableDTO> page = accountsPayableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounts-payables/:id} : get the "id" accountsPayable.
     *
     * @param id the id of the accountsPayableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountsPayableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountsPayableDTO> getAccountsPayable(@PathVariable Long id) {
        log.debug("REST request to get AccountsPayable : {}", id);
        Optional<AccountsPayableDTO> accountsPayableDTO = accountsPayableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountsPayableDTO);
    }

    /**
     * {@code DELETE  /accounts-payables/:id} : delete the "id" accountsPayable.
     *
     * @param id the id of the accountsPayableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountsPayable(@PathVariable Long id) {
        log.debug("REST request to delete AccountsPayable : {}", id);
        accountsPayableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
