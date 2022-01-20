22.01.20

## CSS,Sass,SCSS

## 들어가며

layout을 수정 중에 원하는대로 잘 되지 않았고, style에 SCSS를 쓰는 경우가 많길래 적용시켜보려고 찾아보았다. 



### CSS (Cascading Style Sheets)

문서의 스타일을 구현하는 스타일 시트 언어

W3C에서 1996년 도입한 것으로, 이전에는 HTML언어 하나로 문서와 디자인을 모두 구현

수정 및 유지보수를 해야하는 경우 스타일, 문서 중 하나를 수정 하더라도 모두 수정해야하는 단점이 있었고, 문서(content)와 표현(presentation)을 분리하여 수정 관리할 수 있도록 하기 위해 CSS가 나옴



### Sass (Syntactically Awesome Stylesheets)

문법적으로 어썸한 스타일 시트, CSS의 단점을 보정하기 위한 CSS의 확장으로 나온 스크립트 언어

프로젝트가 점점 커지고, 복잡해져가면서 스타일시트도 커지고 관리할게 많아 복잡해져 유지보수도 어려움

단순해서 배우기 쉽지만 단순하기만 해서 가지는 단점들을 보완하기 위해 variable, nesting, mixin, inheritance 등의 개념이 추가

Sass는 Preprocessing 과정을 통해 css로 해석 및 컴파일됨

'전처리기(Pre-Processor)'와 그 '구문(Syntax)' 두가지를 모두 지칭

Sass는 CSS의 { } 블록이 아닌 들여쓰기 감지를 핵심특성으로 갖는 구문을 가리킴

 

### SCSS (Sassy CSS)

Sass와 거의 유사하고 문법만 살짝 다름

Sass 3 버전에서 좀 더 CSS에 호환될 수 있도록 도입됨

 SCSS를 쓰면 CSS를 쓰던 방식과 유사하게 Sass의 기능을 사용

Sass구문은 들여쓰기 문법을 사용하지만,  SCSS구문은 대괄호와 세미콜론을 사용

SCSS라는 CSS친화적인 구문을 제공함으로써 Sass와 CSS 사이의 차이를 좁히는 방향으로 변화

그 이후 Sass(전처리기)는 들여쓰기 구문인 Sass와 SCSS의 구문 모두 사용 가능하고, 동등한 기능을 가짐



### 기타

Sass외에 다른 전처리기들도 있고, 문법이나 작동 방식이 조금 다른 차이 (후처리기의 개념도 있음)

- [LESS](http://lesscss.org/)
- [Stylus](http://learnboost.github.io/stylus/)
- [cssnext](https://cssnext.github.io/)
- [PostCSS](https://github.com/postcss/postcss)



### CSS, Sass, SCSS 구문 비교

**CSS**

```
/**CSS Syntax**/

nav ul {
  margin: 0;
  padding: 0;
  list-style: none;
}
nav li {
  display: inline-block;
}
nav a {
  display: block;
  padding: 6px 12px;
  text-decoration: none;
}
```

**Sass**

```
/**Sass Syntax**/

nav
  ul
    margin: 0
    padding: 0
    list-style: none

  li
    display: inline-block

  a
    display: block
    padding: 6px 12px
    text-decoration: none
```

**SCSS**

```
/**SCSS Syntax**/

nav {
  ul {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  li { display: inline-block; }

  a {
    display: block;
    padding: 6px 12px;
    text-decoration: none;
  }
}
```

 

### **SCSS 사용법**

SCSS, Sass를 사용하기 위해서는 별도의 loader 필요

CSS의 단점을 보완하기 위한 확장된 기능들의 간단한 예시/가이드를 아래 공홈링크에서 참고 가능



### 오류

vue에서 scss와 같은 css형식을 사용하기 위해 sass-loader를 설치한 뒤에 실행하면 나는 오류 두가지 발생

1

```
Syntax Error: TypeError: this.getOptions is not a function
```

vue 2.6 버전대에서 sass-loader 11버전을 사용할 경우에 호환되지 않아 발생

기존 sass-loader를 삭제하고 10버전을 설치해 해결

```
$npm uninstall sass-loader
$npm install --save sass-loader@10
```



2

```
Syntax Error: Error: PostCSS received undefined instead of CSS string
```

sass 관련 환경을 구성하기 위해 node-sass 모듈을 하나 더 설치해 해결

```
$npm install --save node-sass
```



## 마치며

설치하기까지 은근히 에러가 많았고, 위치는 프론트가 아니라 상위폴더에 해야한다. 컨퍼런스화면은 아직 비디오쪽까지는 넣지 않았고, 회의이력화면은 제대로 되지 않은거 같아 아예 다시 해야할 것 같다.



#### 참고

https://sass-lang.com/guide

https://sass-guidelin.es/ko/

https://yunzema.tistory.com/269

https://nykim.work/97

https://heropy.blog/2018/01/31/sass/