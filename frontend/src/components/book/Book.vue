<template>
  <div class="container">
    <!-- <book-search></book-search> -->
    <!-- <hr /> -->
    <book-list v-if="!isSearch"></book-list>
  </div>
</template>

<script>
// import BookSearch from './BookSearch.vue';
import BookList from './BookList.vue';

export default {
  name:"book",
  components: {
    // BookSearch,
    BookList
  },
  data() {
    return {
      isSearch: false
    };
  },

  created() {

  },
  methods:{

  },
};

</script>

<style scoped>

</style>