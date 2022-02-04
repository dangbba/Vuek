<template>
  <div>
    <b-button 
    v-b-modal.modal3-prevent-closing 
    variant="primary" 
    class="col-4 mb-3"
    @click="$bvModal.show('modal-scoped')"
    >컨퍼런스 생성하기</b-button>
      <b-modal
      id="modal3-prevent-closing"
      ref="modal"
      title="컨퍼런스 생성하기"
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
            placeholder="방 제목을 입력해주세요"
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
            placeholder="설명을 입력해주세요"
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
        <b-button variant="outline-primary" class="me-2" @click="roomCreate() ? ok() : _"> 생성 </b-button>
        <b-button @click="formReset(); cancel();"> 취소 </b-button> 
      </template>

      
    </b-modal>

  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import Swal from "sweetalert2";

export default {
  name: "ConferenceCreate",
  data() {
    return {
      roomName: "",
      roomContent: "", 
      thumbnailFile: "",
      selectedOption: "1", // (명세서 상) 업무를 기본값으로 하라고 함
      options: [
        { value: '1', text: '업무' },
        { value: '2', text: '교육' },
        { value: '3', text: '기타' },
      ],    

    };
  },
  created() {

  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
  },
  methods: {
    ...mapActions("conferenceStore", ["createRoom"]),
    roomIsValid: function () {
      console.log(this.thumbnailFile) // thumbnail 업로드 관련 확인 필요
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
        conference_type: this.selectedOption,
        book_detail_id: 1, // 임시 - book DB와 연동 필요
        // call_start_time: this.date_time(), // 자동으로 지정됨
        user_user_id: this.userInfo.user_id,
        thumbnail_url: this.thummbnailFile, // 미디어파일 업로드 - 백엔드에 확인 필요
        title: this.roomName,
        // 방 설명?? 어디에 입력함??? 백엔드에 확인 필요
        is_active: 1, // 기본값이 0(종료)이라 1(진행중)로 생성해야함
        participant: null
      }
    },
    formReset() {
        this.selectedOption = null
        this.thumbnailFile = ""
        this.roomName = ""
        this.roomContent = "" // 확인 필요 // 임시 - 아직 DB에 내용 저장하는 항목이 없음 : 추가 요청함
    }, // 입력된 form 지우기
    roomCreate() {
      if (this.roomIsValid()) {
        this.createRoom(this.roomData()) // 회의실 생성하기 // 바인딩된 데이터 payload로 전달
        this.formReset() // 입력된 form 지우기
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
