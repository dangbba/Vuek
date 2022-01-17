import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import Naver from "../components/user/NaverLogin.vue";
import CallBack from "../components/user/CallBack.vue";
import Profile from "../components/user/Profile.vue";
import Board from "../components/board/Board.vue";
import Book from "../components/book/Book.vue";
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
    path: "/login",
    name: "Naver",
    component: Naver,
  },
  {
    path: "/login/naver",
    name: "CallBack",
    component: CallBack,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
  },
  {
    path: "/book",
    name: "Book",
    component: Book,
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
