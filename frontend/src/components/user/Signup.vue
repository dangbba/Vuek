<template>
  <div class="container my-5 px-4 px-lg-5">
    <div class="row gx-4 gx-lg-5 justify-content-center">
      <div class="col-md-10 col-lg-8 col-xl-7">
        <h2>회원가입</h2>
        <div class="my-5">
          <form id="userform" method="post" action="">
            <div class="text-start">
              <label for="id">ID</label>
              <input
                class="form-control"
                id="id"
                type="text"
                v-model="id"
                placeholder="ID를 입력해주세요."
              />
              <div id="idresult" class="mt-1"></div>
            </div>
            <div class="text-start">
              <label for="name">이름</label>
              <input
                class="form-control"
                id="name"
                type="text"
                v-model="name"
                placeholder="이름을 입력해주세요."
              />
            </div>
            <div class="text-start">
              <label for="pass">Password</label>
              <input
                class="form-control"
                id="pass"
                type="password"
                v-model="pass"
                placeholder="비밀번호를 입력해주세요."
              />
            </div>
            <div class="text-start">
              <label for="pass">Password Check</label>
              <input
                class="form-control"
                id="passcheck"
                type="password"
                v-model="passcheck"
                placeholder="비밀번호를 다시 입력해주세요."
              />
            </div>
            <!-- email 주소 -->
            <div class="text-start">
              <p class="mt-3 mb-0">(선택정보)</p>
              <label for="email">E-mail</label>
              <input
                class="form-control"
                id="email"
                type="e-mail"
                v-model="email"
                placeholder="e-mail을 입력해주세요.(example: -----@-----.--- 형식으로 입력)"
              />
            </div>
            <!-- 관심장르 -->
            <div class="text-start">
              <label for="genre">관심 장르</label>
              <input
                class="form-control"
                id="genre"
                type="text"
                v-model="genre"
                placeholder="관심 장르를 입력해주세요."
              />
            </div>
            <!-- 목표 -->
             <div class="text-start">
              <label for="goal">목표</label>
              <input
                class="form-control"
                id="goal"
                type="text"
                v-model="goal"
                placeholder="목표를 입력해주세요."
              />
            </div>           
            <!-- 소셜 링크 -->
             <div class="text-start">
              <label for="socialLink">SNS 링크</label>
              <input
                class="form-control"
                id="socialLink"
                type="url"
                v-model="socialLink"
                placeholder="소셜링크를 입력해주세요.(example: https://----.--- 형식으로 입력)"
              />
            </div>    
            <br />
            <!-- Submit Button-->
            <div class="float-end">
              <b-button variant="primary" @click="checkValue"
                >제출</b-button
              >&nbsp;
              <b-button @click="resetValue"
                >취소</b-button
              >
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/config/http-common.js";
import Swal from "sweetalert2";

export default {
  name: "Signup",
  data() {
    return {
      name: "",
      id: "",
      pass: "",
      passcheck: "",
      email: "",
      genre: null,
      goal: null,
      socialLink: ""
    };
  },

  methods: {
    checkValue: function () {
      if (
        this.name === "" ||
        this.pass === "" ||
        this.passcheck === "" ||
        this.id == ""
      ) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "이름, 아이디, 비밀번호, 비밀번호 확인란은 필수 입력사항입니다.",
        });
      } else if (this.pass !== this.passcheck) {
        Swal.fire({
          icon: "error",
          title: "PasswordNotMatch",
          text: "비밀번호가 일치하지 않습니다",
        });
      } else if (this.name.length > 30) {
        Swal.fire({
          icon: "error",
          title: "UsernameError",
          text: "이름은 30자를 넘을 수 없습니다.",
        });
      } else if (this.id.length > 16) {
        Swal.fire({
          icon: "error",
          title: "IdError",
          text: "아이디는 16자를 넘을 수 없습니다 ",
        });
      } else if (
        16 < this.pass.length ||
        9 > this.pass.length
      ) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 길이 오류",
          text: "비밀번호(확인)는 9자 이상 16자 이하여야 합니다.",
        });
      } else if (
        this.pass.search(/[0-9]/g) < 0 ||
        this.pass.search(/[a-z]/gi) < 0 ||
        this.pass.search(/[`~!@#$%^&*/?;:]/gi) < 0
      ) {
        Swal.fire({
          icon: "error",
          title: "비밀번호 유형 오류",
          text: "비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다.",
        });
      } else {
        Swal.fire({
        icon: "question",
        text: '입력하신 정보로 제출하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네',
        }).then((result) => {
          if (result.isConfirmed) {
            this.registMember();
          } else {
            Swal.fire({
              icon: "warning",
              text: "취소되었습니다.",
            });
          }
        });
      }
    },
    // 일단 회원가입할 때 독서 마라톤을 생성한다
    createMarathon(id) {
      http
        .post(`/marathon/create`, {
          user: {
             userId: id,
          },
          goalPages: 5, // 기본적으로 1으로 생성
          nowPages: 0,
        })
        .then(({ data }) => {
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          console.log(data)
        })
        .catch((err) => {
          console.log(err.response)
          Swal.fire({
            icon: "error",
            text: "독서마라톤 생성에 실패했습니다. 관리자에게 문의하세요.",
          })
        })
    },
    registMember() {
      http
        .post(`/users`, {
          userName: this.name,
          userId: this.id,
          password: this.pass,
          email: this.email,
          genre: this.genre,
          goal: this.goal,
          socialLink: this.socialLink
        })
        .then(({ data }) => {
          let msg = "등록에 문제가 생겼습니다.";
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          if (data === "success") {
            msg = "회원가입을 축하합니다.";
            // 독서마라톤 생성
            this.createMarathon(this.id)
            Swal.fire({
              icon: "success",
              text: msg,
            })
            this.$router.push({ name: "Home" });
          }
        })
        .catch((err) => {
          Swal.fire({
            icon: "error",
            text: err.response.data,
        })
      })
    },
    resetValue() {
      this.$router.push("/");
    },
  },
};
</script>

<style>
</style>
