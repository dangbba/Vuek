<template>
  <div>
    <b-card>
      <b-input-group :prepend="name" class="mt-3">
        <b-form-textarea
          id="textarea"
          v-model="context"
          :placeholder="'코멘트를 작성해주세요.'"
          rows="3"
          max-rows="6"
        ></b-form-textarea>
        <b-input-group-append>
          <b-button variant="primary" @click="createComment()"
            >작성하기</b-button
          >
        </b-input-group-append>
      </b-input-group>
    </b-card>
  </div>
</template>

<script>
import data from "@/data";

export default {
  name: "CommentCreate",
  props: {
    contentId: Number,
    reloadComment: Function,
    commentId: Number,
  },
  data() {
    return {
      name: "name",
      context: "",
    };
  },
  methods: {
    createComment() {
      const comment_id = data.Comment[data.Comment.length - 1].comment_id + 1;
      data.Comment.push({
        comment_id: comment_id,
        user_id: 1,
        content_id: this.contentId,
        context: this.context,
        created_at: "2019-04-19",
        updated_at: null,
      });
      this.reloadComment();
      this.context = "";
    },
  },
};
</script>

<style scoped>
.comment-create {
  display: flex;
  margin-bottom: 1em;
}
</style>
