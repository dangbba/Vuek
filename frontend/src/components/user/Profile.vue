<template>
  <div class="container">
    <h1 class="fw-bold mt-5 mb-3 title-deco">{{ userInfo.user_name }}'s 프로필</h1>
    <b-row class="bCon justify-content-center">

      <b-card bg-variant="light" class="col-4">
        <b-avatar
          variant="primary"
          src="https://dcimg4.dcinside.co.kr/viewimage.php?id=26b4ca33ebd339af&no=24b0d769e1d32ca73ceb84fa11d02831abf8dd37dbca42c9c56298cca8560265a7d8652086bfe4b9343f67919400852e8de3921a268bec3508394fa2d71e05d88e6ff22c1eb5a3174ddf19d920114166da3bce0caad0a7b3d2c982290a031781"
          size="10rem"
        ></b-avatar>
        <h5 class="my-3">{{ userInfo.user_name }}</h5>
        <!-- <h5>이름 : {{ userInfo.user_name }}</h5> -->
        <h5>아이디 : {{ userInfo.user_id }}</h5>
        <h5>이번 달 목표 :</h5>
        <h5>이메일 :</h5>
        <h5>관심장르:</h5>
        <b-button class="me-2">수정</b-button>
        <b-button>탈퇴</b-button>
      </b-card>
    </b-row>
    
    <b-row class="pyo justify-content-center mt-4">
      <b-row><p class="text-center fs-5 fw-bold">📗독서마라톤🏃‍♂️</p></b-row>
      <b-progress
        :value="value"
        :max="max"
        show-progress
        class="mb-3 col-4"
      ></b-progress>
    </b-row>
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

/* .bCon {
  width: 600px;
  margin-top: 20px;
} */

.info {
  width: 600px;
  border: solid;
  /* float: left; */
}

.pyo {
  margin-top: 100px;
}

.title-deco {
  background: linear-gradient(to right, #A7A3FF, #FFA7A3, #671cc4, #5673bd); 
  -webkit-background-clip: text; 
  -webkit-text-fill-color: transparent;
}
</style>
