import Vue from "vue";
import Vuex from "vuex";
import mutations from "../store/mutaition";
import createPersistedState from "vuex-persistedstate";

import userStore from "@/store/modules/userStore.js";

Vue.use(Vuex);

const state = {
  LoadingStatus: false
}


const store = new Vuex.Store({
  modules: {
    userStore,
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
