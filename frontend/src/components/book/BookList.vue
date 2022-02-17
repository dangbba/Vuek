<template>
  <div>
    <div>
      
      <h2 class="fw-bold mt-5 mb-4">베스트 셀러</h2>
      <div class="carousel">
        <carousel v-bind="options" :nav="false">

          <!-- slide(0,4)로 bestSeller 4권묶음 -->
          <b-row class="justify-content-center">
            <b-col 
            col
            cols="12"
            sm="6"
            md="3"
            lg="3"
            v-for="(item, index) in bestSellerItems"
            :key="index"
            >
              <b-card
              bg-variant="dark"
              tag="article"
              no-body 
              class="overflow-hidden card-image"
              >
                <div class="bookmark-button" title="책갈피 추가" @click="bookmark(item.isbn13)">
                  <b-button variant="primary" size="lg"><i :index="index" class="fas fa-bookmark"></i></b-button>
                </div>
                <b-card-img :src="imgPath(item.itemId, item.isbn)" @error="replaceByDefault"></b-card-img>
                <b-card-text class="px-3 pt-3 mb-0">
                  <p> {{ item.author }}</p>
                  <hr>
                  <p class="card-font">{{ truncDiscription(unescapeHtml(item.description)) }}</p>
                </b-card-text>
                <b-row class="bg-dark mt-0 pt-0">
                  <b-col>
                    <b-button :href="item.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세페이지로</b-button>
                  </b-col>
                  <b-col class="ps-0">
                    <book-conference-create :item="item"></book-conference-create>
                  </b-col>
                </b-row>
              </b-card>
            </b-col>
          </b-row>

          <!-- slide(4,8)로 bestSeller2 4권묶음 -->
          <b-row class="justify-content-center">
            <b-col 
            col
            cols="12"
            sm="6"
            md="3"
            lg="3"
            v-for="(item, index) in bestSellerItems2"
            :key="index"
            >
              <b-card
              bg-variant="dark"
              tag="article"
              no-body 
              class="overflow-hidden card-image"
              >
                <div href="#" class="bookmark-button" title="책갈피 추가" @click="bookmark(item.isbn13)">
                  <b-button variant="primary" size="lg"><i :index="index" class="fas fa-bookmark"></i></b-button>
                </div>
                <b-card-img :src="imgPath(item.itemId, item.isbn)" @error="replaceByDefault"></b-card-img>
                <b-card-text class="px-3 pt-3 mb-0">
                  <p>{{ item.author }}</p>
                  <hr>
                  <p class="card-font">{{ truncDiscription(unescapeHtml(item.description)) }}</p>
                </b-card-text>
                <b-row class="bg-dark mt-0 pt-0">
                  <b-col>
                    <b-button :href="item.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세페이지로</b-button>
                  </b-col>
                  <b-col class="ps-0">
                    <book-conference-create :item="item"></book-conference-create>
                  </b-col>
                </b-row>
              </b-card>
            </b-col>
          </b-row>

        </carousel>
      </div>
    </div>
    
    <hr />
    <div>
      <h2 class="mt-5 mb-4">신간 도서</h2>
      <!-- <h2 class="mt-5 mb-4">New books</h2> -->

      <div class="carousel-wrap">
        <carousel v-bind="options" :nav="false">

          <!-- slide(0,4)로 newSpecial 4권묶음 -->
          <b-row class="justify-content-center">
            <b-col 
            col
            cols="12"
            sm="6"
            md="3"
            lg="3"
            v-for="(item, index) in newSpecialItems"
            :key="index"
            >
              <b-card
              bg-variant="dark"
              tag="article"
              no-body 
              class="overflow-hidden card-image"
              >
                <div href="#" class="bookmark-button" title="책갈피 추가" @click="bookmark(item.isbn13)">
                  <b-button variant="primary" size="lg"><i :index="index" class="fas fa-bookmark"></i></b-button>
                </div>
                <b-card-img :src="imgPath(item.itemId, item.isbn)" @error="replaceByDefault"></b-card-img>
                <b-card-text class="px-3 pt-3 mb-0">
                  <p>{{ item.author }}</p>
                  <hr>
                  <p class="card-font">{{ truncDiscription(unescapeHtml(item.description)) }}</p>
                </b-card-text>
                <b-row class="bg-dark mt-0 pt-0">
                  <b-col>
                    <b-button :href="item.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세페이지로</b-button>
                  </b-col>
                  <b-col class="ps-0">
                    <book-conference-create :item="item"></book-conference-create>
                  </b-col>
                </b-row>
              </b-card>
            </b-col>
          </b-row>

          <!-- slide(4,8)로 newSpecial2 4권묶음 -->
          <b-row class="justify-content-center">
            <b-col 
            col
            cols="12"
            sm="6"
            md="3"
            lg="3"
            v-for="(item, index) in newSpecialItems2"
            :key="index"
            >
              <b-card
              bg-variant="dark"
              tag="article"
              no-body 
              class="overflow-hidden card-image"
              >
                <div href="#" class="bookmark-button" title="책갈피 추가" @click="bookmark(item.isbn13)">
                  <b-button variant="primary" size="lg"><i :index="index" class="fas fa-bookmark"></i></b-button>
                </div>
                <b-card-img :src="imgPath(item.itemId, item.isbn)" @error="replaceByDefault"></b-card-img>
                <b-card-text class="px-3 pt-3 mb-0">
                  <p>{{ item.author }}</p>
                  <hr>
                  <p class="card-font">{{ truncDiscription(unescapeHtml(item.description)) }}</p>
                </b-card-text>
                <b-row class="bg-dark mt-0 pt-0">
                  <b-col>
                    <b-button :href="item.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세페이지로</b-button>
                  </b-col>
                  <b-col class="ps-0">
                    <book-conference-create :item="item"></book-conference-create>
                  </b-col>
                </b-row>                
              </b-card>
            </b-col>
          </b-row>

        </carousel>
      </div>

    </div>
  </div>
  
