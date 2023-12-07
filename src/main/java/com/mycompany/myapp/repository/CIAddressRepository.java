package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CIAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CIAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIAddressRepository extends JpaRepository<CIAddress, Long> {}
