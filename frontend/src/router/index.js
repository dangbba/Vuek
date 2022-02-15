import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import Profile from "../components/user/Profile.vue";
import Signup from "../components/user/Signup.vue";
import Book from "../components/book/Book.vue";
import BookSearchResult from "../components/book/BookSearchResult.vue";
import Conference from "../components/conference/Conference.vue";
import Conference_detail from "../components/conference/Conference_detail.vue";
import store from "../store/index";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/book",
    name: "Book",
    component: Book,
  },
  {
    path: "/conference",
    name: "Conference",
    component: Conference,
  },
  {
    path: "/conference/view/:contentId",
    name: "Conference_detail",
    component: Conference_detail,
  },
  {
    path: "/book/search",
    name: "BookSearchResult",
    component: BookSearchResult,
  },
];

const router = new VueRouter({
  mode: "history",
  scrollBehavior() { 
    return { x: 0, y: 0 } 
  },
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  store.commit("startSpinner");
  setTimeout(() => {
    next();
  }, 30);
});

// eslint-disable-next-line
router.afterEach((to, from) => {
  store.commit("endSpinner");
});

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => {
    if (err.name != "NavigationDuplicated") throw err;
  });
};

export default router;
