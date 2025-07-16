<script setup>

import { onMounted, reactive, ref, useTemplateRef } from "vue";
import { deptService } from "@/api/dept.js";
import { ElMessage, ElMessageBox } from "element-plus";

/* 部门数据 */
const deptList = ref([])

/* 对话框 */
const dialogVisible = reactive({
  saveOrEdit: false,
})

const title = ref("添加部门")

/* 模版引用 */
const formRef = useTemplateRef("formRef");

/* 表单数据 */
const formData = ref({})

/* 表单校验规则 */
const rules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称长度2到10', trigger: 'blur' },
  ]
}

/* 获取数据 */
const initData = async () => {
  const res = await deptService.list()
  deptList.value = res.data
}

const onCancel = () => {
  dialogVisible.saveOrEdit = false
}

function clearFormData() {
  formData.value = {}
}

/* 确认 */
const onSubmit = async () => {
  /* 校验表单 */
  console.log("formRef", formRef.value)
  await formRef.value.validate(async (valid, fields) => {
    if (valid) {
      let res, optype = formData.value.id === undefined ? "添加" : "编辑"
      if (formData.value.id) {
        res = await deptService.updateById(formData.value);
      } else if (formData.value.id === undefined) {
        res = await deptService.save(formData.value);
      }
      if (res.code === 1) {
        ElMessage({
          type: "success",
          message: optype + "成功"
        })
        dialogVisible.saveOrEdit = false
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
  formData.value = {
    id: deptById.data.id,
    name: deptById.data.name,
  }
}

/* 生命周期 */
onMounted(() => {
  initData()
})
</script>

<template>
  <h3>部门管理</h3>
  <el-button type="primary" @click="handleAdd">添加部门</el-button>
  <br><br>
  <el-table border :data="deptList" :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
    <el-table-column prop="id" label="部门编号" width="90" />
    <el-table-column prop="name" label="部门名称" />
    <el-table-column prop="updateTime" label="更新时间" />
    <el-table-column label="操作" width="200">
      <template #default="{ row }">
        <el-button type="warning" @click="handleEdit(row.id)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 添加/编辑部门 -->
  <el-dialog v-model="dialogVisible.saveOrEdit" :title="title" width="500" :show-close="false">
    <el-form :model="formData" :rules="rules" ref="formRef">
      <el-form-item label="部门名称" prop="name">
        <el-input type="text" v-model="formData.name" />
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