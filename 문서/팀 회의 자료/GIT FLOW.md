# GIT FLOW





### 0. master branch에서 develop branch를 분화시킨 후 push한다

```bash 
$ git branch develop
$ git switch develop
$ git push origin develop
```



### 1. branch에 있는 내용을 받은 후, 각자 feature branch를 생성하여 작업한다.

```bash
$ git branch
$ git checkout -b feature_branch
feature_branch 내에서 작업
```



### 2. develop branch로 이동 후 작업 내용을 develop branch에 merge한다

```bash
$ git checkout develop
$ git merge feature_branch
```



### 3. master branch를 develop branch에 merge/master한다

```bash
$ git checkout master
$ git merge develop 
$ git push origin master
```



### 4. develop branch를 삭제하고 새로운 develop branch를 만든다

```bash
$ git branch -d develop
$ git push --delete origin develop
```



featurebranch 작업내용
