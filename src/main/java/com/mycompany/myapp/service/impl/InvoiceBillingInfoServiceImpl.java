package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.InvoiceBillingInfo;
import com.mycompany.myapp.repository.InvoiceBillingInfoRepository;
import com.mycompany.myapp.service.InvoiceBillingInfoService;
import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
import com.mycompany.myapp.service.mapper.InvoiceBillingInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.InvoiceBillingInfo}.
 */
@Service
@Transactional
public class InvoiceBillingInfoServiceImpl implements InvoiceBillingInfoService {

    private final Logger log = LoggerFactory.getLogger(InvoiceBillingInfoServiceImpl.class);

    private final InvoiceBillingInfoRepository invoiceBillingInfoRepository;

    private final InvoiceBillingInfoMapper invoiceBillingInfoMapper;

    public InvoiceBillingInfoServiceImpl(
        InvoiceBillingInfoRepository invoiceBillingInfoRepository,
        InvoiceBillingInfoMapper invoiceBillingInfoMapper
    ) {
        this.invoiceBillingInfoRepository = invoiceBillingInfoRepository;
        this.invoiceBillingInfoMapper = invoiceBillingInfoMapper;
    }

    @Override
    public InvoiceBillingInfoDTO save(InvoiceBillingInfoDTO invoiceBillingInfoDTO) {
        log.debug("Request to save InvoiceBillingInfo : {}", invoiceBillingInfoDTO);
        InvoiceBillingInfo invoiceBillingInfo = invoiceBillingInfoMapper.toEntity(invoiceBillingInfoDTO);
        invoiceBillingInfo = invoiceBillingInfoRepository.save(invoiceBillingInfo);
        return invoiceBillingInfoMapper.toDto(invoiceBillingInfo);
    }

    @Override
    public InvoiceBillingInfoDTO update(InvoiceBillingInfoDTO invoiceBillingInfoDTO) {
        log.debug("Request to update InvoiceBillingInfo : {}", invoiceBillingInfoDTO);
        InvoiceBillingInfo invoiceBillingInfo = invoiceBillingInfoMapper.toEntity(invoiceBillingInfoDTO);
        invoiceBillingInfo = invoiceBillingInfoRepository.save(invoiceBillingInfo);
        return invoiceBillingInfoMapper.toDto(invoiceBillingInfo);
    }

    @Override
    public Optional<InvoiceBillingInfoDTO> partialUpdate(InvoiceBillingInfoDTO invoiceBillingInfoDTO) {
        log.debug("Request to partially update InvoiceBillingInfo : {}", invoiceBillingInfoDTO);

        return invoiceBillingInfoRepository
            .findById(invoiceBillingInfoDTO.getId())
            .map(existingInvoiceBillingInfo -> {
                invoiceBillingInfoMapper.partialUpdate(existingInvoiceBillingInfo, invoiceBillingInfoDTO);

                return existingInvoiceBillingInfo;
            })
            .map(invoiceBillingInfoRepository::save)
            .map(invoiceBillingInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceBillingInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceBillingInfos");
        return invoiceBillingInfoRepository.findAll(pageable).map(invoiceBillingInfoMapper::toDto);
    }

    /**
     *  Get all the invoiceBillingInfos where Invoice is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceBillingInfoDTO> findAllWhereInvoiceIsNull() {
        log.debug("Request to get all invoiceBillingInfos where Invoice is null");
        return StreamSupport
            .stream(invoiceBillingInfoRepository.findAll().spliterator(), false)
            .filter(invoiceBillingInfo -> invoiceBillingInfo.getInvoice() == null)
            .map(invoiceBillingInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceBillingInfoDTO> findOne(Long id) {
        log.debug("Request to get InvoiceBillingInfo : {}", id);
        return invoiceBillingInfoRepository.findById(id).map(invoiceBillingInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceBillingInfo : {}", id);
        invoiceBillingInfoRepository.deleteById(id);
    }
}
