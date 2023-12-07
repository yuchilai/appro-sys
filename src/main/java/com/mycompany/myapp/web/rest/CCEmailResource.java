package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CCEmailRepository;
import com.mycompany.myapp.service.CCEmailService;
import com.mycompany.myapp.service.dto.CCEmailDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CCEmail}.
 */
@RestController
@RequestMapping("/api/cc-emails")
public class CCEmailResource {

    private final Logger log = LoggerFactory.getLogger(CCEmailResource.class);

    private static final String ENTITY_NAME = "cCEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCEmailService cCEmailService;

    private final CCEmailRepository cCEmailRepository;

    public CCEmailResource(CCEmailService cCEmailService, CCEmailRepository cCEmailRepository) {
        this.cCEmailService = cCEmailService;
        this.cCEmailRepository = cCEmailRepository;
    }

    /**
     * {@code POST  /cc-emails} : Create a new cCEmail.
     *
     * @param cCEmailDTO the cCEmailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCEmailDTO, or with status {@code 400 (Bad Request)} if the cCEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CCEmailDTO> createCCEmail(@Valid @RequestBody CCEmailDTO cCEmailDTO) throws URISyntaxException {
        log.debug("REST request to save CCEmail : {}", cCEmailDTO);
        if (cCEmailDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCEmailDTO result = cCEmailService.save(cCEmailDTO);
        return ResponseEntity
            .created(new URI("/api/cc-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cc-emails/:id} : Updates an existing cCEmail.
     *
     * @param id the id of the cCEmailDTO to save.
     * @param cCEmailDTO the cCEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCEmailDTO,
     * or with status {@code 400 (Bad Request)} if the cCEmailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CCEmailDTO> updateCCEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CCEmailDTO cCEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CCEmail : {}, {}", id, cCEmailDTO);
        if (cCEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cCEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cCEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CCEmailDTO result = cCEmailService.update(cCEmailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCEmailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cc-emails/:id} : Partial updates given fields of an existing cCEmail, field will ignore if it is null
     *
     * @param id the id of the cCEmailDTO to save.
     * @param cCEmailDTO the cCEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCEmailDTO,
     * or with status {@code 400 (Bad Request)} if the cCEmailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cCEmailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cCEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CCEmailDTO> partialUpdateCCEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CCEmailDTO cCEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CCEmail partially : {}, {}", id, cCEmailDTO);
        if (cCEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cCEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cCEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CCEmailDTO> result = cCEmailService.partialUpdate(cCEmailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCEmailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cc-emails} : get all the cCEmails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCEmails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CCEmailDTO>> getAllCCEmails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CCEmails");
        Page<CCEmailDTO> page = cCEmailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cc-emails/:id} : get the "id" cCEmail.
     *
     * @param id the id of the cCEmailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCEmailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CCEmailDTO> getCCEmail(@PathVariable Long id) {
        log.debug("REST request to get CCEmail : {}", id);
        Optional<CCEmailDTO> cCEmailDTO = cCEmailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCEmailDTO);
    }

    /**
     * {@code DELETE  /cc-emails/:id} : delete the "id" cCEmail.
     *
     * @param id the id of the cCEmailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCCEmail(@PathVariable Long id) {
        log.debug("REST request to delete CCEmail : {}", id);
        cCEmailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
