package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CCEmailDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CCEmail}.
 */
public interface CCEmailService {
    /**
     * Save a cCEmail.
     *
     * @param cCEmailDTO the entity to save.
     * @return the persisted entity.
     */
    CCEmailDTO save(CCEmailDTO cCEmailDTO);

    /**
     * Updates a cCEmail.
     *
     * @param cCEmailDTO the entity to update.
     * @return the persisted entity.
     */
    CCEmailDTO update(CCEmailDTO cCEmailDTO);

    /**
     * Partially updates a cCEmail.
     *
     * @param cCEmailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CCEmailDTO> partialUpdate(CCEmailDTO cCEmailDTO);

    /**
     * Get all the cCEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CCEmailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cCEmail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CCEmailDTO> findOne(Long id);

    /**
     * Delete the "id" cCEmail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
