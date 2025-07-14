package com.msa.gathering.entity;

public enum TechStacks {
    // 기획
    PLANNING("기획"),
    UX_UI_DESIGN("UX/UI 디자인"),
    PRODUCT_MANAGEMENT("프로덕트 매니지먼트"),

    // 백엔드
    SPRING_BOOT("Spring Boot"),
    NODE_JS("Node.js"),
    DJANGO("Django"),
    EXPRESS_JS("Express.js"),
    REST_API("REST API"),

    // 프론트엔드
    REACT("React"),
    ANGULAR("Angular"),
    VUE("Vue.js"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    HTML("HTML"),
    CSS("CSS"),

    // AI
    MACHINE_LEARNING("Machine Learning"),
    DEEP_LEARNING("Deep Learning"),
    NLP("자연어 처리"),
    COMPUTER_VISION("Computer Vision"),
    TENSORFLOW("TensorFlow"),
    PYTORCH("PyTorch"),

    // 마케팅
    SEO("SEO"),
    CONTENT_MARKETING("콘텐츠 마케팅"),
    SOCIAL_MEDIA("소셜 미디어 마케팅"),
    DATA_ANALYTICS("데이터 분석"),

    // 디자인
    GRAPHIC_DESIGN("그래픽 디자인"),
    UI_UX("UI/UX"),
    ADOBE_PHOTOSHOP("Adobe Photoshop"),
    ADOBE_ILLUSTRATOR("Adobe Illustrator");

    private final String displayName;

    TechStacks(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
