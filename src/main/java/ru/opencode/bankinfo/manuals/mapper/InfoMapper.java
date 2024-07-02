package ru.opencode.bankinfo.manuals.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.opencode.bankinfo.manuals.dto.InfoCreationDTO;
import ru.opencode.bankinfo.manuals.entity.Info;

@Mapper(componentModel = "spring")
public interface InfoMapper {

    Info infoCreationDTOToInfo(InfoCreationDTO infoCreationDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInfoFromInfoCreationDTO(InfoCreationDTO infoCreationDTO, @MappingTarget Info info);
}
