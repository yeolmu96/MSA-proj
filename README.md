# MSA 프로젝트

Spring Cloud 기반의 마이크로서비스 아키텍처 프로젝트입니다.
<br /><br />

Notion <br />

https://canary-marquis-2c0.notion.site/MSA-21fdf07bd8c780c2bf9ef217e9fed606?source=copy_link

<br /><br />

<img width="785" height="436" alt="스크린샷 2025-07-22 오후 1 55 49" src="https://github.com/user-attachments/assets/75c3bf72-599f-4d77-8ef6-e2d123971071" />


<br /><br />


<img width="769" height="360" alt="image" src="https://github.com/user-attachments/assets/98b96f98-4260-455c-a64c-8858c0eb2d40" />
<br /><br />



<br /><br />


## 프로젝트 구조
<br />

```
msa/
├── eureka-server/         # 서비스 디스커버리 서버 (8761)
├── gateway-service/       # API 게이트웨이 (8080)
├── account-service/       # 계정 서비스 (8081)
├── board-service/         # 게시판 서비스 (8082)
├── information-service/   # 정보 서비스 (8084)
├── qna-service/          # QnA 서비스 (8085)
├── gathering-service/     # 프로젝트 팀모집 서비스 (8086)
└── review-service/       # 리뷰 서비스 (8087)
```

## 서비스 포트 정보

| 서비스명 | 포트 | 담당자 |
|---------|------|--------|
| Eureka Server | 8761 | - |
| API Gateway | 8080 | - |
| Account Service | 8081 | 정은선 |
| Board Service | 8082 | 김정범 |
| Information Service | 8084 | 김지한 |
| QnA Service | 8085 | 김지한 |
| Gathering Service | 8086 | 최현수 |
| Review Service | 8087 | 김혜인 |  


### 주요 기능
<br />

✅ 계정(Account) 관련
<br /> <br />

회원 가입, 로그인, 로그아웃 기능 제공

사용자 정보 조회 및 수정 (비밀번호, 닉네임, 교육기관 등)

서비스 간 사용자 정보 연동 (/gathering-info, /review-info)

토큰 재발급 및 인증 관련 처리

포인트 시스템 제공 (조회, 업데이트)

회원 탈퇴 처리
<br /><br />

✅ 교육 기관(Institutions) 관리
<br /><br />
교육 기관 등록, 조회, 수정, 삭제

전체 목록 및 특정 기관 상세 조회

✅ 교육과정(Training) 관리
<br /><br />
교육과정 등록, 조회, 수정, 삭제

전체 목록 및 개별 과정 조회 가능
<br /><br />

✅ 게시판(Board) 기능
<br /><br />
게시판에 게시글 생성 및 전체 목록 조회

특정 게시판 또는 특정 게시글 조회

게시글 추천수 증가, 인기 게시글 확인

게시글 수정 및 삭제 가능

특정 게시글에 댓글 작성

특정 게시글의 전체 댓글 목록 조회
<br /><br />

✅ 리뷰(Review) 기능
<br /><br />
리뷰 생성, 수정, 삭제

모든 리뷰 또는 특정 교육과정/사용자의 리뷰 조회

사용자 기반 리뷰 조회 시 권한 검사 포함
<br /><br />
✅ 모임(Gathering) 기능
<br /><br />
모임 생성 및 전체 목록 조회

특정 모임 상세 조회 및 수정, 삭제 가능

신청 시 알림 및 모임 참여 및 탈퇴 처리

참가 조건 확인 및 모임 완료 처리

특정 사용자의 모임 목록 조회 가능

Account 서비스와 연동하여 사용자 정보 요청
<br /><br />
✅ Q&A 기능
<br /><br />
질문 생성 및 전체 목록 조회

특정 질문 상세 조회 및 수정, 삭제 가능

질문에 대한 답변 등록 및 내 답변 목록 조회

질문 추천 기능 및 사용자별 질문 이력 조회 가능

  <br /><br />







<img width="1427" height="748" alt="image" src="https://github.com/user-attachments/assets/9632e97b-fffc-459c-9319-834db8e46926" />


<br /><br />


< 메인(randing) 페이지 >


<br /><br />



<img width="1446" height="732" alt="image" src="https://github.com/user-attachments/assets/0a36b7ca-02c7-4a9a-a82d-616dc0a0c828" />


<br /><br />



< 리뷰 페이지 >



<br /><br />



