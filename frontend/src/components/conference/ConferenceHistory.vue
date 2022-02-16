 <template>
  <div class="container">
    <h2 class="fw-bold my-5">회의 참여 이력</h2>
    <!-- <h2 class="fw-bold my-5">Conference History</h2> -->
    <div v-if="conferenceHistory" class="text-center">
      현재 참여한 회의 이력이 없습니다.
    </div>
    <div v-else class="mt-4">
      <b-card
      v-for="(history, index) in conferenceHistory" 
      :img-src="transUrl(history.conference.bookDetail.titleUrl)"
      img-width="200px"
      img-alt="book-img" 
      img-left 
      class="mx-4 mb-4 shadow" 
      :key="index"
      bg-variant="dark"
      >
        <b-card-text>
          <p>회의 번호: {{ history.conference.id }}</p>
          <hr>
          <h4 class="d-inline me-3">주제 도서: {{ transStr(history.conference.bookDetail.title) }}</h4>
          <!-- <b-button :href="history.conference.bookDetail.link" onclick="window.open(this.href, '_blank', 'width=800, height=600');">상세정보</b-button> -->
          <!--DB에 상세 링크 정보가 없어서 X -->
          <hr>
          <h5 class="mb-3">컨퍼런스 제목: {{ history.conference.title }}</h5>
          <p>컨퍼런스 내용: {{ history.conference.description }}</p>
          <p>컨퍼런스 시작시간: {{ history.conference.callStartTime }}</p>
          <p>컨퍼런스 참여시간: {{ transTime(history.insertedTime) }}</p>
          <p>컨퍼런스 종료시간: {{ transTime(history.conference.callEndTime) }}</p>
        </b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>

import { mapState, mapActions } from "vuex";


export default {
  name: "ConferenceHistory",
  components: {
  },
  data() {
    return {
    };
  },
  created() {
    this.getConferenceHistory(this.userInfo.userId) // 지난 회의 이력 가져오기
  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
    ...mapState("conferenceStore", ["conferenceHistory", "conferenceDetail"]),
  },
  methods: {
    ...mapActions("conferenceStore", ["getConferenceHistory", "getConferenceInfo"]),
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
    transTime(time) {
      if (time) { // null값 방지
        let transedStr = time.split('.')
        return transedStr[0]
      }
    },
  },
};
</script>

<style>

</style>
