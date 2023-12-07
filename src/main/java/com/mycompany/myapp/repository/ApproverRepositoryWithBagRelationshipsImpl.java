package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Approver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ApproverRepositoryWithBagRelationshipsImpl implements ApproverRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Approver> fetchBagRelationships(Optional<Approver> approver) {
        return approver.map(this::fetchApprovedWorkEntries);
    }

    @Override
    public Page<Approver> fetchBagRelationships(Page<Approver> approvers) {
        return new PageImpl<>(fetchBagRelationships(approvers.getContent()), approvers.getPageable(), approvers.getTotalElements());
    }

    @Override
    public List<Approver> fetchBagRelationships(List<Approver> approvers) {
        return Optional.of(approvers).map(this::fetchApprovedWorkEntries).orElse(Collections.emptyList());
    }

    Approver fetchApprovedWorkEntries(Approver result) {
        return entityManager
            .createQuery(
                "select approver from Approver approver left join fetch approver.approvedWorkEntries where approver.id = :id",
                Approver.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Approver> fetchApprovedWorkEntries(List<Approver> approvers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, approvers.size()).forEach(index -> order.put(approvers.get(index).getId(), index));
        List<Approver> result = entityManager
            .createQuery(
                "select approver from Approver approver left join fetch approver.approvedWorkEntries where approver in :approvers",
                Approver.class
            )
            .setParameter("approvers", approvers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
