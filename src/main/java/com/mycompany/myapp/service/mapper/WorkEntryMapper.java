package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.domain.HourlyRate;
import com.mycompany.myapp.domain.Invoice;
import com.mycompany.myapp.domain.ProjectService;
import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.dto.HourlyRateDTO;
import com.mycompany.myapp.service.dto.InvoiceDTO;
import com.mycompany.myapp.service.dto.ProjectServiceDTO;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkEntry} and its DTO {@link WorkEntryDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkEntryMapper extends EntityMapper<WorkEntryDTO, WorkEntry> {
    @Mapping(target = "hourlyRate", source = "hourlyRate", qualifiedByName = "hourlyRateName")
    @Mapping(target = "projectService", source = "projectService", qualifiedByName = "projectServiceName")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "applicationUserId")
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceId")
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    WorkEntryDTO toDto(WorkEntry s);

    @Named("hourlyRateName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HourlyRateDTO toDtoHourlyRateName(HourlyRate hourlyRate);

    @Named("projectServiceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProjectServiceDTO toDtoProjectServiceName(ProjectService projectService);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);

    @Named("invoiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceDTO toDtoInvoiceId(Invoice invoice);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
