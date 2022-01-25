import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueProfile from "vue-profile";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import ReactiveSearch from "@appbaseio/reactivesearch-vue";

Vue.use(VueProfile, "vue-profile");
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(ReactiveSearch);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
