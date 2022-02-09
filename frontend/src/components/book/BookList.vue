<template>
  <div>
    <div>
      <h2 class="my-4">베스트 셀러</h2>
      
      <b-row class="justify-content-center">
        <b-col 
        col
        cols="12"
        sm="6"
        md="4"
        lg="3"
        v-for="(item, index) in bestSellerItems"
        :key="index"
        >
          <b-card
          :title="item.title"
          :img-src="imgPath(item.itemId, item.isbn)"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 15rem;"
          class="mb-3"
          >
            <b-card-text>
              <p>저자 : {{ item.author }}</p>
              <p>줄거리 : {{ truncDiscription(item.description) }}</p>
            </b-card-text>

            <b-button :href="item.link" variant="secondary">상세페이지로</b-button>
          </b-card>
        </b-col>

      </b-row>


    </div>
    <hr />
    <div>
      <h2 class="my-4">신간 도서</h2>
      <b-row class="justify-content-center">
        <b-col 
        col
        cols="12"
        sm="6"
        md="4"
        lg="3"
        v-for="(item, index) in newSpecialItems"
        :key="index"
        >
          <b-card
          :title="item.title"
          :img-src="imgPath(item.itemId, item.isbn)"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 15rem;"
          >
            <b-card-text>
              <p>저자 : {{ item.author }}</p>
              <p>줄거리 : {{ truncDiscription(item.description) }}</p>
            </b-card-text>

            <b-button :href="item.link" variant="secondary">상세페이지로</b-button>
          </b-card>
        </b-col>

      </b-row>

    </div>
  </div>
  
</template>

<script>
import http from "@/config/http-common.js";

export default {
  name:"bookList",
  data() {
    return {
      bestSellerItems: [],
      newSpecialItems: [],
    };
  },

  created() {
    this.getBestSeller(),
    this.getNewSpecial()
  },
  methods:{
    getBestSeller() {
      http({
        method: "get",
        url: `/search/bestseller`,
      })
      .then((response) => {
        console.log(response.data.item)
        this.bestSellerItems = response.data.item.slice(0, 8) // 10개중 8개까지 보여줌(화면상)
        // this.bestSellerItems.replace('sum', '500')
      })
      .catch((err) => {
        console.log(err)
      })
    },
    getNewSpecial() {
      http({
        method: "get",
        url: `/search/newspecial`,
      })
      .then((response) => {
        console.log(response.data.item)
        this.newSpecialItems = response.data.item.slice(0, 8) // 10개중 8개까지 보여줌(화면상)
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
      if (dis.length <= 100) {
        return dis
      } else {
        return `${dis.substr(0, 100)}... (중략)`
      }
      
    },
  },
};

</script>

<style scoped>

</style>