package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CIPhoneNumDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CIPhoneNum}.
 */
public interface CIPhoneNumService {
    /**
     * Save a cIPhoneNum.
     *
     * @param cIPhoneNumDTO the entity to save.
     * @return the persisted entity.
     */
    CIPhoneNumDTO save(CIPhoneNumDTO cIPhoneNumDTO);

    /**
     * Updates a cIPhoneNum.
     *
     * @param cIPhoneNumDTO the entity to update.
     * @return the persisted entity.
     */
    CIPhoneNumDTO update(CIPhoneNumDTO cIPhoneNumDTO);

    /**
     * Partially updates a cIPhoneNum.
     *
     * @param cIPhoneNumDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CIPhoneNumDTO> partialUpdate(CIPhoneNumDTO cIPhoneNumDTO);

    /**
     * Get all the cIPhoneNums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CIPhoneNumDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cIPhoneNum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CIPhoneNumDTO> findOne(Long id);

    /**
     * Delete the "id" cIPhoneNum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
