package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ApprovalDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Approval}.
 */
public interface ApprovalService {
    /**
     * Save a approval.
     *
     * @param approvalDTO the entity to save.
     * @return the persisted entity.
     */
    ApprovalDTO save(ApprovalDTO approvalDTO);

    /**
     * Updates a approval.
     *
     * @param approvalDTO the entity to update.
     * @return the persisted entity.
     */
    ApprovalDTO update(ApprovalDTO approvalDTO);

    /**
     * Partially updates a approval.
     *
     * @param approvalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApprovalDTO> partialUpdate(ApprovalDTO approvalDTO);

    /**
     * Get all the approvals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApprovalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" approval.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApprovalDTO> findOne(Long id);

    /**
     * Delete the "id" approval.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
