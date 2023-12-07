package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.repository.ContactInfoRepository;
import com.mycompany.myapp.service.ContactInfoService;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import com.mycompany.myapp.service.mapper.ContactInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ContactInfo}.
 */
@Service
@Transactional
public class ContactInfoServiceImpl implements ContactInfoService {

    private final Logger log = LoggerFactory.getLogger(ContactInfoServiceImpl.class);

    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoMapper contactInfoMapper;

    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository, ContactInfoMapper contactInfoMapper) {
        this.contactInfoRepository = contactInfoRepository;
        this.contactInfoMapper = contactInfoMapper;
    }

    @Override
    public ContactInfoDTO save(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to save ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    @Override
    public ContactInfoDTO update(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to update ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    @Override
    public Optional<ContactInfoDTO> partialUpdate(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to partially update ContactInfo : {}", contactInfoDTO);

        return contactInfoRepository
            .findById(contactInfoDTO.getId())
            .map(existingContactInfo -> {
                contactInfoMapper.partialUpdate(existingContactInfo, contactInfoDTO);

                return existingContactInfo;
            })
            .map(contactInfoRepository::save)
            .map(contactInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactInfos");
        return contactInfoRepository.findAll(pageable).map(contactInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactInfoDTO> findOne(Long id) {
        log.debug("Request to get ContactInfo : {}", id);
        return contactInfoRepository.findById(id).map(contactInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactInfo : {}", id);
        contactInfoRepository.deleteById(id);
    }
}
