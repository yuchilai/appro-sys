package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CIPhoneNumRepository;
import com.mycompany.myapp.service.CIPhoneNumService;
import com.mycompany.myapp.service.dto.CIPhoneNumDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CIPhoneNum}.
 */
@RestController
@RequestMapping("/api/ci-phone-nums")
public class CIPhoneNumResource {

    private final Logger log = LoggerFactory.getLogger(CIPhoneNumResource.class);

    private static final String ENTITY_NAME = "cIPhoneNum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIPhoneNumService cIPhoneNumService;

    private final CIPhoneNumRepository cIPhoneNumRepository;

    public CIPhoneNumResource(CIPhoneNumService cIPhoneNumService, CIPhoneNumRepository cIPhoneNumRepository) {
        this.cIPhoneNumService = cIPhoneNumService;
        this.cIPhoneNumRepository = cIPhoneNumRepository;
    }

    /**
     * {@code POST  /ci-phone-nums} : Create a new cIPhoneNum.
     *
     * @param cIPhoneNumDTO the cIPhoneNumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIPhoneNumDTO, or with status {@code 400 (Bad Request)} if the cIPhoneNum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CIPhoneNumDTO> createCIPhoneNum(@Valid @RequestBody CIPhoneNumDTO cIPhoneNumDTO) throws URISyntaxException {
        log.debug("REST request to save CIPhoneNum : {}", cIPhoneNumDTO);
        if (cIPhoneNumDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIPhoneNum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIPhoneNumDTO result = cIPhoneNumService.save(cIPhoneNumDTO);
        return ResponseEntity
            .created(new URI("/api/ci-phone-nums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-phone-nums/:id} : Updates an existing cIPhoneNum.
     *
     * @param id the id of the cIPhoneNumDTO to save.
     * @param cIPhoneNumDTO the cIPhoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIPhoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the cIPhoneNumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIPhoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CIPhoneNumDTO> updateCIPhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CIPhoneNumDTO cIPhoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CIPhoneNum : {}, {}", id, cIPhoneNumDTO);
        if (cIPhoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIPhoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIPhoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CIPhoneNumDTO result = cIPhoneNumService.update(cIPhoneNumDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIPhoneNumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ci-phone-nums/:id} : Partial updates given fields of an existing cIPhoneNum, field will ignore if it is null
     *
     * @param id the id of the cIPhoneNumDTO to save.
     * @param cIPhoneNumDTO the cIPhoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIPhoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the cIPhoneNumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cIPhoneNumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cIPhoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CIPhoneNumDTO> partialUpdateCIPhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CIPhoneNumDTO cIPhoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CIPhoneNum partially : {}, {}", id, cIPhoneNumDTO);
        if (cIPhoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIPhoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIPhoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CIPhoneNumDTO> result = cIPhoneNumService.partialUpdate(cIPhoneNumDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIPhoneNumDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ci-phone-nums} : get all the cIPhoneNums.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIPhoneNums in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CIPhoneNumDTO>> getAllCIPhoneNums(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CIPhoneNums");
        Page<CIPhoneNumDTO> page = cIPhoneNumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ci-phone-nums/:id} : get the "id" cIPhoneNum.
     *
     * @param id the id of the cIPhoneNumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIPhoneNumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CIPhoneNumDTO> getCIPhoneNum(@PathVariable Long id) {
        log.debug("REST request to get CIPhoneNum : {}", id);
        Optional<CIPhoneNumDTO> cIPhoneNumDTO = cIPhoneNumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIPhoneNumDTO);
    }

    /**
     * {@code DELETE  /ci-phone-nums/:id} : delete the "id" cIPhoneNum.
     *
     * @param id the id of the cIPhoneNumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCIPhoneNum(@PathVariable Long id) {
        log.debug("REST request to delete CIPhoneNum : {}", id);
        cIPhoneNumService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
