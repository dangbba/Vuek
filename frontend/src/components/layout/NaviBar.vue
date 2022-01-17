<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="primary">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/logo.png"
            class="d-inline-block align-middle"
            width="80px"
            alt="Kitten"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-button v-b-toggle.sidebar-variant>메뉴</b-button>
        <b-sidebar
          id="sidebar-variant"
          title="메뉴"
          bg-variant="dark"
          text-variant="light"
          shadow
        >
          <div class="px-3 py-2">
            <div class="icon-bar">
              <router-link to="/" id="tooltip-1"
                ><b-iconstack font-scale="1" animation="spin">
                  <b-icon
                    stacked
                    icon="camera"
                    variant="info"
                    scale="0.75"
                    shift-v="-0.25"
                  ></b-icon>
                  <b-icon stacked icon="slash-circle" variant="danger"></b-icon>
                </b-iconstack>
              </router-link>
              <b-tooltip target="tooltip-1" triggers="hover">
                화상회의
              </b-tooltip>
              <router-link to="/about" id="tooltip-2"
                ><b-icon-bookmark-dash></b-icon-bookmark-dash>
              </router-link>
              <b-tooltip target="tooltip-2" triggers="hover">
                지난 화상회의 이력
              </b-tooltip>
              <router-link to="/profile" id="tooltip-3"
                ><b-icon-emoji-laughing></b-icon-emoji-laughing
              ></router-link>
              <b-tooltip target="tooltip-3" triggers="hover">
                프로필
              </b-tooltip>
              <router-link to="/board" id="tooltip-4"
                ><b-icon-clipboard></b-icon-clipboard
              ></router-link>
              <b-tooltip target="tooltip-4" triggers="hover">
                게시판
              </b-tooltip>
              <router-link to="/book" id="tooltip-5"
                ><b-icon-book></b-icon-book
              ></router-link>
              <b-tooltip target="tooltip-5" triggers="hover">
                베스트셀러/신간도서
              </b-tooltip>
            </div>
          </div>
        </b-sidebar>

        <b-navbar-nav class="ml-auto" v-if="isNaverLogin">
          <b-nav-item class="align-self-center"> 네이버로그인 성공</b-nav-item>
          <b-nav-item
            class="link align-self-center"
            @click.prevent="onClickNaverLogout"
            >로그아웃</b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="but" v-else>
          <div>
            <b-button v-b-modal.modal-prevent-closing>회원가입</b-button>
            <b-modal
              id="modal-prevent-closing"
              ref="modal"
              title="회원가입"
              hide-footer
            >
              <form ref="form">
                <b-form-group
                  class="signupmodal"
                  label="이름"
                  label-for="name-input"
                >
                  <b-form-input
                    id="name-input"
                    v-model="signupCredential.userName"
                  ></b-form-input>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  class="signupmodal"
                  label="아이디"
                  label-for="Id-input"
                >
                  <b-form-input
                    id="name-input"
                    v-model="signupCredential.userId"
                  ></b-form-input>
                  <b-button
                    class="mt-3"
                    @click="isIdExist(signupCredential.userId)"
                  >
                    ID 중복확인
                  </b-button>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  class="signupmodal"
                  label="비밀번호"
                  label-for="password-input"
                >
                  <b-form-input
                    type="password"
                    id="password-input"
                    v-model="signupCredential.password"
                  ></b-form-input>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  class="signupmodal"
                  label="비밀번호 확인"
                  label-for="passwordConfirmation-input"
                >
                  <b-form-input
                    type="password"
                    id="passwordConfirmation-input"
                    v-model="signupCredential.passwordConfirmation"
                  ></b-form-input>
                </b-form-group>
              </form>
              <hr />
              <b-button @click="signupIsValid(signupCredential)">
                가입하기
              </b-button>
            </b-modal>
            <b-button variant="success"
              ><router-link :to="{ name: 'Naver' }"
                >네이버</router-link
              ></b-button
            >
            <b-button v-b-modal.modal2-prevent-closing>로그인</b-button>
            <b-modal
              id="modal2-prevent-closing"
              ref="modal"
              title="로그인"
              hide-footer
            >
              <form ref="form">
                <b-form-group
                  class="loginmodal"
                  label="아이디"
                  label-for="id-logininput"
                >
                  <b-form-input
                    id="id-input"
                    v-model="credentials.userId"
                  ></b-form-input>
                </b-form-group>
              </form>
              <form ref="form">
                <b-form-group
                  class="loginmodal"
                  label="비밀번호"
                  label-for="password-input"
                >
                  <b-form-input
                    type="password"
                    id="password-input"
                    v-model="credentials.password"
                  ></b-form-input>
                </b-form-group>
              </form>
              <div class="find_password">
                <a href="/forget_password"
                  >아이디 또는 비밀번호를 잊으셨나요?</a
                >
              </div>
              <hr />
              <b-button @click="loginIsValid(credentials)"> 로그인 </b-button>
            </b-modal>
          </div>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations } from "vuex";

