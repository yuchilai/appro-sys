package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.AddressService;
import com.mycompany.myapp.service.ApplicationUserService;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Address}.
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressService addressService;

    private final AddressRepository addressRepository;

    private final ApplicationUserService applicationUserService;

    public AddressResource(
        AddressService addressService,
        AddressRepository addressRepository,
        ApplicationUserService applicationUserService
    ) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.applicationUserService = applicationUserService;
    }

    /**
     * {@code POST  /addresses} : Create a new address.
     *
     * @param addressDTO the addressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new addressDTO, or with status {@code 400 (Bad Request)} if
     *         the address has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressDTO);
        if (addressDTO.getId() != null) {
            throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity
            .created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /addresses/:id} : Updates an existing address.
     *
     * @param id         the id of the addressDTO to save.
     * @param addressDTO the addressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated addressDTO,
     *         or with status {@code 400 (Bad Request)} if the addressDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the addressDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AddressDTO addressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Address : {}, {}", id, addressDTO);
        if (addressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddressDTO result = addressService.update(addressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /addresses/:id} : Partial updates given fields of an existing
     * address, field will ignore if it is null
     *
     * @param id         the id of the addressDTO to save.
     * @param addressDTO the addressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated addressDTO,
     *         or with status {@code 400 (Bad Request)} if the addressDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the addressDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the addressDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AddressDTO> partialUpdateAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AddressDTO addressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Address partially : {}, {}", id, addressDTO);
        if (addressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddressDTO> result = addressService.partialUpdate(addressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /addresses} : get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of addresses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Addresses");
        boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN");
        if (isAdmin) {
            log.debug(ENTITY_NAME + "getAllAddresses isAdmin ~~~~~~~~~~~~~~~~~~~");
            Page<AddressDTO> page = addressService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            log.debug(ENTITY_NAME + "getAllAddresses isNotAdmin~~~~~~~~~~~~~~~~~");
            Long applicationUserId = applicationUserService
                .findOneByLogin(SecurityUtils.getCurrentUserLogin().get().toString())
                .get()
                .getId();
            Page<AddressDTO> page = addressService.findAllByApplicationUserId(applicationUserId, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    /**
     * {@code GET  /addresses/:id} : get the "id" address.
     *
     * @param id the id of the addressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the addressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Optional<AddressDTO> addressDTO = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressDTO);
    }

    /**
     * {@code DELETE  /addresses/:id} : delete the "id" address.
     *
     * @param id the id of the addressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
