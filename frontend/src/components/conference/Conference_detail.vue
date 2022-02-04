<template>
  <div>
    <div class="Conference">회의화면 부분</div>
    <div class="ChatList"></div>
    <div class="ToolBox">툴 박스</div>
    <div class="ChatBox"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "Conference_detail",
  data() {
    return {
      userName: "",
      message: "",
      recvList: [],
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
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
