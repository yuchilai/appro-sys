package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Approval;
import com.mycompany.myapp.repository.ApprovalRepository;
import com.mycompany.myapp.service.ApprovalService;
import com.mycompany.myapp.service.dto.ApprovalDTO;
import com.mycompany.myapp.service.mapper.ApprovalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Approval}.
 */
@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    private final Logger log = LoggerFactory.getLogger(ApprovalServiceImpl.class);

    private final ApprovalRepository approvalRepository;

    private final ApprovalMapper approvalMapper;

    public ApprovalServiceImpl(ApprovalRepository approvalRepository, ApprovalMapper approvalMapper) {
        this.approvalRepository = approvalRepository;
        this.approvalMapper = approvalMapper;
    }

    @Override
    public ApprovalDTO save(ApprovalDTO approvalDTO) {
        log.debug("Request to save Approval : {}", approvalDTO);
        Approval approval = approvalMapper.toEntity(approvalDTO);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    @Override
    public ApprovalDTO update(ApprovalDTO approvalDTO) {
        log.debug("Request to update Approval : {}", approvalDTO);
        Approval approval = approvalMapper.toEntity(approvalDTO);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    @Override
    public Optional<ApprovalDTO> partialUpdate(ApprovalDTO approvalDTO) {
        log.debug("Request to partially update Approval : {}", approvalDTO);

        return approvalRepository
            .findById(approvalDTO.getId())
            .map(existingApproval -> {
                approvalMapper.partialUpdate(existingApproval, approvalDTO);

                return existingApproval;
            })
            .map(approvalRepository::save)
            .map(approvalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Approvals");
        return approvalRepository.findAll(pageable).map(approvalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApprovalDTO> findOne(Long id) {
        log.debug("Request to get Approval : {}", id);
        return approvalRepository.findById(id).map(approvalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Approval : {}", id);
        approvalRepository.deleteById(id);
    }
}
