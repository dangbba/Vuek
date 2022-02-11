<template>
  <div class="container">
    <book-list></book-list>
  </div>
</template>

<script>
import BookList from './BookList.vue';

export default {
  name:"book",
  components: {
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