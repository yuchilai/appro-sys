package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CCEmail;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.service.dto.CCEmailDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCEmail} and its DTO {@link CCEmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface CCEmailMapper extends EntityMapper<CCEmailDTO, CCEmail> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    CCEmailDTO toDto(CCEmail s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
