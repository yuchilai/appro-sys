package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.domain.ProjectService;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.dto.ProjectServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectService} and its DTO {@link ProjectServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectServiceMapper extends EntityMapper<ProjectServiceDTO, ProjectService> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    ProjectServiceDTO toDto(ProjectService s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
