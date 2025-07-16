import instance from "@/request/request.js";

const LOGIN_PATH = "/login"

export const loginService = {
  /**
   * login
   * @param body login info object
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  login(body) {
    return instance.post(LOGIN_PATH, body)
  }
}
