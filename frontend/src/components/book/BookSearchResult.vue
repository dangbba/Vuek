<template>
  <div class="container">
    <div v-if="!bookItems">
      {{ bookItems }}
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
          class="overflow-hidden"
          >
            <b-card-img :src="imgPath(item.image)" @error="replaceByDefault"></b-card-img>
            <b-card-text class="px-3 pt-3 mb-0">
              <p> {{ item.author }}</p>
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
import { mapState } from "vuex";

export default {
  name: "BookSearchResult",
  components: {
    BookConferenceCreateBySearch,  
  },
  data() {
    return {
      
    };
  },
  created() {

  },
  computed: {
    ...mapState("bookStore", ['bookItems'])
  },
  methods:{
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
.card {
  height: 100%;
}
.card-text {
  height: 100%;
  color: whitesmoke;
}
.card-font {
  font-family: 'Gowun Batang', serif;
  font-size: 20px;
}
</style>