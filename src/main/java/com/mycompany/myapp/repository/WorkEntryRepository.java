package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.WorkEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkEntry entity.
 */
@Repository
public interface WorkEntryRepository extends JpaRepository<WorkEntry, Long> {
    default Optional<WorkEntry> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<WorkEntry> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<WorkEntry> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select workEntry from WorkEntry workEntry left join fetch workEntry.hourlyRate left join fetch workEntry.projectService",
        countQuery = "select count(workEntry) from WorkEntry workEntry"
    )
    Page<WorkEntry> findAllWithToOneRelationships(Pageable pageable);

    @Query("select workEntry from WorkEntry workEntry left join fetch workEntry.hourlyRate left join fetch workEntry.projectService")
    List<WorkEntry> findAllWithToOneRelationships();

    @Query(
        "select workEntry from WorkEntry workEntry left join fetch workEntry.hourlyRate left join fetch workEntry.projectService where workEntry.id =:id"
    )
    Optional<WorkEntry> findOneWithToOneRelationships(@Param("id") Long id);
}
