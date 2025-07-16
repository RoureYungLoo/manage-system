<script setup>

import {ref} from "vue";
import {loginService} from "@/api/login.js";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const router = useRouter();
const form = ref({})
const login = async () => {
  const response = await loginService.login(form.value);
  if (response.code === 1) {
    localStorage.setItem("token", response.data["token"])
    localStorage.setItem("name", response.data["name"])
    ElMessage.success(response.msg)
    await router.push("/")
  } else {
    ElMessage.error(response.msg)
  }
}

const register = () => {
  // 暂未实现
}
</script>

<template>
  <el-container class="container">
    <el-main>
      <el-row>
        <el-col>
          <el-card style="width: 400px;margin: 0 auto" header-class="test">
            <template #header>
              <el-row justify="center">
                欢迎登录管理系统
              </el-row>
            </template>
            <el-form :model="form" label-width="60" style="max-width: 300px; margin: 0 auto">
              <el-form-item label="用户名">
                <el-input v-model="form.username" type="text" placeholder="请输入登录名" autocomplete="true"/>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="form.password" type="password" placeholder="请输入密码" autocomplete="true" @keyup.enter="login"/>
              </el-form-item>
              <el-form-item label="">
                <el-button @click="login" >登录</el-button>
                <el-button @click="register">注册</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>

    </el-main>
  </el-container>
</template>

<style scoped>
.container {
  height: 100dvh;
  background: url("https://images.xxapi.cn/images/wallpaper/0YbjIgHhQh1PU5mm5YnboqEf.jpg") center/cover no-repeat;
  align-items: center;
}

.test {
  display: flex;
  justify-content: center;
}
</style>