package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AccountsPayable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountsPayable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, Long> {}
