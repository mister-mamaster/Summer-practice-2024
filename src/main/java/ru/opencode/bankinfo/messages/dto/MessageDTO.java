package ru.opencode.bankinfo.messages.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.opencode.bankinfo.adapter.LocalDateAdapter;
import ru.opencode.bankinfo.adapter.LocalDateTimeAdapter;
import ru.opencode.bankinfo.messages.dto.subDTO.EntryDTO;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ED807", namespace = "urn:cbr-ru:ed:v2.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageDTO {

    private String eMessageName;

    private Long edReceiver;

    @NonNull
    @NotNull
    @NotEmpty
    @XmlElement(name = "BICDirectoryEntry", namespace = "urn:cbr-ru:ed:v2.0")
    private Set<EntryDTO> entries;

    @NonNull
    @NotNull
    @XmlAttribute(name = "EDNo")
    @Size(max = 9)
    private Long edNo;

    @NonNull
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "EDDate")
    private LocalDate edDate;

    @NonNull
    @NotNull
    @XmlAttribute(name = "EDAuthor")
    @Size(min = 10, max = 10)
    private Long edAuthor;

    @NonNull
    @NotNull
    @NotBlank
    @XmlAttribute(name = "CreationReason")
    @Size(min = 4, max = 4)
    private String creationReason;

    @NonNull
    @NotNull
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlAttribute(name = "CreationDateTime")
    private LocalDateTime creationTime;

    @NonNull
    @NotNull
    @NotBlank
    @XmlAttribute(name = "InfoTypeCode")
    @Size(min = 4, max = 4)
    private String infoTypeCode;

    @NonNull
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "BusinessDay")
    private LocalDate businessDay;

}
