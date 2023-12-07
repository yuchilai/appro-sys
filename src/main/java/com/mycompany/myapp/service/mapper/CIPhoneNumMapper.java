package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CIPhoneNum;
import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.service.dto.CIPhoneNumDTO;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIPhoneNum} and its DTO {@link CIPhoneNumDTO}.
 */
@Mapper(componentModel = "spring")
public interface CIPhoneNumMapper extends EntityMapper<CIPhoneNumDTO, CIPhoneNum> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoId")
    CIPhoneNumDTO toDto(CIPhoneNum s);

    @Named("contactInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactInfoDTO toDtoContactInfoId(ContactInfo contactInfo);
}
