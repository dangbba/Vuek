<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
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

      <b-collapse id="nav-collapse" is-nav class="text-center">
        <b-navbar-brand v-b-toggle.sidebar-variant>메뉴</b-navbar-brand>
        <b-sidebar
          v-if="userInfo"
          id="sidebar-variant"
          title="메뉴"
          bg-variant="dark"
          text-variant="light"
          shadow
          width="110px"
          no-header
        >
          <div class="px-0 py-2">
            <div class="icon-bar">
              <router-link to="/conference" id="tooltip-1"
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
              <router-link to="/book" id="tooltip-5"
                ><b-icon-book></b-icon-book
              ></router-link>
              <b-tooltip target="tooltip-5" triggers="hover">
                베스트셀러/신간도서
              </b-tooltip>
            </div>
          </div>
        </b-sidebar>
        <b-sidebar
          v-else
          id="sidebar-variant"
          title="메뉴"
          bg-variant="dark"
          text-variant="light"
          shadow
          width="110px"
          no-header
        >
          <div class="px-0 py-3">
            <div class="icon-bar">
              <router-link to="/book" id="tooltip-5"
                ><b-icon-book></b-icon-book
              ></router-link>
              <b-tooltip target="tooltip-5" triggers="hover">
                베스트셀러/신간도서
              </b-tooltip>
            </div>
          </div>
        </b-sidebar>

        <b-navbar-nav v-if="userInfo">
          <b-nav-item>
            <h5 class="Hello my-0">
              <router-link to="/profile" class="text-reset text-decoration-none">
                {{ userInfo.userName }}님 안녕하세요
              </router-link>
            </h5>
          </b-nav-item>
          <b-nav-item href="#" @click.prevent="onClickLogout">로그아웃</b-nav-item>

        </b-navbar-nav>

        <b-navbar-nav v-else>
          <b-nav-item>
            <router-link to="/signup" class="text-reset text-decoration-none">회원가입</router-link>
          </b-nav-item>
          <b-nav-item v-b-modal.modal2-prevent-closing>로그인</b-nav-item>
          <b-modal
            id="modal2-prevent-closing"
            ref="modal"
            title="로그인"
            hide-footer
          >
            <b-alert show variant="danger" v-if="isLoginError">
              아이디 또는 비밀번호를 확인하세요
            </b-alert>
            <form ref="form">
              <b-form-group
                class="loginmodal"
                label="아이디"
                label-for="user_id"
              >
                <b-form-input
                  id="user_id"
                  v-model="user.user_id"
                ></b-form-input>
              </b-form-group>
            </form>
            <form ref="form" autocomplete="off" onsubmit="return false">
              <b-form-group
                class="loginmodal"
                label="비밀번호"
                label-for="user_pw"
              >
                <b-form-input
                  type="password"
                  id="user_pw"
                  v-model="user.user_pw"
                  @keyup.enter="checkValue"
                ></b-form-input>
              </b-form-group>
            </form>
            <hr />
            <b-button class="float-end" variant="outline-primary" @click="checkValue"> 로그인 </b-button>
          </b-modal>
        </b-navbar-nav>

        <b-collapse is-nav>
        </b-collapse>

        <book-search></book-search>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import Swal from "sweetalert2";
import BookSearch from "@/components/book/BookSearch.vue"


import jwt_decode from "jwt-decode";

const userStore = "userStore";

export default {
  name: "Navibar",
  components: {
    BookSearch
  },
  data() {
    return {
      user: {
        user_id: "",
        user_pw: "",
      },
    };
  },
  mounted() {
    console.log(jwt_decode());
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "isLoginError"]),
  },
  destroyed() {
    this.onClickLogout();
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        this.user.user_id = ""
        this.user.user_pw = ""
        await this.getUserInfo(token);
        this.$router.push({ name: "Book" });
      }
    },
    checkValue: function () {
      if (
        this.user.user_pw === "" ||
        this.user.user_id == ""
      ) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "아이디, 비밀번호는 필수 입력사항입니다.",
        });
      } else {
        this.confirm()
      }
    },
    onClickLogout() {
      sessionStorage.removeItem("access-token");
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      Swal.fire({
          icon: "success",
          text: "정상적으로 로그아웃 되었습니다.",
        });
      if (this.$route.path !== "/" || this.$route.path !== "/book") {
        console.log(this.$route.path)
        this.$router.push({ name: "Home" });
      }
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
  width: 110px;
  background-color: "";
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
  padding: 20px;
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

.Hello {
  color: gold;
}




</style>
