package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PhoneNum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PhoneNum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneNumRepository extends JpaRepository<PhoneNum, Long> {}
