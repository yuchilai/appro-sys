package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CCAddressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CCAddress}.
 */
public interface CCAddressService {
    /**
     * Save a cCAddress.
     *
     * @param cCAddressDTO the entity to save.
     * @return the persisted entity.
     */
    CCAddressDTO save(CCAddressDTO cCAddressDTO);

    /**
     * Updates a cCAddress.
     *
     * @param cCAddressDTO the entity to update.
     * @return the persisted entity.
     */
    CCAddressDTO update(CCAddressDTO cCAddressDTO);

    /**
     * Partially updates a cCAddress.
     *
     * @param cCAddressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CCAddressDTO> partialUpdate(CCAddressDTO cCAddressDTO);

    /**
     * Get all the cCAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CCAddressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cCAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CCAddressDTO> findOne(Long id);

    /**
     * Delete the "id" cCAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
