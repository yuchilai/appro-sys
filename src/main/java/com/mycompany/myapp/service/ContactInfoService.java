package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ContactInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ContactInfo}.
 */
public interface ContactInfoService {
    /**
     * Save a contactInfo.
     *
     * @param contactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ContactInfoDTO save(ContactInfoDTO contactInfoDTO);

    /**
     * Updates a contactInfo.
     *
     * @param contactInfoDTO the entity to update.
     * @return the persisted entity.
     */
    ContactInfoDTO update(ContactInfoDTO contactInfoDTO);

    /**
     * Partially updates a contactInfo.
     *
     * @param contactInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactInfoDTO> partialUpdate(ContactInfoDTO contactInfoDTO);

    /**
     * Get all the contactInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contactInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactInfoDTO> findOne(Long id);

    /**
     * Delete the "id" contactInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
