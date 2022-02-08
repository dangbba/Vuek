import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  const userDto = {
    userId: user["user_id"],
    password: user["user_pw"],
  };
  await api
    .post(`/auth/login`, JSON.stringify(userDto))
    .then(success)
    .catch(fail);
}

async function findById(userId, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/users/me/${userId}`).then(success).catch(fail);
}
export { login, findById };
