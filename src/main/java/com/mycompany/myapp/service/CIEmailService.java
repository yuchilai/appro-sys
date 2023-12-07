package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CIEmailDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CIEmail}.
 */
public interface CIEmailService {
    /**
     * Save a cIEmail.
     *
     * @param cIEmailDTO the entity to save.
     * @return the persisted entity.
     */
    CIEmailDTO save(CIEmailDTO cIEmailDTO);

    /**
     * Updates a cIEmail.
     *
     * @param cIEmailDTO the entity to update.
     * @return the persisted entity.
     */
    CIEmailDTO update(CIEmailDTO cIEmailDTO);

    /**
     * Partially updates a cIEmail.
     *
     * @param cIEmailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CIEmailDTO> partialUpdate(CIEmailDTO cIEmailDTO);

    /**
     * Get all the cIEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CIEmailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cIEmail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CIEmailDTO> findOne(Long id);

    /**
     * Delete the "id" cIEmail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
