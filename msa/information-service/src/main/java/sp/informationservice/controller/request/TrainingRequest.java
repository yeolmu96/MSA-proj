package sp.informationservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.informationservice.entity.Training;
import sp.informationservice.enums.NcsType;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequest {

    @NotBlank(message = "훈련과정명은 필수입니다")
    private String name;

    @NotNull(message = "NCS 직종은 필수입니다")
    private NcsType ncsType;

    @NotNull(message = "훈련기간은 필수입니다")
    private Integer period;

    @NotNull(message = "훈련시작일은 필수입니다")
    private LocalDate startDate;

    @NotNull(message = "훈련종료일은 필수입니다")
    private LocalDate endDate;

    @NotNull(message = "훈련기관 ID는 필수입니다")
    private Long institutionId;

    public Training toEntity() {
        return Training.builder()
                .name(this.name)
                .ncsType(this.ncsType)
                .period(this.period)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
