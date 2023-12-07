package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.repository.WorkEntryRepository;
import com.mycompany.myapp.service.WorkEntryService;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import com.mycompany.myapp.service.mapper.WorkEntryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.WorkEntry}.
 */
@Service
@Transactional
public class WorkEntryServiceImpl implements WorkEntryService {

    private final Logger log = LoggerFactory.getLogger(WorkEntryServiceImpl.class);

    private final WorkEntryRepository workEntryRepository;

    private final WorkEntryMapper workEntryMapper;

    public WorkEntryServiceImpl(WorkEntryRepository workEntryRepository, WorkEntryMapper workEntryMapper) {
        this.workEntryRepository = workEntryRepository;
        this.workEntryMapper = workEntryMapper;
    }

    @Override
    public WorkEntryDTO save(WorkEntryDTO workEntryDTO) {
        log.debug("Request to save WorkEntry : {}", workEntryDTO);
        WorkEntry workEntry = workEntryMapper.toEntity(workEntryDTO);
        workEntry = workEntryRepository.save(workEntry);
        return workEntryMapper.toDto(workEntry);
    }

    @Override
    public WorkEntryDTO update(WorkEntryDTO workEntryDTO) {
        log.debug("Request to update WorkEntry : {}", workEntryDTO);
        WorkEntry workEntry = workEntryMapper.toEntity(workEntryDTO);
        workEntry = workEntryRepository.save(workEntry);
        return workEntryMapper.toDto(workEntry);
    }

    @Override
    public Optional<WorkEntryDTO> partialUpdate(WorkEntryDTO workEntryDTO) {
        log.debug("Request to partially update WorkEntry : {}", workEntryDTO);

        return workEntryRepository
            .findById(workEntryDTO.getId())
            .map(existingWorkEntry -> {
                workEntryMapper.partialUpdate(existingWorkEntry, workEntryDTO);

                return existingWorkEntry;
            })
            .map(workEntryRepository::save)
            .map(workEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEntries");
        return workEntryRepository.findAll(pageable).map(workEntryMapper::toDto);
    }

    public Page<WorkEntryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return workEntryRepository.findAllWithEagerRelationships(pageable).map(workEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkEntryDTO> findOne(Long id) {
        log.debug("Request to get WorkEntry : {}", id);
        return workEntryRepository.findOneWithEagerRelationships(id).map(workEntryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkEntry : {}", id);
        workEntryRepository.deleteById(id);
    }
}
