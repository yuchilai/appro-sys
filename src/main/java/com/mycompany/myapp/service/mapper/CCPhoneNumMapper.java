package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CCPhoneNum;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.service.dto.CCPhoneNumDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCPhoneNum} and its DTO {@link CCPhoneNumDTO}.
 */
@Mapper(componentModel = "spring")
public interface CCPhoneNumMapper extends EntityMapper<CCPhoneNumDTO, CCPhoneNum> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    CCPhoneNumDTO toDto(CCPhoneNum s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
