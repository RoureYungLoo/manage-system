<script setup>


import {computed, onMounted, reactive, ref, useTemplateRef, watchEffect} from "vue";
import empService from "@/api/emp.js";
import {clazzService} from "@/api/clazz.js";
import {ElMessage, ElMessageBox} from "element-plus";


const labelWidth = 80
const dialogVisible = ref(false)
const title = ref("新增")
const operationType = ref('add')
const clazzData = reactive({
  total: 0,
  rows: []
})

/* 员工数据 */
const empData = reactive([])

const subjectData = [
  {value: 1, name: "Java"},
  {value: 2, name: "前端"},
  {value: 3, name: "大数据"},
  {value: 4, name: "Python"},
  {value: 5, name: "Go"},
  {value: 6, name: "嵌入式"},
]
const dateRange = ref([])
const begin = computed(() => {
  if (dateRange.value && dateRange.value.length === 2) {
    return dateRange.value[0]
  } else {
    return ""
  }
})
const end = computed(() => {
  if (dateRange.value && dateRange.value.length === 2) {
    return dateRange.value[1]
  } else {
    return ""
  }
})

const queryParam = reactive({
  name: "",
  begin: "",
  end: "",
  page: 1,
  pageSize: 10
})

const pageSizes = reactive(function () {
  const arr = []
  for (let i = 0; i < 100;) {
    i += 5
    arr.push(i)
  }
  return arr
}())

const clazzForm = reactive({
  id: null,
  name: null,
  room: null,
  beginDate: null,
  endDate: null,
  masterId: null,
  subject: null
})

const rules = {
  name: [
    {required: true, message: "班级名称必填", trigger: 'blur'}
  ],
  beginDate: [
    {required: true, message: "开课时间必填", trigger: 'blur'}
  ],
  endDate: [
    {required: true, message: "结课时间必填", trigger: 'blur'}
  ],
  subject: [
    {required: true, message: "学科必填", trigger: 'blur'}
  ]
}

const clazzFormRef = useTemplateRef("clazzFormRef");
//=============================================================
const handleSizeChange = () => {
  fetchClazzData()
}
const handleCurrentChange = () => {
  fetchClazzData()
}

/* 添加班级 */
const handleAdd = () => {
  dialogVisible.value = true
  operationType.value = 'add'
}

/* 修改班级 */
const handleEdit = async (clazzId) => {
  const res = await clazzService.findById(clazzId);
  if (res.code === 1) {
    let clazz = res.data
    clazzForm.id = clazz.id
    clazzForm.name = clazz.name
    clazzForm.room = clazz.room
    clazzForm.beginDate = clazz.beginDate
    clazzForm.endDate = clazz.endDate
    clazzForm.masterId = clazz.masterId
    clazzForm.subject = clazz.subject
  } else {
    ElMessage.error("数据回显失败")
  }

  dialogVisible.value = true
  operationType.value = 'edit'
  title.value = "编辑"

}

/* 删除班级 */
const handleDelete = (clzssId) => {
  ElMessageBox.confirm("真的要删除班级吗?", "提示", {
    type: "warning",
    draggable: true
  }).then(async () => {
    const res = await clazzService.deleteById(clzssId);
    if (res.code === 1) {
      ElMessage.success("删除成功")
      fetchClazzData()
    } else {
      ElMessage.error("删除失败")
    }
  })
}

/* 保存 */
const handleSubmit = () => {
  clazzFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      let res
      if (operationType.value === 'add') {
        res = await clazzService.save(clazzForm);
      } else if (operationType.value === 'edit') {
        res = await clazzService.updateById(clazzForm);
      }
      if (res.code === 1) {
        ElMessage.success(`${title.value}成功`)
        fetchClazzData()
        dialogVisible.value = false
      } else {
        ElMessage.error(`${title.value}失败`)
      }
    } else {
      console.log("表单校验失败 ")
    }
  })
}
/* 取消 */
const handleCancel = () => {
  dialogVisible.value = false
  clazzForm.name = null
  clazzForm.room = null
  clazzForm.beginDate = null
  clazzForm.endDate = null
  clazzForm.masterId = null
  clazzForm.subject = null
}

/* 关闭对话框 */
const handleDialogClose = () => {
  handleCancel()
}

const fetchClazzData = async () => {
  queryParam.begin = begin.value
  queryParam.end = end.value
  const res = await clazzService.findPage(queryParam);
  clazzData.total = res.data.total
  clazzData.rows = res.data.rows
}
const handleQuery = () => {
  fetchClazzData()
}

