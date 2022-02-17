<template>
  <div>
  <p class="fs-3 fw-bold mt-3">
    <i class="far fa-bookmark" style="color: #0dcaf0;"></i>
      나의 책갈피
  </p> <!-- 폰트어썸 아이콘 -->
    <b-button size="sm" v-b-modal.modal-prevent-closing variant="warning" class="mb-5" @click="getUserBook(userInfo.userId)">확인하기</b-button>
    <b-modal scrollable size="lg" ok-only ok-title="확인" ok-variant="outline-primary" id="modal-prevent-closing" ref="modal" title="나의 책갈피">
      <!-- {{ userBookmark }} -->
      <div v-if="userBookmark.length === 0">
        <h5 class="text-center">현재 책갈피된 도서가 없습니다. 도서를 검색하여 책갈피를 등록해주세요.</h5>
      </div>
      <div v-else>
        <b-card v-for="bookmark, index in userBookmark" :key="index" :img-src="transUrl(bookmark.titleUrl)" img-width="150px" img-alt="book image" img-left class="mb-3">
          <b-card-text>
            도서명: {{ transStr(bookmark.title) }}
            <hr>
            저자: {{ transStr(bookmark.author) }} / 출판사: {{ transStr(bookmark.publisher) }}
            <div v-if="bookmark.overview">
              <hr>
              줄거리: {{ unescapeHtml(transStr(bookmark.overview)) }}
            </div>
          </b-card-text>
        </b-card>
      </div>
    
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name:"Bookmark",
  data() {
    return {
    };
  },
  methods:{
    ...mapActions("bookStore", ['getUserBook']),
    transUrl(url) {
      let transedUrl = ""
      if(url) {
        transedUrl = url.replace('=m1&', '=m200&') 
      } else {
        transedUrl = require('@/assets/thumbnail/thumbnail_default_img.jpg') 
      }
      return transedUrl
    },
    transStr(str) {
      let transedStr = str.replaceAll('<b>', '')
      transedStr = transedStr.replaceAll('</b>', '')
      return transedStr
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
  },
  computed:{
    ...mapState("userStore", ["userInfo"]),
    ...mapState("bookStore", ["userBookmark"]),
  }
};

</script>

<style scoped>

</style>