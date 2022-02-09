<template>
  <div class="container">
    <b-form inline class="searchBox mt-3 row col-5" autocomplete="off" onsubmit="return false"> 
      <b-form-input
        class="form-control d-inline"
        v-model.trim="text"
        placeholder="도서데이터 검색"
        @keyup.enter="bookSearch"
      >
      
      </b-form-input>
      <b-button @click="bookSearch">검색</b-button>
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
