<template>
  <div class="container">
    <div class="header">
      <h1 class="fw-bold my-5">Conference List</h1>
    </div>

    <conference-search></conference-search>

    <conference-create></conference-create>
    <div class="row">
      <conference-sort class="offset-5 col-4"></conference-sort>
      <conference-filter class="col-3"></conference-filter>
    </div>

    <conference-list></conference-list>
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
