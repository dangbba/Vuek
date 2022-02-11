<template>
  <div class="container">
    
    <vue-profile 
    :nickname="`${userInfo.userName}`"
    :bodyPhrase="`관심 장르: ${userInfo.genre} & 목표: ${userInfo.goal}`"
    :socialLinks="myLinks"
    :profileImg="Img"
    coverColor="#5498e9"
    /> 
    <!-- 회원정보 수정 -->
    <b-button v-b-modal.modal2-prevent-closing class="me-3" @click="getUserData()">회원정보 수정</b-button>
      <b-modal
        id="modal2-prevent-closing"
        ref="modal"
        title="회원정보 수정"
        hide-footer
        v-if="modalCheck"
      > 
        <form ref="form">
          <b-form-group
            type="file"
            label="프로필 이미지 등록"
            label-for="profileImg"
          >
            <b-form-file
              id="profileImg"
              accept=".png, .jpg, .jpeg, .gif"
              v-model="user.profileImage"
              plain
            ></b-form-file>
            <!-- <div class="mt-3">Selected file: {{ user.profileImage ? user.profileImage : '' }}</div> -->
          </b-form-group>
        </form>
        <form ref="form">
          <b-form-group
            label="변경할 username"
            label-for="username"
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
            ></b-form-input>
          </b-form-group>
        </form>
        <form ref="form">
          <b-form-group
            label="변경할 email"
            label-for="user_email"
          >
            <b-form-input
              type="email"
              id="user_email"
              v-model="user.email"
              placeholder="변경할 e-mail을 입력해주세요(-----@-----.--- 형식)"
            ></b-form-input>
          </b-form-group>
        </form>
        <form ref="form">
          <b-form-group
            label="변경할 관심 장르"
            label-for="user_genre"
          >
            <b-form-input
              type="text"
              id="user_genre"
              v-model="user.genre"
              placeholder="변경할 관심 장르를 입력해주세요"
            ></b-form-input>
          </b-form-group>
        </form>
        <form ref="form">
          <b-form-group
            label="변경할 목표 확인"
            label-for="user_goal"
          >
            <b-form-input
              type="text"
              id="user_goal"
              v-model="user.goal"
              placeholder="변경할 목표를 입력해주세요"
              @keyup.enter="userInfoUpdate()"
            ></b-form-input>
          </b-form-group>
        </form>
        <form>
          <b-form-group
            label="변경할 SNS 링크"
            label-for="user_socialLink"
          >
            <b-form-input
              type="url"
              id="user_socialLink"
              v-model="user.socialLink"
              placeholder="변경할 SNS링크를 입력해주세요(https://----.--- 형식)"
              @keyup.enter="userInfoUpdate()"
            ></b-form-input>
          </b-form-group>
        </form>


        <hr />
        <b-button @click="userInfoUpdate()"> 수정 </b-button>
      </b-modal>
    <b-button @click="deleteUser">회원탈퇴</b-button>


    <!-- 독서 마라톤 관련 -->
    <b-row class="justify-content-center mt-4" v-if="marathon.max">  <!-- max value 있을 때(서버에 독서마라톤 정보 있을 때)만 렌더링 -->
      <p class="fs-5 fw-bold">
        <i class="fas fa-running text-primary"></i>
          독서마라톤 
        <i class="fas fa-running text-primary"></i>
      </p> <!-- 폰트어썸 아이콘 -->
      <b-progress
        :max="marathon.max"
        show-value
        class="mb-3 col-4 px-0"
        animated
        height="1.5rem"
      >
        <b-progress-bar :value="marathon.value" :variant=color()>
          <span><strong>{{ marathon.value }} / {{ marathon.max }} km</strong></span>
        </b-progress-bar>
      </b-progress>
      
      <p v-if="marathon.value !== 0 && marathon.value===marathon.max">
        마라톤을 100% 달성했습니다! 거리를 갱신해주세요.
        <b-button v-b-modal.modal-sm size="sm" variant="warning" class="ms-2" @click="marathon.modalCheck=true">갱신하기!</b-button>
        <b-modal v-if="marathon.modalCheck" hide-footer hide-header id="modal-sm" size="sm" title="독서마라톤 거리 갱신">
          <b-input-group prepend="km" class="mt-3">
          <b-form-input :value="marathon.max" v-model="changeValue" @keyup.enter="updateGoal();"></b-form-input>
          <b-input-group-append>
            <b-button variant="outline-warning" @click="updateGoal();">갱신!</b-button>
          </b-input-group-append>
          </b-input-group>
        </b-modal>
      </p>
      <p v-else>
        완주까지 {{ marathon.max -  marathon.value }}km!
      </p>
      <p v-if="marathon.value===0">
        화상회의에 참여하여 달린 거리를 up!
      </p>

    </b-row>
    <div class="space"></div>

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
      myLinks: {         
        email: "",
        link: "", 
      },
      Img: require('@/assets/profile/profile_default_img.jpg'),
      marathon: {
        id: 0,
        value: 0, 
        max: 0,
        modalCheck: false
      },
      user: {
        profileImage: "",
        user_name: "",
        user_pw: "",
        user_pw_check: "",
        email: "",
        genre: "",
        goal: "",
        socialLink: "",
      },
      modalCheck: false,
      changeValue: 0,
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
        url: `/users/${this.userInfo.userId}`,
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
      this.user.user_name = this.userInfo.userName
      this.user.email = this.userInfo.email
      this.user.genre = this.userInfo.genre
      this.user.goal = this.userInfo.goal
      this.user.socialLink = this.userInfo.socialLink
      this.user.profileImage = this.userInfo.profileImage
      
    },
    checkInfo() { // 입력값 유효성 체크
      console.log(this.user.profileImage)
      if(this.user.user_pw != this.user.user_pw_check) {
        alert('비밀번호를 동일하게 입력해주세요')
        return false
      // } else if(this.user.profileImage === null) {
      //   alert('프로필 이미지를 등록해주세요')
      //   return false
      } else if(this.user.user_pw === '' && this.user.user_pw_check == '') {
        alert('변경할 password를 입력해주세요')
        return false
      } else if(this.user.user_name === '') { // 변경할 유저네임 미입력시
        alert('변경할 username을 입력해주세요')
        return false
      } else if(this.user.user_name === this.userInfo.userName ) {
        if (confirm('username을 그대로 유지하시겠습니까?')) {
          return true
        } else {
          return false
        }
      } else {
        return true
      }

    },
    userInfoUpdate() { // 회원정보 수정 // 2.11 저녁 기준 BE 수정 X
      if(this.checkInfo()) {
        http({
          // headers: { 'Content-Type': 'multipart/form-data' }, // 확인 필요
          method: 'put',
          url: `/users/${this.userInfo.userId}`,
          data: {
            'password': this.user.user_pw,
            'userId': this.userInfo.userId,
            'userName': this.user.user_name,
            'email': this.user.email,
            'genre': this.user.genre,
            'goal': this.user.goal,
            'socialLink': this.user.socialLink,
            'profileImage': this.user.profileImage // 확인 필요
            }
          })
          .then((response) => {
            console.log(response)
            alert('회원정보 수정이 완료되었습니다')

            // 갱신된 정보 페이지에 바로 렌더링이 안되는 문제 수정 (기존 userStore 참조)
            let token = sessionStorage.getItem("access-token");
            this.getUserInfo(token)
            this.myLinks.email = this.userInfo.email, // 링크 갱신
            this.myLinks.link = this.userInfo.socialLink // 링크 갱신
           
            // 수정 완료 후 폼 비우고 모달창 꺼지게
            this.user.user_pw = ""
            this.user.user_pw_check = ""
            this.modalCheck = false
            
          })
          .catch((err) => {
            console.dir(err)
          })
      }
    },
    //독서마라톤 조회 기능
    getMarthonInfo() {
      http({
        method: "get",
        url: `/marathon/marathon-info/${this.userInfo.userId}`,
      })
        .then((response) => {
          console.log(response.data)
          this.marathon.value = response.data.nowPages
          this.marathon.max = response.data.goalPages
          this.marathon.id = response.data.id
          this.changeValue = response.data.nowPages
          
        })
        .catch((error) => {
          console.log(error.response)
          alert('독서 마라톤 정보 조회에 실패했습니다. 관리자에게 문의하세요')
        });
    },
    // 독서마라톤 목표(goalPages)수정 기능 --> 백엔드에 확인 필요
    updateGoal(){
       http({
        method: "put", // ...? // 다 입력해야한다고 함(마라톤 아이디 / 기존 max / 기존 value / 기존 userid / 갱신할 max)                      //왜 여기는 p가 소문자???
        url: `/marathon/updateGoalpages?goalPages=${this.marathon.max}&id=${this.marathon.id}&nowPages=${this.marathon.value}&userId=${this.userInfo.userId}&goalpages=${this.changeValue}`,
        //    /marathon/updateGoalpages?goalPages=100                 &id=1                  &nowPages=1                     &userId=test                   &goalpages=2
      })
        .then((response) => {
          console.log(response)
          alert('갱신 성공!')
          this.marathon.modalCheck = false
          this.getMarthonInfo()
        })
        .catch((error) => {
          console.log(error)
          this.marathon.modalCheck = false
          alert('갱신에 실패했습니다. 관리자에게 문의하세요')
        });
    },
    color() { // 퍼센트에 따른 색상 변경 가능(보류)
      if (this.marathon.value >= this.marathon.max) {
        return "danger"
      } else {
        return "primary"
      }
    }
  },
  created() {
    // 링크 관련
    this.myLinks.email = this.userInfo.email,
    this.myLinks.link = this.userInfo.socialLink
    // 독서마라톤 관련 정보 가져오기
    this.getMarthonInfo()
    
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
};
</script>

<style>
.space {
  height:350px;
}
</style>