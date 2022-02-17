<template>
  <div>
    <b-form-select v-model="selected" :options="options" class="mb-3 ms-2" style="height: 2.5rem">
      <template #first>
        <b-form-select-option :value="null" disabled>--카테고리를 선택해주세요--</b-form-select-option>
      </template>
    </b-form-select>
    <b-button class="ms-2" @click="getFilter">조회</b-button>

    <!-- <div class="mt-3">Selected: <strong>{{ selected }}</strong></div> -->
  </div>

</template>

<script>
import { mapActions } from "vuex";
import Swal from "sweetalert2";

const conferenceStore = "conferenceStore";

export default {
  name: 'ConferenceFilter',
  methods: {
    ...mapActions(conferenceStore, ['getConferenceByCategory']),
    getFilter() {
      // console.log(this.selected)
      if (this.selected == null) {
        // alert('카테고리를 선택해주세요')
        Swal.fire({
          icon: "error",
          text: "카테고리를 선택해주세요.",
        })
      } else {
        this.getConferenceByCategory(this.selected)
      }
  }
  },
  data: function () {
    return {
      selected: null,
      options: [
        { value: "1", text: "책 소개" },
        { value: "2", text: "토의" },
        { value: "3", text: "세미나" },
        { value: "4", text: "기타" },
      ]
    }
  },

}
</script>

<style>

</style>