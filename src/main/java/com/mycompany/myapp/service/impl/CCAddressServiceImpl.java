package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CCAddress;
import com.mycompany.myapp.repository.CCAddressRepository;
import com.mycompany.myapp.service.CCAddressService;
import com.mycompany.myapp.service.dto.CCAddressDTO;
import com.mycompany.myapp.service.mapper.CCAddressMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CCAddress}.
 */
@Service
@Transactional
public class CCAddressServiceImpl implements CCAddressService {

    private final Logger log = LoggerFactory.getLogger(CCAddressServiceImpl.class);

    private final CCAddressRepository cCAddressRepository;

    private final CCAddressMapper cCAddressMapper;

    public CCAddressServiceImpl(CCAddressRepository cCAddressRepository, CCAddressMapper cCAddressMapper) {
        this.cCAddressRepository = cCAddressRepository;
        this.cCAddressMapper = cCAddressMapper;
    }

    @Override
    public CCAddressDTO save(CCAddressDTO cCAddressDTO) {
        log.debug("Request to save CCAddress : {}", cCAddressDTO);
        CCAddress cCAddress = cCAddressMapper.toEntity(cCAddressDTO);
        cCAddress = cCAddressRepository.save(cCAddress);
        return cCAddressMapper.toDto(cCAddress);
    }

    @Override
    public CCAddressDTO update(CCAddressDTO cCAddressDTO) {
        log.debug("Request to update CCAddress : {}", cCAddressDTO);
        CCAddress cCAddress = cCAddressMapper.toEntity(cCAddressDTO);
        cCAddress = cCAddressRepository.save(cCAddress);
        return cCAddressMapper.toDto(cCAddress);
    }

    @Override
    public Optional<CCAddressDTO> partialUpdate(CCAddressDTO cCAddressDTO) {
        log.debug("Request to partially update CCAddress : {}", cCAddressDTO);

        return cCAddressRepository
            .findById(cCAddressDTO.getId())
            .map(existingCCAddress -> {
                cCAddressMapper.partialUpdate(existingCCAddress, cCAddressDTO);

                return existingCCAddress;
            })
            .map(cCAddressRepository::save)
            .map(cCAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CCAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCAddresses");
        return cCAddressRepository.findAll(pageable).map(cCAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CCAddressDTO> findOne(Long id) {
        log.debug("Request to get CCAddress : {}", id);
        return cCAddressRepository.findById(id).map(cCAddressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCAddress : {}", id);
        cCAddressRepository.deleteById(id);
    }
}
