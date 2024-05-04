## moit-be

![썸네일3_완 (1)](https://github.com/moit03/moit-be/assets/157124813/63ecc417-5e4c-40fb-a67e-0c4ea8cebd84)

# MOIT 설명

평생 공부하는! 

혼자 공부하다가 지친! 

공부하는 방법 자체가 역량인!

개발자를 위한 스터디, 모각코 등 모임을 게시하고 참여할 수 있는 커뮤니티 moit 입니다.

✨ [MOIT 바로가기](https://moit.me/)

💻 [moit-fe](https://github.com/moit03/moit-fe)

💻 [moit-be](https://github.com/moit03/moit-be)

📃 [MOIT 노션](https://sumptuous-aragon-de9.notion.site/MOIT-b18be1c726dd408bb0c8818756569f2f)

📃 [MOIT 브로셔](https://sumptuous-aragon-de9.notion.site/MOIT-b20d01a1bc67427bbd8a9f6c74cb98d4)


## 👥 팀원 역할

| 역할  | 이름                                 | 분담                                                                                                          |
| ----- | ------------------------------------ | ------------------------------------------------------------------------------------------------------------- |
| BE 👑 | [ 송두용 ](https://github.com/song-dyong)   | CI-CD: GithubActions → DockerHub → DockerCompose, Swagger, GlobalException, SpringSecurity 초기 코드, RestTemplate을 활용한 위도, 경도 정보 저장, 인기 모임, 검색 API, HTTPS 전환: elb 활용, AWS 서버 관리: beanstalk, auto-scaling, 총무, ElastiCache Redis, Prometheus & Grafana 모니터링 툴 적용 |
| BE    | [ 유하정 ](https://github.com/yuha00e) | 모임 수정, 삭제, 회원 참가, 탈퇴, 회원 탈퇴, Spring Scheduler 모임 완료 처리, Prometheus & Grafana 유저 모니터링 툴 적용, AWS 서버 관리: beanstalk, auto-scaling |
| BE    | [ 천옥수 ](https://github.com/OKSUchun) | 과거 채팅 조회, 채팅 생성, Spring Scheduler 모임 완료 처리, 모임 조회, 모임 상세 조회, 참여 중인 모임 조회, DB 변경, 쿼리 성능 테스트, 유저 카카오 회원 탈퇴, AWS 서버 관리: beanstalk, auto-scaling |
| BE    | [ 최산하 ](https://github.com/choisasa) | 소셜 로그인, 리프레시 토큰 적용(레디스), 로그아웃, 마이페이지 통계 조회, 완료된 모임, 개최한 모임, 북마크, 북마크 삭제, 북마크 여부 조회, AWS 서버 관리: beanstalk, auto-scaling |

<br />

# 🏗️ 서비스 아키텍처

![image](https://github.com/moit03/moit-fe/assets/124010808/2c928dab-21a9-4517-ae88-b466d824781c)

# 🛠️ 기술 스택

<img src="https://img.shields.io/badge/java-F7DF1E?style=for-the-badge&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white"/> <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"/> <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white"/> <img src="https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white"/> <img src="https://img.shields.io/badge/Querydsl-0285C9?style=for-the-badge&logo=querydsl&logoColor=white"/> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/> <img src="https://img.shields.io/badge/amazonelasticache-C925D1?style=for-the-badge&logo=amazonelasticache&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonAWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"/><img src="https://img.shields.io/badge/apachejmeter-D22128?style=for-the-badge&logo=apachejmeter&logoColor=white"/> <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"/> <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"/> <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>

# ❗ 기술적 의사결정

| 요구사항 | 고려한 기술 | 기술 선택 이유 |
| --- | --- | --- |
| CI/CD | - GithubActions
- Jenkins
- Docker | ✅ GithubActions
- GithubActions는 잘 만들어진 템플릿을 통해 간단하게 CI/CD 설정이 가능했으며, 무엇보다 Github와 연동되어 접근성이 좋았습니다.

❌ Jenkins
- Jenkins는 참고할 자료가 다양했지만, 알아야 할 설정들이 많았습니다.
- 플러그인 의존성이 높아, 플러그인 업데이트가 있을 경우, 개발자가 업데이트를 추적하여 유지보수해야하는 단점이 존재했습니다.
- AWS Infra구성이 변경될 경우, 이에 따른 플러그인 추가 관리가 필요한 것으로 예상이 됩니다. ————————————————————————————————————————————————————————————-
✅ Docker
- 도커는 컨테이너화된 환경을 제공했기 때문에, 개발 환경과 운영 환경의 일관성을 유지할 수 있습니다.
- 또한, 컨테이너 기반으로 가볍게 배포할 수 있었기에, 해당 기술을 선택했습니다. 
- Docker Hub를 통해 CI/CD 환경을 편리하게 구축할 수 있었습니다. |
|  리프레시 토큰 저장 | Redis | ✅  Redis(In-memory DB)
- RDB와는 다르게 TTL(Time-To-Live) 즉, 데이터의 만료일을 지정할 수 있습니다. 
→ TTL을 토큰의 만료일과 똑같이 맞춰두고 관리하면 토큰 만료시 Redis에서 자동으로 삭제 되기 때문에 데이터 관리가 용이합니다.
- 리프레시 토큰은 액세스 토큰을 재발급 하는 과정에서 자주 호출하기 때문에, RDB에 저장하는 것보다 In-Memory DB에 저장하는 것이 훨씬 속도가 빠르기 때문에 Redis를 사용하는 것이 더 효율적입니다.

——————————————————————————————

❌ PostgreSQL(RDBMS)
- 현재 사용중인 데이터베이스에서는 리프레시 토큰을 만료시키기 위해 주기적으로 만료된 토큰에 대한 삭제 요청을 보내거나 스케쥴러를 사용해야하는데 이 작업이 비효율적입니다. |
| 위치 기반 모임 리스트 구현 | - MySQL
- PostgreSQL | ✅ PostgreSQL
- PostgreSQL 는 PostGIS extension 을 활용하여 다양한 위치 공간 조회가 가능하고, GiST 인덱싱을 사용하여 위치 관련 칼럼을 조회하는데에 용이합니다. 
- PostgreSQL 의 jsonb 형식은 JSON 데이터를 이진 형식으로 저장하고 indexing 이 가능하여 반정규화된 데이터의 읽기 처리 성능을 최적화할 수 있습니다.

❌ MySQL 
- MySQL은 설정과 관리가 상대적으로 간단하고, 초기 설정과 운영이 PostgreSQL보다 간단해 생태계가 넓고, 확장성이 뛰어납니다.
- PostgreSQL 와 달리, 복잡한 위치 타입이나 json 타입의 고급 쿼리 기능을 지원하지 않습니다. |
| 실시간 채팅 | - STOMP
- Long Polling | ✅ STOMP over WebSocket
- Spring 의 내장 spring-websocket 모듈 통해 쉽게 구현 가능합니다.
- 단순히 웹소켓을 사용할 때와는 다르게, STOMP에는  메시지의 형식, 유형, 내용 등을 정해져있어 별도로 개발자가 규격을 정하지 않아도 메시지를 쉽게 전달할 수 있습니다. 

❌ Long Polling
- 클라이언트가 서버에 요청을 보내고 서버가 새로운 데이터가 있을 때까지 응답을 보류하는 방식입니다.
- 모임별 채팅의 양은 많을 것으로 예상되어, 서버 부하와 네트워크 오버헤드가 있을 것으로 예상됩니다. |
| 검색/ 조회 | - QueryDsl
- JPQL | ✅ QueryDsl
- 컴파일 시점에 타입을 체크하여 오타나 잘못된 속성을 사용하는 오류를 사전에 방지하여 안전한 쿼리 작성이 가능했습니다.
- 동적 쿼리를 쉽게 작성하고 변경할 수 있어서 유지보수가 용이하며, 코드의 가독성과 이해도를 높일 수 있었습니다.
- 복잡한 Join문과 Where 조건절을 간편하게 작성할 수 있었습니다.

✅ QueryMethod-
 간단한 쿼리는 QueryMethod를 통해 작성했습니다.

❌ JPQL
- 서비스 특성 상 특정 위치 기반 모임 조회 등 동적 쿼리를 자주 사용하는데에 반해, JPQL 은 문자열로 구성되어, 동적 쿼리를 구성하는 것이 어렵습니다. 
- 가독성이 떨어져 유지 보수가 어렵습니다. |
| 로그인 | OAuth2.0 | ✅ Oauth2(소셜 로그인)
- 사용자가 가장 편안한 방법을 선택할 수 있어 사용자 경험 측면에서 이점이 있습니다.
- OAuth2 공급자(카카오, 네이버 등)가 제공하는 보안 메커니즘을 활용하여 자격 증명을 시스템에 노출하지 않고 사용자를 인증할 수 있습니다.
- OAuth2는 비밀번호가 아닌 토큰을 사용하기 때문에 도용의 위험이 줄어듭니다.

❌ 일반 로그인
- 일반 로그인 시스템에는 일반적으로 사용자 이름/비밀번호 조합이 포함되고, 이는 비밀번호 추측, 피싱 공격, 무차별 공격과 같은 취약성에 취약할 수 있습니다. |
| 유저 모니터링 구축 | - Prometheus & Grafana
- Actuator
- NodeExport | ✅ Prometheus & Grafana
- 채팅 테스트를 진행하던 중 메모리가 상승하는 것을 알 수 있었습니다. 그 결과 서버가 다운되었고, 메모리와 CPU를 모니터링하기 위해 도입했습니다.
- 수집된 메트릭을 이용하여 모니터링을 진행해주는 Prometheus 와 시각화를 위해 Grafana를 사용하였습니다.
✅ Actuator
- 스프링에 메트릭을 수집하기 위해 스프링에서 제공하는 Actuator를 도입했습니다.
✅ NodeExport
- 애플리케이션 레벨이 아닌, 시스템 레벨 모니터링 필요했습니다.
- 인스턴스 서버의 리소스 정보를 확인하기 위해 도입했습니다. (Scale Up) |

# ✨ 기능 소개

### 🌱 회원가입 / 로그인

- 소셜로그인 (kakao, naver)
  ![Untitled](https://github.com/moit03/moit-fe/assets/124010808/6d9046c6-889c-4eb0-a655-c61bcda4ebef)

### 🌱 모임 조회하기 (메인페이지)

- 위치 기반으로 모집 중인 모임을 조회할 수 있습니다.
- 지역, 경력, 기술스택 필터별로 조회가 가능합니다.
- 선택 지역에서 반경 5km 이내 모임을 조회할 수 있습니다.
  ![메인페이지 조회](https://github.com/moit03/moit-fe/assets/124010808/b293c019-49f7-4cd1-9c97-114d99e67787)

![image](https://github.com/moit03/moit-fe/assets/124010808/e54cf6d2-7fd2-4ab3-866d-2b67dd8ca02a)

### 🌱 검색

- 제목, 내용, 위치로 검색 가능합니다.
- 인기 모임 TOP5 조회 가능합니다.
- 최근검색어 확인이 가능합니다. (최대 10개)
  ![검색페이지](https://github.com/moit03/moit-fe/assets/124010808/e35167a1-d60f-4592-b64e-ba8a8d7ccb6f)

### 🌱 상세페이지

- 지도로 모임 장소를 확인할 수 있습니다.
- 원하는 모임에 참여버튼을 통해 참여할 수 있습니다.
- 버튼을 통해 참여 중인지, 마감되었는 지 확인할 수 있습니다.
- 내가 개최한 모임일 때는 수정, 삭제 버튼이 보입니다.
  ![상세페이지](https://github.com/moit03/moit-fe/assets/124010808/dd680470-2481-45c9-a353-4a2efd673343)

### 🌱 모임 CRUD

- 제목, 내용, 날짜, 시간, 장소, 인원, 기술, 경력을 선택할 수 있습니다.
- 빈칸을 다 채우면 생성하기 버튼이 활성화 됩니다.
- 날짜, 시간 제외 수정이 가능합니다.
- 모임 상세페이지에서 ‘**수정**’ 버튼을 눌러 수정할 수 있습니다.
- 수정, 삭제는 본인 게시글만 할 수 있습니다.
  ![모임생성](https://github.com/moit03/moit-fe/assets/124010808/bd9ed309-4ca6-4a09-b179-5706d47162ee)
  ![모임 수정, 삭제](https://github.com/moit03/moit-fe/assets/124010808/61fa0b76-e64d-4734-a0ea-5f8db0355b82)

### 🌱 채팅

- 모임에 참여한 사람들끼리 그룹채팅이 가능합니다.
  ![채팅](https://github.com/moit03/moit-fe/assets/124010808/c8eb09a1-3e91-4130-87d9-a7541b982d66)

### 🌱 북마크

- 리스트, 개인카드, 상세페이지에서 원하는 모임을 북마크할 수 있습니다.
- 북마크 목록은 마이페이지에서 조회 가능합니다.
  ![image](https://github.com/moit03/moit-fe/assets/124010808/ec9f38aa-0c03-4d62-9f04-0d454bf0270b)

### 🌱 마이페이지

- 참여한 모임 / 스터디 시간 / 내가 개최한 모임 개수 확인 가능합니다.
- 참여 중 / 참여 완료 / 내가 개최한 모임 조회 가능합니다.
- 북마크 리스트
- 로그아웃 / 회원탈퇴
  ![마이페이지](https://github.com/moit03/moit-fe/assets/124010808/489aa8ff-4b08-47da-8688-b48d2dd5bb55)


