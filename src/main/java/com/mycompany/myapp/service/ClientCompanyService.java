package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ClientCompany}.
 */
public interface ClientCompanyService {
    /**
     * Save a clientCompany.
     *
     * @param clientCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    ClientCompanyDTO save(ClientCompanyDTO clientCompanyDTO);

    /**
     * Updates a clientCompany.
     *
     * @param clientCompanyDTO the entity to update.
     * @return the persisted entity.
     */
    ClientCompanyDTO update(ClientCompanyDTO clientCompanyDTO);

    /**
     * Partially updates a clientCompany.
     *
     * @param clientCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientCompanyDTO> partialUpdate(ClientCompanyDTO clientCompanyDTO);

    /**
     * Get all the clientCompanies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientCompanyDTO> findAll(Pageable pageable);

    /**
     * Get all the clientCompanies with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientCompanyDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" clientCompany.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientCompanyDTO> findOne(Long id);

    /**
     * Delete the "id" clientCompany.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
