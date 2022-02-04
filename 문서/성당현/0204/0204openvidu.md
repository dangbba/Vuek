22.02.05

## openvidu

openvidu로 어떻게 진행될 것인지 파악하기 위해 팀전체가 같이 봄

vue: https://docs.openvidu.io/en/stable/tutorials/openvidu-insecure-vue/

java: https://docs.openvidu.io/en/stable/tutorials/openvidu-js-java/



React로 만들어지긴 했지만 책관련 프로젝트라 참고하면 좋을것 같음

https://github.com/CodingOnTeamBook/Rebook_React_Node

기한을 지키기 위해 부가기능을 쳐내는 간소화작업 진행

develop으로 합친 후 다시 branch로 받는 과정에서 충돌, IntelliJ도 갑자기 안되는 등 에러 발생

```
// book 스켈레톤용
<template>
  <div>
    <book-search></book-search>

  <div class="wrap">
    <div class="carousel-wrap">
      <h2>베스트셀러
      <carousel v-bind="options" @initialized="init" @changed="changed">
        <a href="http:\/\/www.aladin.co.kr\/shop\/wproduct.aspx?ItemId=286778297&amp;partner=openAPI&amp;start=api">
        <img :src="`https:\/\/image.aladin.co.kr\/product\/28677\/82\/coversum\/8934952318_1.jpg`">
        </a>
        <a href="http:\/\/www.aladin.co.kr\/shop\/wproduct.aspx?ItemId=286778398&amp;partner=openAPI&amp;start=api">
        <img :src="`https:\/\/image.aladin.co.kr\/product\/28677\/83\/coversum\/8934952326_1.jpg`">
        </a>
        <img :src="`https:\/\/image.aladin.co.kr\/product\/26942\/84\/coversum\/k582730818_1.jpg`">
        <img :src="`https:\/\/image.aladin.co.kr\/product\/28465\/73\/coversum\/k092835920_1.jpg`">
        <img :src="`https:\/\/image.aladin.co.kr\/product\/28730\/75\/coversum\/k712836673_1.jpg`">
        <img :src="`https:\/\/image.aladin.co.kr\/product\/28689\/95\/coversum\/k332836361_1.jpg`">
      </carousel>
      </h2>
    </div>
    <hr />
    <div class="carousel-wrap">
      <h2>새로나온책
      <carousel v-bind="options" @initialized="init" @changed="changed">
        <a href="https://maciejsmolinski.com/books/socratic">
        <img src="https://images-na.ssl-images-amazon.com/images/I/81WmLFAO4yL.jpg">
        </a>
        <img src="https://images-na.ssl-images-amazon.com/images/I/81J4b6i9KyL.jpg">
        <img src="https://images-na.ssl-images-amazon.com/images/I/610AIYRtaDL.jpg">
        <img src="https://images-na.ssl-images-amazon.com/images/I/51iupjtHU%2BL._SX342_QL70_ML2_.jpg">
        <img src="https://images-eu.ssl-images-amazon.com/images/I/51XswOmuLqL.jpg">
        <img src="https://images-na.ssl-images-amazon.com/images/I/51H17R%2BbW8L._SX331_BO1,204,203,200_.jpg">
      </carousel>
      </h2>
    </div>
  </div>
  
  </div>
</template>

<script>
import http from "@/config/http-common.js";
import carousel from 'vue-owl-carousel2';
import BookSearch from './BookSearch.vue';

export default {
  name:"book",
  components: {
    carousel,
    BookSearch,
  },
  data() {
    return {
      plugin: null,
      result: [],
      options: {
        autoplay:false,
        items:5,
        startPosition:0,
        autoplayTimeout:1000,
      },
    };
  },
// data지정까지만 해도 동작함
  methods:{
    handleScroll() {
      window.addEventListener("scroll", ()=>{
        let scrollT=window.scrollY;

        if (scrollT > 0 && this.options.autoplay===false) {
          this.options.autoplay=true;
          setTimeout(()=>{
            this.plugin.refresh();
          }, 300);
        } else if (scrollT===0) {
            this.options.autoplay=false;
            setTimeout(()=>{
              this.plugin.refresh();
            }, 300);
          }
        });
      },
    init() {
      this.plugin=this.$children[0];
    },
    changed(e) {
      this.options.startPosition=e.item.index;
    },
  },
  mounted() {
    this.handleScroll();
  },
  destroyed(){
    window.removeEventListener("scroll",this.handleScroll);
  },
  

  created() {
    http({
      method: "get",
      url: `/search/bestseller`,
    })
    .then((response) => {
      console.log(response.data.item)
    })
    .catch((err) => {
      console.log(err)
    })
  },
};

</script>

<style scoped>
.wrap{
  border: 1px solid#000;
height: 3000px;
}
.carousel-wrap{
  width: 600px;
  margin: 0 auto;
}

</style>
```

```
// bookstore 임시로 짬
import http from "@/config/http-common.js";

const bookStore = {
  namespaced: true,
  state: {
    bookBestseller: [],
    bookNewspecial: [],

  },

  mutation: {
    LOAD_BOOK_BESTSELLER: function (state, results) {
      if (results.length == 0){
        state.bookBestseller = results;
        // console.log(state.bookBestseller)
      } else {
        state.bookBestseller = results.sort((a, b) => {
          return b.id - a.id;
        });
      }
    },
    LOAD_BOOK_NEWSPECIAL: function (state, results) {
      if (results.length == 0){
        state.bookNewspecial = results;
        // console.log(state.bookNewspecial)
      } else {
        state.bookNewspecial = results.sort((a, b) => {
          return b.id - a.id;
        });
      }
    },
  },
  
  actions: {
    LoadBookBestseller: function ({commit}) {
      http({
        method: 'get',
        url: `/search/bestseller`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_BOOK_BESTSELLER', response.data)
        })
    },
    LoadBookNewspecial: function ({commit}) {
      http({
        method: 'get',
        url: `/search/newspecial`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_BOOK_NEWSPECIAL', response.data)
        })
    },
    searchNaver: function ({commit}) {
      http({
        method: 'get',
        url: `/search/naver`,
      })
        .then((response) => {
          console.log(response);
          commit('', response.data);
        })
    },
  },
};

export default bookStore;
```

