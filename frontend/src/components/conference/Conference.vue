<template>
  <div class="container">
    <h2 class="fw-bold my-5">독서모임 목록</h2>
    <!-- <h2 class="fw-bold my-5">Conference List</h2> -->

    <conference-create></conference-create>
    <conference-search></conference-search>

    <b-row class="mt-4">
      <b-col md="4">
       <conference-sort></conference-sort>
      </b-col>
      <b-col md="5">
        <conference-filter></conference-filter>
      </b-col>
      <b-col offset-md="1" class="ms-5 mt-3">
        <div> <!-- 종료된 회의 안보기 체크박스 --> <!-- 하나의 체크박스는 단일 boolean 값을 가진다 --> 
          <input class="form-check-input mx-2 my-0" type="checkbox" id="flexCheckDefault" v-model="checked">
          <label class="form-check-label" for="flexCheckDefault">
            종료된 독서모임 보지 않기
          </label>
        </div>
      </b-col>

    </b-row>

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
      checked: true
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
