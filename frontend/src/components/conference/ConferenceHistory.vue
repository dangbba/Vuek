 <template>
  <div class="container">
    <h2 class="fw-bold my-5">독서모임 참여 이력</h2>
    <!-- <h2 class="fw-bold my-5">Conference History</h2> -->
    <div v-if="conferenceHistory.length===0" class="text-center">
      현재 참여한 독서모임 이력이 없습니다.
    </div>
    <div class="mt-4">
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
          <p>독서모임 번호: {{ history.conference.id }}</p>
          <hr>
          <h4 class="d-inline me-3">주제 도서: {{ transStr(history.conference.bookDetail.title) }}</h4>
          <!-- <b-button :href="history.conference.bookDetail.link" onclick="window.open(this.href, '_blank', 'width=800, height=600');">상세정보</b-button> -->
          <!--DB에 상세 링크 정보가 없어서 X -->
          <hr>
          <h5 class="historyContent">독서모임 제목: {{ history.conference.title }}</h5>
          <p class="historyContent">내용: {{ history.conference.description }}</p>
          <p class="historyContent">카테고리: {{ history.conference.conferenceType.name }}</p>
          <p class="historyContent">시작시간: {{ transTime(history.conference.callStartTime) }}</p>
          <p class="historyContent">입장시간: {{ transTime(history.insertedTime) }}</p>
          <p class="mb-0">종료시간: 
            <span v-if="history.conference.callEndTime">{{ transTime(history.conference.callEndTime) }}</span>
            <span v-else class="text-primary">{{ transTime(history.conference.callEndTime) }}아직 진행중인 독서모임입니다.</span>
          </p>
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
.historyContent {
  margin-bottom: 7px;
}
</style>
