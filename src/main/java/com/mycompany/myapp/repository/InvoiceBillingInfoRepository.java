package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InvoiceBillingInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InvoiceBillingInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceBillingInfoRepository extends JpaRepository<InvoiceBillingInfo, Long> {}
