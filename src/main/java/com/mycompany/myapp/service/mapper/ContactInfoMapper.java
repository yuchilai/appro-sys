package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactInfo} and its DTO {@link ContactInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactInfoMapper extends EntityMapper<ContactInfoDTO, ContactInfo> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    ContactInfoDTO toDto(ContactInfo s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
