<template>
  <div class="container">
    <div class="header">
      <h1 class="fw-bold my-5">Conference List</h1>
    </div>

    <conference-search></conference-search>
    <conference-create></conference-create>


    <div class="row">
      <conference-sort class="offset-2 col-4"></conference-sort>
      <conference-filter class="col-3"></conference-filter>
      <div class="col-3"> <!-- 종료된 회의 안보기 체크박스 --> <!-- 하나의 체크박스는 단일 boolean 값을 가진다 --> 
        <input class="form-check-input" type="checkbox" id="flexCheckDefault" v-model="checked">
        <label class="form-check-label" for="flexCheckDefault">
          종료된 회의 보지 않기
        </label>
      </div>
    </div>

    <conference-list :checked="checked"></conference-list> <!-- :checked="checked" -->
  </div>
</template>

<script>
import ConferenceList from "./ConferenceList.vue";
import ConferenceFilter from "./ConferenceFilter.vue";
import ConferenceSearch from "./ConferenceSearch.vue";
import ConferenceSort from "./ConferenceSort.vue";
import ConferenceCreate from "./ConferenceCreate.vue";
export default {
  name: "Conference",
  components: {
    ConferenceList,
    ConferenceFilter,
    ConferenceSearch,
    ConferenceSort,
    ConferenceCreate,
  },
  data() {
    return {
      checked: false
    };
  },
  computed: {
  },
  methods: {
  },
};
</script>

<style>
</style>
