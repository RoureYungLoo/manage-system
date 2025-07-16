<script setup>
import {
  Histogram,
  HomeFilled,
  List,
  Location,
  Management,
  Menu,
  Setting,
  Stamp,
  SwitchButton,
  Edit,
  User, Tools, Avatar, PieChart, TrendCharts, Flag
} from "@element-plus/icons-vue";

const handleOpen = (key, path) => {
  console.log(key, path)
}

const handleClose = (key, path) => {
  console.log(key, path)
}

import {onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";

const url = "https://v2.xxapi.cn/api/wallpaper"
const imgUrl = ref("https://images.xxapi.cn/images/wallpaper/pw8MHsDfqdxTobPjEbiNWihI.jpg")

onMounted(() => {
  loadImg()
})

const loadImg = async () => {
  const response = await axios(
      {
        url,
        method: "GET"
      }
  );
  imgUrl.value = response.data.data
}

const change = () => {
  loadImg()
}

const router = useRouter();
/* 退出登录 */
const logout = () => {
  ElMessageBox.confirm("真的要退出登录吗?", "提示", {type: "info"})
      .then(() => {
        localStorage.removeItem("token")
        console.log("localStorage.removeItem(\"token\")")
        router.replace("/login")
      }).catch(() => {
    ElMessage.info("用户取消登录")
  })
}

/* 登录用户的用户名 */
const name = localStorage.getItem("name");

/* 点击首页 */
const handleIndex = () => {
  location.reload()
}
</script>

<template>
  <el-container>
    <el-header>
      <el-row class="header">
        <el-col :span="10" @click="handleIndex" style="cursor: pointer">管理系统</el-col>
        <el-col :span="14" style="text-align: right">
          <el-link class="link" :icon="SwitchButton" underline="never" @click="logout" style="cursor: pointer">
            退出登录
          </el-link>
          <el-link class="link" :icon="Edit" underline="never">修改密码</el-link>
          <el-link class="link" :icon="User" underline="never">{{ name }}</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu @open="handleOpen"
                 @close="handleClose" router>
          <el-menu-item index="/index" @click="change">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            首页
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Menu/>
              </el-icon>
              班级管理
            </template>
            <el-menu-item index="/class">
              <el-icon>
                <Management/>
              </el-icon>
              班级管理
            </el-menu-item>
            <el-menu-item index="/stu">
              <el-icon>
                <List/>
              </el-icon>
              学员管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Tools/>
              </el-icon>
              系统管理
            </template>
            <el-menu-item index="/dept">
              <el-icon>
                <Stamp/>
              </el-icon>
              部门管理

            </el-menu-item>
            <el-menu-item index="/emp">
              <el-icon>
                <Avatar/>
              </el-icon>
              员工管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <histogram/>
              </el-icon>
              数据统计
            </template>
            <el-menu-item index="/empstat">
              <el-icon>
                <TrendCharts/>
              </el-icon>
              员工统计
            </el-menu-item>
            <el-menu-item index="/stustat">
              <el-icon>
                <TrendCharts/>
              </el-icon>
              学生统计
            </el-menu-item>
            <el-menu-item index="/logstat">
              <el-icon>
                <TrendCharts/>
              </el-icon>
              日志统计
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <RouterView/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.header {
  background: linear-gradient(to right, lightcoral, #5d8bda, dodgerblue);
  padding: 0 10px;
  line-height: 60px;
  font-size: 30px;
  color: white;
  font-family: '楷体', serif;
  font-weight: 550;
}

.link {
  color: white;
  font-family: '黑体', serif;
  margin: 0 5px;
  font-size: 16px;
}
</style>