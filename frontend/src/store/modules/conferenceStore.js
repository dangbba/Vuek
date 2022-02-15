import http from "@/config/http-common.js";
import router from "@/router/index.js"
import Swal from "sweetalert2";

const conferenceStore = {
  namespaced: true,
  state: {
    conferenceItems: [], // 회의 전체 목록 저장
    conferenceDetail: [], // 회의 상세정보 저장
    conferenceHistory: [], // 회의 이력 저장
    conferenceCategory: [], // 회의 카테고리 저장
  },
  mutations: {
    LOAD_CONFERENCE_ITEMS: function (state, results) {
      if (results.length == 0) {
        state.conferenceItems = false;
      } else {
        state.conferenceItems = results.sort((a, b) => {
          return b.id - a.id;
        }); // 내림차순으로 보여줌
      }
    },
    LOAD_CONFERENCE_SORTED_ITEMS: function (state, results) {
      if (results.length == 0) {
        state.conferenceItems = false;
      } else {
        state.conferenceItems = results;
      } // sort한 데이터를 보여주는 경우 그대로 보여줌
    },
    LOAD_CONFERENCE_DETAIL: function (state, results) {
      state.conferenceDetail = results
    },
    LOAD_CONFERENCE_HISTORY: function (state, results) {
      //state.conferenceHistory = results; // 중복 제거해서 넣기
      // console.log(state.conferenceHistory)
      state.conferenceHistory = results.filter(
        (arr, index, callback) => index === callback.findIndex(t => t.conference.id === arr.conference.id)
      ); // 컨퍼런스 아이디가 중복인 것은 제거
    },
    LOAD_CONFERENCE_CATEGORY: function (state, results) {
      state.conferenceCategory = results;
      // console.log(state.conferenceCategory)
    },
  },
  actions: {
    // 방목록 조회
    LoadConferenceItems: function ({commit}) {
      http({
      method: 'get',
      url: `/conferences`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data)
        })
        .catch((err) => {
          console.dir(err);
      });
    },
    // 방목록 카테고리 필터링
    getConferenceByCategory : function ({commit}, conference_type) {
      http({
        method: 'get',
        url: `/conferences/getConferenceByCategory?conference_type_id=${conference_type}`,
        })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
        });
    },
    // 방 목록 옵션에 따라 정렬
    getConferenceBySort : function ({commit}, sort_option) {
      // console.log(sort_option)
      http({
      method: 'get',
      url: `/conferences/getConferenceBySort?asc=${sort_option}`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_SORTED_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
        });
    },
    // 방목록 제목에 따라 검색
    searchByTitle : function ({commit}, search_value) {
      http({
      method: 'get',
      url: `/conferences/searchByTitle?word=${search_value}`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
        });
    },
    // 방 생성
    createRoom: function ({commit}, room_data) {
      http({
        method: "post",
        url: `/conferences/create`,
        data: room_data
      })
        .then((response) => {
          commit('LoadConferenceItems'); // 임시
          router.push({
            path: `/conference/view/${response.data}`,
          }); // router를 import 안해서 에러가 있었음
        })
        .catch((error) => {
          // alert('컨퍼런스 룸 생성에 실패했습니다.')
          Swal.fire({
            icon: "error",
            text: "컨퍼런스 룸 생성에 실패했습니다.",
          });
          console.dir(error)
        });

    },
    // 방 카테고리 조회
    getConferenceCategories: function ({commit}) {
      http({
        method: "get",
        url: `/conferences/conference-categories`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_CATEGORY', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 세부정보 조회
    getConferenceInfo: function ({commit}, conference_id) {
      // console.log('store')
      console.log(conference_id)
      http({
        method: "get",
        url: `/conferences/conference-info/${conference_id}`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_CONFERENCE_DETAIL', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 종료
    conferenceClose: function ({dispatch}, conference_id) {
      // console.log(conference_id)
      http({
        method: "post",
        url: `/conferences/close?idconference=${conference_id}`, 
      })
        .then((response) => {
          console.log(response);
          dispatch('LoadConferenceItems');
          Swal.fire({
            icon: "success",
            text: "회의가 종료되었습니다.",
          });
          router.push({
            path: `/conference`,
          });
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 정보 수정
    conferenceUpdate: function ({dispatch}, conference_data) {
      http({
        method: "put",
        url: `/conferences/conference-info`,
        data: conference_data
      })
        .then((response) => {
          console.log(response);
          dispatch('getConferenceInfo', conference_data.id);
          Swal.fire({
            icon: "success",
            text: "컨퍼런스 룸 정보가 수정되었습니다.",
          });
        })
        .catch((error) => {
          console.dir(error);
        }); 
    },
    // 방 삭제
    conferenceDelete: function ({dispatch}, conference_id) {
      console.log(conference_id)
      http({
        method: "post",
        url: `/conferences/delete?conferenceId=${conference_id}`,
      })
        .then((response) => {
          console.log(response);
          dispatch('LoadConferenceItems');
          alert("회의가 완전히 종료(삭제)되었습니다.")
          router.push({
            path: `/conference`,
          });
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 참여 및 참여 이력 생성
    // eslint-disable-next-line
    createHistory: function ({dispatch}, conference_data) {
      http({
        method: "post",
        url: `/conferences/createHistory`,
        data: conference_data
      })
        .then((response) => {
          console.log(response)
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    getConferenceHistory: function ({commit}, user_id) {
      http({
        method: "get",
        url: `/conferences/getConferenceHistory?user_id=${user_id}`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_CONFERENCE_HISTORY', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    /////////////////////////
    // 방 참여 -- 백엔드 기능 보완중
    // 
    //
    // createParticipant: function ({dispatch}, conference_data) {
    //   console.log('&&&')
    //   http({
    //     method: "post",
    //     url: `/enter/createParticipant`,
    //     data: conference_data
    //   })
    //     .then((response) => {
    //       console.log(response);
    //       console.log(conference_data)
    //       dispatch('getConferenceInfo', conference_data.conferenceInfoDto.id);
    //     })
    //     .catch((error) => {
    //       console.log(conference_data)
    //       console.dir(error);
    //     });
    // },
    // 지난 회의 이력 조회
  },
};

export default conferenceStore;
