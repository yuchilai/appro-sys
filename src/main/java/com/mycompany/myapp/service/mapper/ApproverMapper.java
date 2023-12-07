package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Approver;
import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.service.dto.ApproverDTO;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Approver} and its DTO {@link ApproverDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApproverMapper extends EntityMapper<ApproverDTO, Approver> {
    @Mapping(target = "approvedWorkEntries", source = "approvedWorkEntries", qualifiedByName = "workEntryTitleSet")
    ApproverDTO toDto(Approver s);

    @Mapping(target = "removeApprovedWorkEntries", ignore = true)
    Approver toEntity(ApproverDTO approverDTO);

    @Named("workEntryTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    WorkEntryDTO toDtoWorkEntryTitle(WorkEntry workEntry);

    @Named("workEntryTitleSet")
    default Set<WorkEntryDTO> toDtoWorkEntryTitleSet(Set<WorkEntry> workEntry) {
        return workEntry.stream().map(this::toDtoWorkEntryTitle).collect(Collectors.toSet());
    }
}
