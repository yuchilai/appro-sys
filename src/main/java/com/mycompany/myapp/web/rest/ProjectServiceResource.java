package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProjectServiceRepository;
import com.mycompany.myapp.service.ProjectServiceService;
import com.mycompany.myapp.service.dto.ProjectServiceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProjectService}.
 */
@RestController
@RequestMapping("/api/project-services")
public class ProjectServiceResource {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceResource.class);

    private static final String ENTITY_NAME = "projectService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectServiceService projectServiceService;

    private final ProjectServiceRepository projectServiceRepository;

    public ProjectServiceResource(ProjectServiceService projectServiceService, ProjectServiceRepository projectServiceRepository) {
        this.projectServiceService = projectServiceService;
        this.projectServiceRepository = projectServiceRepository;
    }

    /**
     * {@code POST  /project-services} : Create a new projectService.
     *
     * @param projectServiceDTO the projectServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectServiceDTO, or with status {@code 400 (Bad Request)} if the projectService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProjectServiceDTO> createProjectService(@Valid @RequestBody ProjectServiceDTO projectServiceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectService : {}", projectServiceDTO);
        if (projectServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectServiceDTO result = projectServiceService.save(projectServiceDTO);
        return ResponseEntity
            .created(new URI("/api/project-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-services/:id} : Updates an existing projectService.
     *
     * @param id the id of the projectServiceDTO to save.
     * @param projectServiceDTO the projectServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectServiceDTO,
     * or with status {@code 400 (Bad Request)} if the projectServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectServiceDTO> updateProjectService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectServiceDTO projectServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectService : {}, {}", id, projectServiceDTO);
        if (projectServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectServiceDTO result = projectServiceService.update(projectServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-services/:id} : Partial updates given fields of an existing projectService, field will ignore if it is null
     *
     * @param id the id of the projectServiceDTO to save.
     * @param projectServiceDTO the projectServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectServiceDTO,
     * or with status {@code 400 (Bad Request)} if the projectServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectServiceDTO> partialUpdateProjectService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectServiceDTO projectServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectService partially : {}, {}", id, projectServiceDTO);
        if (projectServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectServiceDTO> result = projectServiceService.partialUpdate(projectServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-services} : get all the projectServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectServices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProjectServiceDTO>> getAllProjectServices(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProjectServices");
        Page<ProjectServiceDTO> page = projectServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-services/:id} : get the "id" projectService.
     *
     * @param id the id of the projectServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectServiceDTO> getProjectService(@PathVariable Long id) {
        log.debug("REST request to get ProjectService : {}", id);
        Optional<ProjectServiceDTO> projectServiceDTO = projectServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectServiceDTO);
    }

    /**
     * {@code DELETE  /project-services/:id} : delete the "id" projectService.
     *
     * @param id the id of the projectServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectService(@PathVariable Long id) {
        log.debug("REST request to delete ProjectService : {}", id);
        projectServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
