package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AccountsPayableDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.AccountsPayable}.
 */
public interface AccountsPayableService {
    /**
     * Save a accountsPayable.
     *
     * @param accountsPayableDTO the entity to save.
     * @return the persisted entity.
     */
    AccountsPayableDTO save(AccountsPayableDTO accountsPayableDTO);

    /**
     * Updates a accountsPayable.
     *
     * @param accountsPayableDTO the entity to update.
     * @return the persisted entity.
     */
    AccountsPayableDTO update(AccountsPayableDTO accountsPayableDTO);

    /**
     * Partially updates a accountsPayable.
     *
     * @param accountsPayableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccountsPayableDTO> partialUpdate(AccountsPayableDTO accountsPayableDTO);

    /**
     * Get all the accountsPayables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountsPayableDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accountsPayable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountsPayableDTO> findOne(Long id);

    /**
     * Delete the "id" accountsPayable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
