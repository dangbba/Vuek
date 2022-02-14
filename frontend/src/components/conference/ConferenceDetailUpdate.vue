<template>
  <div>
    <b-button 
    v-b-modal.modal3-prevent-closing 
    variant="primary" 
    @click="$bvModal.show('modal-scoped'); beforeRoomInfo()"
    >컨퍼런스 정보 수정하기</b-button>
      <b-modal
      id="modal3-prevent-closing"
      ref="modal"
      title="컨퍼런스 정보 수정하기"
    >
      <form ref="form">
        <b-form-group
          class="roommodal"
          id="dropdown-1"
          label="용도"
          label-for="usage-roominput"
        >
          <b-form-select v-model="selectedOption" :options="options" style="width:100%; height:2.5rem; border-color:#ced4da;">
            <template #first>
              <b-form-select-option :value="null" disabled>--카테고리를 선택해주세요--</b-form-select-option>
            </template>
          </b-form-select>
        </b-form-group>
      </form>
      <form ref="form">
        <b-form-group
          class="roommodal"
          label="방 제목"
          label-for="name-roominput"
        >
          <b-form-input
            id="name-input"
            v-model="roomName"
            placeholder="수정할 방 제목을 입력해주세요"
          ></b-form-input>
        </b-form-group>
      </form>
      <form ref="form">
        <b-form-group
          type="textarea"
          class="roommodal"
          label="설명"
          label-for="content-roominput"
        >
          <b-form-textarea
            id="textarea"
            v-model="roomContent"
            placeholder="수정할 방 설명을 입력해주세요"
            row="3"
            max-rows="6"
          ></b-form-textarea>
        </b-form-group>
      </form>
      <!-- 방 도서정보 수정 가능?? 불가능?? -->

      <br />
      <template #modal-footer="{ ok, cancel }">
        <b-button variant="outline-primary" class="me-2" @click="roomUpdate() ? ok() : _"> 수정 </b-button>
        <b-button @click="cancel()"> 취소 </b-button> 
      </template>

      
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import Swal from "sweetalert2";

const userStore = "userStore";
const conferenceStore = "conferenceStore";

export default {
  name: "ConferenceDetailUpdate",
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapState(conferenceStore, ["conferenceDetail"]),
  },
  created() {
  },
  data() {
    return {
      conferenceId: this.$route.params.contentId,
      roomName: "",
      roomContent: "", 
      selectedOption: null,
      options : [
        { value: '1', text: '업무' },
        { value: '2', text: '교육' },
        { value: '3', text: '기타' },
      ]
    };
  },
  methods: {
    ...mapActions(conferenceStore, ["conferenceUpdate", "getConferenceInfo"]),
    beforeRoomInfo() {
      this.roomName = this.conferenceDetail.title,
      this.roomContent = this.conferenceDetail.description,
      this.selectedOption = this.conferenceDetail.conferenceType.id
      this.book = this.conferenceDetail.conferenceType.id
    },
    roomIsValid: function () {
      if (
        this.selectedOption === null ||
        this.roomName === ""
      ) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "용도, 제목은 필수 입력사항입니다.",
        });
        return false
      } else if (this.roomName.length > 31) {
        Swal.fire({
          icon: "error",
          title: "RoomnameError",
          text: "제목은 최대 30자까지 입력 가능합니다.",
        });
        return false
      } else {
        console.log(this.selectedOption);
        return true
      }
    },
    roomData() {
      return {
        id: this.conferenceId,
        user: { 
          userId: this.userInfo.userId
        },
        bookDetail: {
          id: this.conferenceDetail.bookDetail.id, 
        },
        conferenceType: { 
          id: this.selectedOption,
        },
        title: this.roomName,
        description: this.roomContent,
      }
    },
    roomUpdate() {
      if (this.roomIsValid()) {
        this.conferenceUpdate(this.roomData()) // 회의실 수정하기
        console.log(this.roomData())
        return true
      }
      else {
        return false
      }
    },
  },
};
</script>

<style>
</style>
