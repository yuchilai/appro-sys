package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PhoneNumDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PhoneNum}.
 */
public interface PhoneNumService {
    /**
     * Save a phoneNum.
     *
     * @param phoneNumDTO the entity to save.
     * @return the persisted entity.
     */
    PhoneNumDTO save(PhoneNumDTO phoneNumDTO);

    /**
     * Updates a phoneNum.
     *
     * @param phoneNumDTO the entity to update.
     * @return the persisted entity.
     */
    PhoneNumDTO update(PhoneNumDTO phoneNumDTO);

    /**
     * Partially updates a phoneNum.
     *
     * @param phoneNumDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhoneNumDTO> partialUpdate(PhoneNumDTO phoneNumDTO);

    /**
     * Get all the phoneNums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneNumDTO> findAll(Pageable pageable);

    /**
     * Get the "id" phoneNum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneNumDTO> findOne(Long id);

    /**
     * Delete the "id" phoneNum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
