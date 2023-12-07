package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.domain.HourlyRate;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import com.mycompany.myapp.service.dto.HourlyRateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HourlyRate} and its DTO {@link HourlyRateDTO}.
 */
@Mapper(componentModel = "spring")
public interface HourlyRateMapper extends EntityMapper<HourlyRateDTO, HourlyRate> {
    @Mapping(target = "clientCompany", source = "clientCompany", qualifiedByName = "clientCompanyId")
    HourlyRateDTO toDto(HourlyRate s);

    @Named("clientCompanyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientCompanyDTO toDtoClientCompanyId(ClientCompany clientCompany);
}
