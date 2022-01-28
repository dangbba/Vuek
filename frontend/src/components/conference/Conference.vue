<template>
  <div class="container">

    <div class="header">
      <h1 class="fw-bold my-5">Conference List</h1>
    </div>


    <conference-search></conference-search>

  
    <b-button v-b-modal.modal3-prevent-closing variant="primary" class="col-4 mb-3"
      >컨퍼런스 생성하기</b-button
    >
        <b-modal
      id="modal3-prevent-closing"
      ref="modal"
      title="컨퍼런스 생성하기"
      hide-footer
    >
      <form ref="form">
        <b-form-group
          class="roommodal"
          id="dropdown-1"
          text="Dropdown Button"
          label="용도"
          label-for="usage-roominput"
        >
          <b-dropdown
            id="ddCommodity"
            name="ddCommodity"
            v-model="dd.ddSelectedOption"
            text="아이템 목록"
            variant="primary"
            class="m-md-2" v-on:change="changeItem"
          >
            <b-dropdown-item
              v-for="option in dd.options" 
              :key="option.value" 
              :value="option.value"
              @click="dd.ddSelectedOption = option.value">{{ option.text }}</b-dropdown-item>
            <b-dropdown-divider></b-dropdown-divider>
            <b-dropdown-item disabled>Disabled action</b-dropdown-item>
          </b-dropdown>
          <span>Selected: {{ dd.ddSelectedOption }}</span>
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
            v-model="roomCredentials.name"
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
            v-model="roomCredentials.content"
            placeholder="Enter something..."
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
            v-model="roomCredentials.thumbnail"
            class="mt-3"
            plain
          ></b-form-file>
          <div class="mt-3">Selected file: {{ roomCredentials.thumbnail ? roomCredentials.thumbnail.name : "" }}</div>
        </b-form-group>
      </form>
      <br />
      <b-button variant="outline-primary" @click="roomIsValid(roomCredentials)"> 생성 </b-button>&nbsp;
      <b-button @click="resetValue"> 취소 </b-button>
    </b-modal>


    <div class="row">
      <conference-sort class="offset-5 col-4"></conference-sort>
      <conference-filter class="col-3"></conference-filter>
    </div>

    <conference-list></conference-list>

    <div class="page-content">
      <div class="main-panel">
        <div class="video-navigation">
          <span>View:</span>
          <a
            :class="{ active: activeView === 'tile' }"
            @click="setActiveView('tile')"
            >Tile</a
          >
          <a
            :class="{ active: activeView === 'top-thumbs' }"
            @click="setActiveView('top-thumbs')"
            >Top</a
          >
        </div>
      </div>
    </div>
    <router-link to="/Conference/view">1번</router-link>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import ConferenceList from './ConferenceList.vue';
import ConferenceFilter from './ConferenceFilter.vue';
import ConferenceSearch from './ConferenceSearch.vue';
import ConferenceSort from './ConferenceSort.vue';

export default {
  name: "Conference",
  components: {
    ConferenceList,
    ConferenceFilter,
    ConferenceSearch,
    ConferenceSort,
  },
  data() {
    return {
      text: "",
      roomCredentials: {
        name: "",
        content: "",
        file: "",
      },
      //
      someOtherProperty: null,
      dd: {
        originalValue: [],
        ddSelectedOption: "업무",
        disabled: false,
        readonly: false,
        visible: true,
        color: "",
        options: [
          {
            "value": "업무",
            "text": "업무"
          },
          {
            "value": "교육",
            "text": "교육"
          },
          {
            "value": "기타",
            "text": "기타"
          }
        ]
      }
      //
    };
  },
  methods: {
    roomIsValid: function (cred) {
      console.log(cred);
      if (
        cred.usage ||
        cred.username === "" ||
        cred.content === "" ||
        cred.thumbnail === "" ||
        cred.userid
      ) {
        Swal.fire({
          icon: "error",
          title: "Stop!",
          text: "제목, 설명, 썸네일은 필수 입력사항입니다.",
        });
      } else if (cred.roomName.length > 31) {
        Swal.fire({
          icon: "error",
          title: "RoomnameError",
          text: "제목은 최대 30자까지 입력 가능합니다.",
        });
      } else if (cred.file !== cred.file.png || cred.file.jpg || cred.file.jpeg || cred.file.gif || "") {
        Swal.fire({
          icon: "error",
          title: "ImgError",
          text: "업로드 가능한 파일의 확장자는 png, jpg, jpeg, gif입니다.",
        });
      } else if (cred.file.size > 2) {
        Swal.fire({
          icon: "error",
          title: "ImgsizeError",
          text: "업로드 가능한 파일의 최대 사이즈는 2MB입니다.",
        });
      } else {
        console.log(cred);
      }
    },
    //
    changeItem: async function () {
      //grab some remote data
      try {
        let response = await this.$http.get('https://www.example.com/api/' + this.dd.ddSelectedOption + '.json');
        console.log(response.data);
        this.someOtherProperty = response.data;
      } catch (error) {
          console.log(error)
        }
    },
    resetValue() {
      this.$router.push("/");
    },
    //
  },
  watch: {

  },
  async created() {

  },
  resetValue() {
      this.$router.push("/");
    },
};
</script>

<style>
body {
  margin: 0;
  padding: 0;
}

#app {
  font-family: "Open Sans", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #000;
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;

  .header {
    background: #fff;
    color: #333132;
    font-weight: 400;
    padding: 16px 20px;
    box-shadow: 0 4px 24px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    font-size: 21px;

    svg {
      height: 30px;
      margin-right: 8px;
    }
  }

  .page-content {
    flex-grow: 1;
    display: flex;
    overflow: hidden;

    @media all and (max-width: 768px) {
      flex-direction: column;
    }
  }
}

.main-panel {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  background: #333;
  padding: 0 20px 20px 20px;
}

.video-navigation {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  padding: 6px 0;
  color: #ccc;

  > a,
  > span {
    padding: 6px 10px;
    line-height: 1em;
    font-weight: bold;
  }

  a {
    color: #fff;
    text-decoration: none;
    border-radius: 4px;

    &:hover {
      text-decoration: underline;
      cursor: pointer;
    }

    &.active {
      color: #333;
      background: #fff;

      &:hover {
        cursor: default;
        text-decoration: none;
      }
    }
  }
}

@media all and (max-width: 768px) {
  .video-navigation {
    display: none;
  }

  .main-panel {
    padding: 20px;
  }
}

.but {
  color: white;
  margin: 10px;
}
.icon-bar {
  height: 100%;
  width: 90px;
  background-color: #555;
  position: sticky; /* Fixed Sidebar (stay in place on scroll) */
  /*z-index: 1;*/ /* Stay on top */
  bottom: 0; /* Stay at the top */
  left: 0;
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 0;
}

.icon-bar a {
  display: block;
  text-align: center;
  padding: 16px;
  transition: all 0.3s ease;
  color: white;
  font-size: 36px;
}

.icon-bar a:hover {
  background-color: #858585;
}

.active {
  background-color: #5eaf13;
}

.loginmodal {
  margin: 2rem;
  font-size: 1.8rem;
}

.signupmodal {
  margin: 2rem;
  font-size: 1.8rem;
}


</style>
