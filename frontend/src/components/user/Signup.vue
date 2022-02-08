<template>
  <div class="container my-5 px-4 px-lg-5">
    <div class="row gx-4 gx-lg-5 justify-content-center">
      <div class="col-md-10 col-lg-8 col-xl-7">
        <h1>회원가입</h1>
        <div class="my-5">
          <form id="userform" method="post" action="">
            <div class="form-floating">
              <input
                class="form-control"
                id="name"
                type="text"
                v-model="name"
                placeholder="이름을 입력해주세요."
              />
              <label for="name">Name</label>
            </div>
            <div class="form-floating">
              <input
                class="form-control"
                id="id"
                type="text"
                v-model="id"
                placeholder="ID를 입력해주세요."
              />
              <label for="id">ID</label>
              <div id="idresult" class="mt-1"></div>
            </div>
            <div class="form-floating">
              <input
                class="form-control"
                id="pass"
                type="password"
                v-model="pass"
                placeholder="비밀번호를 입력해주세요."
              />
              <label for="pass">Password</label>
            </div>
            <div class="form-floating">
              <input
                class="form-control"
                id="passcheck"
                type="password"
                v-model="passcheck"
                placeholder="비밀번호를 다시 입력해주세요."
              />
              <label for="pass">Password Check</label>
            </div>
            <br />
            <!-- Submit Button-->
            <b-button variant="outline-primary" @click="checkValue"
              >Sign Up</b-button
            >&nbsp;
            <b-button variant="outline-danger" @click="resetValue"
              >Cancel</b-button
            >
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/config/http-common.js";

export default {
  name: "Signup",
  data() {
    return {
      name: "",
      id: "",
      pass: "",
      passcheck: "",
    };
  },

  methods: {
    // 입력값 체크하기 - 체크가 성공하면 registBook 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      !this.name &&
        ((msg = "이름을 확인해주세요."),
        (err = false)) /*, this.$refs.name.focus()*/;
      err && !this.id && ((msg = "ID를 확인해주세요."), (err = false)) /*,
        this.$refs.id.focus()*/;
      err &&
        !this.pass &&
        ((msg = "비밀번호를 확인해주세요."), (err = false)) /*,
        this.$refs.id.focus()*/;
      err &&
        this.pass != this.passcheck &&
        ((msg = "비밀번호를 다시 확인해주세요"), (err = false));

      if (!err) alert(msg);
      else this.registMember();
    },

    registMember() {
      http
        .post(`/users`, {
          userName: this.name,
          userId: this.id,
          password: this.pass,
        })
        .then(({ data }) => {
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          let msg = "등록에 문제가 생겼습니다.";
          if (data === "success") {
            msg = "회원가입을 축하합니다.";
          }
          alert(msg);
          this.$router.push({ name: "Home" });
        });
    },
    resetValue() {
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
