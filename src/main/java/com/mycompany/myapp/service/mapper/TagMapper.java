package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ApplicationUser;
import com.mycompany.myapp.domain.Tag;
import com.mycompany.myapp.domain.WorkEntry;
import com.mycompany.myapp.service.dto.ApplicationUserDTO;
import com.mycompany.myapp.service.dto.TagDTO;
import com.mycompany.myapp.service.dto.WorkEntryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "workEntries", source = "workEntries", qualifiedByName = "workEntryTitleSet")
    @Mapping(target = "applicationUser", source = "applicationUser", qualifiedByName = "applicationUserId")
    TagDTO toDto(Tag s);

    @Mapping(target = "removeWorkEntry", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Named("workEntryTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    WorkEntryDTO toDtoWorkEntryTitle(WorkEntry workEntry);

    @Named("workEntryTitleSet")
    default Set<WorkEntryDTO> toDtoWorkEntryTitleSet(Set<WorkEntry> workEntry) {
        return workEntry.stream().map(this::toDtoWorkEntryTitle).collect(Collectors.toSet());
    }

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
