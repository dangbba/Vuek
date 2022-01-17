22.01.17

## Git & Jira

## 들어가며

지난주 금요일에 jira, commit, branch, gitlab의 기본이론과 경험에 관한 세션 발표가 있어 참석했는데, 오늘도 git과 jira 관련 세션 발표가 진행된다고 해서 보게 되었고 그것을 정리해보았다.



### Git

### 버전 관리 시스템

파일 변화를 시간에 따라 기록했다가 나중에 특정 시점의 버전을 다시 꺼내올 수 있는 시스템

- 로컬 버전 관리
- 중앙집중식 버전 관리(CVCS)
- 분산 버전 관리 시스템

`참고`https://git-scm.com/book/en/v2

### 용어정리

- Remote: 중앙저장소 / 원격저장소
- Local: 내 컴퓨터에서 작업중인 repository
- Branch: 프로젝트 단위
- Merge: A브랜치와 B브랜치의 내용을 합치는 작업

- git clone: Remote에 있는 repository를 local로 가져옴

- checkout: 작업할 브랜치로 이동하는 과정

  ```
  $git branch <branch>
  $git branch -D <branch> (branch 삭제)
  $git checkout <branch>
  $git checkout -b <branch> (branch 생성 후 checkout하기)
  $git checkout -t <remote/branch> (remote의 branch를 local로 가져오기)
  ```

- add: 모든 작업 내역을 stage에 올려줌(원본과 내 작업내용을 비교할 수 있도록)

  ```
  $git add .
  $git add <file>
  $git commit -m "커밋메시지"
  $git commit -am "커밋메시지" (add와 commit을 동시에)
  $git push <remote> <branch>
  $git pull <remote> <branch>
  ```

### 코딩 컨벤션

commit 자동화 library : git-cz

```
$npm install -g git-cz
```

### 용어 정리

- Pull Request(PR): 내가 작업한 코드를 병합(merge)하기 전에 검토 부탁
  - 코드 리뷰를 하기 위해서
  - Push 권한이 없는 repository에 기여하기 위해(ex. 오픈소스 프로젝트)



### Jira

1. 그루밍

- 스프린트에 들어가기 전, 개발할 기능에 대해서 대략적으로 리뷰를 하는 행위

- 스프린트에 대한 가시성 확보

- 사용자 스토리나 UX 프로토타입 리뷰

2. 사용자 스토리 작성

- 주요 기능을 사용자 관점에서 기술

- 직관적으로 이해가 되어야 하며 테스트가 가능해야 함

- 분류, 스토리, 설명 형태로 기술하는 것이 좋음

3. 스토리 보팅

- 플래닝 포커(PLANNING POKER)

  - 개발팀 전체가 모여 각각의 사용자 스토리에 대해 개발 기간 투표

  - 포인트의 의미는 임의로 정함(ex.  1포인트는 1시간)

  - 전체 팀원이 서로 설득당할 때까지 계속 진행

  - 점수가 가장 높은 사람과 가장 낮은 사람의 의견을 듣고 다시 보팅

4. 개발 일정(일정 지정)

- 산출된포인트로 개발 일정 지정(2~4주)

- 버퍼(회의, 문서, 배포, 오류 수정 등) 배정 필요

- 4주가 넘는 일정은 우선순위에 따라 자름

5. 스프린트 시작

- Jira에 등록

- EPIC: 스토리의 집합
- STORY: 사용자 중심의 기능 목록
- ISSUE: 매니저가 관리하는 이슈
- SUB-TASK: 테스크들의 서브 테스크



## 마치며

브랜치 전략으로 git flow말고도 github flow, gitlab flow 등등이 있는건 처음 알았고, Jira를 관리할 때 역할을 분담해 일주일에 200시간 정도 스토리포인트를 산정하게 되면 그래프가 우하향이 되도록 하는게 좋은데, 미리 대략적으로 짜놓고 수정해가면서 to do를 done으로 해결해가는 팁도 얻었다.

