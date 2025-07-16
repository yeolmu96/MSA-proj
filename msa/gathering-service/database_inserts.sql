-- Insert data into Gathering table
INSERT INTO gathering (host_id, team_name, title, description, max_member_count, current_member_count, created_at, updated_at, status) VALUES
(1001, '코딩 마스터즈', '스프링부트 기반 쇼핑몰 프로젝트', '스프링부트와 리액트로 구현하는 온라인 쇼핑몰 프로젝트입니다. 주니어 개발자 환영!', 5, 1, NOW(), NOW(), 'RECRUITING'),
(1002, '클라우드 익스플로러', 'AWS 기반 클라우드 마이크로서비스 구축', '여러 마이크로서비스를 AWS 환경에서 구축하고 배포하는 프로젝트입니다.', 6, 3, NOW() - INTERVAL '2 DAY', NOW(), 'RECRUITING'),
(1003, 'AI 비전', '이미지 인식 AI 앱 개발', '텐서플로우와 파이토치를 활용한 이미지 인식 앱을 개발합니다. 컴퓨터 비전에 관심 있는 분들 환영!', 4, 4, NOW() - INTERVAL '5 DAY', NOW(), 'STARTED'),
(1004, '디자인 크루', '모바일 앱 디자인 프로젝트', '새로운 여행 앱을 위한 UI/UX 디자인 프로젝트입니다.', 3, 3, NOW() - INTERVAL '10 DAY', NOW() - INTERVAL '2 DAY', 'COMPLETED'),
(1005, '풀스택 개발팀', 'SNS 웹 서비스 개발', 'Next.js와 Express를 활용한 소셜 네트워킹 서비스를 개발합니다. 프론트엔드, 백엔드 개발자 모두 환영합니다.', 8, 4, NOW() - INTERVAL '3 DAY', NOW(), 'STARTED'),
(1006, '모바일 개발자 모임', 'iOS/Android 하이브리드 앱 개발', 'React Native를 활용한 하이브리드 앱 개발 프로젝트입니다.', 5, 2, NOW() - INTERVAL '1 DAY', NOW(), 'RECRUITING'),
(1007, '데이터 사이언스팀', '빅데이터 분석 프로젝트', '공공데이터를 활용한 빅데이터 분석 프로젝트를 진행합니다.', 4, 1, NOW(), NOW(), 'RECRUITING'),
(1008, '블록체인 이노베이터', '블록체인 기반 투표 시스템', '이더리움 기반 분산 투표 시스템을 개발합니다. 블록체인에 관심있는 개발자 모집합니다.', 6, 3, NOW() - INTERVAL '7 DAY', NOW(), 'STARTED'),
(1009, 'UX 디자이너 그룹', '핀테크 앱 UX 개선 프로젝트', '기존 핀테크 앱의 UX를 개선하는 프로젝트를 진행합니다.', 4, 4, NOW() - INTERVAL '15 DAY', NOW() - INTERVAL '1 DAY', 'COMPLETED'),
(1010, '게임 개발 크루', '모바일 캐주얼 게임 개발', 'Unity를 활용한 모바일 캐주얼 게임을 개발합니다.', 7, 5, NOW() - INTERVAL '4 DAY', NOW(), 'STARTED');

-- Insert data into GatheringMember table
INSERT INTO gathering_member (account_id, role, gathering_id, is_host) VALUES
-- 코딩 마스터즈 팀 멤버
(1001, 'BACKEND_DEVELOPER', 1, true),

-- 클라우드 익스플로러 팀 멤버
(1002, 'FULLSTACK_DEVELOPER', 2, true),
(1011, 'BACKEND_DEVELOPER', 2, false),
(1012, 'FRONTEND_DEVELOPER', 2, false),

-- AI 비전 팀 멤버
(1003, 'AI_ENGINEER', 3, true),
(1013, 'DATA_ENGINEER', 3, false),
(1014, 'BACKEND_DEVELOPER', 3, false),
(1015, 'FRONTEND_DEVELOPER', 3, false),

-- 디자인 크루 팀 멤버
(1004, 'DESIGNER', 4, true),
(1016, 'DESIGNER', 4, false),
(1017, 'PLANNER', 4, false),

-- 풀스택 개발팀 멤버
(1005, 'FULLSTACK_DEVELOPER', 5, true),
(1018, 'FRONTEND_DEVELOPER', 5, false),
(1019, 'BACKEND_DEVELOPER', 5, false),
(1020, 'DESIGNER', 5, false),

