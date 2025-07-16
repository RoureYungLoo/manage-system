<script setup>

import {onMounted, reactive, ref, useTemplateRef, watch} from "vue";
import {deptService} from "@/api/dept.js";
import {ElMessage, ElMessageBox} from "element-plus";

/* 部门数据 */
const deptList = reactive({
  total: null,
  rows: [],
})

/* 对话框 */
const dialogVisible = reactive({
  saveOrEdit: false,
})

const title = ref("添加部门")

/* 模版引用 */
const formRef = useTemplateRef("formRef");

/* 表单数据 */
const formData = reactive({
  id: null,
  name: null,
  pageNo: 1,
  pageSize: 10
})

/* 表单校验规则 */
const rules = {
  name: [
    {required: true, message: '请输入部门名称', trigger: 'blur'},
    {min: 2, max: 10, message: '部门名称长度2到10', trigger: 'blur'},
  ]
}

/* 生成分页大小数据 */
const pageSizes = function () {
  const arr = []
  for (let i = 0; i < 100;) {
    i += 5
    arr.push(i)
  }
  return arr
}()

/* 获取数据 */
const initData = () => {
  deptService.findPage(formData).then((res) => {
    deptList.rows = res.data.rows
    deptList.total = res.data.total
  }).catch((err) => {
    console.log(err)
  })
}

/* 取消按钮 */
const onCancel = () => {
  dialogVisible.saveOrEdit = false
  clearFormData()
}

const handleIdUp = () => {
  console.log("Enter Up")
}

const handleNameUp = () => {
  console.log("Enter Name Up")
}

/* 查询 */
const handleQuery = () => {
  console.log(formData)
}

/* 清空表单 */
function clearFormData() {
  formData.id = null
  formData.name = null
}

/* 确认 */
const onSubmit = async () => {
  /* 校验表单 */
  console.log("formRef", formRef.value)
  await formRef.value.validate(async (valid, fields) => {
    if (valid) {
      let res, optype = formData.id === null ? "添加" : "编辑"
      if (formData.id) {
        res = await deptService.updateById(formData);
      } else if (formData.id === null) {
        res = await deptService.save(formData);
      }
      if (res.code === 1) {
        ElMessage({
          type: "success",
          message: optype + "成功"
        })
        onCancel()
      } else {
        ElMessage.error(optype + "失败")
      }
      clearFormData()
      initData()
    } else {
      ElMessage.warning("请输入合法的部门名称")
      console.log("error sumbit", fields)
    }
  })
}

/* 点击删除按钮 */
const handleDelete = (deptId) => {
  ElMessageBox.confirm("确认删除", "提示", {
    confirmButtonText: "删除",
    cancelButtonText: "取消",
    type: "warning"
  }).then(async () => {
    /* 确认删除 */
    const res = await deptService.deleteById(deptId);
    if (res.code === 1) {
      ElMessage({
        type: "success",
        message: "删除成功"
      })
    } else {
      ElMessage({
        type: "error",
        message: "删除失败"
      })
    }
    initData()
  }).catch(() => {
    /* 用户取消 */
    ElMessage.info("用户取消")
  })
}

/* 添加部门 */
const handleAdd = () => {
  dialogVisible.saveOrEdit = true
  title.value = "添加部门"
}

/* 编辑部门 */
const handleEdit = async (deptId) => {
  dialogVisible.saveOrEdit = true
  title.value = "编辑部门"
  const deptById = await deptService.findById(deptId);
  console.log(deptById)
  formData.id = deptById.data.id
  formData.name = deptById.data.name
}

/* 翻页 */
const onCurrentChange = (pageNo) => {
  formData.pageNo = pageNo
}

/* 切换页大小 */
const onSizeChange = (pageSize) => {
  formData.pageSize = pageSize
}

/* 生命周期 */
onMounted(() => {
  initData()
})

/* 监听表单响应式状态 */
watch(formData, () => {
  initData()
}, {deep: true})
</script>

<template>
  <h3>部门管理</h3>
  <el-row>
    <el-col :span="24">
      <el-form inline>
        <el-form-item label="部门编号" label-width="80">
          <el-input type="text" placeholder="输入部门编号" v-model="formData.id" @keyup.enter="handleIdUp" clearable/>
        </el-form-item>
        <el-form-item label="部门名称" label-width="80">
          <el-input type="text" placeholder="输入部门名称" v-model="formData.name" @keyup.enter="handleNameUp" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery" type="primary">查询</el-button>
          <el-button @click="clearFormData" type="info">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
  <el-button type="primary" @click="handleAdd">添加部门</el-button>
  <br><br>
  <el-table border :data="deptList.rows" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }">
    <el-table-column prop="id" label="部门编号" width="90"/>
    <el-table-column prop="name" label="部门名称"/>
    <el-table-column prop="updateTime" label="更新时间"/>
    <el-table-column label="操作" width="200">
      <template #default="{ row }">
        <el-button type="warning" @click="handleEdit(row.id)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <br/>

  <!-- 分页条 -->
  <el-pagination
      v-model:current-page="formData.pageNo"
      v-model:page-size="formData.pageSize"
      :page-sizes="pageSizes"
      :size="'default'"
      :total="deptList.total"
      layout="total,sizes,prev,pager,next,jumper"
      @current-change="onCurrentChange"
      @size-change="onSizeChange"
      :disabled="false"
      :background="true"
  />

  <!-- 添加/编辑部门 -->
  <el-dialog v-model="dialogVisible.saveOrEdit" :title="title" width="500" :show-close="false">
    <el-form :model="formData" :rules="rules" ref="formRef">
      <el-form-item label="部门名称" prop="name">
        <el-input type="text" v-model="formData.name"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="onCancel">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>