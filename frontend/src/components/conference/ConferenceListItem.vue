<template>
  <div class="col" v-if="item.isActive"> <!-- 진행중인 회의만 표기됨 -->
    <b-card no-body bg-variant="dark" class="overflow-hidden mx-2 my-2" style="max-width: 540px;">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img v-if="transUrl(item.bookDetail.titleUrl)" :src="transUrl(item.bookDetail.titleUrl)" alt="Image" class="rounded-3 mt-3 ms-3"></b-card-img>
          <b-card-img v-else :src="thumbnail_url" alt="book_image" class="rounded-3 mt-3 ms-3"></b-card-img>
        </b-col>
        <b-col md="6" class="mb-3">
          <b-card-body :title="item.title"> <!--방 제목-->
            <b-card-text>
              <p class="card-text">회의번호: {{ item.id }}</p> <!--회의 번호-->
              <p class="card-text">주제도서: {{ transStr(item.bookDetail.title) }}</p>
              <p class="card-text">카테고리: {{ item.conferenceType.name }}</p> 
              <p class="card-text">시작시간: {{ item.callStartTime }}</p>
              <!-- <p class="card-text">회의종료시간: {{ item.callEndTime }}</p> -->
              <p v-if="item.isActive" class="card-text">진행중 유무: <span class="text-success">진행중</span> </p> 
              <p v-else class="card-text">진행중 유무: <span class="text-danger">종료</span></p> 
              <p class="card-text">방 참여 인원: {{ item.participant.length }}</p> <!--participant는 array이므로 참여인원수를 출력-->
              
            </b-card-text>
          </b-card-body>
          <b-button variant="primary" @click="enterConference" v-if="item.isActive">입장</b-button> 
          <b-button variant="secondary" class="disabled" v-else>입장불가</b-button>
        </b-col>
      </b-row>
    </b-card>
  </div>


</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
const conferenceStore = "conferenceStore";

export default {
  name: 'ConferenceListItem',
  props: {
    item: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapState(conferenceStore, ["conferenceDetail"]),
  },
  methods: {
    ...mapActions("conferenceStore", ["createRoom", "getConferenceCategories"]),
    enterConference() {
      this.$router.push({
        path: `conference/view/${this.item.id}`,
      });
    },
    transStr(str) {
      let transedStr = str.replaceAll('<b>', '')
      transedStr = transedStr.replaceAll('</b>', '')
      return transedStr
    },
    transUrl(url) {
      let transedUrl = url.replace('=m1&', '=m200&') 
      return transedUrl
    }
  },
  data: function () {
    return {
      thumbnail_url: require('@/assets/thumbnail/thumbnail_default_img.png') // 원래는 thumbnail 없을때만....
    }
  }
}
</script>

<style>

</style>