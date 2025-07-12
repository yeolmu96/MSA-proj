# Spring Cloud Gateway + Eureka 마이크로서비스 스터디 프로젝트

Spring Cloud Gateway와 Eureka를 활용한 기본 마이크로서비스 아키텍처 학습 프로젝트입니다.

## 프로젝트 구조

```
eureka_gateway_study/
├── eureka-server/          # 서비스 디스커버리 서버
├── gateway-service/        # API 게이트웨이
└── user-service/          # 예제 사용자 서비스
```

### 각 서비스 설명

- **eureka-server** (포트 8761): Netflix Eureka 서비스 디스커버리 서버
- **gateway-service** (포트 8080): Spring Cloud Gateway를 통한 API 게이트웨이
- **user-service** (포트 8081): 기본 사용자 API를 제공하는 마이크로서비스

## 기술 스택

- **Spring Boot**: 3.5.3
- **Spring Cloud**: 2025.0.0
- **Java**: 17
- **빌드 도구**: Gradle

## 사전 요구사항

- Java 17 이상
- IDE (IntelliJ IDEA, Eclipse, VS Code 등)

## 실행 방법

### 1. 서비스 실행 순서

**중요**: 반드시 아래 순서대로 실행해야 합니다.

#### 1.1 Eureka Server 실행 (첫 번째)

- 브라우저에서 `http://localhost:8761` 접속하여 Eureka 대시보드 확인
- 서비스 등록 목록이 비어있는 것을 확인

#### 1.2 User Service 실행 (두 번째)

- Eureka 대시보드에서 `USER-SERVICE` 등록 확인

#### 1.3 Gateway Service 실행 (세 번째)

- Eureka 대시보드에서 `GATEWAY-SERVICE` 등록 확인

### 2. 테스트

모든 서비스가 실행되면 다음 방법으로 테스트할 수 있습니다:

#### 직접 User Service 호출
```bash
curl http://localhost:8081/api/users/1
```

#### Gateway를 통한 호출
```bash
curl http://localhost:8080/api/users/1
```

**예상 응답:**
```json
{
  "id": 1,
  "name": "User 1",
  "email": "user1@example.com"
}
```

## API 엔드포인트

### User Service
- `GET /api/users/{id}`: 사용자 정보 조회

### Gateway Routes
- `GET /api/users/**`: User Service로 프록시

## 개발 가이드

### 새로운 서비스 추가하기

1. **새 모듈 생성**

2. **Eureka Client 의존성 추가**
   ```gradle
   implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
   ```

3. **application.yaml 설정**
   ```yaml
   spring:
     application:
       name: new-service
   
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/
   ```

4. **Gateway 라우팅 설정**
   ```yaml
   spring:
     cloud:
       gateway:
         routes:
           - id: new-service
             uri: lb://NEW-SERVICE
             predicates:
               - Path=/api/new/**
   ```

### 포트 설정

서비스별 기본 포트:
- Eureka Server: 8761
- Gateway Service: 8080
- User Service: 8081
- 추가 서비스: 8082, 8083, ...

### 서비스가 Eureka에 등록되지 않는 경우

1. Eureka Server가 먼저 실행되었는지 확인
2. `application.yaml`의 `eureka.client.service-url.defaultZone` 설정 확인
3. 로그에서 연결 오류 메시지 확인

### Gateway 라우팅이 작동하지 않는 경우

1. 대상 서비스가 Eureka에 등록되었는지 확인
2. 서비스 이름이 대문자로 설정되었는지 확인 (예: `USER-SERVICE`)
3. Gateway 설정의 `lb://SERVICE-NAME` 형식 확인
