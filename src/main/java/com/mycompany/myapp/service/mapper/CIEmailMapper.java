package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CIEmail;
import com.mycompany.myapp.domain.ContactInfo;
import com.mycompany.myapp.service.dto.CIEmailDTO;
import com.mycompany.myapp.service.dto.ContactInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIEmail} and its DTO {@link CIEmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface CIEmailMapper extends EntityMapper<CIEmailDTO, CIEmail> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoId")
    CIEmailDTO toDto(CIEmail s);

    @Named("contactInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactInfoDTO toDtoContactInfoId(ContactInfo contactInfo);
}
