<script setup>

import {computed, onMounted, reactive, ref, useTemplateRef, watch, watchEffect} from "vue";
import empService from "@/api/emp.js";
import {deptService} from "@/api/dept.js";
import {Delete, Plus, UploadFilled} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import emp from "@/api/emp.js";

/* 分页条件查询参数 */
const formData = ref({
  name: "",
  gender: null,
  begin: "",
  end: "",
  page: 1,
  pageSize: 10,
})

/* 分页条件查询 */
const fetchData = () => {
/*  const response = await empService.findPage(formData.value);
  empData.data.records = response.data.rows
  empData.data.total = response.data.total*/
  empService.findPage(formData.value)
      .then((res) => {
        empData.data.records = res.data.rows
        empData.data.total = res.data.total
      })
      .catch((err) => {
        console.log(err)
        return Promise.reject(err)
      })
}

/* 监听formData */
// watchEffect(() => {
//   fetchData()
// })

/* 日期选择器 */
const datePicker = ref([])
/* 监听器 */
watchEffect(() => {
  if (datePicker.value !== null && datePicker.value.length === 2) {
    formData.value.begin = datePicker.value[0]
    formData.value.end = datePicker.value[1]
  } else if (datePicker.value == null) {
    formData.value.begin = ""
    formData.value.end = ""
  }
  // fetchData()
})
// =======================分页相关=======================
/* 切换页大小 */
const handleSizeChange = (size) => {
  formData.value.pageSize = size
  fetchData()
}

/* 翻页 */
const handleCurrentChange = (pageNo) => {
  formData.value.page = pageNo
  fetchData()
}
/* 生成页大小 */
const pageSizes = () => {
  let sizes = []
  for (let i = 0; i < 100;) {
    i += 5
    sizes.push(i)
  }
  return sizes
}

/* 查询 */
const handleSelect = () => {
  // console.log(formData.value)
  fetchData()
}

/* 重置查询条件 */
const handleReset = () => {
  formData.value = {
    name: "",
    gender: null,
    begin: "",
    end: "",
    page: 1,
    pageSize: 10,
  }
  datePicker.value = []
  fetchData()
}

/* 搜索栏日期组件 */
/*const handleDatePicker = () => {
  if (datePicker.value) {
    formData.value.begin = datePicker.value[0]
    formData.value.end = datePicker.value[1]
  } else {
    console.log("datePicker.value is null")
  }
}*/

/* 员工数据 */
const empData = reactive({
  data: {
    records: [],
    total: 0
  },
})

/* 部门数据 */
const deptList = ref([])
/* 获取部门数据 */
const fetchDeptList = async () => {
  const res = await deptService.list();
  deptList.value = res.data
}

/* 表格渲染映射 */
const gender = {
  [1]: "男",
  [2]: "女",
}

const job = {
  [1]: "班主任",
  [2]: "讲师",
  [3]: "学工主管",
  [4]: "教研主管",
  [5]: "咨询师",
}

/* 是否可以被中 */
const selectable = (row, index) => {
  // if (row.id === 200) return false
  return true
}

/* 下拉选择框 */
const genderOptions = [
  {value: 1, label: "男",},
  {value: 2, label: "女",}]

const jobOptions = [
  {value: 1, label: "班主任"},
  {value: 2, label: "讲师"},
  {value: 3, label: "学工主管"},
  {value: 4, label: "教研主管"},
  {value: 5, label: "咨询师"},
]

const OpType = ref("添加")
/* 添加员工 */
const labelWidth = ref(80)
const dialogVisible = ref(false)
const title = computed(() => `${OpType.value}员工`)


const handleAdd = () => {
  dialogVisible.value = true
  OpType.value = "添加"
}

/* 添加员工表单数据 */
const empFormData = reactive({
  id: null,
  image: "",
  username: "",
  name: "",
  gender: null,
  job: null,
  entryDate: "",
  deptId: null,
  phone: "",
  salary: null,
  exprList: [
    // {
    //   id: 78,
    //   company: "百度科技股份有限公司",
    //   job: "java开发",
    //   begin: "2012-07-01",
    //   end: "2019-03-03",
    //   /* 编辑员工回显日期 */
    //   // dateRange:["2012-07-01","2019-03-03"]
    // },
  ]
})

