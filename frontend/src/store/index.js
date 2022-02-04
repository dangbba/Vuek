import Vue from "vue";
import Vuex from "vuex";
import mutations from "../store/mutaition";
import createPersistedState from "vuex-persistedstate";

import userStore from "@/store/modules/userStore.js";
import conferenceStore from "@/store/modules/conferenceStore.js";

Vue.use(Vuex);

const state = {
  LoadingStatus: false
}


const store = new Vuex.Store({
  modules: {
    userStore,
    conferenceStore,
  },
  state,
  mutations,
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

export default store;
