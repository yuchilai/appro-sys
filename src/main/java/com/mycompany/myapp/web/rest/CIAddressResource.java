package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CIAddressRepository;
import com.mycompany.myapp.service.CIAddressService;
import com.mycompany.myapp.service.dto.CIAddressDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CIAddress}.
 */
@RestController
@RequestMapping("/api/ci-addresses")
public class CIAddressResource {

    private final Logger log = LoggerFactory.getLogger(CIAddressResource.class);

    private static final String ENTITY_NAME = "cIAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIAddressService cIAddressService;

    private final CIAddressRepository cIAddressRepository;

    public CIAddressResource(CIAddressService cIAddressService, CIAddressRepository cIAddressRepository) {
        this.cIAddressService = cIAddressService;
        this.cIAddressRepository = cIAddressRepository;
    }

    /**
     * {@code POST  /ci-addresses} : Create a new cIAddress.
     *
     * @param cIAddressDTO the cIAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIAddressDTO, or with status {@code 400 (Bad Request)} if the cIAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CIAddressDTO> createCIAddress(@Valid @RequestBody CIAddressDTO cIAddressDTO) throws URISyntaxException {
        log.debug("REST request to save CIAddress : {}", cIAddressDTO);
        if (cIAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIAddressDTO result = cIAddressService.save(cIAddressDTO);
        return ResponseEntity
            .created(new URI("/api/ci-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-addresses/:id} : Updates an existing cIAddress.
     *
     * @param id the id of the cIAddressDTO to save.
     * @param cIAddressDTO the cIAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIAddressDTO,
     * or with status {@code 400 (Bad Request)} if the cIAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CIAddressDTO> updateCIAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CIAddressDTO cIAddressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CIAddress : {}, {}", id, cIAddressDTO);
        if (cIAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIAddressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CIAddressDTO result = cIAddressService.update(cIAddressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ci-addresses/:id} : Partial updates given fields of an existing cIAddress, field will ignore if it is null
     *
     * @param id the id of the cIAddressDTO to save.
     * @param cIAddressDTO the cIAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIAddressDTO,
     * or with status {@code 400 (Bad Request)} if the cIAddressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cIAddressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cIAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CIAddressDTO> partialUpdateCIAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CIAddressDTO cIAddressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CIAddress partially : {}, {}", id, cIAddressDTO);
        if (cIAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cIAddressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cIAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CIAddressDTO> result = cIAddressService.partialUpdate(cIAddressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIAddressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ci-addresses} : get all the cIAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIAddresses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CIAddressDTO>> getAllCIAddresses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CIAddresses");
        Page<CIAddressDTO> page = cIAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ci-addresses/:id} : get the "id" cIAddress.
     *
     * @param id the id of the cIAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CIAddressDTO> getCIAddress(@PathVariable Long id) {
        log.debug("REST request to get CIAddress : {}", id);
        Optional<CIAddressDTO> cIAddressDTO = cIAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIAddressDTO);
    }

    /**
     * {@code DELETE  /ci-addresses/:id} : delete the "id" cIAddress.
     *
     * @param id the id of the cIAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCIAddress(@PathVariable Long id) {
        log.debug("REST request to delete CIAddress : {}", id);
        cIAddressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
