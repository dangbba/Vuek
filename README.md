# 모두의 책갈피

<!-- 필수 항목 -->

## 카테고리

| Application | Domain | Language | Framework |
| ---- | ---- | ---- | ---- |
| :white_check_mark: Desktop Web | :black_square_button: AI | :white_check_mark: JavaScript | :white_check_mark: Vue.js |
| :white_check_mark: Mobile Web | :black_square_button: Big Data | :black_square_button: TypeScript | :black_square_button: React |
| :black_square_button: Responsive Web | :black_square_button: Blockchain | :black_square_button: C/C++ | :black_square_button: Angular |
| :black_square_button: Android App | :black_square_button: IoT | :black_square_button: C# | :black_square_button: Node.js |
| :black_square_button: iOS App | :black_square_button: AR/VR/Metaverse | :black_square_button: Python | :black_square_button: Flask/Django |
| :black_square_button: Desktop App | :black_square_button: Game | :white_check_mark: Java | :white_check_mark: Spring/Springboot |
| | | :black_square_button: Kotlin | |



## 프로젝트 소개

* 프로젝트명: 모두의 책갈피

* 서비스 특징

  실시간으로 도서 정보를 검색 or 공유할 수 있는 웹 사이트, 개인의 독서 의욕을 높이기 위한 시스템을 만들기 주요 기능 : 도서정보 API(국립중앙도서관 등의 API)를 활용하여 도서정보를 활용, 화상회의 생성 시  도서 정보 출력 및 썸네일 이미지로 활용하여 특정 도서에 대한 접근성을 확보, 프로필에 각자 읽은 도서목록 업데이트 / 본인의 관심사를 카테고리에 따라서 설정하여 후에 베스트셀러 추천 기능/신간도서 추천 기능에 활용 예정, 독서마라톤 챌린지, 베스트셀러 추천 및 신간도서 추천, 나의 책갈피 

* 주요 기능
  - 회원 관리
  - 화상 미팅룸
  
* 주요 기술
  - WebRTC
  - JWT Authentication
  - REST API
  
* 배포 환경
  - URL: https://i6c205.u.ssafy.io/
  - 테스트 계정: // 로그인이 필요한 경우, 사용 가능한 테스트 계정(ID/PW) 기입



## 팀 소개
* 김성현: 팀장, 백엔드 개발
* 이동현: 프론트엔드 개발
* 박 설: 백엔드 개발
* 성당현: 프론트엔드 개발
* 문찬송: 프론트엔드 개발
* 김성준: 백엔드 개발



## 프로젝트 상세 설명

#### 개발환경

- OS : Windows 10
- Server : AWS EC2
  - Ubuntu 20.04.1
  - Docker 20.10.12
- Backend
  - Java : Java 1.8.0
  - Framework : SpringBoot 2.4.5
  - ORM : JPA(Hibernate)
  - Nginx : 1.18.0 (Ubuntu)
  - IDE : Intellij 2021.1.3 / Visual Studio Code 1.59
  - Dependency tool : gradle-6.9.2
  - Database : MySQL 8.0
- Frontend - HTML5, CSS3, Javascript(Es6) - Vue 3.0.0 - @vue/cli 4.5.0 - Vuex 4.0.0 - Vue-axios 3.2.4



#### 기술 스택 

![기술스택](/문서/팀 회의 자료/기술스택/기술스택.png)



#### 시스템 구성도

![service_architecture2](/문서/팀 회의 자료/시스템구성도/service_architecture2.jpg)



#### ERD

![모두의책갈피ERD](/문서/팀 회의 자료/ERD/모두의책갈피ERD.png)





#### 핵심기능
- 신간도서 및 베스트셀러

![신간도서](/문서/팀 회의 자료/핵심 기능 화면/신간도서.PNG)

![베스트셀러](/문서/팀 회의 자료/핵심 기능 화면/베스트셀러.PNG)

- 프로필, 독서마라톤 및 책갈피

![프로필](/문서/팀 회의 자료/핵심 기능 화면/프로필.PNG)

![책갈피](/문서/팀 회의 자료/핵심 기능 화면/책갈피.PNG)

- 독서모임 리스트

![독서모임리스트](/문서/팀 회의 자료/핵심 기능 화면/독서모임리스트.PNG)

- 화상회의

![화상회의](/문서/팀 회의 자료/핵심 기능 화면/화상회의.PNG)

