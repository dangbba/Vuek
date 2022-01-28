<template>
  <div class="col"> <!--활성중인 회의실만 렌더링하도록-->
    <b-card no-body bg-variant="light" class="overflow-hidden mx-2 my-2" style="max-width: 540px;">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img :src="thumbnail_url" alt="Image" class="rounded-3 mt-3 ms-3"></b-card-img>
        </b-col>
        <b-col md="6" class="mb-3">
          <b-card-body :title="item.title"> <!--방 제목-->
            <b-card-text>
              <p class="card-text">방번호: {{ item.id }}</p> <!--회의 번호-->
              <p class="card-text">책제목: {{ item.book_detail_id }}</p> <!--bookStore와 연결해서 책 제목 가져와야함..?-->
              <p class="card-text" v-if="item.conference_type==1">방카테고리: 업무</p> <!--카테고리번호-->
              <p class="card-text" v-if="item.conference_type==2">방카테고리: 교육</p> <!--카테고리번호-->
              <p class="card-text" v-if="item.conference_type==3">방카테고리: 기타</p> <!--카테고리번호-->
              <p class="card-text">회의시작시간: {{ item.call_start_time }}</p>
              <p class="card-text">회의종료시간: {{ item.call_end_time }}</p>
              <p v-if="item.is_active" class="card-text">진행중 유무: <span class="text-success">진행중</span> </p> 
              <p v-else class="card-text">진행중 유무: <span class="text-danger">종료</span></p> 
              
            </b-card-text>
          </b-card-body>
          <b-button variant="primary" @click="enterConference" v-if="item.is_active">입장</b-button> <!--클릭시 회의실id에 맞는 detail 링크와 연결-->
          <p class="badge bg-dark text-wrap fs-6 mb-1" v-else style="padding: 12px;">입장불가</p> <!--클릭시 회의실id에 맞는 detail 링크와 연결-->
        </b-col>
      </b-row>
    </b-card>
  </div>


</template>

<script>
export default {
  name: 'ConferenceListItem',
  props: {
    item: {
      type: Object,
      required: true,
    },
  },
  methods: {
    enterConference() {
      this.$router.push({
        path: `/Conference/view/${this.item.id}`,
      });
    }
  },
  data: function () {
    return {
      thumbnail_url: 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20150415_3%2Fkoowq_1429106016874OoUDk_PNG%2F%25C0%25E7%25BB%25FD_%25BE%25C6%25C0%25CC%25C4%25DC2-02.png&type=sc960_832' //임시 기본이미지
      // thumbnail_url: `https://iamge/${this.item.thumbnail_url}`,
    }
  }
}
</script>

<style>

</style>