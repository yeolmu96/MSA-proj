package sp.informationservice.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sp.informationservice.entity.Institution;
import sp.informationservice.enums.InstitutionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionRequest {

    @NotBlank(message = "기관명은 필수입니다")
    private String name;

    @NotBlank(message = "주소는 필수입니다")
    private String address;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다")
    private String phoneNumber;

    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    private String website;

    @NotNull(message = "기관 유형은 필수입니다")
    private InstitutionType institutionType;

    public Institution toEntity() {
        return Institution.builder()
                .name(this.name)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .website(this.website)
                .institutionType(this.institutionType)
                .build();
    }
}
