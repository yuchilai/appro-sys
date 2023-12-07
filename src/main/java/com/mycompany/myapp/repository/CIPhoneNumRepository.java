package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CIPhoneNum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CIPhoneNum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIPhoneNumRepository extends JpaRepository<CIPhoneNum, Long> {}
