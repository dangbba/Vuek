<template>
  <div>
    <b-row class="mt-2">
      <b-col cols="7">
        <b-row>
          <span>
            <user-video :stream-manager="mainStreamManager" />
            <user-video
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click.native="updateMainVideoStreamManager(sub)"
            />
          </span>
        </b-row>
        <b-row>
        </b-row>
      </b-col>
      <b-col cols="5">
        <b-container>
          <b-card border-variant="secondary" style="width:100%; height:100%; background-color:#343a40;">
            <b-row>
              <b-col cols="4">
               <b-card-img :src="transUrl(conferenceDetail.bookDetail.titleUrl)" class="mb-3 d-inline" img-alt="book cover"></b-card-img>
              </b-col>
              <b-col>
                <h5>{{ transStr(conferenceDetail.bookDetail.title) }}</h5>
                <hr>
                <p>저자: {{ transStr(conferenceDetail.bookDetail.author) }} </p>
                <hr>
                <p>줄거리: {{ transStr(conferenceDetail.bookDetail.overview) }}</p>
              </b-col>
            </b-row>
            <hr>
            <b-card-text>
              <h4 class="mb-3">독서모임 제목: {{ conferenceDetail.title }}</h4>
              <p>독서모임 카테고리: {{ conferenceDetail.conferenceType.name }}</p>
              <p>독서모임 소개: {{ conferenceDetail.description }}</p>
            </b-card-text>
            <hr>
              <p class="text-white">독서모임 시작시간: {{ conferenceDetail.callStartTime }}</p>
              <div class="text-end">
            <span v-if="conferenceDetail.user.userId === userInfo.userId" >
              <!-- 방 종료 / 수정 관련 -->
                <conference-detail-update></conference-detail-update>
                <conference-detail-close @change="change"></conference-detail-close> <!--하위컴포넌트에서 이벤트 받음-->
            </span>
              <input
                class="btn btn-large btn-danger"
                type="button"
                id="buttonLeaveSession"
                @click="leaveSession"
                value="나가기"
              />
            </div>
          </b-card>
        </b-container>
      </b-col>
    </b-row>
    <b-row>
      <b-col offset="2" cols="5" class="mb-3">
        <input
          class="btn btn-large btn-warning me-2"
          type="button"
          id="buttonVideo"
          @click="videoOnOff"
          value="비디오 켜기 / 끄기"
        />
        <input
          class="btn btn-large btn-warning"
          type="button"
          id="buttonAudio"
          @click="audioOnOff"
          value="오디오 켜기 / 끄기"
        />
      </b-col>
      <b-col col="4">
      </b-col>
    </b-row>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
import ConferenceDetailUpdate from "./ConferenceDetailUpdate";
import ConferenceDetailClose from "./ConferenceDetailClose";
// import ConferenceDetailDelete from "./ConferenceDetailDelete";
import Swal from "sweetalert2";

import { OpenVidu } from "openvidu-browser";
import UserVideo from "./UserVideo.vue";

const userStore = "userStore";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i6c205.u.ssafy.io" + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

const conferenceStore = "conferenceStore";

export default {
  name: "Conference_detail",
  components: {
    UserVideo,
    ConferenceDetailUpdate,
    ConferenceDetailClose,
    // ConferenceDetailDelete,
  },
  data() {
    return {
      userName: "",
      // message: "",
      recvList: [],
      // 독서모임 정보 관련
      conferenceId: this.$route.params.contentId,
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: undefined,
      isClose: false
    };
  },
  created() {
    this.getConferenceInfo(this.conferenceId);
    // 방 참여 이력 생성
    this.createHistory(this.historyData());
    console.log("방 번호" + this.conferenceId);
    this.mySessionId = this.conferenceId;
    this.joinSession();
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapState(conferenceStore, ["conferenceDetail", "conferenceCategory"]),
  },
  methods: {
    ...mapActions(conferenceStore, ["getConferenceInfo", "createHistory"]),
    historyData() {
      return {
        conference: {
          id: this.conferenceId,
        },
        user: {
          userId: this.userInfo.userId,
        },
        // action: 1, // 뭔지 모르겠음 / null값 가능함 / 액션 기본값은 0
      };
    },
    change() { // 하위컴포넌트에서 emit시 동작하는 이벤트
      this.isClose = true
    },
    transStr(str) {
      // return str
      let transedStr = str.replaceAll("<b>", "");
      transedStr = transedStr.replaceAll("</b>", "");
      return transedStr;
    },
    transUrl(url) {
      let transedUrl = ""
      if(url) {
        transedUrl = url.replace('=m1&', '=m200&') 
      } else {
        transedUrl = require('@/assets/thumbnail/thumbnail_default_img.jpg') 
      }
      return transedUrl
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
          .connect(token, { clientData: this.userInfo.userName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(this.userInfo.userName, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "250x250", // The resolution of your video
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
      this.$router.push({
        path: `../../conference`,
      });
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
    videoOnOff() {
      this.videoEnabled = !this.videoEnabled;
      this.publisher.publishVideo(this.videoEnabled);
    },
    audioOnOff() {
      this.audioEnabled = !this.audioEnabled;
      this.publisher.publishAudio(this.audioEnabled);
    },
  },
  beforeRouteLeave(to, from, next) {
    // 독서모임 종료 버튼 누른 경우는 작동하면 안됨
    // 하위컴포넌트에서 이벤트 emit하여 isClose 변경
    if (!this.isClose) {
    //swal 적용
    console.log(this.isClose)
    Swal.fire({
        icon: "question",
        text: '독서모임을 나가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네',
      }).then((result) => {
        if (result.isConfirmed) {
          if (this.session) this.session.disconnect();
          this.session = undefined;
          this.mainStreamManager = undefined;
          this.publisher = undefined;
          this.subscribers = [];
          this.OV = undefined;
          window.removeEventListener("beforeunload", this.leaveSession);
          next();
        } else {
          next(false);
          Swal.fire({
            icon: "warning",
            text: "취소되었습니다.",
          });
        }
      });
    } else { //독서모임 종료하는 경우
      if (this.session) this.session.disconnect();
        this.session = undefined;
        this.mainStreamManager = undefined;
        this.publisher = undefined;
        this.subscribers = [];
        this.OV = undefined;
        window.removeEventListener("beforeunload", this.leaveSession);
        next();
    }
  },
};
</script>

<style>
</style>
