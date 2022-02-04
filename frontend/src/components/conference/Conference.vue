<template>
  <div class="container">
    <div class="header">
      <h1 class="fw-bold my-5">Conference List</h1>
    </div>

    <conference-search></conference-search>

    <b-button
      v-b-modal.modal3-prevent-closing
      variant="primary"
      class="col-4 mb-3"
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
            class="m-md-2"
            v-on:change="changeItem"
          >
            <b-dropdown-item
              v-for="option in dd.options"
              :key="option.value"
              :value="option.value"
              @click="dd.ddSelectedOption = option.value"
              >{{ option.text }}</b-dropdown-item
            >
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
          <div class="mt-3">
            Selected file:
            {{
              roomCredentials.thumbnail ? roomCredentials.thumbnail.name : ""
            }}
          </div>
        </b-form-group>
      </form>
      <br />
      <b-button variant="outline-primary" @click="roomIsValid(roomCredentials)">
        생성 </b-button
      >&nbsp;
      <b-button @click="resetValue"> 취소 </b-button>
    </b-modal>
    <div id="main-container" class="container">
      <div id="join" v-if="!session">
        <div id="join-dialog" class="jumbotron vertical-center">
          <h1>접속하실 화상회의를 적어주세요</h1>
          <div class="form-group">
            <p>
              <label>Session</label>
              <input
                v-model="mySessionId"
                class="form-control"
                type="text"
                required
              />
            </p>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="joinSession()">
                Join!
              </button>
            </p>
          </div>
        </div>
      </div>

      <div id="session" v-if="session">
        <div id="session-header">
          <h1 id="session-title">{{ mySessionId }}</h1>
          <input
            class="btn btn-large btn-danger"
            type="button"
            id="buttonLeaveSession"
            @click="leaveSession"
            value="Leave session"
          />
        </div>
        <div id="main-video" class="col-md-6">
          <user-video :stream-manager="mainStreamManager" />
        </div>
        <div id="video-container" class="col-md-6">
          <user-video
            :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)"
          />
          <user-video
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            @click.native="updateMainVideoStreamManager(sub)"
          />
        </div>
      </div>
    </div>
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
  </div>
</template>

<script>
import Swal from "sweetalert2";
import ConferenceList from "./ConferenceList.vue";
import ConferenceFilter from "./ConferenceFilter.vue";
import ConferenceSearch from "./ConferenceSearch.vue";
import ConferenceSort from "./ConferenceSort.vue";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./UserVideo.vue";
import { mapState } from "vuex";

const userStore = "userStore";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
export default {
  name: "Conference",
  components: {
    ConferenceList,
    ConferenceFilter,
    ConferenceSearch,
    ConferenceSort,
    UserVideo,
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
            value: "업무",
            text: "업무",
          },
          {
            value: "교육",
            text: "교육",
          },
          {
            value: "기타",
            text: "기타",
          },
        ],
      },
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      mySessionId: "SessionA",
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
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
      } else if (
        cred.file !== cred.file.png ||
        cred.file.jpg ||
        cred.file.jpeg ||
        cred.file.gif ||
        ""
      ) {
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
        let response = await this.$http.get(
          "https://www.example.com/api/" + this.dd.ddSelectedOption + ".json"
        );
        console.log(response.data);
        this.someOtherProperty = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    resetValue() {
      this.$router.push("/");
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.userInfo.user_name })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "480x360", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    //
  },
  watch: {},
  async created() {},
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
