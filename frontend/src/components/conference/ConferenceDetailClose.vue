<template>
  <b-col>
    <b-button @click="closeConference()">회의 종료</b-button> 
  </b-col>
</template>

<script>
import { mapActions } from "vuex";
import Swal from "sweetalert2";

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
      Swal.fire({
        icon: "question",
        text: '회의를 종료하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네',
      }).then((result) => {
        if (result.isConfirmed) {
          this.userInfoUpdate()
          Swal.fire({
            icon: "success",
            text: "회의가 종료되었습니다.",
          });
        } else {
          Swal.fire({
            icon: "warning",
            text: "취소되었습니다.",
          });
        }
      });
    }
  },
};
</script>

<style>
</style>
