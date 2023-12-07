package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CIAddressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CIAddress}.
 */
public interface CIAddressService {
    /**
     * Save a cIAddress.
     *
     * @param cIAddressDTO the entity to save.
     * @return the persisted entity.
     */
    CIAddressDTO save(CIAddressDTO cIAddressDTO);

    /**
     * Updates a cIAddress.
     *
     * @param cIAddressDTO the entity to update.
     * @return the persisted entity.
     */
    CIAddressDTO update(CIAddressDTO cIAddressDTO);

    /**
     * Partially updates a cIAddress.
     *
     * @param cIAddressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CIAddressDTO> partialUpdate(CIAddressDTO cIAddressDTO);

    /**
     * Get all the cIAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CIAddressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cIAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CIAddressDTO> findOne(Long id);

    /**
     * Delete the "id" cIAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
