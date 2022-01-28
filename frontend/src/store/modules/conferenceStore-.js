import http from "@/config/http-common.js";

const conferenceStore = {
  namespaced: true,
  state: {
    conferenceItems: []
  },
  mutations: {
    LOAD_CONFERENCE_ITEMS: function (state, results) {
      if (results.length == 0){
        state.conferenceItems = false;
      } else {
      state.conferenceItems = results;
      }
    },

  },
  actions: {
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
          console.log(err);
      });
    },
    getConferenceByCategory : function ({commit}, conference_type) {
      http({
      method: 'get',
      url: `/conferences/getConferenceByCategory?categoryType=${conference_type}`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
          console.log(err);
          console.log(conference_type)
        });
    },
    getConferenceBySort : function ({commit}, sort_option) {
      console.log(sort_option)
      http({
      method: 'get',
      url: `/conferences/getConferenceBySort?asc=${sort_option}`,
      })
        .then((response) => {
          console.log('ok')
          console.log(response);
          commit('LOAD_CONFERENCE_ITEMS', response.data);
        })
        .catch((err) => {
          console.dir(err);
          console.log(err);
        });
    },
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
          console.log(err);
          console.log(search_value)
        });
    },
  },
};

export default conferenceStore;
