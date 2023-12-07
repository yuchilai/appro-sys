package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CIAddress;
import com.mycompany.myapp.repository.CIAddressRepository;
import com.mycompany.myapp.service.CIAddressService;
import com.mycompany.myapp.service.dto.CIAddressDTO;
import com.mycompany.myapp.service.mapper.CIAddressMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CIAddress}.
 */
@Service
@Transactional
public class CIAddressServiceImpl implements CIAddressService {

    private final Logger log = LoggerFactory.getLogger(CIAddressServiceImpl.class);

    private final CIAddressRepository cIAddressRepository;

    private final CIAddressMapper cIAddressMapper;

    public CIAddressServiceImpl(CIAddressRepository cIAddressRepository, CIAddressMapper cIAddressMapper) {
        this.cIAddressRepository = cIAddressRepository;
        this.cIAddressMapper = cIAddressMapper;
    }

    @Override
    public CIAddressDTO save(CIAddressDTO cIAddressDTO) {
        log.debug("Request to save CIAddress : {}", cIAddressDTO);
        CIAddress cIAddress = cIAddressMapper.toEntity(cIAddressDTO);
        cIAddress = cIAddressRepository.save(cIAddress);
        return cIAddressMapper.toDto(cIAddress);
    }

    @Override
    public CIAddressDTO update(CIAddressDTO cIAddressDTO) {
        log.debug("Request to update CIAddress : {}", cIAddressDTO);
        CIAddress cIAddress = cIAddressMapper.toEntity(cIAddressDTO);
        cIAddress = cIAddressRepository.save(cIAddress);
        return cIAddressMapper.toDto(cIAddress);
    }

    @Override
    public Optional<CIAddressDTO> partialUpdate(CIAddressDTO cIAddressDTO) {
        log.debug("Request to partially update CIAddress : {}", cIAddressDTO);

        return cIAddressRepository
            .findById(cIAddressDTO.getId())
            .map(existingCIAddress -> {
                cIAddressMapper.partialUpdate(existingCIAddress, cIAddressDTO);

                return existingCIAddress;
            })
            .map(cIAddressRepository::save)
            .map(cIAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CIAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIAddresses");
        return cIAddressRepository.findAll(pageable).map(cIAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CIAddressDTO> findOne(Long id) {
        log.debug("Request to get CIAddress : {}", id);
        return cIAddressRepository.findById(id).map(cIAddressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CIAddress : {}", id);
        cIAddressRepository.deleteById(id);
    }
}
