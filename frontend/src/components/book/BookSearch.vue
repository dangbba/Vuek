<template>
  <div class="container">
    <b-form inline class="searchBox mt-3 row offset-7 col-5" autocomplete="off" onsubmit="return false"> 
        <!-- autocomplet / onsubmit 설정 안하면 엔터치면 페이지 새로고침됨(form 태그 안에 버튼태그가 있어서 엔터를 누르면 button의 submit이 동작해서 그렇다고 함) -->
      <b-form-input
        class="form-control d-inline"
        v-model.trim="text"
        placeholder="책을 검색해보세요"
        @keyup.enter="bookSearch"
      >
      </b-form-input>
      <b-button class="offset-10 col-2 mt-1" @click="bookSearch">검색</b-button>
    </b-form>
  </div>

</template>

<script>
import { mapActions } from "vuex";

const bookStore = "bookStore";

export default {
  name: 'BookSearch',
  methods: {
    ...mapActions(bookStore, ['searchNaver']),
    bookSearch() {
      console.log(this.text)
      const value = this.text
      return this.searchNaver(value)
    } 
  },
  data: function () {
    return {
      text: ''
    }
  },

}
</script>

<style>

</style>
