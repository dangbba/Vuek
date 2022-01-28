<template>
  <div class="container"> <!-- 명세서에서는 팝업 적용하라고 되어있음-->
    <b-form inline class="searchBox mt-3 row offset-7 col-5" autocomplete="off" onsubmit="return false"> 
            <!-- autocomplet / onsubmit 설정 안하면 엔터치면 페이지 새로고침됨(form 태그 안에 버튼태그가 있어서 엔터를 누르면 button의 submit이 동작해서 그렇다고 함) -->
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