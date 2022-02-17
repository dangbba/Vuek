<template>
  <div class="d-inline">
    <b-button class="mx-2" variant="dark" @click="closeConference();">독서모임 종료</b-button> 
  </div>
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
        text: '독서모임을 종료하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네',
      }).then((result) => {
        if (result.isConfirmed) {
          this.conferenceClose(this.conferenceId)
          this.$emit('change') // 상위컴포넌트로 이벤트 발생시켜서 상위컴포넌트의 데이터 조작(isClosed -> true로 만들기)
        } else {
          Swal.fire({
            icon: "warning",
            text: "취소되었습니다.",
          });
        }
      });
    },
  },
};
</script>

<style>
</style>
