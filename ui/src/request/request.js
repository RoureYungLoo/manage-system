import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router/router.js";

const instance = axios.create({
  baseURL: "/api",
  // headers: {
  //   "token": "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5a6L5rGfIiwiaWQiOjEsImV4cCI6MTc1MTkxMjQ1NiwiaWF0IjoxNzUxODc2NDU2LCJ1c2VybmFtZSI6InNvbmdqaWFuZyJ9.X8glVFqQJJ1pgoQlFaFjZzIGbq1-Oudfrt582mD8kDc"
  // }
});

instance.interceptors.request.use(function (config) {
  config.headers["token"] = localStorage.getItem("token")
  // console.log("localStorage.getItem(\"token\"): ", localStorage.getItem("token"))
  // console.log(config)
  return config;
}, function (error) {
  return Promise.reject(error);
});

instance.interceptors.response.use(function (response) {
  let data = response.data;
  console.log("服务端响应: ", data)
  if (data.code === 0) {
    ElMessage.error(data.msg)
  } else if (data.code === 1) {
    return Promise.resolve(data)
  }
  // return data;
}, function (error) {
  const response = error.response;
  if (response.status === 401) {
    ElMessage({
      type: "error",
      message: "请登录"
    })
    router.replace("/login")
  } else if (response.status >= 400 && response.status < 500) {
    console.log("客户端异常", error)
  } else if (response.status >= 500) {
    console.log("服务端异常", error)
  }
  return Promise.reject(error);
});


export default instance