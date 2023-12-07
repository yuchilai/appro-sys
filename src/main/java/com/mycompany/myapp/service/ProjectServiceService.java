package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProjectServiceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ProjectService}.
 */
public interface ProjectServiceService {
    /**
     * Save a projectService.
     *
     * @param projectServiceDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectServiceDTO save(ProjectServiceDTO projectServiceDTO);

    /**
     * Updates a projectService.
     *
     * @param projectServiceDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectServiceDTO update(ProjectServiceDTO projectServiceDTO);

    /**
     * Partially updates a projectService.
     *
     * @param projectServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectServiceDTO> partialUpdate(ProjectServiceDTO projectServiceDTO);

    /**
     * Get all the projectServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectServiceDTO> findOne(Long id);

    /**
     * Delete the "id" projectService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
