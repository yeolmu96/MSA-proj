package sp.informationservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.informationservice.entity.Institution;
import sp.informationservice.entity.Training;
import sp.informationservice.enums.NcsType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponse {
    private Long id;
    private String name;
    private NcsType ncsType;
    private String ncsTypeDescription;
    private int period;
    private Long institutionId;
    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public static TrainingResponse from(Training training) {
        return TrainingResponse.builder()
                .id(training.getId())
                .name(training.getName())
                .ncsType(training.getNcsType())
                .ncsTypeDescription(training.getNcsType().getDescription())
                .period(training.getPeriod())
                .institutionId(training.getInstitution().getId())
                .institutionName(training.getInstitution().getName())
                .startDate(training.getStartDate())
                .endDate(training.getEndDate())
                .createdAt(training.getCreatedAt())
                .build();
    }
}
