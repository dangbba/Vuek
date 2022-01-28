<template>
  <div class="container">

    <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3 g-4" v-if="conferenceItems">
      <conference-list-item
        v-for="item in conferenceItems"
        :key="item.id"
        :item="item"
      >
      </conference-list-item>
    </div>
    <div v-else>
      <h3 class="text-danger my-3">- 현재 진행중인 회의가 없습니다 -</h3> <!--명세서에서는 아이콘 표시하라함..-->
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { mapActions } from "vuex";
import ConferenceListItem from './ConferenceListItem.vue';

const conferenceStore = "conferenceStore";

export default {
  name: 'Home',
  components: {
    ConferenceListItem
  },
  created: function () {
    this.LoadConferenceItems
  },
  computed: {
    ...mapActions(conferenceStore, ['LoadConferenceItems']),
    ...mapState(conferenceStore, ['conferenceItems'])    
  }
}
</script>
