# Git Flow

## 들어가며

Git push 충돌을 막고 다양한 개발코드를 시도해보기 위해 브랜치를 써보기로 한다

다양한 개발자와 협업시, 규칙을 정해 Git을 사용하는 Git 브랜치 전략이 필요하다



### Git Flow

Git-Flow는 브랜치를 크게 4가지로 나누어 개발하는 전략

`master`와 `develop`이라는 항상 존재하는 메인 브랜치와 `featuer-*`, `hotfix-*`, `release-*`라는 필요에 따라 생성하는 브랜치가 있다 (`improvement-*`,`bugfix-` 등 프로젝트에 따라 모델 추가)

merge된 feature, release, hotfix 브랜치는 삭제하도록 한다



### 메인 브랜치(Main branch)

- `master`: 배포가능한 상태만을 관리하는 브랜치

- `develop`: 다음에 배포할 것을 개발하는 브랜치 (통합 브랜치의 역할)



### 보조 브랜치(feature branch 또는 topic branch)

- 기능을 개발하는 브랜치로 develop에는 기존에 잘 작동하는 개발코드가 담겨있다면, 보조 브랜치는 새로 변경될 개발코드를 분리하고 각각 보존하는 역할 (완성되면 merge, 결과가 나쁘면 버림)
- 기능을 다 완성할 때까지 유지하고 origin에는 push하지 않음 (`feature/#이슈번호`와 같은 형태)



### 릴리즈 브랜치(release branch)

- 배포를 위한 최종적인 버그 수정 등의 개발을 수행하는 브랜치
- 배포 가능 상태가 되면 master 브랜치로 병합, 출시된 master 브랜치에 버전 태그(ex, v1.0, v0.2) 



### 핫픽스 브랜치(hotfix branch)

- 배포한 버전에서 긴급하게 수정할 필요가 있을 때 master에서 분리하는 브랜치

- 변경사항은 develop 에도 merge하여 문제가 되는 부분 처리해주어야 함



### 브랜치 만들기

- CLI로 브랜치를 만든다면

  - git branch 브랜치이름(해당 브랜치가 존재하지 않으면 브랜치를 새로 만듦)
    - ex) git branch feature/#1
  - git checkout -b 브랜치이름(해당 브랜치가 존재하지 않으면 새로 만들어 그 브랜치로 이동)
    - ex) git checkout -b feature/#1

  - git checkout 브랜치이름(존재하는 브랜치가 있다면 그 브랜치로 이동)
    - ex) git checkout feature/#1

- feature브랜치를 생성시 develop 브랜치에서부터 생성하고 브랜치 생성 전 develop 브랜치 pull 

  - ex) feature/login (기능적기)

- feature 브랜치는 작은 기능 단위로 쪼개서 최대 10개 미만의 커밋으로 구성
- 병합된 feature 브랜치는 로컬과 원격에서 삭제

### Pull Request 보내기

Pull Request를 보내는 이유: Merge하기 전에 코드 리뷰를 하기 위해서

```
git add .
git commit -m "커밋 컨벤션 메시지"
git push origin 브랜치이름
```



```
//git flow

skeleton 생성 - initial

develop branch 생성
develop branch에서 각자 feacher branch를 가져감
merge request, 코드리뷰 - 통과하면 develop branch에 병합

release branch 생성 후 테스트
(오류 발생 시 bugfix branch 생성 후 수정)
bugfix branch에서 수정된 내용은 develop과 release에 적용해야 함

1번사람
git branch develop : develop branch 생성 
git switch develop : develop branch로 이동
작업
git commit -am "C1" : 첫번째 기능 커밋
작업
git commit -am "C2" : 2번째 기능 커밋
작업
git commit -am "C3" : 3번째 기능 커밋

C1작업시점으로 돌아가려고 한다면 : git checkout 264f(앞4글자)
작업1 : C1에서 작업내용이 분화된다.
git commit -am "C1-B1"
작업2 
git commit -am "C1-B2"
git merge develop

git add .
git commit -m"merge branch 'develop' into bugfix/1"

git switch develop
git merge --no-ff bugfix/1

git switch -c feat/2
```



## 마치며

Git flow를 자유자재로 쓸 수 있어야 다양한 시행착오를 겪더라도 쉽게 돌아올 수 있을거라 생각한다. 



#### 참고

https://overcome-the-limits.tistory.com/협업-협업을-위한-Git-명령어-가이드?category=911197

https://overcome-the-limits.tistory.com/협업-협업을-위한-Git-Flow-설정하기