-- 모바일 개발자 모임 멤버
(1006, 'MOBILE_DEVELOPER', 6, true),
(1021, 'MOBILE_DEVELOPER', 6, false),

-- 데이터 사이언스팀 멤버
(1007, 'DATA_ENGINEER', 7, true),

-- 블록체인 이노베이터 멤버
(1008, 'BACKEND_DEVELOPER', 8, true),
(1022, 'FRONTEND_DEVELOPER', 8, false),
(1023, 'FULLSTACK_DEVELOPER', 8, false),

-- UX 디자이너 그룹 멤버
(1009, 'DESIGNER', 9, true),
(1024, 'DESIGNER', 9, false),
(1025, 'PLANNER', 9, false),
(1026, 'FRONTEND_DEVELOPER', 9, false),

-- 게임 개발 크루 멤버
(1010, 'FULLSTACK_DEVELOPER', 10, true),
(1027, 'MOBILE_DEVELOPER', 10, false),
(1028, 'DESIGNER', 10, false),
(1029, 'BACKEND_DEVELOPER', 10, false),
(1030, 'FRONTEND_DEVELOPER', 10, false);

-- Insert data into GatheringTechStack table
INSERT INTO gathering_tech_stack (gathering_id, tech_stack_id) VALUES
-- 코딩 마스터즈 팀의 기술 스택
(1, 4), -- Spring Boot
(1, 9), -- React
(1, 8), -- REST API

-- 클라우드 익스플로러 팀의 기술 스택
(2, 5), -- Node.js
(2, 7), -- Express.js
(2, 9), -- React

-- AI 비전 팀의 기술 스택
(3, 20), -- TensorFlow
(3, 21), -- PyTorch
(3, 19), -- Computer Vision

-- 디자인 크루 팀의 기술 스택
(4, 27), -- UI/UX
(4, 28), -- Adobe Photoshop
(4, 29), -- Adobe Illustrator

-- 풀스택 개발팀의 기술 스택
(5, 9), -- React
(5, 7), -- Express.js
(5, 12), -- JavaScript
(5, 13), -- TypeScript

-- 모바일 개발자 모임의 기술 스택
(6, 9), -- React
(6, 12), -- JavaScript
(6, 13), -- TypeScript

-- 데이터 사이언스팀의 기술 스택
(7, 25), -- 데이터 분석
(7, 16), -- Machine Learning

-- 블록체인 이노베이터의 기술 스택
(8, 12), -- JavaScript
(8, 13), -- TypeScript
(8, 5), -- Node.js

-- UX 디자이너 그룹의 기술 스택
(9, 27), -- UI/UX
(9, 2), -- UX/UI 디자인
(9, 26), -- 그래픽 디자인

-- 게임 개발 크루의 기술 스택
(10, 14), -- HTML
(10, 15), -- CSS
(10, 12), -- JavaScript
(10, 26); -- 그래픽 디자인

-- Insert data into GatheringMemberTechStack table
INSERT INTO gathering_member_tech_stack (gathering_member_id, tech_stack_id) VALUES
-- 다양한 멤버들의 기술 스택
(1, 4), -- 멤버 1001: Spring Boot
(1, 8), -- 멤버 1001: REST API

(2, 5), -- 멤버 1002: Node.js
(2, 7), -- 멤버 1002: Express.js
(2, 9), -- 멤버 1002: React

(3, 5), -- 멤버 1011: Node.js
(4, 9), -- 멤버 1012: React

(5, 20), -- 멤버 1003: TensorFlow
(5, 21), -- 멤버 1003: PyTorch
(5, 19), -- 멤버 1003: Computer Vision

(6, 25), -- 멤버 1013: 데이터 분석
(6, 16), -- 멤버 1013: Machine Learning
(7, 4), -- 멤버 1014: Spring Boot
(8, 9), -- 멤버 1015: React

(9, 27), -- 멤버 1004: UI/UX
(9, 28), -- 멤버 1004: Adobe Photoshop
(9, 29), -- 멤버 1004: Adobe Illustrator
(10, 28), -- 멤버 1016: Adobe Photoshop
(11, 1), -- 멤버 1017: 기획

