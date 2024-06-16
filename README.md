# MOIT
![image](https://github.com/moit03/moit-be/assets/157124813/9b4deefe-a3ef-4bff-8906-68a7d9dcbe6b)


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

<img src="https://img.shields.io/badge/java-F7DF1E?style=for-the-badge&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white"/> <img src="https://img.shields.io/badge/Websocket-40AEF0?style=for-the-badge&logo=websocket&logoColor=white"/> <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"/> <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white"/> <img src="https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white"/> <img src="https://img.shields.io/badge/Querydsl-0285C9?style=for-the-badge&logo=querydsl&logoColor=white"/> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon Elasti Cache-C925D1?style=for-the-badge&logo=amazonelasticache&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonAWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"/><img src="https://img.shields.io/badge/apachejmeter-D22128?style=for-the-badge&logo=apachejmeter&logoColor=white"/> 


# 🛠️ 사용 Tool
<img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"/> <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"/> <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>

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


