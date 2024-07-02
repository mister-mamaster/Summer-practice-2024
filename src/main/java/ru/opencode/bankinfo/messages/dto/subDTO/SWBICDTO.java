package ru.opencode.bankinfo.messages.dto.subDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SWBICS", namespace = "urn:cbr-ru:ed:v2.0")
public class SWBICDTO {

    @NonNull
    @NotBlank
    @XmlAttribute(name = "SWBIC")
    @Size(max = 11)
    private String SWBIC;

    @NonNull
    @XmlAttribute(name = "DefaultSWBIC")
    private Boolean defaultSWBIC;

}
