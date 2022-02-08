<template>
  <div>
    <b-button variant="dark" @click="deleteConference()">회의 삭제</b-button> <!-- 주최자인 경우에만 삭제할 수 있도록 표시 수정할 것 -->
  </div>
</template>

<script>
import { mapActions } from "vuex";
// const userStore = "userStore";
const conferenceStore = "conferenceStore";

export default {
  name: "ConferenceDetailDelete",
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
    ...mapActions(conferenceStore, ["conferenceDelete"]),
    deleteConference() {
      if(confirm("회의를 완전히 종료(삭제)하시겠습니까?")){
        // console.log(this.conferenceId)
        this.conferenceDelete(this.conferenceId)
      } else {
        alert("취소되었습니다.")
      }
    }
  },
};
</script>

<style>
</style>