/* 添加工作经历 */
const addEmpExpr = () => {
  empFormData.exprList.push({
    id: null,
    company: "",
    job: "",
    begin: "",
    end: "",
    dateRange: []
  })
  console.log(empFormData)
}

/* 工作经历日期处理 */
const onEmpExprDateRangeChange = (expr) => {
//   if (expr.dateRange && expr.dateRange.length === 2) {
//     expr.begin = expr.dateRange[0]
//     expr.end = expr.dateRange[1]
//   } else {
//     expr.begin = ""
//     expr.end = ""
//   }
//   console.log(expr)
}

/* watch 工作经历 */
watchEffect(() => {
  if (empFormData.exprList != null && empFormData.exprList.length !== 0) {
    empFormData.exprList.forEach(expr => {
      /* 日期处理 */
      if (expr.dateRange && expr.dateRange.length === 2) {
        expr.begin = expr.dateRange[0]
        expr.end = expr.dateRange[1]
      } else {
        expr.begin = ""
        expr.end = ""
      }
    })
  }
})

/* 删除工作经历 */
const removeEmpExpr = (index) => {
  console.log(empFormData.exprList)
  // empFormData.exprList = empFormData.exprList.filter((v, i) => i !== index ? v : null)
  empFormData.exprList.splice(index, 1)
}

/* 头像上传请求头 */
const token = ref(null)
const headers = {
  token: token.value
}
watch(token, (newToken) => {
  headers.token = newToken
})
/* 头像上传成功 */
const handleAvatarSuccess = (res, uploadFile) => {
  empFormData.image = res.data
}

/* 头像上传之前 */
const beforeAvatarUpload = (rawFile) => {
  console.log("rawFile", rawFile)
  console.log(`image size ${rawFile.size / 1024} KB`)
  console.log(`image type ${rawFile.type}`)
}

/* 提交 */
const handleSubmit = () => {
  console.log(empFormData)
  /* 校验表单 */
  empFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      let res;
      if (empFormData.id) { // 编辑员工
        res = await empService.updateById(empFormData)
      } else {// 新增员工
        res = await empService.save(empFormData)
      }
      console.log("==> handleSubmit", res);
      if (res.code === 1) {
        ElMessage.success(`${OpType.value}成功`)
        fetchData()
        handleCancel()
      } else {
        ElMessage.error(`${OpType.value}失败`)
      }
    } else {
      console.log("表单校验失败")
    }
  })
}

/* 取消 */
const handleCancel = () => {
  dialogVisible.value = false
  empFormData.image = ""
  empFormData.username = ""
  empFormData.name = ""
  empFormData.gender = null
  empFormData.job = null
  empFormData.entryDate = ""
  empFormData.deptId = null
  empFormData.phone = ""
  empFormData.salary = null
  empFormData.exprList = []
}

/* 编辑员工 */
const handleEdit = async (empId) => {
  dialogVisible.value = true
  OpType.value = "编辑"
  const res = await empService.findByEmpId(empId);
  const emp = res.data;
  empFormData.id = emp.id
  empFormData.image = emp.image
  empFormData.username = emp.username
  empFormData.name = emp.name
  empFormData.gender = emp.gender
  empFormData.job = emp.job
  empFormData.entryDate = emp.entryDate
  empFormData.deptId = emp.deptId
  empFormData.phone = emp.phone
  empFormData.salary = emp.salary

  /* 日期回显 */
  if (emp.exprList) {
    emp.exprList.forEach(expr => expr.dateRange = [expr.begin, expr.end])
  }
  empFormData.exprList = emp.exprList
}

/* 待删除的员工ID */
const ids = ref([])
const selectionChange = (emps) => {
  ids.value = emps.map(emp => emp.id)
}

