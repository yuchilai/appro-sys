package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByApplicationUserId(Long applicationUserId, Pageable pageable);
}