<img width="1441" height="765" alt="image" src="https://github.com/user-attachments/assets/c24ae6b1-bc18-46d6-8a08-233155af67bf" />
<br /><br />
< 훈련기관 정보 제공 페이지 (+ 검색 기능) >

<br /><br />
<img width="1446" height="761" alt="image" src="https://github.com/user-attachments/assets/5e664b00-af14-4182-9032-46136edf9d26" />
<br /><br />
< 훈련교육기관 정보 제공 페이지 (+ 검색 기능) >
<br /><br />


<img width="1423" height="667" alt="image" src="https://github.com/user-attachments/assets/dd015050-e754-4125-a7f8-7ca3b57a4eb4" />
<br /><br />
<img width="1380" height="759" alt="image" src="https://github.com/user-attachments/assets/0c51ad69-531b-4356-857d-87b379d9b809" />
<br /><br />

< 커뮤니티 게시판 >
<br /><br />


<img width="1412" height="792" alt="image" src="https://github.com/user-attachments/assets/939935dd-3f95-4eab-b3a7-11f55364d2c4" />
<br /><br />
<img width="1363" height="775" alt="image" src="https://github.com/user-attachments/assets/21f33483-ca5d-4391-8506-0629e0f6eec4" />
<br /><br />
<img width="1345" height="775" alt="image" src="https://github.com/user-attachments/assets/d84be86e-1e6d-457d-9988-37f1540afa95" />


<br /><br />





<img width="1227" height="756" alt="image" src="https://github.com/user-attachments/assets/ec53c4d5-07c5-46cc-a50a-56c442f8e5aa" />
<br /><br />
<img width="1240" height="729" alt="image" src="https://github.com/user-attachments/assets/bbd542fb-bcc0-4341-b479-a6f563852e4f" />
<br /><br />
<img width="1241" height="751" alt="image" src="https://github.com/user-attachments/assets/868ffcc6-bfd2-4b36-af4f-40c93da30687" />
<br /><br />

< 모임 >

<br /><br />
<img width="1381" height="757" alt="image" src="https://github.com/user-attachments/assets/5086457b-5288-4073-ae21-981c9f768c27" />
<br /><br />
<img width="1370" height="746" alt="image" src="https://github.com/user-attachments/assets/6286e9a7-557c-4fb5-92e0-55ea66dd99e0" />
<br /><br />
<img width="1349" height="768" alt="image" src="https://github.com/user-attachments/assets/19e6748b-02a1-4fa8-989c-c28d1ffc1f24" />
<br /><br />
< 로그인 >
<br /><br />



<img width="1357" height="773" alt="image" src="https://github.com/user-attachments/assets/4e51974a-b34a-4329-b21f-792d9f6155cd" />
<br /><br />
<img width="1282" height="773" alt="image" src="https://github.com/user-attachments/assets/391c5e77-b91f-4e4b-ae39-8584f48dceac" />
<br /><br />
<img width="1370" height="781" alt="image" src="https://github.com/user-attachments/assets/885e36b2-14c9-4110-916a-c89e1b8e0cf8" />
<br /><br />


< 마이페이지 >
<br /><br />


<img width="1398" height="671" alt="image" src="https://github.com/user-attachments/assets/533a793c-fd65-42a4-8f7b-54f90b740bde" />
<br /><br />
<img width="1348" height="657" alt="image" src="https://github.com/user-attachments/assets/c3da5d8b-57b3-4ac4-b1ed-8a689b835144" />
<br /><br />
<img width="1356" height="742" alt="image" src="https://github.com/user-attachments/assets/0d614854-db5f-4cb0-9e7a-f5f77362791c" />
<br /><br />
<img width="1380" height="757" alt="image" src="https://github.com/user-attachments/assets/bf95b087-d4ee-4f9e-9ed0-be125353f287" />

<br /><br />

< Q&A >

<br /><br />






## 데이터베이스 설정

### 사용자 계정 생성
```sql
CREATE USER IF NOT EXISTS 'msa'@'localhost' IDENTIFIED BY '123';
```

### 데이터베이스 생성 및 권한 부여

#### 메인 데이터베이스
```sql
CREATE DATABASE IF NOT EXISTS msa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON msa.* TO 'msa'@'localhost';
```

#### Account Service (8081)
```sql
CREATE DATABASE IF NOT EXISTS msa_account CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON msa_account.* TO 'msa'@'localhost';
```

#### Information Service (8084)
```sql
CREATE DATABASE IF NOT EXISTS msa_information CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON msa_information.* TO 'msa'@'localhost';
```

