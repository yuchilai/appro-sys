package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.WorkEntryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.WorkEntry}.
 */
public interface WorkEntryService {
    /**
     * Save a workEntry.
     *
     * @param workEntryDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEntryDTO save(WorkEntryDTO workEntryDTO);

    /**
     * Updates a workEntry.
     *
     * @param workEntryDTO the entity to update.
     * @return the persisted entity.
     */
    WorkEntryDTO update(WorkEntryDTO workEntryDTO);

    /**
     * Partially updates a workEntry.
     *
     * @param workEntryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEntryDTO> partialUpdate(WorkEntryDTO workEntryDTO);

    /**
     * Get all the workEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkEntryDTO> findAll(Pageable pageable);

    /**
     * Get all the workEntries with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkEntryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" workEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEntryDTO> findOne(Long id);

    /**
     * Delete the "id" workEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
