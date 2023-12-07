package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PhoneNumRepository;
import com.mycompany.myapp.service.PhoneNumService;
import com.mycompany.myapp.service.dto.PhoneNumDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PhoneNum}.
 */
@RestController
@RequestMapping("/api/phone-nums")
public class PhoneNumResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumResource.class);

    private static final String ENTITY_NAME = "phoneNum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneNumService phoneNumService;

    private final PhoneNumRepository phoneNumRepository;

    public PhoneNumResource(PhoneNumService phoneNumService, PhoneNumRepository phoneNumRepository) {
        this.phoneNumService = phoneNumService;
        this.phoneNumRepository = phoneNumRepository;
    }

    /**
     * {@code POST  /phone-nums} : Create a new phoneNum.
     *
     * @param phoneNumDTO the phoneNumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneNumDTO, or with status {@code 400 (Bad Request)} if the phoneNum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PhoneNumDTO> createPhoneNum(@Valid @RequestBody PhoneNumDTO phoneNumDTO) throws URISyntaxException {
        log.debug("REST request to save PhoneNum : {}", phoneNumDTO);
        if (phoneNumDTO.getId() != null) {
            throw new BadRequestAlertException("A new phoneNum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneNumDTO result = phoneNumService.save(phoneNumDTO);
        return ResponseEntity
            .created(new URI("/api/phone-nums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phone-nums/:id} : Updates an existing phoneNum.
     *
     * @param id the id of the phoneNumDTO to save.
     * @param phoneNumDTO the phoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the phoneNumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumDTO> updatePhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PhoneNumDTO phoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhoneNum : {}, {}", id, phoneNumDTO);
        if (phoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhoneNumDTO result = phoneNumService.update(phoneNumDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneNumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phone-nums/:id} : Partial updates given fields of an existing phoneNum, field will ignore if it is null
     *
     * @param id the id of the phoneNumDTO to save.
     * @param phoneNumDTO the phoneNumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneNumDTO,
     * or with status {@code 400 (Bad Request)} if the phoneNumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the phoneNumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the phoneNumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhoneNumDTO> partialUpdatePhoneNum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PhoneNumDTO phoneNumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhoneNum partially : {}, {}", id, phoneNumDTO);
        if (phoneNumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phoneNumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phoneNumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhoneNumDTO> result = phoneNumService.partialUpdate(phoneNumDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneNumDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /phone-nums} : get all the phoneNums.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phoneNums in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PhoneNumDTO>> getAllPhoneNums(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PhoneNums");
        Page<PhoneNumDTO> page = phoneNumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phone-nums/:id} : get the "id" phoneNum.
     *
     * @param id the id of the phoneNumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneNumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumDTO> getPhoneNum(@PathVariable Long id) {
        log.debug("REST request to get PhoneNum : {}", id);
        Optional<PhoneNumDTO> phoneNumDTO = phoneNumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phoneNumDTO);
    }

    /**
     * {@code DELETE  /phone-nums/:id} : delete the "id" phoneNum.
     *
     * @param id the id of the phoneNumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoneNum(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNum : {}", id);
        phoneNumService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
