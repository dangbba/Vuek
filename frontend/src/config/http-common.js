import axios from "axios";
import store from "../store/index";

const axiosInstance = axios.create({
  baseURL: "https://localhost:8443/api/v1",
  //baseURL: "https://i6c205.p.ssafy.io:8443/api/v1",
  headers: {
    "Content-type": "application/json",
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    store.commit("startSpinner");
    return config;
  },
  (error) => {
    alert("데이터 요청 실패");
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => {
    store.commit("endSpinner");
    return response;
  },
  (error) => {
    alert("데이터 응답 실패");
    return Promise.reject(error);
  }
);

export default axiosInstance;
