package sp.informationservice.enums;

import lombok.Getter;

@Getter
public enum InstitutionType {
    PUBLIC("공공훈련기관"),
    PRIVATE("민간훈련기관"),
    OTHER("기타훈련기관");

    private final String description;

    InstitutionType(String description) {
        this.description = description;
    }
}
