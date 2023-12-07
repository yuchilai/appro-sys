package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CCPhoneNumRepository;
import com.mycompany.myapp.service.CCPhoneNumService;
import com.mycompany.myapp.service.dto.CCPhoneNumDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CCPhoneNum}.
 */
@RestController
@RequestMapping("/api/cc-phone-nums")
public class CCPhoneNumResource {

    private final Logger log = LoggerFactory.getLogger(CCPhoneNumResource.class);

    private static final String ENTITY_NAME = "cCPhoneNum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCPhoneNumService cCPhoneNumService;

    private final CCPhoneNumRepository cCPhoneNumRepository;

    public CCPhoneNumResource(CCPhoneNumService cCPhoneNumService, CCPhoneNumRepository cCPhoneNumRepository) {
        this.cCPhoneNumService = cCPhoneNumService;
        this.cCPhoneNumRepository = cCPhoneNumRepository;
    }

    /**
     * {@code POST  /cc-phone-nums} : Create a new cCPhoneNum.
     *
     * @param cCPhoneNumDTO the cCPhoneNumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCPhoneNumDTO, or with status {@code 400 (Bad Request)} if the cCPhoneNum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CCPhoneNumDTO> createCCPhoneNum(@Valid @RequestBody CCPhoneNumDTO cCPhoneNumDTO) throws URISyntaxException {
        log.debug("REST request to save CCPhoneNum : {}", cCPhoneNumDTO);
        if (cCPhoneNumDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCPhoneNum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCPhoneNumDTO result = cCPhoneNumService.save(cCPhoneNumDTO);
        return ResponseEntity
            .created(new URI("/api/cc-phone-nums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cc-phone-nums/:id} : Updates an existing cCPhoneNum.
     *
     * @param id the id of the cCPhoneNumDTO to save.
     * @param cCPhoneNumDTO the cCPhoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCPhoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the cCPhoneNumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCPhoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CCPhoneNumDTO> updateCCPhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CCPhoneNumDTO cCPhoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CCPhoneNum : {}, {}", id, cCPhoneNumDTO);
        if (cCPhoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cCPhoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cCPhoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CCPhoneNumDTO result = cCPhoneNumService.update(cCPhoneNumDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCPhoneNumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cc-phone-nums/:id} : Partial updates given fields of an existing cCPhoneNum, field will ignore if it is null
     *
     * @param id the id of the cCPhoneNumDTO to save.
     * @param cCPhoneNumDTO the cCPhoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCPhoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the cCPhoneNumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cCPhoneNumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cCPhoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CCPhoneNumDTO> partialUpdateCCPhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CCPhoneNumDTO cCPhoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CCPhoneNum partially : {}, {}", id, cCPhoneNumDTO);
        if (cCPhoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cCPhoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cCPhoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CCPhoneNumDTO> result = cCPhoneNumService.partialUpdate(cCPhoneNumDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCPhoneNumDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cc-phone-nums} : get all the cCPhoneNums.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCPhoneNums in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CCPhoneNumDTO>> getAllCCPhoneNums(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CCPhoneNums");
        Page<CCPhoneNumDTO> page = cCPhoneNumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cc-phone-nums/:id} : get the "id" cCPhoneNum.
     *
     * @param id the id of the cCPhoneNumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCPhoneNumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CCPhoneNumDTO> getCCPhoneNum(@PathVariable Long id) {
        log.debug("REST request to get CCPhoneNum : {}", id);
        Optional<CCPhoneNumDTO> cCPhoneNumDTO = cCPhoneNumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCPhoneNumDTO);
    }

    /**
     * {@code DELETE  /cc-phone-nums/:id} : delete the "id" cCPhoneNum.
     *
     * @param id the id of the cCPhoneNumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCCPhoneNum(@PathVariable Long id) {
        log.debug("REST request to delete CCPhoneNum : {}", id);
        cCPhoneNumService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
