package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AccountsPayable;
import com.mycompany.myapp.repository.AccountsPayableRepository;
import com.mycompany.myapp.service.AccountsPayableService;
import com.mycompany.myapp.service.dto.AccountsPayableDTO;
import com.mycompany.myapp.service.mapper.AccountsPayableMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.AccountsPayable}.
 */
@Service
@Transactional
public class AccountsPayableServiceImpl implements AccountsPayableService {

    private final Logger log = LoggerFactory.getLogger(AccountsPayableServiceImpl.class);

    private final AccountsPayableRepository accountsPayableRepository;

    private final AccountsPayableMapper accountsPayableMapper;

    public AccountsPayableServiceImpl(AccountsPayableRepository accountsPayableRepository, AccountsPayableMapper accountsPayableMapper) {
        this.accountsPayableRepository = accountsPayableRepository;
        this.accountsPayableMapper = accountsPayableMapper;
    }

    @Override
    public AccountsPayableDTO save(AccountsPayableDTO accountsPayableDTO) {
        log.debug("Request to save AccountsPayable : {}", accountsPayableDTO);
        AccountsPayable accountsPayable = accountsPayableMapper.toEntity(accountsPayableDTO);
        accountsPayable = accountsPayableRepository.save(accountsPayable);
        return accountsPayableMapper.toDto(accountsPayable);
    }

    @Override
    public AccountsPayableDTO update(AccountsPayableDTO accountsPayableDTO) {
        log.debug("Request to update AccountsPayable : {}", accountsPayableDTO);
        AccountsPayable accountsPayable = accountsPayableMapper.toEntity(accountsPayableDTO);
        accountsPayable = accountsPayableRepository.save(accountsPayable);
        return accountsPayableMapper.toDto(accountsPayable);
    }

    @Override
    public Optional<AccountsPayableDTO> partialUpdate(AccountsPayableDTO accountsPayableDTO) {
        log.debug("Request to partially update AccountsPayable : {}", accountsPayableDTO);

        return accountsPayableRepository
            .findById(accountsPayableDTO.getId())
            .map(existingAccountsPayable -> {
                accountsPayableMapper.partialUpdate(existingAccountsPayable, accountsPayableDTO);

                return existingAccountsPayable;
            })
            .map(accountsPayableRepository::save)
            .map(accountsPayableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountsPayableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountsPayables");
        return accountsPayableRepository.findAll(pageable).map(accountsPayableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountsPayableDTO> findOne(Long id) {
        log.debug("Request to get AccountsPayable : {}", id);
        return accountsPayableRepository.findById(id).map(accountsPayableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountsPayable : {}", id);
        accountsPayableRepository.deleteById(id);
    }
}
