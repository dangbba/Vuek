 <template>
  <div class="container">
    <h1 class="fw-bold my-5">지난 회의 이력</h1>
    <b-list-group>

        <b-list-group-item v-for="(history, index) in conferenceHistory" :key="index">
          <p>컨퍼런스 번호: {{ history.conference.id }}</p>
          <p>책 번호: {{ history.conference.bookDetail.id }}</p>
          <p>컨퍼런스 시작시간: {{ history.conference.callStartTime }}</p>
          <p>컨퍼런스 참여시간: {{ history.insertedTime }}</p>
          <p>컨퍼런스 종료시간: {{ history.conference.callEndTime }}</p>
        </b-list-group-item>
        
  
      <!-- <b-modal id="modal-1" title="컨퍼런스 상세 정보">
        <p class="my-4">컨퍼런스 번호: {{ conferenceDetail.id }}</p>
        <p class="my-4">컨퍼런스 제목: {{ conferenceDetail.title }}</p>
        <p class="my-4">컨퍼런스 시작시간: {{ conferenceDetail.callStartTime }}</p>
        <p class="my-4">컨퍼런스 종료시간: {{ conferenceDetail.callEndTime }}</p> 
      </b-modal> -->
    </b-list-group>
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


  },
};
</script>

<style>

</style>
