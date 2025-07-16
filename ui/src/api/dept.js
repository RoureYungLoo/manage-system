import instance from "@/request/request.js";

const DEPT_PATH = "/depts"

export const deptService = {
  /**
   * find all departments
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  list() {
    return instance.get(DEPT_PATH)
  },
  /**
   * save a department
   * @param body
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  save(body) {
    return instance.post(DEPT_PATH, body)
  },
  /**
   * delete a department by its ID
   * @param deptId
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  deleteById(deptId) {
    return instance.delete(`${DEPT_PATH}/${deptId}`)
  },
  /**
   * update a department by its ID
   * @param body
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  updateById(body) {
    return instance.put(DEPT_PATH, body)
  },
  /**
   * find a department by its ID
   * @param deptId
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findById(deptId) {
    return instance.get(`${DEPT_PATH}/${deptId}`)
  },
  /**
   * find departments data by query params and page params
   * @param params
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findPage(params) {
    return instance.get(`${DEPT_PATH}/page3`, {params})
  }
}