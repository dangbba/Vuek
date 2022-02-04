<template>
  <div class="container">
    <h1 class="fw-bold my-5">Book Review 작성(수정)하기</h1>

    <b-form>
      <b-form-group
        label="리뷰 제목"
        label-for="subject"
        description=""
        class="text-start mb-4"
      >
        <b-form-input
          id="subject"
          v-model="subject"
          placeholder="제목을 입력해주세요."
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="리뷰 내용"
        label-for="context"
        description=""
        class="text-start mb-4"
      >
        <b-form-textarea
          id="context"
          v-model="context"
          placeholder="내용을 입력해 주세요"
          rows="6"
          max-rows="10"
          required
        ></b-form-textarea>
      </b-form-group>

      <br />
      <b-button
        @click="updateMode ? updateContent() : uploadContent()"
        variant="primary"
        >저장</b-button
      >&nbsp;
      <b-button @click="cancle">취소</b-button>
    </b-form>
  </div>
</template>

<script>
// import data from '@/data'
import http from "@/config/http-common.js";
import { mapState } from "vuex";

export default {
  name: "BoardCreate",
  data() {
    return {
      subject: "",
      context: "",
      created_at: "",
      updated_at: null,
      updateObject: null,
      updateMode: this.$route.params.contentId > 0 ? true : false,
      review_id: null,
      user_id: "",
    };
  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
  },
  created() {
    if (this.$route.params.contentId > 0) {
      // 수정
      // const contentId = Number(this.$route.params.contentId)
      // 데이터 가져오기
      http({
        method: "get",
        url: `/review/${this.$route.params.contentId}`,
      })
        .then((response) => {
          (this.review_id = response.data.id),
            (this.subject = response.data.review_name),
            (this.context = response.data.review_content),
            (this.user = response.data.user_user_id),
            (this.created_at = response.data.created_at);
          this.updated_at = response.data.updated_at;
          console.log(response);
          console.log("ok");
          console.log(this.updateMode);
        })
        .catch((err) => {
          console.dir(err);
          console.log(err);
        });

      // this.updateObject = data.Content.filter(contentItem => contentItem.content_id === contentId)[0]
      // this.subject = this.updateObject.title;
      // this.context = this.updateObject.context;
    }
  },
  methods: {
    date_time() {
      let today = new Date();

      let year = today.getFullYear(); // 년도
      let month = today.getMonth() + 1; // 월
      let date = today.getDate(); // 날짜
      let hours = today.getHours(); // 시
      let minutes = today.getMinutes(); // 분
      let seconds = today.getSeconds(); // 초

      return (
        year +
        "/" +
        month +
        "/" +
        date +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    uploadContent() {
      // 저장
      http({
        method: "post",
        url: `/review`,
        data: {
          book_detail_id: 1,
          review_content: this.context,
          review_name: this.subject,
          user_user_id: this.userInfo.user_id,
          // user_user_id : 1,
          created_at: this.date_time(),
        },
      })
        .then((response) => {
          console.log(response);
          this.$router.push({
            path: `/board`,
          });
        })
        .catch((error) => {
          console.dir(error);
          console.log(this.userInfo);
          console.log(this.userInfo.user_id);
        });
    },

    updateContent() {
      // 수정
      console.log(this.context);
      console.log(this.subject);
      http({
        method: "put", // 왠지 모르겠는데 204 코드는 돌아오지만 데이터 수정이 안된다.....
        url: `/review/${this.review_id}`,
        data: {
          book_detail_id: this.book_detail_id,
          review_content: this.context,
          review_name: this.subject,
          user_user_id: this.userInfo.user_id,
          created_at: this.created_at,
          updated_at: this.date_time(),
        },
      })
        .then((response) => {
          console.log(response);
          console.log(this.review_id);

          this.$router.push({
            path: `/board/detail/${this.review_id}`,
          });
        })
        .catch((error) => {
          console.dir(error);
          console.log(this.userInfo.user_id);
        });

      // this.updateObject.title = this.subject;
      // this.updateObject.context = this.context;
      // this.$router.push({
      //   path: `/board/detail/${this.updateObject.content_id}`
      // })
    },

    cancle() {
      this.$router.push({
        path: "/board",
      });
    },
  },
};
</script>

<style></style>
