package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClientCompany;
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
public class ClientCompanyRepositoryWithBagRelationshipsImpl implements ClientCompanyRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ClientCompany> fetchBagRelationships(Optional<ClientCompany> clientCompany) {
        return clientCompany.map(this::fetchApprovers);
    }

    @Override
    public Page<ClientCompany> fetchBagRelationships(Page<ClientCompany> clientCompanies) {
        return new PageImpl<>(
            fetchBagRelationships(clientCompanies.getContent()),
            clientCompanies.getPageable(),
            clientCompanies.getTotalElements()
        );
    }

    @Override
    public List<ClientCompany> fetchBagRelationships(List<ClientCompany> clientCompanies) {
        return Optional.of(clientCompanies).map(this::fetchApprovers).orElse(Collections.emptyList());
    }

    ClientCompany fetchApprovers(ClientCompany result) {
        return entityManager
            .createQuery(
                "select clientCompany from ClientCompany clientCompany left join fetch clientCompany.approvers where clientCompany.id = :id",
                ClientCompany.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ClientCompany> fetchApprovers(List<ClientCompany> clientCompanies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clientCompanies.size()).forEach(index -> order.put(clientCompanies.get(index).getId(), index));
        List<ClientCompany> result = entityManager
            .createQuery(
                "select clientCompany from ClientCompany clientCompany left join fetch clientCompany.approvers where clientCompany in :clientCompanies",
                ClientCompany.class
            )
            .setParameter("clientCompanies", clientCompanies)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
