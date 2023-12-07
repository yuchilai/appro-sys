package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ApproverDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Approver}.
 */
public interface ApproverService {
    /**
     * Save a approver.
     *
     * @param approverDTO the entity to save.
     * @return the persisted entity.
     */
    ApproverDTO save(ApproverDTO approverDTO);

    /**
     * Updates a approver.
     *
     * @param approverDTO the entity to update.
     * @return the persisted entity.
     */
    ApproverDTO update(ApproverDTO approverDTO);

    /**
     * Partially updates a approver.
     *
     * @param approverDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApproverDTO> partialUpdate(ApproverDTO approverDTO);

    /**
     * Get all the approvers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApproverDTO> findAll(Pageable pageable);

    /**
     * Get all the approvers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApproverDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" approver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApproverDTO> findOne(Long id);

    /**
     * Delete the "id" approver.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
