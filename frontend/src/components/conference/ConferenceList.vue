<template>
  <div class="container overflow-auto">
    <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3 g-4" v-if="conferenceItems">
      <conference-list-item
        id="conference"
        v-for="item in conferenceItems"
        :key="item.id"
        :item="item"
        :checked="checked"
      >
      </conference-list-item>
      <!-- <conference-list-item
        id="conference"
        v-for="item in list"
        :key="item.id"
        :item="item"
        :checked="checked"
      >
      </conference-list-item> -->
      <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading>  -->
      <!-- 인피니트 스크롤 적용 실패의 흔적 / BE에서 넘어오는 값이 잘못되었음 
          / 값을 잘라서 리턴해주지 않고 첫번째값부터 계속 다시 리턴함
           질문해서 해결할 시간 있는지 봐서 처리 -->
    </div>
    <div v-else class="text-center">
      <i class="far fa-calendar-times" style="font-size:10rem; margin-top:100px;"></i>
      <h3 class="my-5 text-danger">
        현재 진행중인 독서모임이 없습니다.
      </h3>
    </div>

  </div>
</template>

<script>
import { mapState } from "vuex";
import { mapActions } from "vuex";
import ConferenceListItem from './ConferenceListItem.vue';
// import InfiniteLoading from 'vue-infinite-loading';
// import http from "@/config/http-common.js";

const conferenceStore = "conferenceStore";

export default {
  name: 'Home',
  components: {
    ConferenceListItem,
    // InfiniteLoading
  },
  props: {
    checked: Boolean,
  },
  data() {
    return {
      // list : [],
      // limit : 0,
    }
  },
  // methods: {
  //   infiniteHandler($state) {
  //     http({
  //       method: "get",
  //       url: `/conferences/conferencesLimit?limit=${this.limit}`,
  //       // url: `/conferences/conferencesLimit?limit=1`,
  //     })
  //       .then((response) => {
  //         console.log(response)
  //         setTimeout(() => {
  //           // this.list = this.list.concat(response.data);
  //           // $state.complete();
  //           console.log(response.data.length)
  //           if (response.data.length < this.conferenceItems.length) {
  //             console.log(response.data.length)
  //             console.log(this.conferenceItems.length)
  //             this.list = this.list.concat(response.data);
  //             this.limit += 6;
  //             $state.loaded();
  //           } else {
  //             $state.complete();
  //           }
  //         }, 1000);
  //       })
  //       .catch((error) => {
  //         console.log(error.response)
  //       });
  //   },
  // },
  created: function () {
    this.LoadConferenceItems
  },
  computed: {
    ...mapActions(conferenceStore, ['LoadConferenceItems']),
    ...mapState(conferenceStore, ['conferenceItems']),    
  },
}
</script>