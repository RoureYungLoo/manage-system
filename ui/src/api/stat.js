/* 数据统计 */

import instance from "@/request/request.js";

const STAT = {
  EMP: {
    JOB: "/report/empJobData",
    GENDER: "/report/empGenderData"
  },
  STU: {
    DEGREE: "/report/studentDegreeData",
    COUNT: "/report/studentCountData",
  }
}

const statService = {
  /**
   * query statistic data of emp's jobs
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  queryEmpJobStat() {
    return instance.get(STAT.EMP.JOB)
  },

  /**
   * query statistic data of emp's gender
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  queryEmpGender() {
    return instance.get(STAT.EMP.GENDER)
  },
  /**
   * query statistic data of students' degree
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  queryStuDegree() {
    return instance.get(STAT.STU.DEGREE)
  },
  /**
   * query statistic data of students' count
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  queryStuCount() {
    return instance.get(STAT.STU.COUNT)
  }


}

export default statService