<script setup>

import { onMounted, reactive, ref } from "vue";
import axios from "axios";
import empService from "@/api/emp.js";

const url = "http://localhost:5173/api/admin/employee/page"

const formData = ref({
  name: "",
  gender: null,
  begin: "",
  end: "",
  page: 1,
  pageSize: 10,
})

// const empList = ref([])
const empData = reactive({
  data: {
    records: [],
    total: 0
  },
})

// const fetchData = async () => {
//   const res = await axios(
//       {
//         url,
//         params: formData.value,
//         headers: {
//           "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiaWQiOjEsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE3NTMwNzAwNzV9.K19cR_B3fionpu8xagQ66SFjvFFVeK5do1u83emw7fA"
//         }
//
//       }
//   )
//   console.log(res.data.data)
//   // empList.value = res.data.data
//   // empList = res.data.data
//   empData.data = res.data.data
//
//   console.log("empData:===> ", empData.data.records)
//
//   // for (const a of empList) {
//   //   console.log("------> ",a)
//   //
//   // }
// }

const fetchData = async () => {
  const response = await empService.findPage(formData.value);
  console.log(response.data.data)
  empData.data.records = response.data.data.rows
  empData.data.total = response.data.data.total
}

const gender = {
  ['0']: "女",
  ['1']: "男",
}

const job = {
  ["1"]: "班主任",
  ["2"]: "讲师",
  ["3"]: "其他",
}

onMounted(async () => {
  await fetchData()
})

const change = (param) => {
  console.log("change----> ", param.map((v, i) => {
    return v.id
  }))
}

const selectable = (row, index) => {
  if (row.id === 200) return false
  return true
}

const pageData = reactive({
  pageNo: 1,
  pageSize: 10,
  total: 20
})

const handleSizeChange = (size) => {
  console.log(pageData)
}

const handleCurrentChange = (pageNo) => {
  console.log(pageData)
}
</script>
<template>

  <!--  <el-row>-->
  <!--    <el-col :span="2">-->
  <!--      <h3>管理系统</h3>-->
  <!--      <el-menu>-->

  <!--        <el-menu-item >员工管理</el-menu-item>-->
  <!--        <el-menu-item >部门管理</el-menu-item>-->
  <!--        <el-menu-item >用户管理</el-menu-item>-->


  <!--      </el-menu>-->
  <!--    </el-col>-->
  <!--  </el-row>-->
  <!--  <table>-->
  <!--    <thead>-->
  <!--    <tr>-->
  <!--      <th>编号</th>-->
  <!--      <th>姓名</th>-->
  <!--      <th>性别</th>-->
  <!--      <th>职位</th>-->
  <!--      <th>头像</th>-->
  <!--      <th>入职日期</th>-->
  <!--      <th>更新时间</th>-->
  <!--    </tr>-->
  <!--    </thead>-->
  <!--    <tbody>-->
  <!--    <tr v-for="emp in empList" :key="emp.id">-->
  <!--      <th>{{ emp.id }}</th>-->
  <!--      <th>{{ emp.name }}</th>-->
  <!--      <th>{{ gender[emp.gender] }}</th>-->
  <!--      <th>{{ job[emp.job] }}</th>-->
  <!--      <th>-->
  <!--        <img :src="emp.image" width="50"/>-->
  <!--      </th>-->
  <!--      <th>{{ emp.entrydate }}</th>-->
  <!--      <th>{{ emp.updatetime }}</th>-->
  <!--    </tr>-->
  <!--    </tbody>-->
  <!--  </table>-->

  <el-table @selection-change="change" :data="empData.data.records || []" :header-cell-style="{ textAlign: 'center' }"
    :cell-style="{ textAlign: 'center' }">
    <el-table-column type="selection" :selectable="selectable" width="30" />
    <el-table-column prop="id" label="员工编号" width="80" />
    <el-table-column prop="username" label="登录账户" width="80" />
    <el-table-column prop="name" label="员工姓名" width="80" />
    <el-table-column label="员工性别" width="80">
      <template v-slot="{ row }">
        {{ gender[row.sex] }}
      </template>
    </el-table-column>
    <el-table-column prop="password" label="密码" />
    <el-table-column prop="idNumber" label="身份证号码" />
    <el-table-column prop="phone" label="手机" />
    <!--    <el-table-column label="员工职位" width="80">-->
    <!--      <template v-slot="{row}">-->
    <!--        {{ job[row.job] }}-->
    <!--      </template>-->
    <!--    </el-table-column>-->
    <!--    <el-table-column label="头像" width="80">-->
    <!--      <template #default="{row}">-->
    <!--        &lt;!&ndash;        <el-image :src="row.image" style="width: 50px"/>&ndash;&gt;-->
    <!--        <img :src="row.image" width="50"/>-->
    <!--      </template>-->
    <!--    </el-table-column>-->
    <!--    <el-table-column prop="entrydate" label="入职日期" width="100"/>-->
    <el-table-column prop="createTime" label="创建时间" width="140" />
    <el-table-column prop="updateTime" label="更新时间" width="140" />
    <el-table-column prop="createUser" label="创建人ID" width="90" />
    <el-table-column prop="updateUser" label="更新人ID" width="90" />
    <el-table-column prop="status" label="账户状态" width="90" />
    <el-table-column label="操作" width="160">
      <el-button type="primary">编辑</el-button>
      <el-button type="danger">删除</el-button>
    </el-table-column>
  </el-table>
  <el-pagination v-model:current-page="pageData.pageNo" v-model:page-size="pageData.pageSize"
    :page-sizes="[10, 20, 30, 40]" :size="'default'" :disabled="false" :background="true"
    layout="total, sizes, prev, pager, next, jumper" :total="empData.data.total" @size-change="handleSizeChange"
    @current-change="handleCurrentChange" />
</template>

<style scoped>
table {
  border-collapse: collapse;
}

table tr,
th,
td {
  border: 1px solid black;
}

/* 自定义样式，确保内容垂直居中 */
el-table .custom-cell {
  vertical-align: middle;
  /* 垂直居中 */
  text-align: center;
  /* 水平居中（可选） */
}
</style>