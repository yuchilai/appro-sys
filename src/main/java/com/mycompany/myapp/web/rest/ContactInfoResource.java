package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ContactInfoRepository;
import com.mycompany.myapp.service.ContactInfoService;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactInfo}.
 */
@RestController
@RequestMapping("/api/contact-infos")
public class ContactInfoResource {

    private final Logger log = LoggerFactory.getLogger(ContactInfoResource.class);

    private static final String ENTITY_NAME = "contactInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactInfoService contactInfoService;

    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoResource(ContactInfoService contactInfoService, ContactInfoRepository contactInfoRepository) {
        this.contactInfoService = contactInfoService;
        this.contactInfoRepository = contactInfoRepository;
    }

    /**
     * {@code POST  /contact-infos} : Create a new contactInfo.
     *
     * @param contactInfoDTO the contactInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactInfoDTO, or with status {@code 400 (Bad Request)} if the contactInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContactInfoDTO> createContactInfo(@Valid @RequestBody ContactInfoDTO contactInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ContactInfo : {}", contactInfoDTO);
        if (contactInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactInfoDTO result = contactInfoService.save(contactInfoDTO);
        return ResponseEntity
            .created(new URI("/api/contact-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-infos/:id} : Updates an existing contactInfo.
     *
     * @param id the id of the contactInfoDTO to save.
     * @param contactInfoDTO the contactInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactInfoDTO,
     * or with status {@code 400 (Bad Request)} if the contactInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> updateContactInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactInfoDTO contactInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactInfo : {}, {}", id, contactInfoDTO);
        if (contactInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactInfoDTO result = contactInfoService.update(contactInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-infos/:id} : Partial updates given fields of an existing contactInfo, field will ignore if it is null
     *
     * @param id the id of the contactInfoDTO to save.
     * @param contactInfoDTO the contactInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactInfoDTO,
     * or with status {@code 400 (Bad Request)} if the contactInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactInfoDTO> partialUpdateContactInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactInfoDTO contactInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactInfo partially : {}, {}", id, contactInfoDTO);
        if (contactInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactInfoDTO> result = contactInfoService.partialUpdate(contactInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-infos} : get all the contactInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContactInfoDTO>> getAllContactInfos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ContactInfos");
        Page<ContactInfoDTO> page = contactInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-infos/:id} : get the "id" contactInfo.
     *
     * @param id the id of the contactInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> getContactInfo(@PathVariable Long id) {
        log.debug("REST request to get ContactInfo : {}", id);
        Optional<ContactInfoDTO> contactInfoDTO = contactInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactInfoDTO);
    }

    /**
     * {@code DELETE  /contact-infos/:id} : delete the "id" contactInfo.
     *
     * @param id the id of the contactInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactInfo(@PathVariable Long id) {
        log.debug("REST request to delete ContactInfo : {}", id);
        contactInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
