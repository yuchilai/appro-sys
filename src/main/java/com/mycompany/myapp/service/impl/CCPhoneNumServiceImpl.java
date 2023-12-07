package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CCPhoneNum;
import com.mycompany.myapp.repository.CCPhoneNumRepository;
import com.mycompany.myapp.service.CCPhoneNumService;
import com.mycompany.myapp.service.dto.CCPhoneNumDTO;
import com.mycompany.myapp.service.mapper.CCPhoneNumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CCPhoneNum}.
 */
@Service
@Transactional
public class CCPhoneNumServiceImpl implements CCPhoneNumService {

    private final Logger log = LoggerFactory.getLogger(CCPhoneNumServiceImpl.class);

    private final CCPhoneNumRepository cCPhoneNumRepository;

    private final CCPhoneNumMapper cCPhoneNumMapper;

    public CCPhoneNumServiceImpl(CCPhoneNumRepository cCPhoneNumRepository, CCPhoneNumMapper cCPhoneNumMapper) {
        this.cCPhoneNumRepository = cCPhoneNumRepository;
        this.cCPhoneNumMapper = cCPhoneNumMapper;
    }

    @Override
    public CCPhoneNumDTO save(CCPhoneNumDTO cCPhoneNumDTO) {
        log.debug("Request to save CCPhoneNum : {}", cCPhoneNumDTO);
        CCPhoneNum cCPhoneNum = cCPhoneNumMapper.toEntity(cCPhoneNumDTO);
        cCPhoneNum = cCPhoneNumRepository.save(cCPhoneNum);
        return cCPhoneNumMapper.toDto(cCPhoneNum);
    }

    @Override
    public CCPhoneNumDTO update(CCPhoneNumDTO cCPhoneNumDTO) {
        log.debug("Request to update CCPhoneNum : {}", cCPhoneNumDTO);
        CCPhoneNum cCPhoneNum = cCPhoneNumMapper.toEntity(cCPhoneNumDTO);
        cCPhoneNum = cCPhoneNumRepository.save(cCPhoneNum);
        return cCPhoneNumMapper.toDto(cCPhoneNum);
    }

    @Override
    public Optional<CCPhoneNumDTO> partialUpdate(CCPhoneNumDTO cCPhoneNumDTO) {
        log.debug("Request to partially update CCPhoneNum : {}", cCPhoneNumDTO);

        return cCPhoneNumRepository
            .findById(cCPhoneNumDTO.getId())
            .map(existingCCPhoneNum -> {
                cCPhoneNumMapper.partialUpdate(existingCCPhoneNum, cCPhoneNumDTO);

                return existingCCPhoneNum;
            })
            .map(cCPhoneNumRepository::save)
            .map(cCPhoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CCPhoneNumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCPhoneNums");
        return cCPhoneNumRepository.findAll(pageable).map(cCPhoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CCPhoneNumDTO> findOne(Long id) {
        log.debug("Request to get CCPhoneNum : {}", id);
        return cCPhoneNumRepository.findById(id).map(cCPhoneNumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCPhoneNum : {}", id);
        cCPhoneNumRepository.deleteById(id);
    }
}
