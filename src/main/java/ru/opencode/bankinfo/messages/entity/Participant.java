package ru.opencode.bankinfo.messages.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.opencode.bankinfo.messages.entity.subClass.Rstr;

import java.time.LocalDate;
import java.util.List;

@Embeddable
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Participant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long idP;

    @NonNull
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String nameP;
    
    private String regN;

    private String cntrCd;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Integer rgn;

    private Integer ind;

    private String tnp;

    private String nnp;

    private String adr;

    private String prntBic;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private LocalDate dateIn;

    private LocalDate dateOut;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Byte ptType;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Byte srvcs;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Byte xchType;

    @NonNull
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String UID;

    private String participantStatus;

    @OneToMany(mappedBy = "entry", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Column(length = 500)
    private List<Rstr> rstrList;

}
