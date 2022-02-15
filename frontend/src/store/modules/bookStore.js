import http from "@/config/http-common.js";
import router from "@/router/index.js"
import Swal from "sweetalert2";

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
    
    // eslint-disable-next-line
    createUserBook: function ({commit}, data) {
      http({
      method: 'get',
      url: `/search/createUserBook?isbn=${data.isbn}userId=${data.userId}`,
      })
        .then((response) => {
          console.log(response);
          Swal.fire({
            icon: "success",
            text: "북마크가 추가되었습니다!",
          });
        })
        .catch((err) => {
          console.dir(err);
      });
    },

  },
};

export default bookStore;
