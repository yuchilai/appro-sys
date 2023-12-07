package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CCAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CCAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCAddressRepository extends JpaRepository<CCAddress, Long> {}
