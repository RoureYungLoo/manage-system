import instance from "@/request/request.js";

const STU_PATH = "/students"

export const stuService = {
  /**
   * find page data of student by query params
   * @param params
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findPage(params) {
    return instance.get(STU_PATH, {params})
  },
  /**
   * delete students whose id in array of deleteIds
   * @param deleteIds
   */
  deleteBatch(deleteIds) {
    return instance.delete(`${STU_PATH}/${deleteIds}`)
  },
  /**
   * save a student
   * @param body
   */
  save(body) {
    return instance.post(STU_PATH, body)
  },
  /**
   *
   * @param stuId
   */
  findById(stuId) {
    return instance.get(`${STU_PATH}/${stuId}`)
  },
  /**
   *
   * @param body
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  updateById(body) {
    return instance.put(STU_PATH, body)
  },
  /**
   * update student's violation by its ID
   * @param id
   * @param score
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  violation({id, score}) {
    return instance.put(`${STU_PATH}/violation/${id}/${score}`)
  }
}