</template>

<script>
import http from "@/config/http-common.js";
import carousel from "vue-owl-carousel2";
import _ from 'lodash'
import BookConferenceCreate from './BookConferenceCreate.vue';
import { mapActions, mapState } from "vuex";
import Swal from "sweetalert2";


export default {
  name:"bookList",
  components:{
    carousel,
    BookConferenceCreate,  
  },
  data() {
    return {
      options:{
        autoplay:true,
        items:1,
        startPosition:1,
        autoplayTimeout:7000,
        autoplaySpeed:4000,
      },
      bestSellerItems: [],
      bestSellerItems2: [],
      newSpecialItems: [],
      newSpecialItems2: [],
    };
  },
  created() {
    this.getBestSeller()
    this.getNewSpecial()
  },
  computed: {
    ...mapState("userStore", ['userInfo'])
  },
  methods:{
    ...mapActions("bookStore", ['createUserBook']),
    getBestSeller() {
      http({
        method: "get",
        url: `/search/bestseller`,
      })
      .then((response) => {
        // console.log(response.data.item)
        let bestItems = _.sampleSize(response.data.item, 8)
        this.bestSellerItems = bestItems.slice(0, 4)
        this.bestSellerItems2 = bestItems.slice(4, 8)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    getNewSpecial() {
      http({
        method: "get",
        url: `/search/newSpecial`,
      })
      .then((response) => {
        let newItems = _.sampleSize(response.data.item, 8)
        this.newSpecialItems = newItems.slice(0, 4)
        this.newSpecialItems2 = newItems.slice(4, 8)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    imgPath(itemId, isbn) {
      // console.log(typeof(itemId))
      // console.log(isbn)
      // console.log(itemId.toString().substr(0,5))
      // console.log(itemId.toString().substr(5,2))
      // console.log(`https://image.aladin.co.kr/product/${itemId.toString().substr(0,5)}/${itemId.toString().substr(5,2)}/cover500/${isbn}_1.jpg`)
      return `https://image.aladin.co.kr/product/${itemId.toString().substr(0,5)}/${Number(itemId.toString().substr(5,2))}/cover500/${isbn}_1.jpg`
    },
    truncDiscription(dis) {
      if (dis.length <= 200) {
        return dis
      } else {
        return `${dis.substr(0, 200)}... (중략)`
      }
    },
    unescapeHtml(str) {
      if (str == null) {
        return "";
      }
      return str
        .replace(/&amp;/g, '&')
        .replace(/&lt;/g, '<')
        .replace(/&gt;/g, '>')
        .replace(/&quot;/g, '"')
        .replace(/&#039;/g, "'")
        .replace(/&#39;/g, "'");
    },
    replaceByDefault(e) {
      e.target.src = require('@/assets/thumbnail/thumbnail_default_img.jpg')
    },
    bookmark(isbn) {
      if (this.userInfo) {
        const obj = {
          isbn: isbn,
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