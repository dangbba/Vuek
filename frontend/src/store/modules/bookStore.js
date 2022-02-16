import http from "@/config/http-common.js";
import router from "@/router/index.js"
import Swal from "sweetalert2";

const bookStore = {
  namespaced: true,
  state: {
    bookItems: [], // 책 검색 데이터 저장
    userBookmark: [], // 읽은책 조회시 저장
  },
  mutations: {
    LOAD_BOOK_ITEMS: function (state, results) {
      state.bookItems = results
    },
    LOAD_USER_BOOK_ITEMS: function (state, results) {
      state.userBookmark = results 
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
      console.log(data)
      http({
      method: 'post',
      url: `/search/createUserBook?isbn=${data.isbn}&userId=${data.userId}`,
      })
        .then((response) => {
          console.log(response);
          Swal.fire({
            icon: "success",
            text: "책갈피가 추가되었습니다!",
          });
        })
        .catch((err) => {
          console.dir(err);
          Swal.fire({
            icon: "warning",
            text: "이미 등록된 책갈피입니다!",
          });
      });
    },
    getUserBook : function ({commit}, user_id) {
      http({
        method: "get",
        url: `search/userBooks?userId=${user_id}`,
      })
        .then((response) => {
          console.log(response);
          commit('LOAD_USER_BOOK_ITEMS', response.data);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
  },
};

export default bookStore;
