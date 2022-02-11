<template>
  <div>
    <b-button @click="closeConference()">회의 종료</b-button> 
  </div>
</template>

<script>
import { mapActions } from "vuex";
const conferenceStore = "conferenceStore";

export default {
  name: "ConferenceDetailClose",
  computed: {
  },
  data() {
    return {
        conferenceId: this.$route.params.contentId,
      }
  },
  methods: {
    ...mapActions(conferenceStore, ["conferenceClose"]),
    closeConference() {
      if(confirm("회의를 중단하시겠습니까?")){
        this.conferenceClose(this.conferenceId)
      } else {
        alert("취소되었습니다.")
      }
    }
  },
};
</script>

<style>
</style>
