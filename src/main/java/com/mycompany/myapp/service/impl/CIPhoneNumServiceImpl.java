package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CIPhoneNum;
import com.mycompany.myapp.repository.CIPhoneNumRepository;
import com.mycompany.myapp.service.CIPhoneNumService;
import com.mycompany.myapp.service.dto.CIPhoneNumDTO;
import com.mycompany.myapp.service.mapper.CIPhoneNumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CIPhoneNum}.
 */
@Service
@Transactional
public class CIPhoneNumServiceImpl implements CIPhoneNumService {

    private final Logger log = LoggerFactory.getLogger(CIPhoneNumServiceImpl.class);

    private final CIPhoneNumRepository cIPhoneNumRepository;

    private final CIPhoneNumMapper cIPhoneNumMapper;

    public CIPhoneNumServiceImpl(CIPhoneNumRepository cIPhoneNumRepository, CIPhoneNumMapper cIPhoneNumMapper) {
        this.cIPhoneNumRepository = cIPhoneNumRepository;
        this.cIPhoneNumMapper = cIPhoneNumMapper;
    }

    @Override
    public CIPhoneNumDTO save(CIPhoneNumDTO cIPhoneNumDTO) {
        log.debug("Request to save CIPhoneNum : {}", cIPhoneNumDTO);
        CIPhoneNum cIPhoneNum = cIPhoneNumMapper.toEntity(cIPhoneNumDTO);
        cIPhoneNum = cIPhoneNumRepository.save(cIPhoneNum);
        return cIPhoneNumMapper.toDto(cIPhoneNum);
    }

    @Override
    public CIPhoneNumDTO update(CIPhoneNumDTO cIPhoneNumDTO) {
        log.debug("Request to update CIPhoneNum : {}", cIPhoneNumDTO);
        CIPhoneNum cIPhoneNum = cIPhoneNumMapper.toEntity(cIPhoneNumDTO);
        cIPhoneNum = cIPhoneNumRepository.save(cIPhoneNum);
        return cIPhoneNumMapper.toDto(cIPhoneNum);
    }

    @Override
    public Optional<CIPhoneNumDTO> partialUpdate(CIPhoneNumDTO cIPhoneNumDTO) {
        log.debug("Request to partially update CIPhoneNum : {}", cIPhoneNumDTO);

        return cIPhoneNumRepository
            .findById(cIPhoneNumDTO.getId())
            .map(existingCIPhoneNum -> {
                cIPhoneNumMapper.partialUpdate(existingCIPhoneNum, cIPhoneNumDTO);

                return existingCIPhoneNum;
            })
            .map(cIPhoneNumRepository::save)
            .map(cIPhoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CIPhoneNumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIPhoneNums");
        return cIPhoneNumRepository.findAll(pageable).map(cIPhoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CIPhoneNumDTO> findOne(Long id) {
        log.debug("Request to get CIPhoneNum : {}", id);
        return cIPhoneNumRepository.findById(id).map(cIPhoneNumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CIPhoneNum : {}", id);
        cIPhoneNumRepository.deleteById(id);
    }
}
