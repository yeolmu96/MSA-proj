package sp.qna_service.enums;

import lombok.Getter;

@Getter
public enum QuestionType {
    CAREER("취업/진로"),
    COURSE_CONTENT("과정내용"),
    PROJECT("프로젝트"),
    STUDY("스터디"),
    NETWORK("네트워킹"),
    GENERAL("일반질문");

    private final String description;

    QuestionType(String description) {
        this.description = description;
    }
}