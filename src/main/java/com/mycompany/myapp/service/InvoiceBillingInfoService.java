package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.InvoiceBillingInfo}.
 */
public interface InvoiceBillingInfoService {
    /**
     * Save a invoiceBillingInfo.
     *
     * @param invoiceBillingInfoDTO the entity to save.
     * @return the persisted entity.
     */
    InvoiceBillingInfoDTO save(InvoiceBillingInfoDTO invoiceBillingInfoDTO);

    /**
     * Updates a invoiceBillingInfo.
     *
     * @param invoiceBillingInfoDTO the entity to update.
     * @return the persisted entity.
     */
    InvoiceBillingInfoDTO update(InvoiceBillingInfoDTO invoiceBillingInfoDTO);

    /**
     * Partially updates a invoiceBillingInfo.
     *
     * @param invoiceBillingInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InvoiceBillingInfoDTO> partialUpdate(InvoiceBillingInfoDTO invoiceBillingInfoDTO);

    /**
     * Get all the invoiceBillingInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InvoiceBillingInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the InvoiceBillingInfoDTO where Invoice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<InvoiceBillingInfoDTO> findAllWhereInvoiceIsNull();

    /**
     * Get the "id" invoiceBillingInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvoiceBillingInfoDTO> findOne(Long id);

    /**
     * Delete the "id" invoiceBillingInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
