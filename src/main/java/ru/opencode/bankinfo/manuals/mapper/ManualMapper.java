package ru.opencode.bankinfo.manuals.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.opencode.bankinfo.manuals.dto.ManualCreationDTO;
import ru.opencode.bankinfo.manuals.entity.Manual;


@Mapper(componentModel = "spring")
public interface ManualMapper {

    Manual manualCreationDTOToManual(ManualCreationDTO createManualDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateManualFromManualCreationDTO(ManualCreationDTO manualCreationDTO, @MappingTarget Manual manual);
}
