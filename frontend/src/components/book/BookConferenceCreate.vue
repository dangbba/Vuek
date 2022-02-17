<template>
  <div class="text-center">
    <b-button
      variant="primary"
      @click="$bvModal.show(item.isbn13); selectBook(item)"
      >독서모임 생성</b-button
    >
    
    <b-modal scrollable :id=item.isbn13 ref="modal" title="독서모임 생성하기" v-if="userInfo">
      <h5 class="d-inline">주제 도서: {{ selectedBook }}</h5>
      <hr>
      <form ref="form">
        <b-form-group
          class="roommodal"
          id="dropdown-1"
          label="독서모임 카테고리"
          label-for="usage-roominput"
        >
          <b-form-select v-model="selectedOption" :options="options" style="width:100%; height:2.5rem; border-color:#ced4da;">
            <template #first>
              <b-form-select-option :value="null" disabled
                >--카테고리를 선택해주세요--</b-form-select-option
              >
            </template>
          </b-form-select>
        </b-form-group>
      </form>
      <form ref="form">
        <b-form-group
          class="roommodal"
          label="독서모임 제목"
          label-for="title"
        >
          <b-form-input
            id="title"
            v-model="roomName"
            placeholder="독서모임 제목을 입력해주세요"
          ></b-form-input>
        </b-form-group>
      </form>
      <form ref="form">
        <b-form-group
          type="textarea"
          class="roommodal"
          label="독서모임 소개"
          label-for="description"
        >
          <b-form-textarea
            id="description"
            v-model="roomContent"
            placeholder="독서모임 소개를 입력해주세요"
            row="3"
            max-rows="6"
          ></b-form-textarea>
        </b-form-group>
      </form>
      <br />
      <template #modal-footer="{ ok, cancel }">
        <b-button
          variant="outline-primary"
          class="me-2"
          @click="roomCreate() ? ok() : _"
        >
          생성
        </b-button>
        <b-button
          @click="
            formReset();
            cancel();
          "
        >
          취소
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import Swal from "sweetalert2";
import http from "@/config/http-common.js";


export default {
  name: "BookConferenceCreate",
  data() {
    return {
      roomName: "",
      roomContent: "",
      bookSearchValue: "",
      selectedOption: null, // (명세서 상) 업무를 기본값으로 하라고 하지만 선택사항 없는 상황에서 선택하는 것이 나은 것 같아서 수정
      options: [ // 카테고리를 조회해서 DB에서 데이터를 연동했으면 좋았겠지만 구현 편의상 직접 작성함 // 카테고리 명칭 바뀌면 이 부분을 수정해야함
        { value: "1", text: "책 소개" },
        { value: "2", text: "토의" },
        { value: "3", text: "세미나" },
        { value: "4", text: "기타" },
      ],
      bookData: 1,
      selectedBook: '',
      bookDetailId: null
    };
  },
  props: {
    item: Object,
  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
  },
  methods: {
    ...mapActions("conferenceStore", ["createRoom"]),
    roomIsValid: function () {
      if (this.selectedOption === null || this.roomName === "") {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "용도, 제목은 필수 입력사항입니다.",
        });
        return false;
      } else if (this.roomName.length > 31) {
        Swal.fire({
          icon: "error",
          title: "RoomnameError",
          text: "제목은 최대 30자까지 입력 가능합니다.",
        });
        return false;
      } else {
        return true;
      }
    },
    roomData() {
      return {
        user: {
          userId: this.userInfo.userId, 
        },
        bookDetail: {
          id: this.bookDetailId, 
        }, // 임시 - book DB와 연동 필요
        conferenceType: { 
          id: this.selectedOption,
        },
        title: this.roomName,
        description: this.roomContent,
        isActive: 1
      };
    },
    formReset() {
      this.selectedOption = null;
      this.thumbnailFile = "";
      this.roomName = "";
      this.roomContent = "";
      this.roomSearchValue = "";
      // this.bookData = 1 ;
    }, // 입력된 form 지우기
    roomCreate() {
      if (this.roomIsValid()) {
        this.createRoom(this.roomData()); // 회의실 생성하기 // 바인딩된 데이터 payload로 전달
        this.formReset(); // 입력된 form 지우기
        return true;
      } else {
        return false;
      }
    },
    bookSearch() {
      // 유효성 검사
      if (this.bookSearchValue !== "") {
        http({
          method: "get",
          url: `/search/naver?query=${this.bookSearchValue}`,
        })
          .then((response) => {
            console.log(response);
            this.bookData = response.data.items
          })
          .catch((error) => {
            console.dir(error);
          });
      } else {
        Swal.fire({
          icon: "error",
          text: "검색어를 입력해주세요.",
        })
      }
    },
    transStr(str) {
      // return str
      var transedStr = str.replaceAll('<b>', '')
      transedStr = transedStr.replaceAll('</b>', '')
      return transedStr
    },
    // isbn으로 책 pk 검색
    checkBookInDB(book) {
       console.log(book)
       const isbn = book.isbn13
       console.log(isbn)
       http({
        method: "get",
        url: `/search/${isbn}`,
      })
        .then((response) => {
          console.log(response);
          this.bookDetailId = response.data
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    selectBook(book) {
      if (this.userInfo) {
        this.selectedBook = this.transStr(book.title)
        // this.bookData = 2
        this.bookDetail.id = this.checkBookInDB(book)
      } else {
        Swal.fire({
          icon: "warning",
          text: "독서모임을 생성하려면 로그인해주세요!",
        })
      }

    }
  },
};
</script>

<style>
</style>