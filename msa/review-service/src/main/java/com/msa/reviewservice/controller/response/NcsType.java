package com.msa.reviewservice.controller.response;

import lombok.Getter;

@Getter
public enum NcsType {
    CLOUD("클라우드", "클라우드 컴퓨팅, 클라우드 엔지니어링 등"),
    AI("인공지능", "인공지능 개발, 머신러닝 등"),
    BIG_DATA("빅데이터", "빅데이터 분석, 데이터 시각화 등"),
    SECURITY("정보보안", "정보보안 전문가 양성 등"),
    AR_VR("AR/VR", "증강현실/가상현실 개발 등"),
    FIVE_G("5G", "5G 통신 기술 등");

    private final String displayName;
    private final String description;

    NcsType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
