<template>
  <div>
  <p class="fs-3 fw-bold mt-3">
    <i class="far fa-bookmark" style="color: #0dcaf0;"></i>
      나의 책갈피
  </p> <!-- 폰트어썸 아이콘 -->
    <b-button size="sm" v-b-modal.modal-prevent-closing variant="warning" @click="getUserBook(userInfo.userId)">확인하기</b-button>
    <b-modal scrollable id="modal-prevent-closing" ref="modal" title="나의 책갈피">
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name:"Bookmark",
  data() {
    return {
    };
  },

  methods:{
    ...mapActions("bookStore", ['getUserBook']),
  },
  computed:{
    ...mapState("userStore", ["userInfo"]),
    ...mapState("bookStore", ["userBookmark"]),
  }
};

</script>

<style scoped>

</style>