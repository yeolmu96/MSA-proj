package sp.informationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sp.informationservice.controller.request.InstitutionRequest;
import sp.informationservice.enums.InstitutionType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "institution")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다 (예: 02-1234-5678)")
    @Column(length = 20)
    private String phoneNumber;

    @Email(message = "이메일 형식이 올바르지 않습니다")
    @Column(length = 100)
    private String email;

    private String website;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstitutionType institutionType;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Training> trainings;


    @CreationTimestamp
    private LocalDateTime createdAt;

    public void update(InstitutionRequest request) {
        this.name = request.getName();
        this.address = request.getAddress();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
        this.website = request.getWebsite();
        this.institutionType = request.getInstitutionType();
    }

}
