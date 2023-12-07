package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HourlyRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HourlyRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HourlyRateRepository extends JpaRepository<HourlyRate, Long> {}
