package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.HourlyRateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.HourlyRate}.
 */
public interface HourlyRateService {
    /**
     * Save a hourlyRate.
     *
     * @param hourlyRateDTO the entity to save.
     * @return the persisted entity.
     */
    HourlyRateDTO save(HourlyRateDTO hourlyRateDTO);

    /**
     * Updates a hourlyRate.
     *
     * @param hourlyRateDTO the entity to update.
     * @return the persisted entity.
     */
    HourlyRateDTO update(HourlyRateDTO hourlyRateDTO);

    /**
     * Partially updates a hourlyRate.
     *
     * @param hourlyRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HourlyRateDTO> partialUpdate(HourlyRateDTO hourlyRateDTO);

    /**
     * Get all the hourlyRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HourlyRateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hourlyRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HourlyRateDTO> findOne(Long id);

    /**
     * Delete the "id" hourlyRate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
