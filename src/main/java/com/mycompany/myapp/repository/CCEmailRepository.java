package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CCEmail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CCEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCEmailRepository extends JpaRepository<CCEmail, Long> {}