const handleClear = () => {
  queryParam.name = ""
  // queryParam.begin = ""
  // queryParam.end = ""
  dateRange.value = []
  queryParam.page = 1
  queryParam.pageSize = 10

  fetchClazzData()
}

const fetchEmpData = async () => {
  const res = await empService.findEmpList();
  console.log(res)
  res.data.forEach((emp) => {
    empData.push({
      value: emp.id,
      name: emp.name
    })
  })
}


//=================================================
onMounted(() => {
  console.log("onMounted")
  fetchClazzData()
  fetchEmpData()
})

//=================================================
watchEffect(() => {
  // console.log("watchEffect")
  // console.log(queryParam)
  // fetchClazzData()
})


</script>

<template>
  <h3>班级管理</h3>
  <!-- 分页条件查询 -->
  <el-form inline>
    <el-form-item label="班级名称" :label-width="labelWidth">
      <el-input v-model="queryParam.name" type="text" placeholder="请输入班级名称" clearable style="width: 250px"/>
    </el-form-item>
    <el-form-item label="结课时间" :label-width="labelWidth">
      <el-date-picker
          v-model="dateRange"
          type="daterange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          style="width: 250px"
          clearable
          value-format="YYYY-MM-DD"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleQuery">查询</el-button>
      <el-button type="info" @click="handleClear">清空</el-button>
    </el-form-item>

  </el-form>
  <el-row>
    <el-col :span="24">
      <el-button type="primary" @click="handleAdd">
        新增班级
      </el-button>
    </el-col>
  </el-row>
  <br/>
  <!-- 表格数据展示 -->
  <el-row>
    <el-table :data="clazzData.rows" border :header-cell-style="{textAlign:'center'}"
              :cell-style="{textAlign:'center'}">
      <el-table-column prop="id" label="编号">
      </el-table-column>
      <el-table-column prop="name" label="班级名称"></el-table-column>
      <el-table-column prop="room" label="班级教室"></el-table-column>
      <el-table-column prop="masterName" label="班主任"></el-table-column>
      <el-table-column prop="beginDate" label="开课时间"></el-table-column>
      <el-table-column prop="endDate" label="结课时间"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="updateTime" label="最后操作时间"></el-table-column>
      <el-table-column label="操作">
        <template v-slot:default="{row}">
          <el-button type="primary" text @click="handleEdit(row.id)">编辑</el-button>
          <el-button type="danger" text @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-row>

  <br/>
  <!-- 分页条 -->
  <el-row>
    <el-col>
      <el-pagination
          v-model:current-page="queryParam.page"
          v-model:page-size="queryParam.pageSize"
          :page-sizes="pageSizes"
          :size="'small'"
          :disabled="false"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="clazzData.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </el-col>
  </el-row>

  <!-- 新增班级 -->
  <el-dialog align-center v-model="dialogVisible" :title="`${title}班级`" @close="handleDialogClose" width="500">
    <el-form :model="clazzForm" :rules="rules" ref="clazzFormRef">
      <el-form-item label="班级名称" :label-width="labelWidth" prop="name">
        <el-input v-model="clazzForm.name" type="text" placeholder="请输入班级名称"/>
      </el-form-item>
      <el-form-item label="班级教室" :label-width="labelWidth" prop="room">
        <el-input v-model="clazzForm.room" type="text" placeholder="请输入班级教室"/>
      </el-form-item>
      <el-form-item label="开课时间" :label-width="labelWidth" prop="beginDate">
        <el-date-picker v-model="clazzForm.beginDate" placeholder="请选择开课时间" style="width: 100%"
                        value-format="YYYY-MM-DD"/>
      </el-form-item>
      <el-form-item label="结课时间" :label-width="labelWidth" prop="endDate">
        <el-date-picker v-model="clazzForm.endDate" placeholder="请选择结课时间" style="width: 100%"
                        value-format="YYYY-MM-DD"/>
      </el-form-item>
      <el-form-item label="班主任" :label-width="labelWidth" prop="masterId">
        <el-select v-model="clazzForm.masterId" placeholder="请选择班主任">
          <el-option v-for="emp in empData" :key="emp.id" :label="emp.name" :value="emp.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="学科" :label-width="labelWidth" prop="subject">
        <el-select v-model="clazzForm.subject" placeholder="请选择学科">
          <el-option v-for="subject in subjectData" :key="subject.value" :label="subject.name" :value="subject.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-row :justify="'space-evenly'" style="width: 100%">
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button type="info" @click="handleCancel">取消</el-button>
        </el-row>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped>

</style>