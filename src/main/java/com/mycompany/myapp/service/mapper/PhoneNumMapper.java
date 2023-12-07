package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.PhoneNum;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.PhoneNumDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhoneNum} and its DTO {@link PhoneNumDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhoneNumMapper extends EntityMapper<PhoneNumDTO, PhoneNum> {
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    PhoneNumDTO toDto(PhoneNum s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
