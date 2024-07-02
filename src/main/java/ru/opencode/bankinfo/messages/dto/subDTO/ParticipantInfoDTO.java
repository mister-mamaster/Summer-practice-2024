package ru.opencode.bankinfo.messages.dto.subDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.opencode.bankinfo.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ParticipantInfo", namespace = "urn:cbr-ru:ed:v2.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParticipantInfoDTO {

    @NonNull
    @NotBlank
    @XmlAttribute(name = "NameP")
    @Size(max = 160)
    private String nameP;
    @XmlAttribute(name = "RegN")
    @Size(max = 9)
    private String regN;
    @XmlAttribute(name = "CntrCd")
    @Size(min = 2, max = 2)
    private String cntrCd;

    @NonNull
    @XmlAttribute(name = "Rgn")
    @Size(max = 2)
    private Integer rgn;
    @XmlAttribute(name = "Ind")
    @Size(max = 6)
    private Integer ind;
    @XmlAttribute(name = "Tnp")
    @Size(max = 5)
    private String tnp;
    @XmlAttribute(name = "Nnp")
    @Size(max = 25)
    private String nnp;
    @XmlAttribute(name = "Adr")
    @Size(max = 160)
    private String adr;
    @XmlAttribute(name = "PrntBIC")
    @Size(min = 9, max = 9)
    private String prntBic;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "DateIn")
    @NonNull
    private LocalDate dateIn;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "DateOut")
    private LocalDate dateOut;

    @NonNull
    @XmlAttribute(name = "PtType")
    @Size(max = 2)
    private Byte ptType;

    @NonNull
    @XmlAttribute(name = "Srvcs")
    @Size(max = 1)
    private Byte srvcs;

    @NonNull
    @XmlAttribute(name = "XchType")
    @Size(max = 1)
    private Byte xchType;

    @NonNull
    @NotBlank
    @XmlAttribute(name = "UID")
    @Size(min = 10, max = 10)
    private String UID;
    @XmlAttribute(name = "ParticipantStatus")
    @Size(min = 4, max = 4)
    private String participantStatus;

    @XmlElement(name = "RstrList", namespace = "urn:cbr-ru:ed:v2.0")
    List<RstrListDto> rstrList;
}