/* 批量删除员工 */
const handleDeleteBatch = async (params) => {
  /* 处理单个删除按钮 */
  if (params && params.length === 1) {
    ids.value = params
  }
  if (ids.value.length === 0) {
    await ElMessageBox.alert("请选择要删除的员工", "提示", {
      confirmButtonText: "确定",
      type: "warning"
    })
    return
  }
  ElMessageBox.confirm(
      '删除后不可恢复, 是否继续?',
      '删除警告',
      {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    /* 确定删除 */
    const response = await empService.deleteBatch({
      ids: ids
    });
    if (response.code === 1) {
      ElMessage.success("删除成功")
      fetchData()
    } else {
      ElMessage.error("删除失败")
    }
  }).catch(() => {
    /* 取消删除 */
    ids.value = []
    ElMessage({
      type: 'info',
      message: '用户取消操作',
    })
  })
}

/* 表单校验规则 */
const empFormRef = useTemplateRef("empFormRef");
const rules = {
  username: [
    {required: true, message: "用户名必填", trigger: "blur"},
    {min: 2, max: 20, message: "用户名长度为2-20", trigger: "blur"},

  ],
  name: [
    {required: true, message: "姓名必填", trigger: "blur"},
    {min: 2, max: 10, message: "姓名长度为2-10", trigger: "blur"},
  ],
  gender: [
    {required: true, message: "性别必填", trigger: "blur"}
  ],
  phone: [
    {required: true, message: "手机号必填", trigger: "blur"},
    {pattern: /^1\d{10}$/g, message: "手机号格式不正确", trigger: "blur"}
  ],
  // job: [
  //   {required: true, message: "职位必填", trigger: "blur"},
  //   {min: 2, max: 50, message: "职位长度为2-50", trigger: "blur"},
  // ],
  // company: [
  //   {required: true, message: "公司必填", trigger: "blur"},
  //   {min: 2, max: 50, message: "公司长度为2-50", trigger: "blur"},
  // ]
}

