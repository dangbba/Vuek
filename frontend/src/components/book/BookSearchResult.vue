<template>
  <div class="container">
    <div v-if="bookItems.length===0" class="text-center mt-5">
      <h4>검색결과가 없습니다.</h4>
    </div>
    <div v-else>
      <h2 class="my-5">검색결과</h2>
      <b-row class="justify-content-center">
        <b-col 
        col
        cols="12"
        sm="6"
        md="3"
        lg="3"
        v-for="(item, index) in bookItems"
        :key="index"
        class="mb-5"
        >
          <b-card
          bg-variant="dark"
          tag="article"
          no-body 
          class="overflow-hidden card-image"
          >
          <div class="container">
            <div class="bookmark-button" title="책갈피 추가" @click="bookmark(item.isbn)">
              <!-- <i class="far fa-bookmark"></i> --> <!-- 클래스 바인딩해서 아이콘 색깔 채워보려 했으나 요소가 여러개라X -->
              <!-- <i :index="index" :class="[{ 'far' : bookmarkSign, 'fas': !bookmarkSign }, 'fa-bookmark']"></i> -->
              <b-button variant="primary" size="lg"><i :index="index" class="fas fa-bookmark"></i></b-button>
            </div>
            <b-card-img :src="imgPath(item.image)" @error="replaceByDefault"></b-card-img>
          </div>
            <b-card-text class="px-3 pt-3 mb-0">
              <p> {{ transStr(item.author) }}</p>
              <hr>
              <p class="card-font">{{ transStr(item.description) }}</p>
            </b-card-text>
            <b-row class="bg-dark mt-0 pt-0">
              <b-col>
                <b-button :href="item.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세페이지로</b-button>
              </b-col>
              <b-col class="ps-0">
                <book-conference-create-by-search :item="item"></book-conference-create-by-search>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>
    </div>

  </div>

</template>

<script>
import BookConferenceCreateBySearch from './BookConferenceCreateBySearch.vue';
import { mapState, mapActions } from "vuex";
import Swal from "sweetalert2";

export default {
  name: "BookSearchResult",
  components: {
    BookConferenceCreateBySearch,  
  },
  data() {
    return {
      // bookmarkSign: true
    };
  },
  created() {

  },
  computed: {
    ...mapState("bookStore", ['bookItems']),
    ...mapState("userStore", ['userInfo'])
  },
  methods:{
    ...mapActions("bookStore", ['createUserBook']),
    imgPath(url) {
      let transedUrl = url.replace('=m1&', '=m200&') 
      return transedUrl
    },
    truncDiscription(dis) {
      if (dis.length <= 200) {
        return dis
      } else {
        return `${dis.substr(0, 200)}... (중략)`
      }
    },
    transStr(str) {
      let transedStr = str.replaceAll('<b>', '')
      transedStr = transedStr.replaceAll('</b>', '')
      return transedStr
    },
    replaceByDefault(e) {
      e.target.src = require('@/assets/thumbnail/thumbnail_default_img.jpg')
    },
    bookmark(isbn) {
      if (this.userInfo) {
        const obj = {
          isbn: isbn.split(" ")[1],
          userId: this.userInfo.userId
        }
        this.createUserBook(obj)
        this.bookmarkSign = !this.bookmarkSign
      } else {
          Swal.fire({
            icon: "warning",
            text:  "책갈피를 하시려면 로그인해주세요!",
          });
      }
    }
  },
};

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Batang:wght@700&display=swap');

.row {
  padding: 15px;
  background-color: #343a40;
  border-radius: 15px;
}
.card-text {
  height: 100%;
  color: whitesmoke;
}
.card-font {
  font-family: 'Gowun Batang', serif;
  font-size: 20px;
}

.bookmark-button {
  font-size: 50px;
  cursor: pointer;
  border-radius: 10rem;
  position: absolute;
  z-index: 2;
  /* color: #ffd700; */
  /* background-color: transparent; */
  /* border: transparent; */
}

.bookmark-button :hover {
  color: #ffd700;
}

.card-image {
  width: 100%;
  height: 100%;
}

</style>