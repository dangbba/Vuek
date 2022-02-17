<template>
  <div class="text-center">
    <b-button
      v-b-modal.modal3-prevent-closing
      variant="primary"
      class="col-8 mb-3"
      @click="$bvModal.show('modal-scoped')"
      size="lg"
      >독서모임 생성하기</b-button
    >
    <b-modal scrollable id="modal3-prevent-closing" ref="modal" title="독서모임 생성하기">
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
      <form ref="form" class="mb-3">
        <b-form-group
          type="search"
          class="searchmodal"
          label="도서 검색"
          label-for="bookSearch"
        >
          <b-row>
            <b-col sm="9" md="10" class="pe-0">
              <b-form-input
                id="bookSearch"
                placeholder="키워드 입력"
                v-model="bookSearchValue"
                plain
                @keydown.enter.prevent="bookSearch()"
              >
              </b-form-input>
            </b-col>
            <b-col md="2" class="pe-0">
              <b-button variant="outline-primary" @click="bookSearch()">검색</b-button>
            </b-col>
          </b-row>
        </b-form-group>
      </form>
      <div v-if="bookData===1" class="mx-2 mt-3 text-secondary">
        <p>도서를 검색해서 선택해주세요.</p>
      </div>
      <div v-else-if="bookData===2">
        <h5 class="d-inline ms-2 mt-2">선택 도서: {{ selectedBook }}</h5><b-button size="sm" @click="bookData=1" class="ms-2">선택 취소</b-button>
      </div>
      <div v-else-if="bookData.length===0" class="mx-2 mt-3 text-danger">
        <p>검색결과가 없습니다.</p>
      </div>
      <div v-else>
        <b-list-group>
          <b-list-group-item v-for="book in bookData" :key=book.isbn>
            <b-row>
              <b-col md="3">
                <img :src="book.image" alt="book_img" v-if="book.image" @error="replaceByDefault" style="width:82px;">
                <img :src="require('@/assets/thumbnail/thumbnail_default_img.jpg')" alt="book_img" style="width:82px;" v-else>
              </b-col>
              <b-col md="9">
                <h5>{{ transStr(book.title) }}</h5>
                <p class="mb-1">저자: {{ transStr(book.author) }}</p>
                <b-button size="sm" variant="outline-success" :href="book.link" onclick="window.open(this.href, '_blank', 'width=800, height=600'); return false;">상세보기</b-button>
                <b-button size="sm" variant="outline-primary" class="ms-2" @click="selectBook(book)">도서선택</b-button>
              </b-col>
            </b-row>
          </b-list-group-item>
        </b-list-group>
      </div>


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
  name: "ConferenceCreate",
  data() {
    return {
      roomName: "",
      roomContent: "",
      bookSearchValue: "",
      selectedOption: null,
      options: [
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
  created() {},
  computed: {
    ...mapState("userStore", ["userInfo"]),
  },
  methods: {
    ...mapActions("conferenceStore", ["createRoom"]),
    roomIsValid: function () {
      if (this.selectedOption === null || this.roomName === "" || this.bookDetailId === null) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "용도, 제목, 도서는 필수 입력사항입니다.",
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
      // this.thumbnailFile = "";
      this.roomName = "";
      this.roomContent = "";
      this.roomSearchValue = "";
      this.bookData = 1 ;
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
      //  console.log(book)
       const isbn = book.isbn.split(" ")[1]
      //  console.log(isbn)
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
      this.selectedBook = this.transStr(book.title)
      this.bookData = 2
      this.bookDetail.id = this.checkBookInDB(book)
    },
    replaceByDefault(e) {
      e.target.src = require('@/assets/thumbnail/thumbnail_default_img.jpg')
    }
  },
};
</script>

<style>

</style>