package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Approver;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ApproverRepositoryWithBagRelationships {
    Optional<Approver> fetchBagRelationships(Optional<Approver> approver);

    List<Approver> fetchBagRelationships(List<Approver> approvers);

    Page<Approver> fetchBagRelationships(Page<Approver> approvers);
}
