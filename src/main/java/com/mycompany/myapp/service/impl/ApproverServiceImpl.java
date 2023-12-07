package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.repository.ApproverRepository;
import com.mycompany.myapp.service.ApproverService;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.mapper.ApproverMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Approver}.
 */
@Service
@Transactional
public class ApproverServiceImpl implements ApproverService {

    private final Logger log = LoggerFactory.getLogger(ApproverServiceImpl.class);

    private final ApproverRepository approverRepository;

    private final ApproverMapper approverMapper;

    public ApproverServiceImpl(ApproverRepository approverRepository, ApproverMapper approverMapper) {
        this.approverRepository = approverRepository;
        this.approverMapper = approverMapper;
    }

    @Override
    public ApproverDTO save(ApproverDTO approverDTO) {
        log.debug("Request to save Approver : {}", approverDTO);
        Approver approver = approverMapper.toEntity(approverDTO);
        approver = approverRepository.save(approver);
        return approverMapper.toDto(approver);
    }

    @Override
    public ApproverDTO update(ApproverDTO approverDTO) {
        log.debug("Request to update Approver : {}", approverDTO);
        Approver approver = approverMapper.toEntity(approverDTO);
        approver = approverRepository.save(approver);
        return approverMapper.toDto(approver);
    }

    @Override
    public Optional<ApproverDTO> partialUpdate(ApproverDTO approverDTO) {
        log.debug("Request to partially update Approver : {}", approverDTO);

        return approverRepository
            .findById(approverDTO.getId())
            .map(existingApprover -> {
                approverMapper.partialUpdate(existingApprover, approverDTO);

                return existingApprover;
            })
            .map(approverRepository::save)
            .map(approverMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApproverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Approvers");
        return approverRepository.findAll(pageable).map(approverMapper::toDto);
    }

    public Page<ApproverDTO> findAllWithEagerRelationships(Pageable pageable) {
        return approverRepository.findAllWithEagerRelationships(pageable).map(approverMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApproverDTO> findOne(Long id) {
        log.debug("Request to get Approver : {}", id);
        return approverRepository.findOneWithEagerRelationships(id).map(approverMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Approver : {}", id);
        approverRepository.deleteById(id);
    }
}
