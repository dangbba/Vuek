<template>
  <div>
    <b-form-select v-model="selected_sort_value" :options="sort_value_options" class="ms-3" style="height: 2.5rem"></b-form-select>
    <b-form-select v-model="selected_sort_option" :options="sort_options" class="mx-2" style="height: 2.5rem"></b-form-select>
    <b-button @click="conferenceSort">정렬</b-button>
  </div>

</template>

<script>
import { mapActions } from "vuex";
import Swal from "sweetalert2";

const conferenceStore = "conferenceStore";

export default {
  name: 'ConferenceSort',
  methods: {
    ...mapActions(conferenceStore, ['getConferenceBySort']),
    conferenceSort() {
      if (this.selected_sort_option == null || this.selected_sort_value == null) {
        // alert('옵션을 모두 선택해주세요')
        Swal.fire({
          icon: "error",
          text: "옵션을 모두 선택해주세요.",
        })
      } else {
        this.getConferenceBySort((`${this.selected_sort_option}&sort=${this.selected_sort_value}`))
      }
    }
  },
  data: function () {
    return {
      selected_sort_value: null,
      selected_sort_option: null,
      sort_value_options: [
        { value: null, text: '제목순/생성시간순', disabled: true },
        { value: 'title', text: '제목순' },
        { value: 'call_start_time', text: '생성시간순' },
      ],
      sort_options: [
        { value: null, text: '오름차순/내림차순', disabled: true },
        { value: 'asc', text: '오름차순' },
        { value: 'desc', text: '내림차순' },
      ],
    }
  },

}
</script>

<style>

</style>