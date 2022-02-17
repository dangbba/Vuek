<template>
  <div class="mt-3">
    <b-row>
      <b-col md="4" offset-md="7" class="pe-0">
        <b-form-input
          type="search"
          class="form-control"
          v-model="text"
          placeholder="검색어를 입력하여 독서모임 검색"
          @keyup.enter="conferenceSearch"
          trim 
        >
        </b-form-input>
      </b-col>
      <b-col md="1">
        <b-button @click="conferenceSearch">검색</b-button>
      </b-col>
    </b-row>
  </div>

</template>

<script>
import { mapActions } from "vuex";
import Swal from "sweetalert2";

const conferenceStore = "conferenceStore";

export default {
  name: 'ConferenceSearch',
  methods: {
    ...mapActions(conferenceStore, ['searchByTitle']),
    conferenceSearch() {
      console.log(this.text)
      const value = this.text
      if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length )){ 
        var swalAlert = Swal.fire({
          icon: "error",
          text: "검색어를 입력해주세요.",
        })
        return swalAlert
      } else { 
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