package sp.informationservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.informationservice.entity.Institution;
import sp.informationservice.enums.InstitutionType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionResponse {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private InstitutionType institutionType;
    private String institutionTypeDescription;
    private LocalDateTime createdAt;

    public static InstitutionResponse from(Institution institution) {
        return InstitutionResponse.builder()
                .id(institution.getId())
                .name(institution.getName())
                .address(institution.getAddress())
                .phoneNumber(institution.getPhoneNumber())
                .email(institution.getEmail())
                .website(institution.getWebsite())
                .institutionType(institution.getInstitutionType())
                .institutionTypeDescription(institution.getInstitutionType().getDescription())
                .createdAt(institution.getCreatedAt())
                .build();
    }
}
