package ru.opencode.bankinfo.messages.dto.subDTO;

import jakarta.validation.constraints.Digits;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Accounts", namespace = "urn:cbr-ru:ed:v2.0")
public class AccountDTO {

    @NonNull
    @NotBlank
    @XmlAttribute(name = "Account")
    @Size(min = 20, max = 20)
    private String account;

    @NonNull
    @NotBlank
    @XmlAttribute(name = "RegulationAccountType")
    @Size(min = 4, max = 4)
    private String regulationAccountType;

    @XmlAttribute(name = "CK")
    @Digits(integer = 2, fraction = 0)
    private Byte controlKey;

    @NonNull
    @NotBlank
    @XmlAttribute(name = "AccountCBRBIC")
    @Size(min = 9, max = 9)
    private Long accountCBRBIC;

    @NonNull
    @NotBlank
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlAttribute(name = "DateIn")
    private LocalDate dateIn;

    private LocalDate dateOut;
    @XmlAttribute(name = "AccountStatus")
    @Size(min = 4, max = 4)
    private String accountStatus;
    @XmlElement(name = "AccRstrList", namespace = "urn:cbr-ru:ed:v2.0")
    private List<AccRstrListDto> accRstrLists;

}
