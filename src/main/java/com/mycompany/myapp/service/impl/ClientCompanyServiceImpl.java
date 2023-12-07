package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.repository.ClientCompanyRepository;
import com.mycompany.myapp.service.ClientCompanyService;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.mapper.ClientCompanyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ClientCompany}.
 */
@Service
@Transactional
public class ClientCompanyServiceImpl implements ClientCompanyService {

    private final Logger log = LoggerFactory.getLogger(ClientCompanyServiceImpl.class);

    private final ClientCompanyRepository clientCompanyRepository;

    private final ClientCompanyMapper clientCompanyMapper;

    public ClientCompanyServiceImpl(ClientCompanyRepository clientCompanyRepository, ClientCompanyMapper clientCompanyMapper) {
        this.clientCompanyRepository = clientCompanyRepository;
        this.clientCompanyMapper = clientCompanyMapper;
    }

    @Override
    public ClientCompanyDTO save(ClientCompanyDTO clientCompanyDTO) {
        log.debug("Request to save ClientCompany : {}", clientCompanyDTO);
        ClientCompany clientCompany = clientCompanyMapper.toEntity(clientCompanyDTO);
        clientCompany = clientCompanyRepository.save(clientCompany);
        return clientCompanyMapper.toDto(clientCompany);
    }

    @Override
    public ClientCompanyDTO update(ClientCompanyDTO clientCompanyDTO) {
        log.debug("Request to update ClientCompany : {}", clientCompanyDTO);
        ClientCompany clientCompany = clientCompanyMapper.toEntity(clientCompanyDTO);
        clientCompany = clientCompanyRepository.save(clientCompany);
        return clientCompanyMapper.toDto(clientCompany);
    }

    @Override
    public Optional<ClientCompanyDTO> partialUpdate(ClientCompanyDTO clientCompanyDTO) {
        log.debug("Request to partially update ClientCompany : {}", clientCompanyDTO);

        return clientCompanyRepository
            .findById(clientCompanyDTO.getId())
            .map(existingClientCompany -> {
                clientCompanyMapper.partialUpdate(existingClientCompany, clientCompanyDTO);

                return existingClientCompany;
            })
            .map(clientCompanyRepository::save)
            .map(clientCompanyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientCompanies");
        return clientCompanyRepository.findAll(pageable).map(clientCompanyMapper::toDto);
    }

    public Page<ClientCompanyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clientCompanyRepository.findAllWithEagerRelationships(pageable).map(clientCompanyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientCompanyDTO> findOne(Long id) {
        log.debug("Request to get ClientCompany : {}", id);
        return clientCompanyRepository.findOneWithEagerRelationships(id).map(clientCompanyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientCompany : {}", id);
        clientCompanyRepository.deleteById(id);
    }
}
