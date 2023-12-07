package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.AccountsPayable;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.service.dto.AccountsPayableDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountsPayable} and its DTO {@link AccountsPayableDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountsPayableMapper extends EntityMapper<AccountsPayableDTO, AccountsPayable> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    AccountsPayableDTO toDto(AccountsPayable s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
