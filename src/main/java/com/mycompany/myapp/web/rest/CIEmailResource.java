package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CIEmailRepository;
import com.mycompany.myapp.service.CIEmailService;
import com.mycompany.myapp.service.dto.CIEmailDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CIEmail}.
 */
@RestController
@RequestMapping("/api/ci-emails")
public class CIEmailResource {

    private final Logger log = LoggerFactory.getLogger(CIEmailResource.class);

    private static final String ENTITY_NAME = "cIEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIEmailService cIEmailService;

    private final CIEmailRepository cIEmailRepository;

    public CIEmailResource(CIEmailService cIEmailService, CIEmailRepository cIEmailRepository) {
        this.cIEmailService = cIEmailService;
        this.cIEmailRepository = cIEmailRepository;
    }

    /**
     * {@code POST  /ci-emails} : Create a new cIEmail.
     *
     * @param cIEmailDTO the cIEmailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIEmailDTO, or with status {@code 400 (Bad Request)} if the cIEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CIEmailDTO> createCIEmail(@Valid @RequestBody CIEmailDTO cIEmailDTO) throws URISyntaxException {
        log.debug("REST request to save CIEmail : {}", cIEmailDTO);
        if (cIEmailDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIEmailDTO result = cIEmailService.save(cIEmailDTO);
        return ResponseEntity
            .created(new URI("/api/ci-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-emails/:id} : Updates an existing cIEmail.
     *
     * @param id the id of the cIEmailDTO to save.
     * @param cIEmailDTO the cIEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIEmailDTO,
     * or with status {@code 400 (Bad Request)} if the cIEmailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CIEmailDTO> updateCIEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CIEmailDTO cIEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CIEmail : {}, {}", id, cIEmailDTO);
        if (cIEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CIEmailDTO result = cIEmailService.update(cIEmailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIEmailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ci-emails/:id} : Partial updates given fields of an existing cIEmail, field will ignore if it is null
     *
     * @param id the id of the cIEmailDTO to save.
     * @param cIEmailDTO the cIEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIEmailDTO,
     * or with status {@code 400 (Bad Request)} if the cIEmailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cIEmailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cIEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CIEmailDTO> partialUpdateCIEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CIEmailDTO cIEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CIEmail partially : {}, {}", id, cIEmailDTO);
        if (cIEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CIEmailDTO> result = cIEmailService.partialUpdate(cIEmailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIEmailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ci-emails} : get all the cIEmails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIEmails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CIEmailDTO>> getAllCIEmails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CIEmails");
        Page<CIEmailDTO> page = cIEmailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ci-emails/:id} : get the "id" cIEmail.
     *
     * @param id the id of the cIEmailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIEmailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CIEmailDTO> getCIEmail(@PathVariable Long id) {
        log.debug("REST request to get CIEmail : {}", id);
        Optional<CIEmailDTO> cIEmailDTO = cIEmailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIEmailDTO);
    }

    /**
     * {@code DELETE  /ci-emails/:id} : delete the "id" cIEmail.
     *
     * @param id the id of the cIEmailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCIEmail(@PathVariable Long id) {
        log.debug("REST request to delete CIEmail : {}", id);
        cIEmailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
