package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.HourlyRateRepository;
import com.mycompany.myapp.service.HourlyRateService;
import com.mycompany.myapp.service.dto.HourlyRateDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HourlyRate}.
 */
@RestController
@RequestMapping("/api/hourly-rates")
public class HourlyRateResource {

    private final Logger log = LoggerFactory.getLogger(HourlyRateResource.class);

    private static final String ENTITY_NAME = "hourlyRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HourlyRateService hourlyRateService;

    private final HourlyRateRepository hourlyRateRepository;

    public HourlyRateResource(HourlyRateService hourlyRateService, HourlyRateRepository hourlyRateRepository) {
        this.hourlyRateService = hourlyRateService;
        this.hourlyRateRepository = hourlyRateRepository;
    }

    /**
     * {@code POST  /hourly-rates} : Create a new hourlyRate.
     *
     * @param hourlyRateDTO the hourlyRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hourlyRateDTO, or with status {@code 400 (Bad Request)} if the hourlyRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HourlyRateDTO> createHourlyRate(@Valid @RequestBody HourlyRateDTO hourlyRateDTO) throws URISyntaxException {
        log.debug("REST request to save HourlyRate : {}", hourlyRateDTO);
        if (hourlyRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new hourlyRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HourlyRateDTO result = hourlyRateService.save(hourlyRateDTO);
        return ResponseEntity
            .created(new URI("/api/hourly-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hourly-rates/:id} : Updates an existing hourlyRate.
     *
     * @param id the id of the hourlyRateDTO to save.
     * @param hourlyRateDTO the hourlyRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hourlyRateDTO,
     * or with status {@code 400 (Bad Request)} if the hourlyRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hourlyRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HourlyRateDTO> updateHourlyRate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HourlyRateDTO hourlyRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HourlyRate : {}, {}", id, hourlyRateDTO);
        if (hourlyRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hourlyRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hourlyRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HourlyRateDTO result = hourlyRateService.update(hourlyRateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hourlyRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hourly-rates/:id} : Partial updates given fields of an existing hourlyRate, field will ignore if it is null
     *
     * @param id the id of the hourlyRateDTO to save.
     * @param hourlyRateDTO the hourlyRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hourlyRateDTO,
     * or with status {@code 400 (Bad Request)} if the hourlyRateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hourlyRateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hourlyRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HourlyRateDTO> partialUpdateHourlyRate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HourlyRateDTO hourlyRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HourlyRate partially : {}, {}", id, hourlyRateDTO);
        if (hourlyRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hourlyRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hourlyRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HourlyRateDTO> result = hourlyRateService.partialUpdate(hourlyRateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hourlyRateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hourly-rates} : get all the hourlyRates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hourlyRates in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HourlyRateDTO>> getAllHourlyRates(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HourlyRates");
        Page<HourlyRateDTO> page = hourlyRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hourly-rates/:id} : get the "id" hourlyRate.
     *
     * @param id the id of the hourlyRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hourlyRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HourlyRateDTO> getHourlyRate(@PathVariable Long id) {
        log.debug("REST request to get HourlyRate : {}", id);
        Optional<HourlyRateDTO> hourlyRateDTO = hourlyRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hourlyRateDTO);
    }

    /**
     * {@code DELETE  /hourly-rates/:id} : delete the "id" hourlyRate.
     *
     * @param id the id of the hourlyRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHourlyRate(@PathVariable Long id) {
        log.debug("REST request to delete HourlyRate : {}", id);
        hourlyRateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