#### QnA Service (8085)
```sql
CREATE DATABASE IF NOT EXISTS msa_qna CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON msa_qna.* TO 'msa'@'localhost';
```

#### Board Service (8082)
```sql
CREATE DATABASE IF NOT EXISTS msa_board CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON msa_board.* TO 'msa'@'localhost';
```

#### Review Service (8087)
```sql
CREATE DATABASE IF NOT EXISTS msa_review CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON msa_review.* TO 'msa'@'localhost';
```

#### Gathering Service (8086)
```sql
CREATE DATABASE IF NOT EXISTS msa_gathering CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON msa_gathering.* TO 'msa'@'localhost';
```

### 예제 데이터 (Information Service)

```sql
INSERT INTO training (name, ncs_type, period, start_date, end_date, institution_id, created_at) VALUES
  ('클라우드 AWS 엔지니어 양성과정', 'CLOUD', 6, '2024-03-01', '2024-08-31', (SELECT id FROM institution WHERE name = '서울IT교육원'), NOW()),
  ('AI 머신러닝 개발자 과정', 'AI', 4, '2024-04-01', '2024-07-31', (SELECT id FROM institution WHERE name = '부산기술대학교'), NOW()),
  ('빅데이터 분석 전문가 과정', 'BIG_DATA', 5, '2024-02-15', '2024-07-14', (SELECT id FROM institution WHERE name = '대구소프트웨어마이스터고'), NOW()),
  -- 추가 교육 과정들...
  ('자연어처리 AI 개발과정', 'AI', 7, '2024-05-01', '2024-11-30', (SELECT id FROM institution WHERE name = '포항공과대학교'), NOW());
```

## 기술 스택

- **Spring Boot**: 3.5.3
- **Spring Cloud**: 2025.0.0
- **Java**: 17
- **Spring Cloud Netflix Eureka**: 서비스 디스커버리
- **Spring Cloud Gateway**: API 게이트웨이
- **Spring Data JPA**: 데이터 접근
- **MySQL**: 데이터베이스
- **Gradle**: 빌드 도구

## 사전 요구사항

- Java 17 이상
- MySQL 8.0 이상
- IDE (IntelliJ IDEA, Eclipse, VS Code 등)

## 실행 방법

### 1. 데이터베이스 설정

1. MySQL 서버를 실행합니다.
2. 위의 [데이터베이스 설정](#데이터베이스-설정) 섹션의 SQL 명령어를 실행하여 데이터베이스와 사용자를 생성합니다.

### 2. 서비스 실행

#### run.sh 실행으로 한번에 실행가능합니다.

#### Eureka Server 실행 (필수)
```bash
cd eureka-server
./gradlew bootRun
```
- 접속: http://localhost:8761

#### API Gateway 실행
```bash
cd ../gateway-service
./gradlew bootRun
```

#### 각 마이크로서비스 실행
각 서비스 디렉토리에서 다음 명령어로 실행합니다:
```bash
./gradlew bootRun
```

## API 게이트웨이 라우팅

API Gateway는 다음 경로로 요청을 각 서비스로 라우팅합니다:

- `/api/account/**` → Account Service (8081)
- `/api/board/**` → Board Service (8082)
- `/api/info/**` → Information Service (8084)
- `/api/qna/**` → QnA Service (8085)
- `/api/gathering/**` → Gathering Service (8086)
- `/api/review/**` → Review Service (8087)

## 개발 가이드

### 새로운 서비스 추가하기

1. **새 모듈 생성**
   - `settings.gradle`에 새 모듈 추가
   - `build.gradle` 파일 생성

2. **의존성 추가** (`build.gradle`):
   ```gradle
   dependencies {
       implementation 'org.springframework.boot:spring-boot-starter-web'
       implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
       implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
       runtimeOnly 'com.mysql:mysql-connector-j'
   }
   ```

3. **application.yml 설정**
   ```yaml
   server:
     port: 808X  # 사용할 포트 번호
   
   spring:
     application:
       name: your-service-name
     datasource:
       url: jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
       username: msa
       password: 123
   
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/
   ```

4. **API Gateway에 라우팅 추가** (`gateway-service`의 `application.yml`):
   ```yaml
   spring:
     cloud:
       gateway:
         routes:
           - id: your-service
             uri: lb://YOUR-SERVICE-NAME
             predicates:
               - Path=/api/your-path/**
   ```



