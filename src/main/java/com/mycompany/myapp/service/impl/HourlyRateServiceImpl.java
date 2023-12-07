package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.HourlyRate;
import com.mycompany.myapp.repository.HourlyRateRepository;
import com.mycompany.myapp.service.HourlyRateService;
import com.mycompany.myapp.service.dto.HourlyRateDTO;
import com.mycompany.myapp.service.mapper.HourlyRateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.HourlyRate}.
 */
@Service
@Transactional
public class HourlyRateServiceImpl implements HourlyRateService {

    private final Logger log = LoggerFactory.getLogger(HourlyRateServiceImpl.class);

    private final HourlyRateRepository hourlyRateRepository;

    private final HourlyRateMapper hourlyRateMapper;

    public HourlyRateServiceImpl(HourlyRateRepository hourlyRateRepository, HourlyRateMapper hourlyRateMapper) {
        this.hourlyRateRepository = hourlyRateRepository;
        this.hourlyRateMapper = hourlyRateMapper;
    }

    @Override
    public HourlyRateDTO save(HourlyRateDTO hourlyRateDTO) {
        log.debug("Request to save HourlyRate : {}", hourlyRateDTO);
        HourlyRate hourlyRate = hourlyRateMapper.toEntity(hourlyRateDTO);
        hourlyRate = hourlyRateRepository.save(hourlyRate);
        return hourlyRateMapper.toDto(hourlyRate);
    }

    @Override
    public HourlyRateDTO update(HourlyRateDTO hourlyRateDTO) {
        log.debug("Request to update HourlyRate : {}", hourlyRateDTO);
        HourlyRate hourlyRate = hourlyRateMapper.toEntity(hourlyRateDTO);
        hourlyRate = hourlyRateRepository.save(hourlyRate);
        return hourlyRateMapper.toDto(hourlyRate);
    }

    @Override
    public Optional<HourlyRateDTO> partialUpdate(HourlyRateDTO hourlyRateDTO) {
        log.debug("Request to partially update HourlyRate : {}", hourlyRateDTO);

        return hourlyRateRepository
            .findById(hourlyRateDTO.getId())
            .map(existingHourlyRate -> {
                hourlyRateMapper.partialUpdate(existingHourlyRate, hourlyRateDTO);

                return existingHourlyRate;
            })
            .map(hourlyRateRepository::save)
            .map(hourlyRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HourlyRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HourlyRates");
        return hourlyRateRepository.findAll(pageable).map(hourlyRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HourlyRateDTO> findOne(Long id) {
        log.debug("Request to get HourlyRate : {}", id);
        return hourlyRateRepository.findById(id).map(hourlyRateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HourlyRate : {}", id);
        hourlyRateRepository.deleteById(id);
    }
}
