<template>
  <div>
    <div class="Conference">회의화면 부분</div>
    <div class="ChatList"></div>
    <div class="ToolBox">툴 박스</div>
    <div class="ChatBox"></div>
    <hr />
    <div>
      <!-- 컨퍼런스 정보 관련 정보 표시(임시) -->
      <p>데이터 표시 확인</p>
      <p>
        conference name: {{ conferenceDetail.title }} / conference type:
        {{ categoryName }}
      </p>
    </div>
    <hr />
    <div>
      <!-- 방 종료 / 수정 관련 -->
      <!-- 방 개설자(주최자)만 표시되도록 추후 수정-->
      <!-- <conference-detail-update></conference-detail-update>
      <conference-detail-close></conference-detail-close> -->
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import ConferenceDetailUpdate from './ConferenceDetailUpdate';
// import ConferenceDetailClose from './ConferenceDetailClose';

const userStore = "userStore";
const conferenceStore = "conferenceStore";

export default {
  name: "Conference_detail",
  components: {
    // ConferenceDetailUpdate,
    // ConferenceDetailClose
  },
  data() {
    return {
      userName: "",
      message: "",
      recvList: [],
      // 카테고리 관련
      categoryName: "",
      // 회의 정보 관련
      conferenceId: this.$route.params.contentId,
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // this.connect();
    ///////////////////////////////////////////////

    // 회의실 상세 데이터 가져오기

    this.getConferenceInfo(this.conferenceId);

    // 회의실 카테고리 가져오기
    this.getConferenceCategories();
    // 카테고리 아이디와 일치하는 카테고리명 찾기
    this.matchCategory();
  },
  watch: {
    conferenceDetail() {
      this.matchCategory();
    }, // conferenceDetail 항목이 변하면(수정되면) 카테고리명 다시 찾아서 렌더링하도록...
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapState(conferenceStore, ["conferenceDetail", "conferenceCategory"]),
  },
  methods: {
    ...mapActions(conferenceStore, [
      "getConferenceInfo",
      "getConferenceCategories",
    ]),
    // 카테고리 아이디와 일치하는 카테고리명 찾기
    matchCategory() {
      // this.category = this.conferenceCategory.filter((conferenceCategory) => {
      //     return conferenceCategory.id === this.conferenceDetail.conference_type
      //   })
      for (const cc of this.conferenceCategory)
        if (cc.id === this.conferenceDetail.conference_type) {
          this.categoryName = cc.name;
        }
    },
  },
};
</script>

<style>
.Conference {
  border: solid;
  float: left;
  width: 70%;
  height: 800px;
}
.ChatList {
  border: solid;
  float: right;
  width: 30%;
  height: 800px;
}
.ToolBox {
  border: solid;
  float: left;
  width: 70%;
  height: 200px;
}
.ChatBox {
  border: solid;
  float: right;
  width: 30%;
  height: 200px;
}
</style>
