package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProjectService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectServiceRepository extends JpaRepository<ProjectService, Long> {}
