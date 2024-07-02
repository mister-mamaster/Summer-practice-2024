package ru.opencode.bankinfo.messages.dto.subDTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.opencode.bankinfo.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AccRstrList", namespace = "urn:cbr-ru:ed:v2.0")
public class AccRstrListDto {
    @XmlAttribute(name = "AccRstr")
    @Size(min = 4, max = 4)
    private String accRstr;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "AccRstrDate")
    private LocalDate accRstrDate;

    @XmlAttribute(name = "SuccessorBIC")
    @Size(min = 9, max = 9)
    private Long successorBIC;
}