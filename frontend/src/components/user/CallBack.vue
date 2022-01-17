<template>
  <div>
    <p>Naver Login Access Pages</p>
    <p>로그인 성공</p>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
const userStore = "userStore";

export default {
  mounted() {
    const naver_id_login = new window.naver_id_login(
      "Kv37iCpDIyy8ZdJwDJS2",
      "http://localhost:8080/login/naver"
    );
    sessionStorage.setItem("access token", naver_id_login.getAccessToken());
    this.SET_USER_INFO(naver_id_login.getAccessToken());
  },
  methods: {
    ...mapMutations(userStore, ["SET_USER_INFO"]),
  },
};
</script>

<style></style>
