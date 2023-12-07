package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PhoneNum;
import com.mycompany.myapp.repository.PhoneNumRepository;
import com.mycompany.myapp.service.PhoneNumService;
import com.mycompany.myapp.service.dto.PhoneNumDTO;
import com.mycompany.myapp.service.mapper.PhoneNumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.PhoneNum}.
 */
@Service
@Transactional
public class PhoneNumServiceImpl implements PhoneNumService {

    private final Logger log = LoggerFactory.getLogger(PhoneNumServiceImpl.class);

    private final PhoneNumRepository phoneNumRepository;

    private final PhoneNumMapper phoneNumMapper;

    public PhoneNumServiceImpl(PhoneNumRepository phoneNumRepository, PhoneNumMapper phoneNumMapper) {
        this.phoneNumRepository = phoneNumRepository;
        this.phoneNumMapper = phoneNumMapper;
    }

    @Override
    public PhoneNumDTO save(PhoneNumDTO phoneNumDTO) {
        log.debug("Request to save PhoneNum : {}", phoneNumDTO);
        PhoneNum phoneNum = phoneNumMapper.toEntity(phoneNumDTO);
        phoneNum = phoneNumRepository.save(phoneNum);
        return phoneNumMapper.toDto(phoneNum);
    }

    @Override
    public PhoneNumDTO update(PhoneNumDTO phoneNumDTO) {
        log.debug("Request to update PhoneNum : {}", phoneNumDTO);
        PhoneNum phoneNum = phoneNumMapper.toEntity(phoneNumDTO);
        phoneNum = phoneNumRepository.save(phoneNum);
        return phoneNumMapper.toDto(phoneNum);
    }

    @Override
    public Optional<PhoneNumDTO> partialUpdate(PhoneNumDTO phoneNumDTO) {
        log.debug("Request to partially update PhoneNum : {}", phoneNumDTO);

        return phoneNumRepository
            .findById(phoneNumDTO.getId())
            .map(existingPhoneNum -> {
                phoneNumMapper.partialUpdate(existingPhoneNum, phoneNumDTO);

                return existingPhoneNum;
            })
            .map(phoneNumRepository::save)
            .map(phoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhoneNumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhoneNums");
        return phoneNumRepository.findAll(pageable).map(phoneNumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneNumDTO> findOne(Long id) {
        log.debug("Request to get PhoneNum : {}", id);
        return phoneNumRepository.findById(id).map(phoneNumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneNum : {}", id);
        phoneNumRepository.deleteById(id);
    }
}
