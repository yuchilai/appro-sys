package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CCAddress;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.service.dto.CCAddressDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCAddress} and its DTO {@link CCAddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface CCAddressMapper extends EntityMapper<CCAddressDTO, CCAddress> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    CCAddressDTO toDto(CCAddress s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
