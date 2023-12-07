package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Approval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Approval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {}