/* 生命周期 */
onMounted(async () => {
  fetchData()
  await fetchDeptList()
  token.value = localStorage.getItem("token")
  if (!token.value) {
    console.log("图片上传请求头获取失败")
  }
})
</script>
<template>
  <!-- 条件分页查询 -->
  <el-row>
    <el-form inline>
      <el-form-item label="姓名" label-width="40">
        <el-input type="text" v-model="formData.name" style="width: 200px" clearable/>
      </el-form-item>
      <el-form-item label="性别" label-width="40">
        <el-select
            v-model="formData.gender"
            clearable
            placeholder="性别"
            style="width: 100px"
        >
          <el-option
              v-for="item in genderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker
            v-model="datePicker"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
        />
        <!-- @change="handleDatePicker" -->
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSelect">查询</el-button>
        <el-button @click="handleReset">清空</el-button>
      </el-form-item>
    </el-form>
  </el-row>

  <!-- 添加员工和批量删除 -->
  <el-row>
    <el-button type="primary" @click="handleAdd">添加员工</el-button>
    <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
  </el-row>

  <!-- 员工数据表格 -->
  <el-table @selection-change="selectionChange" :data="empData.data.records" :header-cell-style="{textAlign: 'center'}"
            :cell-style="{ textAlign: 'center' }">
    <el-table-column type="selection" :selectable="selectable" width="30"/>
    <el-table-column prop="id" label="员工编号" width="100"/>
    <el-table-column prop="username" label="登录账户" width="80"/>
    <el-table-column prop="password" label="密码" width="100"/>
    <el-table-column prop="name" label="员工姓名" width="80" fixed="left"/>
    <el-table-column label="员工性别" width="80">
      <template v-slot="{row}">
        {{ gender[row.gender] }}
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="手机" width="120"/>
    <el-table-column label="职位" width="80">
      <template #default="{row}">
        {{ job[row.job] }}
      </template>
    </el-table-column>
    <el-table-column prop="salary" label="工资" width="140"/>
    <el-table-column label="头像" width="60">
      <template #default="{row}">
        <el-image :src="row.image" style="width: 50px"/>
      </template>
    </el-table-column>
    <!--    <el-table-column prop="deptId" label="部门ID" width="80"/>-->
    <el-table-column prop="deptName" label="部门名称" width="80"/>
    <el-table-column prop="entryDate" label="入职时间" width="100"/>
    <el-table-column prop="createTime" label="创建时间" width="160"/>
    <el-table-column prop="updateTime" label="更新时间" width="160"/>
    <el-table-column label="操作" width="160" fixed="right">
      <template #default="{row}">
        <el-button type="primary" @click="handleEdit(row.id)" text>编辑</el-button>
        <el-button type="danger" @click="handleDeleteBatch([row.id])" text>删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <br>
  <!-- 分页组件 -->
  <el-pagination
      v-model:current-page="formData.page"
      v-model:page-size="formData.pageSize"
      :page-sizes="pageSizes()"
      :size="'default'"
      :disabled="false"
      :background="true"
      layout="total, sizes, prev, pager, next, jumper"
      :total="empData.data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"/>

  <!-- 添加员工 -->
  <el-dialog align-center v-model="dialogVisible" :title="title" width="700" @close="handleCancel">
    <el-form :model="empFormData" :rules="rules" ref="empFormRef">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名" :label-width="labelWidth" prop="username">
            <el-input v-model="empFormData.username" placeholder="请输入员工用户名,2-20个字"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" :label-width="labelWidth" prop="name">
            <el-input v-model="empFormData.name" placeholder="请输入员工姓名,2-10个字"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="性别" :label-width="labelWidth" prop="gender">
            <el-select
                v-model="empFormData.gender"
                clearable
                placeholder="请选择性别">
              <el-option
                  v-for="item in genderOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" :label-width="labelWidth" prop="phone">
            <el-input v-model="empFormData.phone" placeholder="请输入员工手机号"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="职位" :label-width="labelWidth">
            <el-select
                v-model="empFormData.job"
                clearable
                placeholder="请选择职位">
              <el-option
                  v-for="item in jobOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资" :label-width="labelWidth">
            <el-input v-model.number="empFormData.salary" placeholder="请输入员工薪资"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="所属部门" :label-width="labelWidth">
            <el-select
                v-model="empFormData.deptId"
                clearable
                placeholder="请选择部门">
              <el-option
                  v-for="dept in deptList"
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期" :label-width="labelWidth">
            <el-date-picker
                v-model="empFormData.entryDate"
                type="date"
                placeholder="请选择入职日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="头像" :label-width="labelWidth">
            <el-upload
                action="/api/upload"
                :headers="headers"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :show-file-list="false">
              <el-avatar v-if="empFormData.image" shape="square" :size="100" fit="cover" :src="empFormData.image"/>
              <el-icon v-else :size="80">
                <UploadFilled/>
              </el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="工作经历" :label-width="labelWidth">
            <el-button type="success" size="small" @click="addEmpExpr">
              <el-icon>
                <Plus/>
              </el-icon>
              添加工作经历
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-for="(empExpr,index) in empFormData.exprList" :key="empExpr.id">
        <el-col :span="10">
          <el-form-item size="small" label="时间" :label-width="labelWidth">
            <el-date-picker
                v-model="empExpr.dateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                @change="onEmpExprDateRangeChange(empExpr)"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item size="small" label="公司" :label-width="50" prop="company">
            <el-input v-model="empExpr.company" type="text" placeholder="请输入公司名称"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item size="small" label="职位" :label-width="50" prop="job">
            <el-input v-model="empExpr.job" type="text" placeholder="请输入职位名称"/>
          </el-form-item>
        </el-col>
        <el-col :span="2">
          <el-form-item :label-width="10">
            <el-button size="small" type="danger" @click="removeEmpExpr(index)">
              <el-icon>
                <Delete/>
              </el-icon>
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row justify="space-evenly">
      <el-button type="primary" @click="handleSubmit">保存</el-button>
      <el-button type="info" @click="handleCancel">取消</el-button>
    </el-row>
  </el-dialog>
</template>

<style scoped>

table {
  border-collapse: collapse;
}

table tr, th, td {
  border: 1px solid black;
}

</style>