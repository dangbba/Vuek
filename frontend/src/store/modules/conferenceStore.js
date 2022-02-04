import http from "@/config/http-common.js";

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
      if (results.length == 0){
        state.conferenceItems = false;
      } else {
        state.conferenceItems = results.sort((a, b) => {
          return b.id - a.id;
        }); // 내림차순으로 보여줌
      }
    },
    LOAD_CONFERENCE_DETAIL: function (state, results) {
      state.conferenceDetail = results;
      // console.log(state.conferenceDetail)
    },
    LOAD_CONFERENCE_HISTORY: function (state, results) {
      state.conferenceHistory = results;
      // console.log(state.conferenceHistory)
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
          console.log(response);
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
        url: `/conferences/getConferenceByCategory?categoryType=${conference_type}`,
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
          console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
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
          console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
        });
    },
    // 방 생성
    createRoom: function ({dispatch}, room_data) {
      http({
        method: "post",
        url: `/conferences`,
        data: room_data
      })
        .then((response) => {
          console.log(response);
          dispatch('LoadConferenceItems'); //임시... 생성된 방으로 이동해야하나 pk값 얻어올 수 없어 일단 방목록으로 이동
        })
        .catch((error) => {
          console.dir(error);
          alert('컨퍼런스 룸 생성에 실패했습니다.')
        });
    },
    // 방 카테고리 조회
    getConferenceCategories: function ({commit}) {
      http({
        method: "get",
        url: `/conferences/conference-categories`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_CONFERENCE_CATEGORY', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 세부정보 조회
    getConferenceInfo: function ({commit}, conference_id) {
      console.log('store')
      console.log(conference_id)
      http({
        method: "get",
        url: `/conferences/conference-info/${conference_id}`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_CONFERENCE_DETAIL', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 종료 // 파라미터 있음..?
    conferenceClose: function ({dispatch}, conference_id) {
      http({
        method: "post",
        url: `/conferences/close/${conference_id}`,
        data: {
          id: conference_id // ???? 파라미터 왜 필요한지? 왜 is_active = 0으로 안바뀌는지 => 백엔드에 수정 요청함
        }
      })
        .then((response) => {
          console.log(response);
          dispatch('LoadConferenceItems');
          alert("회의가 종료되었습니다.")
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    // 방 정보 수정
    conferenceUpdate: function ({dispatch}, conference_data) {
      http({
        method: "put",
        url: `/conferences/conference-info/${conference_data.id}`,
        data: conference_data
      })
        .then((response) => {
          console.log(response);
          dispatch('getConferenceInfo', conference_data.id);
        })
        .catch((error) => {
          console.dir(error);
        }); //왜 is_active = 0으로 바뀌는지..? -> 종료할 때 바뀌고 수정할 때는 안바뀌어야 함 => 백엔드에 수정 요청함
    },
    // 방 참여 및 참여 이력 생성
    createHistory: function ({dispatch}, conference_data) {
      http({
        method: "post",
        url: `/enter/createHistory`,
        data: conference_data
      })
        .then((response) => {
          console.log(response)
          dispatch('conferenceInfo');
        })
        .catch((error) => {
          console.log(conference_data)
          console.dir(error);
        });
    },
    ///////////////////////////////////////////////////////////////////
    // 방 참여 (?)
    createParticipant: function ({dispatch}, conference_data) {
      http({
        method: "post",
        url: `/enter/createParticipant`,
        data: conference_data
      })
        .then((response) => {
          console.log(response);
          dispatch('conferenceInfo');
        })
        .catch((error) => {
          console.log(conference_data)
          console.dir(error);
        });
    },
    // 지난 회의 이력 조회
    getConferenceHistory: function ({commit}) {
      http({
        method: "get",
        url: `/conferences/getConferenceHistory`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_CONFERENCE_HISTORY', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    }
  },
};

export default conferenceStore;
