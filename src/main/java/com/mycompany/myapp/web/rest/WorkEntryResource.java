package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.WorkEntryRepository;
import com.mycompany.myapp.service.WorkEntryService;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.WorkEntry}.
 */
@RestController
@RequestMapping("/api/work-entries")
public class WorkEntryResource {

    private final Logger log = LoggerFactory.getLogger(WorkEntryResource.class);

    private static final String ENTITY_NAME = "workEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEntryService workEntryService;

    private final WorkEntryRepository workEntryRepository;

    public WorkEntryResource(WorkEntryService workEntryService, WorkEntryRepository workEntryRepository) {
        this.workEntryService = workEntryService;
        this.workEntryRepository = workEntryRepository;
    }

    /**
     * {@code POST  /work-entries} : Create a new workEntry.
     *
     * @param workEntryDTO the workEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEntryDTO, or with status {@code 400 (Bad Request)} if the workEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WorkEntryDTO> createWorkEntry(@Valid @RequestBody WorkEntryDTO workEntryDTO) throws URISyntaxException {
        log.debug("REST request to save WorkEntry : {}", workEntryDTO);
        if (workEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new workEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEntryDTO result = workEntryService.save(workEntryDTO);
        return ResponseEntity
            .created(new URI("/api/work-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-entries/:id} : Updates an existing workEntry.
     *
     * @param id the id of the workEntryDTO to save.
     * @param workEntryDTO the workEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEntryDTO,
     * or with status {@code 400 (Bad Request)} if the workEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkEntryDTO> updateWorkEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkEntryDTO workEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkEntry : {}, {}", id, workEntryDTO);
        if (workEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkEntryDTO result = workEntryService.update(workEntryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-entries/:id} : Partial updates given fields of an existing workEntry, field will ignore if it is null
     *
     * @param id the id of the workEntryDTO to save.
     * @param workEntryDTO the workEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEntryDTO,
     * or with status {@code 400 (Bad Request)} if the workEntryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workEntryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkEntryDTO> partialUpdateWorkEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkEntryDTO workEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkEntry partially : {}, {}", id, workEntryDTO);
        if (workEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkEntryDTO> result = workEntryService.partialUpdate(workEntryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEntryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /work-entries} : get all the workEntries.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEntries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkEntryDTO>> getAllWorkEntries(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of WorkEntries");
        Page<WorkEntryDTO> page;
        if (eagerload) {
            page = workEntryService.findAllWithEagerRelationships(pageable);
        } else {
            page = workEntryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-entries/:id} : get the "id" workEntry.
     *
     * @param id the id of the workEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkEntryDTO> getWorkEntry(@PathVariable Long id) {
        log.debug("REST request to get WorkEntry : {}", id);
        Optional<WorkEntryDTO> workEntryDTO = workEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEntryDTO);
    }

    /**
     * {@code DELETE  /work-entries/:id} : delete the "id" workEntry.
     *
     * @param id the id of the workEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkEntry(@PathVariable Long id) {
        log.debug("REST request to delete WorkEntry : {}", id);
        workEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
