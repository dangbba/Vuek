<template>
  <div>
    네이버 로그인 페이지!
    <div id="naver_id_login"></div>
  </div>
</template>

<script>
export default {
  computed: {},
  mounted() {
    const naver_id_login = new window.naver_id_login(
      "Kv37iCpDIyy8ZdJwDJS2",
      "http://localhost:8080/login/naver"
    );
    const state = naver_id_login.getUniqState();
    naver_id_login.setButton("white", 4, 100);
    naver_id_login.setState(state);
    naver_id_login.init_naver_id_login();
  },
};
</script>

<style></style>
