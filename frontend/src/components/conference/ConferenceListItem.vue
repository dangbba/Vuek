<template>
  <div class="col">
    <b-card no-body bg-variant="light" class="overflow-hidden mx-2 my-2" style="max-width: 540px;">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img :src="thumbnail_url" alt="Image" class="rounded-3 mt-3 ms-3"></b-card-img>
        </b-col>
        <b-col md="6" class="mb-3">
          <b-card-body :title="item.title"> <!--방 제목-->
            <b-card-text>
              <p class="card-text">방번호: {{ item.id }}</p> <!--회의 번호-->
              <p class="card-text">책제목: {{ item.bookDetail.id }}</p> <!--bookStore와 연결해서 책 제목 가져와야함..?-->
              <p class="card-text" v-if="item.conferenceType.id==1">방카테고리: 업무</p> <!--카테고리번호-->
              <p class="card-text" v-if="item.conferenceType.id==2">방카테고리: 교육</p> <!--카테고리번호-->
              <p class="card-text" v-if="item.conferenceType.id==3">방카테고리: 기타</p> <!--카테고리번호-->
              <p class="card-text">회의시작시간: {{ item.callStartTime }}</p>
              <p class="card-text">회의종료시간: {{ item.callEndTime }}</p>
              <p v-if="item.isActive" class="card-text">진행중 유무: <span class="text-success">진행중</span> </p> 
              <p v-else class="card-text">진행중 유무: <span class="text-danger">종료</span></p> 
              <p class="card-text">방 참여 인원: {{ item.participant }}</p>
              
            </b-card-text>
          </b-card-body>
          <b-button variant="primary" @click="enterConference" v-if="item.isActive">입장</b-button> <!--클릭시 회의실id에 맞는 detail 링크와 연결-->
          <p class="badge bg-dark text-wrap fs-6 mb-1" v-else style="padding: 12px;">입장불가</p> <!--클릭시 회의실id에 맞는 detail 링크와 연결-->
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
      // 회의실 카테고리 가져오기
      this.getConferenceCategories()
      this.$router.push({
        path: `conference/view/${this.item.id}`,
      });
    }
  },
  data: function () {
    return {
      thumbnail_url: require('@/assets/thumbnail/thumbnail_default_img.png') // 원래는 thumbnail 없을때만....
      // thumbnail_url: `https://iamge/${this.item.thumbnail_url}`,
    }
  }
}
</script>

<style>

</style>