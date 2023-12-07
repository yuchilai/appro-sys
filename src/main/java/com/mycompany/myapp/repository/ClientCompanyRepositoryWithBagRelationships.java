package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClientCompany;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ClientCompanyRepositoryWithBagRelationships {
    Optional<ClientCompany> fetchBagRelationships(Optional<ClientCompany> clientCompany);

    List<ClientCompany> fetchBagRelationships(List<ClientCompany> clientCompanies);

    Page<ClientCompany> fetchBagRelationships(Page<ClientCompany> clientCompanies);
}
