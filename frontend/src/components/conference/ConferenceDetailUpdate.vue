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
          <b-form-select v-model="selectedOption" :options="options">
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
      <form ref="form">
        <b-form-group
          type="file"
          class="roommodal"
          label="썸네일"
          label-for="thumbnail-roominput"
        >
          <b-form-file
            accept=".png, .jpg, .jpeg, .gif"
            v-model="thumbnailFile"
            class="mt-3"
            plain
          ></b-form-file>
          <!-- <div class="mt-3">Selected file: {{ thumbnailFile ? thumbnailFile.name : "" }}</div> -->
        </b-form-group>
      </form>
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
      roomContent: "", // 임시 - 아직 DB에 내용 저장하는 항목이 없음 : 추가 요청함
      thumbnailFile: "",
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
      // this.getConferenceInfo(this.$route.params.contentId); 
      // this.getConferenceInfo(this.conferenceId); 
      this.roomName = this.conferenceDetail.title,
      this.roomContent = this.conferenceDetail.description, // 임시 - 아직 DB에 내용 저장하는 항목이 없음 : 추가 요청함
      this.thumbnailFile = "", // 임시 - thumbnail file 데이터 관련 확인 필요
      this.selectedOption = this.conferenceDetail.conferenceType.id
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
      } else if (this.thumbnailFile.size > 2097152) { //size가 byte 단위임
        Swal.fire({
          icon: "error",
          title: "ImgsizeError",
          text: "업로드 가능한 파일의 최대 사이즈는 2MB입니다.",
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
          id: 1, 
        }, // 임시 - book DB와 연동 필요
        conferenceType: { 
          id: this.selectedOption,
        },
        thumbnailUrl: this.thummbnailFile, // 미디어파일 업로드 - 백엔드에 확인 필요
        title: this.roomName,
        description: this.roomContent,
        // 방 설명?? 어디에 입력함??? 백엔드에 확인 필요
        // isActive: 1
      }
    },
    roomUpdate() {
      if (this.roomIsValid()) {
        this.conferenceUpdate(this.roomData()) // 회의실 수정하기 // 바인딩된 데이터 payload로 전달
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
