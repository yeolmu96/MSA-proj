package sp.informationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import sp.informationservice.controller.request.TrainingRequest;
import sp.informationservice.enums.NcsType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "training")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "훈련과정명은 필수입니다.")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "NCS 직종은 필수입니다.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NcsType ncsType;

    @NotNull(message = "훈련기간은 필수입니다.")
    @Column(nullable = false)
    private int period;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @NotNull(message = "훈련시작일은 필수입니다.")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "훈련종료일은 필수입니다.")
    @Column(nullable = false)
    private LocalDate endDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void update(TrainingRequest request, Institution institution) {
        this.name = request.getName();
        this.ncsType = request.getNcsType();
        this.period = request.getPeriod();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.institution = institution;
    }
}
