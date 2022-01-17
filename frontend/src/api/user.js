import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  const userDto = { userId: user["user_id"], userPw: user["user_pw"] };
  await api
    .post(`/user/login`, JSON.stringify(userDto))
    .then.success.catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}
export { login, findById };
