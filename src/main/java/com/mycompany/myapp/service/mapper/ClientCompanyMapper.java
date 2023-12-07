package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.domain.ClientCompany;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.dto.ClientCompanyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientCompany} and its DTO {@link ClientCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientCompanyMapper extends EntityMapper<ClientCompanyDTO, ClientCompany> {
    @Mapping(target = "approvers", source = "approvers", qualifiedByName = "approverIdSet")
    ClientCompanyDTO toDto(ClientCompany s);

    @Mapping(target = "removeApprover", ignore = true)
    ClientCompany toEntity(ClientCompanyDTO clientCompanyDTO);

    @Named("approverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApproverDTO toDtoApproverId(Approver approver);

    @Named("approverIdSet")
    default Set<ApproverDTO> toDtoApproverIdSet(Set<Approver> approver) {
        return approver.stream().map(this::toDtoApproverId).collect(Collectors.toSet());
    }
}
