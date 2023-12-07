package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CCPhoneNum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CCPhoneNum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCPhoneNumRepository extends JpaRepository<CCPhoneNum, Long> {}
