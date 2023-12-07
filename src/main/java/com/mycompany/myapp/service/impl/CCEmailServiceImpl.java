package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CCEmail;
import com.mycompany.myapp.repository.CCEmailRepository;
import com.mycompany.myapp.service.CCEmailService;
import com.mycompany.myapp.service.dto.CCEmailDTO;
import com.mycompany.myapp.service.mapper.CCEmailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CCEmail}.
 */
@Service
@Transactional
public class CCEmailServiceImpl implements CCEmailService {

    private final Logger log = LoggerFactory.getLogger(CCEmailServiceImpl.class);

    private final CCEmailRepository cCEmailRepository;

    private final CCEmailMapper cCEmailMapper;

    public CCEmailServiceImpl(CCEmailRepository cCEmailRepository, CCEmailMapper cCEmailMapper) {
        this.cCEmailRepository = cCEmailRepository;
        this.cCEmailMapper = cCEmailMapper;
    }

    @Override
    public CCEmailDTO save(CCEmailDTO cCEmailDTO) {
        log.debug("Request to save CCEmail : {}", cCEmailDTO);
        CCEmail cCEmail = cCEmailMapper.toEntity(cCEmailDTO);
        cCEmail = cCEmailRepository.save(cCEmail);
        return cCEmailMapper.toDto(cCEmail);
    }

    @Override
    public CCEmailDTO update(CCEmailDTO cCEmailDTO) {
        log.debug("Request to update CCEmail : {}", cCEmailDTO);
        CCEmail cCEmail = cCEmailMapper.toEntity(cCEmailDTO);
        cCEmail = cCEmailRepository.save(cCEmail);
        return cCEmailMapper.toDto(cCEmail);
    }

    @Override
    public Optional<CCEmailDTO> partialUpdate(CCEmailDTO cCEmailDTO) {
        log.debug("Request to partially update CCEmail : {}", cCEmailDTO);

        return cCEmailRepository
            .findById(cCEmailDTO.getId())
            .map(existingCCEmail -> {
                cCEmailMapper.partialUpdate(existingCCEmail, cCEmailDTO);

                return existingCCEmail;
            })
            .map(cCEmailRepository::save)
            .map(cCEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CCEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCEmails");
        return cCEmailRepository.findAll(pageable).map(cCEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CCEmailDTO> findOne(Long id) {
        log.debug("Request to get CCEmail : {}", id);
        return cCEmailRepository.findById(id).map(cCEmailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCEmail : {}", id);
        cCEmailRepository.deleteById(id);
    }
}
