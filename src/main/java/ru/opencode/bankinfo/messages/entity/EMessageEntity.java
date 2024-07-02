package ru.opencode.bankinfo.messages.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.opencode.bankinfo.util.Audit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "EMessages")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class EMessageEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @NotNull
    @NotBlank
    private String eMessageName;
    @JsonIgnore
    private List<Long> entriesId;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Long edNo;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private LocalDate edDate;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private Long edAuthor;

    private Long edReceiver;

    @NonNull
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String creationReason;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private LocalDateTime creationTime;

    @NonNull
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String infoTypeCode;

    @NonNull
    @NotNull
    @Column(nullable = false)
    private LocalDate businessDay;

}
