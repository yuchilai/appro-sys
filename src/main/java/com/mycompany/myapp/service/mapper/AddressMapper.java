package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    AddressDTO toDto(Address s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