import Swal from "sweetalert2";
import jwt_decode from "jwt-decode";

const userStore = "userStore";

export default {
  name: "Navibar",
  data() {
    return {
      signupCredential: {
        userName: "",
        userId: "",
        password: "",
        passwordConfirmation: "",
      },
      credentials: {
        userId: "",
        password: "",
      },
    };
  },
  mounted() {
    console.log(sessionStorage.getItem("access token"));
    console.log(jwt_decode());
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(userStore, ["isNaverLogin"]),
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    signupIsValid: function (cred) {
      console.log(cred);
      if (
        cred.username === "" ||
        cred.password === "" ||
        cred.passwordConfirmation === "" ||
        cred.userid
      ) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "이름, 아이디, 비밀번호, 비밀번호 확인란은 필수 입력사항입니다.",
        });
      } else if (cred.password !== cred.passwordConfirmation) {
        Swal.fire({
          icon: "error",
          title: "PasswordNotMatch",
          text: "비밀번호가 일치하지 않습니다",
        });
      } else if (cred.userName.length > 30) {
        Swal.fire({
          icon: "error",
          title: "UsernameError",
          text: "이름은 30자를 넘을 수 없습니다.",
        });
      } else if (cred.userId.length > 16) {
        Swal.fire({
          icon: "error",
          title: "IdError",
          text: "아이디는 16자를 넘을 수 없습니다 ",
        });
      } else if (
        16 < cred.password.length ||
        9 > cred.password.length ||
        16 < cred.passwordConfirmation.length ||
        9 > cred.passwordConfirmation.length
      ) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 길이 오류",
          text: "비밀번호(확인)는 9자 이상 16자 이하여야 합니다.",
        });
      } else if (
        cred.password.search(/[0-9]/g) < 0 ||
        cred.password.search(/[a-z]/gi) < 0 ||
        cred.password.search(/[`~!@#$%^&*/?;:]/gi) < 0
      ) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 유형 오류",
          text: "비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다.",
        });
      } else {
        console.log(cred);
      }
    },
    isIdExist: function (id) {
      console.log(id);
    },

    loginIsValid: function (cred) {
      console.log(cred);
      if (cred.userId === "" || cred.password === "") {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "아이디, 비밀번호는 필수 입력사항입니다.",
        });
      } else if (cred.userId.length > 16) {
        Swal.fire({
          icon: "error",
          title: "IdError",
          text: "아이디는 16자를 넘을 수 없습니다.",
        });
      } else if (16 < cred.password.length || 9 > cred.password.length) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 길이 오류",
          text: "비밀번호는 9자 이상 16자 이하여야 합니다.",
        });
      } else if (
        cred.password.search(/[0-9]/g) < 0 ||
        cred.password.search(/[a-z]/gi) < 0 ||
        cred.password.search(/[`~!@#$%^&*/?;:]/gi) < 0
      ) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 유형 오류",
          text: "비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다.",
        });
      } else {
        console.log(cred);
      }
    },
    onClickNaverLogout() {
      sessionStorage.removeItem("access token");
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style>
.but {
  color: white;
  margin: 10px;
}
.icon-bar {
  height: 100%;
  width: 90px;
  background-color: #555;
  position: sticky; /* Fixed Sidebar (stay in place on scroll) */
  /*z-index: 1;*/ /* Stay on top */
  bottom: 0; /* Stay at the top */
  left: 0;
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 0;
}

.icon-bar a {
  display: block;
  text-align: center;
  padding: 16px;
  transition: all 0.3s ease;
  color: white;
  font-size: 36px;
}

.icon-bar a:hover {
  background-color: #858585;
}

.active {
  background-color: #5eaf13;
}

.loginmodal {
  margin: 2rem;
  font-size: 1.8rem;
}

.signupmodal {
  margin: 2rem;
  font-size: 1.8rem;
}
</style>
