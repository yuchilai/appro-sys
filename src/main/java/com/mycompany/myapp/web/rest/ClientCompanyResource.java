package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ClientCompanyRepository;
import com.mycompany.myapp.service.ClientCompanyService;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ClientCompany}.
 */
@RestController
@RequestMapping("/api/client-companies")
public class ClientCompanyResource {

    private final Logger log = LoggerFactory.getLogger(ClientCompanyResource.class);

    private static final String ENTITY_NAME = "clientCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientCompanyService clientCompanyService;

    private final ClientCompanyRepository clientCompanyRepository;

    public ClientCompanyResource(ClientCompanyService clientCompanyService, ClientCompanyRepository clientCompanyRepository) {
        this.clientCompanyService = clientCompanyService;
        this.clientCompanyRepository = clientCompanyRepository;
    }

    /**
     * {@code POST  /client-companies} : Create a new clientCompany.
     *
     * @param clientCompanyDTO the clientCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientCompanyDTO, or with status {@code 400 (Bad Request)} if the clientCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClientCompanyDTO> createClientCompany(@Valid @RequestBody ClientCompanyDTO clientCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClientCompany : {}", clientCompanyDTO);
        if (clientCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientCompanyDTO result = clientCompanyService.save(clientCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/client-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-companies/:id} : Updates an existing clientCompany.
     *
     * @param id the id of the clientCompanyDTO to save.
     * @param clientCompanyDTO the clientCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the clientCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientCompanyDTO> updateClientCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClientCompanyDTO clientCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClientCompany : {}, {}", id, clientCompanyDTO);
        if (clientCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientCompanyDTO result = clientCompanyService.update(clientCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-companies/:id} : Partial updates given fields of an existing clientCompany, field will ignore if it is null
     *
     * @param id the id of the clientCompanyDTO to save.
     * @param clientCompanyDTO the clientCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the clientCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clientCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientCompanyDTO> partialUpdateClientCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClientCompanyDTO clientCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientCompany partially : {}, {}", id, clientCompanyDTO);
        if (clientCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientCompanyDTO> result = clientCompanyService.partialUpdate(clientCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /client-companies} : get all the clientCompanies.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientCompanies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClientCompanyDTO>> getAllClientCompanies(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ClientCompanies");
        Page<ClientCompanyDTO> page;
        if (eagerload) {
            page = clientCompanyService.findAllWithEagerRelationships(pageable);
        } else {
            page = clientCompanyService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-companies/:id} : get the "id" clientCompany.
     *
     * @param id the id of the clientCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientCompanyDTO> getClientCompany(@PathVariable Long id) {
        log.debug("REST request to get ClientCompany : {}", id);
        Optional<ClientCompanyDTO> clientCompanyDTO = clientCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientCompanyDTO);
    }

    /**
     * {@code DELETE  /client-companies/:id} : delete the "id" clientCompany.
     *
     * @param id the id of the clientCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientCompany(@PathVariable Long id) {
        log.debug("REST request to delete ClientCompany : {}", id);
        clientCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
