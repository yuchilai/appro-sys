package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Approval;
import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.service.dto.ApprovalDTO;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Approval} and its DTO {@link ApprovalDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalMapper extends EntityMapper<ApprovalDTO, Approval> {
    @Mapping(target = "approver", source = "approver", qualifiedByName = "approverId")
    @Mapping(target = "workEntry", source = "workEntry", qualifiedByName = "workEntryId")
    ApprovalDTO toDto(Approval s);

    @Named("approverId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApproverDTO toDtoApproverId(Approver approver);

    @Named("workEntryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorkEntryDTO toDtoWorkEntryId(WorkEntry workEntry);
}
