<template>
  <b-navbar-nav>
      <b-nav-form>
        <b-form-input
          type="search"
          class="mr-sm-2 text-white bg-dark d-inline"
          v-model="text"
          placeholder="검색어를 입력하여 도서 검색"
          @keydown.enter.prevent="bookSearch"
          trim 
          style="width:300px;"
        >
        </b-form-input>
        <b-button variant="outline-light" class="my-2 my-sm-0 ms-2 me-3" @click="bookSearch">검색</b-button>
      </b-nav-form>
  </b-navbar-nav>

</template>

<script>
import { mapActions } from "vuex";
import Swal from "sweetalert2";


const bookStore = "bookStore";

export default {
  name: 'BookSearch',
  methods: {
    ...mapActions(bookStore, ['searchBookItems']),
    bookSearch() {
      // console.log(this.text)
      const value = this.text
      if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length )){ 
        var swalAlert = Swal.fire({
          icon: "error",
          text: "검색어를 입력해주세요.",
        })
        return swalAlert
      } else { 
        this.text = ''
        return this.searchBookItems(value)
      }
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