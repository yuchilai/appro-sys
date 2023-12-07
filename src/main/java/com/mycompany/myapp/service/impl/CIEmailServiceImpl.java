package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CIEmail;
import com.mycompany.myapp.repository.CIEmailRepository;
import com.mycompany.myapp.service.CIEmailService;
import com.mycompany.myapp.service.dto.CIEmailDTO;
import com.mycompany.myapp.service.mapper.CIEmailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CIEmail}.
 */
@Service
@Transactional
public class CIEmailServiceImpl implements CIEmailService {

    private final Logger log = LoggerFactory.getLogger(CIEmailServiceImpl.class);

    private final CIEmailRepository cIEmailRepository;

    private final CIEmailMapper cIEmailMapper;

    public CIEmailServiceImpl(CIEmailRepository cIEmailRepository, CIEmailMapper cIEmailMapper) {
        this.cIEmailRepository = cIEmailRepository;
        this.cIEmailMapper = cIEmailMapper;
    }

    @Override
    public CIEmailDTO save(CIEmailDTO cIEmailDTO) {
        log.debug("Request to save CIEmail : {}", cIEmailDTO);
        CIEmail cIEmail = cIEmailMapper.toEntity(cIEmailDTO);
        cIEmail = cIEmailRepository.save(cIEmail);
        return cIEmailMapper.toDto(cIEmail);
    }

    @Override
    public CIEmailDTO update(CIEmailDTO cIEmailDTO) {
        log.debug("Request to update CIEmail : {}", cIEmailDTO);
        CIEmail cIEmail = cIEmailMapper.toEntity(cIEmailDTO);
        cIEmail = cIEmailRepository.save(cIEmail);
        return cIEmailMapper.toDto(cIEmail);
    }

    @Override
    public Optional<CIEmailDTO> partialUpdate(CIEmailDTO cIEmailDTO) {
        log.debug("Request to partially update CIEmail : {}", cIEmailDTO);

        return cIEmailRepository
            .findById(cIEmailDTO.getId())
            .map(existingCIEmail -> {
                cIEmailMapper.partialUpdate(existingCIEmail, cIEmailDTO);

                return existingCIEmail;
            })
            .map(cIEmailRepository::save)
            .map(cIEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CIEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIEmails");
        return cIEmailRepository.findAll(pageable).map(cIEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CIEmailDTO> findOne(Long id) {
        log.debug("Request to get CIEmail : {}", id);
        return cIEmailRepository.findById(id).map(cIEmailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CIEmail : {}", id);
        cIEmailRepository.deleteById(id);
    }
}
