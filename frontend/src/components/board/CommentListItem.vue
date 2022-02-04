<template>
  <div>
    <div class="comment-list-item">
      <span>{{ name }}: </span>

      <p class="d-inline me-3">
        {{ commentObj.context }} | {{ commentObj.created_at }}
      </p>

      <b-button variant="primary" size="sm" class="me-2">수정</b-button>
      <b-button variant="danger" size="sm">삭제</b-button>
      <hr />
    </div>
  </div>
</template>

<script>
import data from "@/data";

export default {
  name: "CommentListItem",
  props: {
    commentObj: Object,
  },
  components: {},
  data() {
    return {
      name: data.User.filter(
        (userItem) => userItem.user_id === this.commentObj.user_id
      )[0].name,
    };
  },
  methods: {},
};
</script>

<style scoped></style>
