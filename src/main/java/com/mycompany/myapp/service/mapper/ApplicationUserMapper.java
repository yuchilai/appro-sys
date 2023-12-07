package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    @Mapping(target = "approver", source = "approver", qualifiedByName = "approverId")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("approverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApproverDTO toDtoApproverId(Approver approver);
}
