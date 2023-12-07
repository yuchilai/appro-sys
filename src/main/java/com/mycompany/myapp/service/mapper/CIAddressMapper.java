package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CIAddress;
import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.service.dto.CIAddressDTO;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIAddress} and its DTO {@link CIAddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface CIAddressMapper extends EntityMapper<CIAddressDTO, CIAddress> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoId")
    CIAddressDTO toDto(CIAddress s);

    @Named("contactInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactInfoDTO toDtoContactInfoId(ContactInfo contactInfo);
}