-- 추가 멤버들의 기술 스택 (나머지 멤버들에 대해서도 적절한 기술 스택 할당)
(12, 9), -- 멤버 1005: React
(12, 5), -- 멤버 1005: Node.js
(12, 7), -- 멤버 1005: Express.js
(12, 12), -- 멤버 1005: JavaScript
(13, 9), -- 멤버 1018: React
(13, 14), -- 멤버 1018: HTML
(13, 15), -- 멤버 1018: CSS
(14, 4), -- 멤버 1019: Spring Boot
(14, 8), -- 멤버 1019: REST API
(15, 27), -- 멤버 1020: UI/UX
(15, 28), -- 멤버 1020: Adobe Photoshop

-- 모바일 개발팀 멤버들의 기술 스택
(16, 9), -- 멤버 1006: React (React Native)
(16, 12), -- 멤버 1006: JavaScript
(16, 13), -- 멤버 1006: TypeScript
(17, 9), -- 멤버 1021: React (React Native)
(17, 12), -- 멤버 1021: JavaScript

-- 데이터 사이언스팀 멤버의 기술 스택
(18, 25), -- 멤버 1007: 데이터 분석
(18, 16), -- 멤버 1007: Machine Learning
(18, 17), -- 멤버 1007: Deep Learning

-- 블록체인 이노베이터 멤버들의 기술 스택
(19, 12), -- 멤버 1008: JavaScript
(19, 5), -- 멤버 1008: Node.js
(20, 9), -- 멤버 1022: React
(20, 14), -- 멤버 1022: HTML
(20, 15), -- 멤버 1022: CSS
(21, 12), -- 멤버 1023: JavaScript
(21, 13), -- 멤버 1023: TypeScript
(21, 9), -- 멤버 1023: React
(21, 5); -- 멤버 1023: Node.js

-- Insert data into GatheringApplication table
INSERT INTO gathering_application (account_id, gathering_id, status, role, message, applied_at) VALUES
(1031, 1, 'PENDING', 'FRONTEND_DEVELOPER', '리액트 개발 경험이 있어 프론트엔드를 맡고 싶습니다.', NOW() - INTERVAL '1 DAY'),
(1032, 1, 'PENDING', 'BACKEND_DEVELOPER', '스프링부트 개발 경험이 있어 백엔드를 도울 수 있습니다.', NOW()),
(1033, 2, 'APPROVED', 'FRONTEND_DEVELOPER', 'AWS와 리액트 경험이 있습니다. 함께하고 싶어요.', NOW() - INTERVAL '2 DAY'),
(1034, 6, 'PENDING', 'MOBILE_DEVELOPER', 'React Native로 앱 개발 경험이 많습니다.', NOW() - INTERVAL '12 HOUR'),
(1035, 7, 'PENDING', 'DATA_ENGINEER', '데이터 분석 및 머신러닝 경험이 있습니다.', NOW() - INTERVAL '6 HOUR'),
(1036, 10, 'REJECTED', 'DESIGNER', 'Unity 게임 UI 디자인 경험이 있습니다.', NOW() - INTERVAL '3 DAY'),
(1037, 8, 'APPROVED', 'FULLSTACK_DEVELOPER', '블록체인과 웹 개발 경험이 있습니다.', NOW() - INTERVAL '5 DAY'),
(1038, 10, 'PENDING', 'BACKEND_DEVELOPER', '게임 서버 개발 경험이 있습니다.', NOW() - INTERVAL '2 DAY');

-- Insert data into GatheringApplicationTechStack table
INSERT INTO gathering_application_tech_stack (gathering_application_id, tech_stack_id) VALUES
(1, 9), -- 지원자 1031: React
(1, 14), -- 지원자 1031: HTML
(1, 15), -- 지원자 1031: CSS
(2, 4), -- 지원자 1032: Spring Boot
(2, 8), -- 지원자 1032: REST API
(3, 9), -- 지원자 1033: React
(3, 14), -- 지원자 1033: HTML
(3, 15), -- 지원자 1033: CSS
(4, 9), -- 지원자 1034: React
(4, 12), -- 지원자 1034: JavaScript
(5, 16), -- 지원자 1035: Machine Learning
(5, 25), -- 지원자 1035: 데이터 분석
(6, 27), -- 지원자 1036: UI/UX
(6, 28), -- 지원자 1036: Adobe Photoshop
(7, 12), -- 지원자 1037: JavaScript
(7, 13), -- 지원자 1037: TypeScript
(7, 5), -- 지원자 1037: Node.js
(8, 5); -- 지원자 1038: Node.js
