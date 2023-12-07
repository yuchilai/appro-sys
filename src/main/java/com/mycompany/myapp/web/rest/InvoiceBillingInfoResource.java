package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.InvoiceBillingInfoRepository;
import com.mycompany.myapp.service.InvoiceBillingInfoService;
import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.InvoiceBillingInfo}.
 */
@RestController
@RequestMapping("/api/invoice-billing-infos")
public class InvoiceBillingInfoResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceBillingInfoResource.class);

    private static final String ENTITY_NAME = "invoiceBillingInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceBillingInfoService invoiceBillingInfoService;

    private final InvoiceBillingInfoRepository invoiceBillingInfoRepository;

    public InvoiceBillingInfoResource(
        InvoiceBillingInfoService invoiceBillingInfoService,
        InvoiceBillingInfoRepository invoiceBillingInfoRepository
    ) {
        this.invoiceBillingInfoService = invoiceBillingInfoService;
        this.invoiceBillingInfoRepository = invoiceBillingInfoRepository;
    }

    /**
     * {@code POST  /invoice-billing-infos} : Create a new invoiceBillingInfo.
     *
     * @param invoiceBillingInfoDTO the invoiceBillingInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceBillingInfoDTO, or with status {@code 400 (Bad Request)} if the invoiceBillingInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InvoiceBillingInfoDTO> createInvoiceBillingInfo(@Valid @RequestBody InvoiceBillingInfoDTO invoiceBillingInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save InvoiceBillingInfo : {}", invoiceBillingInfoDTO);
        if (invoiceBillingInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceBillingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceBillingInfoDTO result = invoiceBillingInfoService.save(invoiceBillingInfoDTO);
        return ResponseEntity
            .created(new URI("/api/invoice-billing-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-billing-infos/:id} : Updates an existing invoiceBillingInfo.
     *
     * @param id the id of the invoiceBillingInfoDTO to save.
     * @param invoiceBillingInfoDTO the invoiceBillingInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceBillingInfoDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceBillingInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceBillingInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceBillingInfoDTO> updateInvoiceBillingInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InvoiceBillingInfoDTO invoiceBillingInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InvoiceBillingInfo : {}, {}", id, invoiceBillingInfoDTO);
        if (invoiceBillingInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceBillingInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceBillingInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InvoiceBillingInfoDTO result = invoiceBillingInfoService.update(invoiceBillingInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceBillingInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /invoice-billing-infos/:id} : Partial updates given fields of an existing invoiceBillingInfo, field will ignore if it is null
     *
     * @param id the id of the invoiceBillingInfoDTO to save.
     * @param invoiceBillingInfoDTO the invoiceBillingInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceBillingInfoDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceBillingInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceBillingInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceBillingInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceBillingInfoDTO> partialUpdateInvoiceBillingInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InvoiceBillingInfoDTO invoiceBillingInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InvoiceBillingInfo partially : {}, {}", id, invoiceBillingInfoDTO);
        if (invoiceBillingInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceBillingInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceBillingInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceBillingInfoDTO> result = invoiceBillingInfoService.partialUpdate(invoiceBillingInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceBillingInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-billing-infos} : get all the invoiceBillingInfos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceBillingInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InvoiceBillingInfoDTO>> getAllInvoiceBillingInfos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("invoice-is-null".equals(filter)) {
            log.debug("REST request to get all InvoiceBillingInfos where invoice is null");
            return new ResponseEntity<>(invoiceBillingInfoService.findAllWhereInvoiceIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of InvoiceBillingInfos");
        Page<InvoiceBillingInfoDTO> page = invoiceBillingInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-billing-infos/:id} : get the "id" invoiceBillingInfo.
     *
     * @param id the id of the invoiceBillingInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceBillingInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceBillingInfoDTO> getInvoiceBillingInfo(@PathVariable Long id) {
        log.debug("REST request to get InvoiceBillingInfo : {}", id);
        Optional<InvoiceBillingInfoDTO> invoiceBillingInfoDTO = invoiceBillingInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceBillingInfoDTO);
    }

    /**
     * {@code DELETE  /invoice-billing-infos/:id} : delete the "id" invoiceBillingInfo.
     *
     * @param id the id of the invoiceBillingInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceBillingInfo(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceBillingInfo : {}", id);
        invoiceBillingInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
