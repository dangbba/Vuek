<template>
  <div class="container">
    <b-form inline class="searchBox mt-3 row offset-7 col-5" autocomplete="off" onsubmit="return false"> 
      <b-form-input
        class="form-control d-inline"
        v-model.trim="text"
        placeholder="검색어를 입력하세요"
        @keyup.enter="conferenceSearch"
      >
      </b-form-input>
      <b-button class="offset-10 col-2 mt-1" @click="conferenceSearch">검색</b-button>
    </b-form>
  </div>

</template>

<script>
import { mapActions } from "vuex";

const conferenceStore = "conferenceStore";

export default {
  name: 'ConferenceSearch',
  methods: {
    ...mapActions(conferenceStore, ['searchByTitle']),
    conferenceSearch() {
      console.log(this.text)
      const value = this.text
      if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){ 
        return alert('검색어를 입력해주세요!')
        }else{ 
          return this.searchByTitle(value)
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