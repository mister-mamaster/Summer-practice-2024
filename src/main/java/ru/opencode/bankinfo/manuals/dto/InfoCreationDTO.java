package ru.opencode.bankinfo.manuals.dto;

import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class InfoCreationDTO {
    @NotNull
    private String name;
    @CreatedDate
    private LocalDate cTime;

    @Version
    private Integer version;

}
