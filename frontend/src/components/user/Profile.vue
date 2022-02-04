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

        <!-- 회원정보 수정 -->
        <b-button v-b-modal.modal2-prevent-closing @click="getUserData()">회원정보 수정</b-button>
            <b-modal
              id="modal2-prevent-closing"
              ref="modal"
              title="회원정보 수정"
              hide-footer
              v-if="modalCheck"
            >
              <form ref="form">
                <b-form-group
                  label="변경할 username"
                  label-for="user_id"
                >
                  <b-form-input
                    id="username"
                    v-model="user.user_name"
                    placeholder="변경할 username을 입력해주세요"
                  ></b-form-input>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  label="변경할 비밀번호"
                  label-for="user_pw"
                >
                  <b-form-input
                    type="password"
                    id="user_pw"
                    v-model="user.user_pw"
                    placeholder="변경할 password를 입력해주세요"
                  ></b-form-input>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  label="변경할 비밀번호 확인"
                  label-for="user_pw_check"
                >
                  <b-form-input
                    type="password"
                    id="user_pw_check"
                    v-model="user.user_pw_check"
                    placeholder="변경할 password 재입력"
                    @keyup.enter="userInfoUpdate()"
                  ></b-form-input>
                </b-form-group>
              </form>
              <hr />
              <b-button @click="userInfoUpdate()"> 수정 </b-button>
            </b-modal>
  

        <b-button @click="deleteUser">회원탈퇴</b-button>
      </b-card>
    </b-row>
    
    <b-row class="pyo justify-content-center mt-4">
      <b-row><p class="text-center fs-5 fw-bold">📗독서마라톤🏃‍♂️</p></b-row>
      <b-progress
        :value="marathon.value"
        :max="marathon.max"
        show-progress
        class="mb-3 col-4"
      ></b-progress>
    </b-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { mapMutations } from "vuex";
import { mapActions } from "vuex";
import http from "@/config/http-common.js";


const userStore = "userStore";
export default {
  data() {
    return {
      marathon: {
        value: 33.333333,
        max: 50,
      },
      user: {
        user_name: "",
        user_pw: "",
        user_pw_check: ""
      },
      modalCheck: false,
    };
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions(userStore, ["getUserInfo"]),
    // 회원탈퇴 기능
    deleteUser() {
      // 확인창
      if (confirm('탈퇴하시겠습니까?')) {
        http({
        method: 'delete',
        url: `/user/${this.userInfo.user_id}`,
        })
        .then((response) => {
          console.log(response);
          // 로그아웃
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          this.$router.push({
            path: "/",
          })
          alert('탈퇴가 완료되었습니다')
        })
        .catch((err) => {
          console.dir(err);
        })
      } else {
        alert('탈퇴가 취소되었습니다')
      }
    },
    getUserData() { // 기존 정보 가져오기
      this.modalCheck = true
      this.user.user_name = this.userInfo.user_name
    },
    checkInfo() { // 입력값 유효성 체크
      if(this.user.user_pw != this.user.user_pw_check) {
        alert('비밀번호를 동일하게 입력해주세요')
        return false
      } else if(this.user.user_pw == '' || this.user.user_pw_check == '') {
        alert('비밀번호를 입력해주세요')
        return false
      } else if(this.username == '') { // 변경할 유저네임 미입력시
        this.user.user_name = this.userInfo.user_name
        return true
      } else {
        return true
      }
    },
    userInfoUpdate() { // 회원정보 수정
      if(this.checkInfo()) {
        http({
        method: 'put',
        url: `/user/${this.userInfo.user_id}`,
        data: {
          'password': this.user.user_pw,
          'user_id': this.userInfo.user_id,
          'user_name': this.user.user_name
          }
        })
          .then((response) => {
            console.log(response)
            alert('회원정보 수정이 완료되었습니다')

            // 갱신된 정보 페이지에 바로 렌더링이 안되는 문제 수정 (기존 userStore 참조)
            let token = sessionStorage.getItem("access-token");
            this.getUserInfo(token)
           
            // 수정 완료 후 폼 비우고 모달창 꺼지게
            this.user.user_pw = ""
            this.user.user_pw_check = ""
            this.modalCheck = false
          })
          .catch((err) => {
            console.dir(err)
          })
      }
    }

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
