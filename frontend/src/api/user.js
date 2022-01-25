import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  const userDto = {
    user_id: user["user_id"],
    password: user["user_pw"],
  };
  await api
    .post(`/user/auth/login`, JSON.stringify(userDto))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}
export { login, findById };
