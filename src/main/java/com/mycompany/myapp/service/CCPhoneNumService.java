package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CCPhoneNumDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CCPhoneNum}.
 */
public interface CCPhoneNumService {
    /**
     * Save a cCPhoneNum.
     *
     * @param cCPhoneNumDTO the entity to save.
     * @return the persisted entity.
     */
    CCPhoneNumDTO save(CCPhoneNumDTO cCPhoneNumDTO);

    /**
     * Updates a cCPhoneNum.
     *
     * @param cCPhoneNumDTO the entity to update.
     * @return the persisted entity.
     */
    CCPhoneNumDTO update(CCPhoneNumDTO cCPhoneNumDTO);

    /**
     * Partially updates a cCPhoneNum.
     *
     * @param cCPhoneNumDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CCPhoneNumDTO> partialUpdate(CCPhoneNumDTO cCPhoneNumDTO);

    /**
     * Get all the cCPhoneNums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CCPhoneNumDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cCPhoneNum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CCPhoneNumDTO> findOne(Long id);

    /**
     * Delete the "id" cCPhoneNum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
