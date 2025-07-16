import instance from "@/request/request.js";

const EMP_PATH = "/emps"

const empService = {

  /**
   * find epmList by page
   * @param params query params
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findPage(params) {
    return instance.get(EMP_PATH, {params: params})
  },
  /**
   * save an emp
   * @param body empInfo
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  save(body) {
    return instance.post(EMP_PATH, body)
  },
  /**
   * delete emps by their IDs
   * @param empIds
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  deleteBatch(params) {
    return instance.delete(EMP_PATH, {params})
  },
  /**
   * find emp by empId
   * @param empId
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findByEmpId(empId) {
    return instance.get(`${EMP_PATH}/${empId}`)
  },
  /**
   * update an emp by its ID
   * @param body
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  updateById(body) {
    return instance.put(EMP_PATH, body)
  },
  /**
   * find all employees
   */
  findEmpList() {
    return instance.get(`${EMP_PATH}/list`)
  }
}

export default empService
