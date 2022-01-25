<template>
  <div>
    <b-container class="bCon">
      <b-avatar
        variant="primary"
        src="https://dcimg4.dcinside.co.kr/viewimage.php?id=26b4ca33ebd339af&no=24b0d769e1d32ca73ceb84fa11d02831abf8dd37dbca42c9c56298cca8560265a7d8652086bfe4b9343f67919400852e8de3921a268bec3508394fa2d71e05d88e6ff22c1eb5a3174ddf19d920114166da3bce0caad0a7b3d2c982290a031781"
        size="10rem"
      ></b-avatar>

      <div class="info">
        <h5>이름 : {{ userInfo.user_name }}</h5>
        <h5>아이디 : {{ userInfo.user_id }}</h5>
        <h5>이번 달 목표 :</h5>
        <h5>이메일 :</h5>
        <h5>관심장르:</h5>
        <b-button style="margin: 10px">수정</b-button>
        <b-button>탈퇴</b-button>
      </div>
      <h5>{{ userInfo.user_name }}</h5>
    </b-container>
    <b-container class="pyo">
      독서마라톤
      <b-progress
        :value="value"
        :max="max"
        show-progress
        class="mb-3"
      ></b-progress>
    </b-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

const userStore = "userStore";
export default {
  data() {
    return {
      value: 33.333333,
      max: 50,
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
};
</script>

<style>
.bCon {
  width: 600px;
  margin-top: 20px;
}

.info {
  width: 600px;
  border: solid;
  float: left;
}

.pyo {
  margin-top: 100px;
}
</style>
