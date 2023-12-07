package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ProjectService;
import com.mycompany.myapp.repository.ProjectServiceRepository;
import com.mycompany.myapp.service.ProjectServiceService;
import com.mycompany.myapp.service.dto.ProjectServiceDTO;
import com.mycompany.myapp.service.mapper.ProjectServiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ProjectService}.
 */
@Service
@Transactional
public class ProjectServiceServiceImpl implements ProjectServiceService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceServiceImpl.class);

    private final ProjectServiceRepository projectServiceRepository;

    private final ProjectServiceMapper projectServiceMapper;

    public ProjectServiceServiceImpl(ProjectServiceRepository projectServiceRepository, ProjectServiceMapper projectServiceMapper) {
        this.projectServiceRepository = projectServiceRepository;
        this.projectServiceMapper = projectServiceMapper;
    }

    @Override
    public ProjectServiceDTO save(ProjectServiceDTO projectServiceDTO) {
        log.debug("Request to save ProjectService : {}", projectServiceDTO);
        ProjectService projectService = projectServiceMapper.toEntity(projectServiceDTO);
        projectService = projectServiceRepository.save(projectService);
        return projectServiceMapper.toDto(projectService);
    }

    @Override
    public ProjectServiceDTO update(ProjectServiceDTO projectServiceDTO) {
        log.debug("Request to update ProjectService : {}", projectServiceDTO);
        ProjectService projectService = projectServiceMapper.toEntity(projectServiceDTO);
        projectService = projectServiceRepository.save(projectService);
        return projectServiceMapper.toDto(projectService);
    }

    @Override
    public Optional<ProjectServiceDTO> partialUpdate(ProjectServiceDTO projectServiceDTO) {
        log.debug("Request to partially update ProjectService : {}", projectServiceDTO);

        return projectServiceRepository
            .findById(projectServiceDTO.getId())
            .map(existingProjectService -> {
                projectServiceMapper.partialUpdate(existingProjectService, projectServiceDTO);

                return existingProjectService;
            })
            .map(projectServiceRepository::save)
            .map(projectServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectServices");
        return projectServiceRepository.findAll(pageable).map(projectServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectServiceDTO> findOne(Long id) {
        log.debug("Request to get ProjectService : {}", id);
        return projectServiceRepository.findById(id).map(projectServiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectService : {}", id);
        projectServiceRepository.deleteById(id);
    }
}
