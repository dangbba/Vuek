<template>
  <div>
    <b-button variant="dark" @click="deleteConference()">독서모임 삭제</b-button>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const conferenceStore = "conferenceStore";

export default {
  name: "ConferenceDetailDelete",
  computed: {
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
