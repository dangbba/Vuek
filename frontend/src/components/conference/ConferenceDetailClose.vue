<template>
  <div>
    <b-button @click="closeConference()">회의 종료</b-button> 
  </div>
</template>

<script>
import { mapActions } from "vuex";
// const userStore = "userStore";
const conferenceStore = "conferenceStore";

export default {
  name: "ConferenceDetailClose",
  computed: {
    // ...mapState(userStore, ["userInfo"]),
    // ...mapState(conferenceStore, ["conferenceDetail"]),
  },
  data() {
    return {
        conferenceId: this.$route.params.contentId,
      }
  },
  methods: {
    ...mapActions(conferenceStore, ["conferenceClose"]),
    closeConference() {
      if(confirm("회의를 종료하시겠습니까?")){
        // console.log(this.conferenceId)
        this.conferenceClose(this.conferenceId)
        this.$router.push({
          path: `/conference`,
        }); // 이부분 약간 문제 있음 / axios 실패했을때도 라우팅됨
        }
      else {
        alert("취소되었습니다.")
      }
    }
  },
};
</script>

<style>
</style>
