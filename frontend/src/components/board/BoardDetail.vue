<template>
  <div class="container mt-5 text-start">
    <hr />
    <p>{{ contentId }}번째 리뷰</p>
    <hr class="mb-5" />
    <h4>리뷰 제목: {{ title }}</h4>
    <hr />
    <p class="d-inline me-3">작성자: {{ user }}</p>
    <b-button variant="primary" size="sm" @click="updateData">수정</b-button
    >&nbsp;
    <b-button variant="danger" size="sm" @click="deleteData">삭제</b-button>
    <p class="mt-3">작성일: {{ created }}</p>
    <hr />
    <p class="mb-5">{{ context }}</p>

    <b-card>
      <CommentList :contentId="contentId"></CommentList>
    </b-card>

    <b-button @click="back" class="mt-4 mb-5">목록으로</b-button>
  </div>
</template>

<script>
// import data from "@/data";
import CommentList from "./CommentList";
import http from "@/config/http-common.js";

export default {
  name: "BoardDetail",
  data() {
    const contentId = Number(this.$route.params.contentId);
    // const contentData = data.Content.filter(
    //   contentItem => contentItem.content_id === contentId
    // )[0];
    return {
      bookDetailId: 0,
      contentId: contentId,
      review_id: null,
      title: "",
      context: "",
      user: "",
      created: "",
    };
  },
  methods: {
    deleteData() {
      http({
        method: "delete",
        url: `/review/${this.review_id}`,
      });
      // const content_index = data.Content.findIndex(
      //   contentItem => contentItem.content_id === this.contentId
      // );
      // data.Content.splice(content_index, 1); // 데이터 삭제
      this.$router.push({
        path: "/board/",
      });
    },
    updateData() {
      this.$router.push({
        path: `/board/create/${this.contentId}`,
      });
    },
    back() {
      this.$router.push({
        path: "/board",
      });
    },
  },
  created() {
    http({
      method: "get",
      url: `/review/${this.$route.params.contentId}`,
    })
      .then((response) => {
        (this.bookDetailId = response.data.book_detail_id),
          (this.review_id = response.data.id),
          (this.title = response.data.review_name),
          (this.context = response.data.review_content),
          (this.user = response.data.user_user_id),
          (this.created_at = response.data.created_at);
        console.log(response);
      })
      .catch((err) => {
        console.dir(err);
        console.log(err);
      });
  },

  components: {
    CommentList,
  },
};
</script>
<style></style>
