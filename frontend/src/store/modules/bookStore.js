import http from "@/config/http-common.js";
import router from "@/router/index.js"
// import Swal from "sweetalert2";

const bookStore = {
  namespaced: true,
  state: {
    bookItems: [], // 책 검색 데이터 저장
    bookmark: [], // 읽은책 저장
  },
  mutations: {
    LOAD_BOOK_ITEMS: function (state, results) {
      state.bookItems = results
    },

  },
  actions: {
    LoadBookItems: function ({commit}) {
      http({
      method: 'get',
      url: `/books`,
      })
        .then((response) => {
          // console.log(response);
          commit('LOAD_book_ITEMS', response.data)
        })
        .catch((err) => {
          console.dir(err);
      });
    },
    searchBookItems : function ({commit}, search_value) {
      http({
        method: "get",
        url: `/search/naver?query=${search_value}`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_BOOK_ITEMS', response.data.items);
          router.push({
            path: `/book/search/`,
          });
        })
        .catch((error) => {
          console.dir(error);
        });
    },


    // 방 참여 및 참여 이력 생성
    // eslint-disable-next-line
    createHistory: function ({dispatch}, book_data) {
      http({
        method: "post",
        url: `/books/createHistory`,
        data: book_data
      })
        .then((response) => {
          console.log(response)
        })
        .catch((error) => {
          console.dir(error);
        });
    },
  },
};

export default bookStore;
