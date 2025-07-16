import instance from "@/request/request.js";

const CLAZZ_PATH = "/clazzs"

export const clazzService = {
  /**
   * 分页条件查询
   * @param params
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  findPage(params) {
    return instance.get(CLAZZ_PATH, {params})
  },
  /**
   * update a class info by its ID
   * @param body
   */
  updateById(body) {
    return instance.put(CLAZZ_PATH, body)
  },
  /**
   * save a class
   * @param body
   */
  save(body) {
    return instance.post(CLAZZ_PATH, body)
  },
  /**
   * find class info by its ID
   * @param clazzId
   */
  findById(clazzId) {
    return instance.get(`${CLAZZ_PATH}/${clazzId}`)
  },
  /**
   * delete a class by its ID
   * @param clzssId
   */
  deleteById(clzssId) {
    return instance.delete(`${CLAZZ_PATH}/${clzssId}`)
  },
  /**
   * find all class info
   */
  list() {
    return instance.get(`${CLAZZ_PATH}/list`)
  }
}