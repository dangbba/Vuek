<template>
  <div class="container">
    <h1 class="fw-bold my-5">Review Board</h1>
    <hr />

    <!-- <p class="mt-3">Current Page: {{ currentPage }}</p> -->
    <b-table
      id="my-table"
      striped
      hover
      :fields="fields"
      :items="items"
      :per-page="perPage"
      :current-page="currentPage"
      small
      @row-clicked="rowClick"
    ></b-table>

    <router-link to="/board/create">
      <b-button variant="primary float-end">글쓰기</b-button>
    </router-link>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      size="sm"
      align="center"
      class="mt-4"
    >
    </b-pagination>
  </div>
</template>

<script>
import http from "@/config/http-common.js";

export default {
  name: "Board",
  data() {
    return {
      perPage: 5,
      currentPage: 1,
      fields: [
        { id: "글번호" },
        { user_user_id: "작성자" },
        { review_name: "제목" },
        { created_at: "작성일" },
      ],
      items: [],
    };
  },
  methods: {
    rowClick(item) {
      this.$router.push({
        path: `/board/detail/${item.id}`,
      });
    },
  },
  created() {
    http({
      method: "get",
      url: `/review/index`,
    })
      .then((response) => {
        // this.items = response.data;
        // console.log(response.data)
        //정렬
        let contentItems = response.data.sort((a, b) => {
          return b.id - a.id;
        }); // 내림차순
        this.items = contentItems;
      })
      .catch((err) => {
        console.dir(err);
        console.log(err);
      });
  },
  computed: {
    rows() {
      return this.items.length;
    },
  },
};
</script>
