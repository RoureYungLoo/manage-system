<script setup>
import {computed, onMounted, reactive, ref, useTemplateRef} from 'vue';
import {clazzService} from "@/api/clazz.js";
import {stuService} from "@/api/stu.js";
import {ElMessage, ElMessageBox} from "element-plus";

/* 分页查询参数 */
const queryParam = reactive({
  page: 1,
  pageSize: 10
})
const resetQueryParam = () => {
  queryParam.name = null
  queryParam.degree = null
  queryParam.clazzId = null
  // queryParam.page = 1
  // queryParam.pageSize = 10
}

/* 下拉选择 学历*/
const degreeOptions = [
  {label: "初中", value: 1},
  {label: "高中", value: 2},
  {label: "大专", value: 3},
  {label: "本科", value: 4},
  {label: "硕士", value: 5},
  {label: "博士", value: 6},
]

/* 下拉选择 班级*/
const clazzOptions = computed(() => {
  if (clazzData.data) {
    return clazzData.data.map(clazz => ({label: clazz.name, value: clazz.id}))
  } else {
    return []
  }
})

/* 班级数据 */
const clazzData = reactive({
  data: []
})

/* 学员数据 */
const stuData = reactive({
  total: 0,
  rows: []
})

/* 编辑表单 */
const stuForm = reactive({})
const resetStuForm = () => {
  stuForm.id = null
  stuForm.name = null
  stuForm.no = null
  stuForm.phone = null
  stuForm.gender = null
  stuForm.degree = null
  stuForm.idCard = null
  stuForm.isCollege = null
  stuForm.address = null
  stuForm.graduationDate = null
  stuForm.violationCount = null
  stuForm.violationScore = null
  stuForm.clazzId = null
}
const assignStuForm = (stu) => {
  stuForm.id = stu.id
  stuForm.name = stu.name
  stuForm.no = stu.no
  stuForm.phone = stu.phone
  stuForm.gender = stu.gender
  stuForm.degree = stu.degree
  stuForm.idCard = stu.idCard
  stuForm.isCollege = stu.isCollege
  stuForm.address = stu.address
  stuForm.graduationDate = stu.graduationDate
  stuForm.violationCount = stu.violationCount
  stuForm.violationScore = stu.violationScore
  stuForm.clazzId = stu.clazzId
}

/* 页面大小数组  */
const pageSizes = function () {
  let arr = []
  for (let i = 0; i < 100;) {
    i += 5
    arr.push(i)
  }
  return arr
}()

/* 返回的结果映射 */
const genderMap = {
  1: "男",
  2: "女"
}
const degreeMap = {
  1: "初中",
  2: "高中",
  3: "大专",
  4: "本科",
  5: "硕士",
  6: "博士",
}

/* 待删除的学员ID数组 */
const deleteIds = ref([])

const dialogVisible = ref(false)
const dialogViolation = ref(false)
const labelWidth = 90
const OpType = ref("add")
const title = computed(() => {
  return OpType.value === "add" ? "添加" : "编辑"
})

/* 下拉选择 性别 */
const genderOptions = [
  {label: "男", value: 1},
  {label: "女", value: 2},
]
/* 下拉选择 是否院校生 */
const isCollegeOptions = [
  {label: "否", value: 0},
  {label: "是", value: 1},
]

/*表单模版引用*/
const stuFormRef = ref(null)
// const stuFormRef = useTemplateRef("stuFormRef");

/* 表单校验规则 */
const rules = reactive({
  name: [
    {required: true, message: "姓名必填", trigger: ['blur',]},
    {min: 2, max: 10, message: "姓名长度2-10", trigger: ['blur',]},
  ],
  no: [
    {required: true, message: "学号必填", trigger: ['blur',]},
    {len: 10, message: "学号长度10", trigger: ['blur',]},
  ],
  gender: [
    {required: true, message: "性别必填", trigger: ['blur',]},
  ],
  phone: [
    {required: true, message: "手机号非法", trigger: ['blur',]},
    {len: 11, message: "手机号非法", trigger: ['blur',]},
  ],
  idCard: [
    {required: true, message: "身份证号码非法", trigger: ['blur',]},
    {len: 18, message: "身份证号码非法", trigger: ['blur',]},
  ],
  isCollege: [
    {required: true, message: "院校学员必填", trigger: ['blur',]},
  ],
})

const violation = reactive({
  id: null,
  score: null
})
/*---------------------------------------------------*/
/* 获取所有班级信息 */
const fetchClazzData = async () => {
  const res = await clazzService.list();
  if (res.code === 1) {
    clazzData.data = res.data
  } else {
    console.log("班级数据获取失败")
  }
}

/* 学生分页条件查询 */
const fetchStuData = () => {
  stuService.findPage(queryParam).then((res) => {
    if (res.code === 1) {
      stuData.rows = res.data.rows
      stuData.total = res.data.total
    } else {
      console.log("学生数据获取失败")
    }
  }).catch((err) => {

  })

}

/* 查询 */
const handleQuery = () => {
  fetchStuData()
}

/* 清空 */
const handleClear = () => {
  resetQueryParam()
  fetchStuData()
}

/* 切换页大小 */
const handleSizeChange = () => {
  fetchStuData()
}

/* 翻页 */
const handleCurrentChange = () => {
  fetchStuData()
}

/* 添加学员 */
const handleAdd = () => {
  dialogVisible.value = true
}

/* 多选 */
const onSelectionChange = (rows) => {
  deleteIds.value = rows.map(row => row.id)
}

/* 批量删除 */
const handleDeleteBatch = async () => {
  if (!deleteIds.value || deleteIds.value.length === 0) {
    ElMessageBox.alert("请选择要删除的学员!", "提示", {type: "warning"})
    return
  }
  const res = await stuService.deleteBatch(deleteIds.value);
  if (res.code === 1) {
    ElMessage.success("删除成功")
    deleteIds.value = []
    fetchStuData();
  } else {
    ElMessage.error("删除失败")
  }
}

/* 编辑学员 */
const handleEdit = async (stuId) => {
  OpType.value = 'edit'
  dialogVisible.value = true
  const res = await stuService.findById(stuId);
  if (res.code !== 1) {
    console.log(res.msg)
    return
  }
  assignStuForm(res.data)
}

/* 违纪处理 */
const handleViolation = (stuId) => {
  dialogViolation.value = true
  violation.id = stuId
}

/* 根据ID删除 */
const handleDeleteById = (stuId) => {
  if (stuId) {
    deleteIds.value = [stuId]
    ElMessageBox.confirm("您确定要删除该学员的信息吗?", "删除学员", {
      type: "warning"
    }).then(() => {
      handleDeleteBatch(deleteIds.value)
    }).catch(() => {
      ElMessage.info("用户取消")
      deleteIds.value = []
    })
  } else {
    ElMessageBox.confirm("数据异常")
  }
}

/* 提交表单 */
const handleSubmit = async () => {
  if (!stuFormRef.value) return
  stuFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      let res
      if (OpType.value === 'add') {
        res = await stuService.save(stuForm);
      } else if (OpType.value === 'edit') {
        res = await stuService.updateById(stuForm);
      }

      if (res.code === 1) {
        ElMessage.success(`${title.value}成功`)
        dialogVisible.value = false
        fetchStuData()
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log("表单校验失败")
    }
  })

}
/* 取消操作 */
const handleCancel = () => {
  dialogVisible.value = false
  resetStuForm()
}

/* 违纪确认 */
const violationConfirm = async () => {
  const res = await stuService.violation(violation);
  if (res.code === 1) {
    ElMessage.success(res.msg)
    violationCancel()
    fetchStuData()
  } else {
    ElMessage.error(res.msg)
  }
}

/* 违纪取消 */
const violationCancel = () => {
  dialogViolation.value = false
  violation.id = null
  violation.score = null
}

/*---------------------------------------------------*/
onMounted(() => {
  fetchClazzData()
  fetchStuData()
})

</script>
<template>
  <h3>学员管理</h3>
  <!-- 条件分页查询 -->
  <el-row>
    <el-col :span="6">
      <el-form-item label="姓名" label-width="60" label-position="right">
        <el-input v-model="queryParam.name" type="text" placeholder="请输入姓名" clearable/>
      </el-form-item>
    </el-col>
    <el-col :span="6">
      <el-form-item label="最高学历" label-width="90" label-position="right">
        <el-select v-model="queryParam.degree" type="text" placeholder="请选择学历" clearable>
          <el-option v-for="option in degreeOptions" :key="option.value" :value="option.value" :label="option.label"/>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="6">
      <el-form-item label="所属班级" label-width="90" label-position="right">
        <el-select v-model="queryParam.clazzId" type="text" placeholder="请选择班级" clearable>
          <el-option v-for="clazz in clazzOptions" :label="clazz.label" :value="clazz.value"/>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="6">
      <el-row justify="end">
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button type="info" @click="handleClear">清空</el-button>
      </el-row>
    </el-col>
  </el-row>
  <!--添加和批量删除-->
  <el-row>
    <el-col :span="24">
      <el-button type="primary" @click="handleAdd">添加学员</el-button>
      <el-button type="danger" text @click="handleDeleteBatch">批量删除</el-button>
    </el-col>
  </el-row>
  <br/>
  <!-- 表格数据展示区 -->
  <el-row>
    <el-col :span="24">
      <el-table border :data="stuData.rows" :header-cell-style="{textAlign:'center'}"
                :cell-style="{textAlign:'center'}" @selectionChange="onSelectionChange">
        <el-table-column type="selection"/>
        <!--        <el-table-column label="ID" prop="id">id</el-table-column>-->
        <el-table-column label="姓名" prop="name" fixed="left"></el-table-column>
        <el-table-column label="学号" prop="no" width="110"></el-table-column>
        <el-table-column label="性别">
          <template v-slot:default="{row}">
            {{ genderMap[row.gender] }}
          </template>
        </el-table-column>
        <el-table-column label="手机号" prop="phone" width="120"></el-table-column>
        <el-table-column label="学历">
          <template v-slot:default="{row}">
            {{ degreeMap[row.degree] }}
          </template>
        </el-table-column>
        <el-table-column label="身份证号" prop="idCard" width="170"></el-table-column>
        <el-table-column label="院校学生" width="90">
          <template #default="{row}">
            {{ row.isCollege === 1 ? "是" : "否" }}
          </template>
        </el-table-column>
        <el-table-column label="联系地址" prop="address" width="90"></el-table-column>
        <el-table-column label="毕业时间" prop="graduationDate" width="100"></el-table-column>
        <el-table-column label="违纪次数" prop="violationCount" width="90"></el-table-column>
        <el-table-column label="违纪扣分" prop="violationScore" width="90"></el-table-column>
        <!--        <el-table-column label="班级ID" prop="clazzId"></el-table-column>-->
        <el-table-column label="班级名称" prop="clazzName" width="160"></el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="160"></el-table-column>
        <el-table-column label="更新时间" prop="updateTime" width="160"></el-table-column>
        <el-table-column label="操作" fixed="right" width="230">
          <template v-slot:default="scope">
            <el-button type="primary" text @click="handleEdit(scope.row.id)">编辑</el-button>
            <el-button type="warning" text @click="handleViolation(scope.row.id)">违纪</el-button>
            <el-button type="danger" text @click="handleDeleteById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <br/>
  <!-- 分页条 -->
  <el-row>
    <el-col :span="24">
      <el-pagination
          v-model:current-page="queryParam.page"
          v-model:page-size="queryParam.pageSize"
          :page-sizes="pageSizes"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="stuData.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </el-col>
  </el-row>
  <!-- 添加和编辑学员 -->
  <!--  -->
  <el-dialog :title="`${title}学员`" v-model="dialogVisible" align-center width="800" :show-close="false"
             @close="resetStuForm" :close-on-click-modal="false">
    <!--    <template v-slot:header>-->
    <!--      <span style="font-size: 20px">{{ `${title}学员` }}</span>-->
    <!--    </template>-->
    <el-form :model="stuForm" :label-width="labelWidth" ref="stuFormRef" :rules="rules">
      <el-row>
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="stuForm.name" placeholder="请输入姓名" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学号" prop="no">
            <el-input v-model="stuForm.no" placeholder="请输入学号" clearable/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="stuForm.gender" placeholder="请选择性别" clearable>
              <el-option v-for="option in genderOptions" :key="option.value" :label="option.label"
                         :value="option.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="stuForm.phone" placeholder="请输入手机号" clearable/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="stuForm.idCard" placeholder="请输入身份证号" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="院校学生" prop="isCollege">
            <el-select v-model="stuForm.isCollege" placeholder="请选择" clearable>
              <el-option v-for="{label,value} in isCollegeOptions" :key="value" :label="label" :value="value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="联系地址">
            <el-input v-model="stuForm.address" placeholder="请输入联系地址" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最高学历">
            <el-select v-model="stuForm.degree" placeholder="请选择最高学历" clearable>
              <el-option v-for="{label,value} in degreeOptions" :key="value" :label="label" :value="value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="毕业时间">
            <el-date-picker style="width: 100%" type="date" v-model="stuForm.graduationDate" value-format="YYYY-MM-DD"
                            placeholder="请选择毕业时间"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属班级">
            <el-select v-model="stuForm.clazzId" placeholder="请选择所属班级" clearable>
              <el-option v-for="{label,value} in clazzOptions" :key="value" :label="label" :value="value"/>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row justify="center">
        <el-col :span="12" style="display: flex;justify-content:space-evenly">
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button type="info" @click="handleCancel">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>

  <!-- 学员违纪 -->
  <el-dialog :show-close="false" v-model="dialogViolation" title="学员违纪处理" width="400"
             :close-on-click-modal="false" align-center>
    <el-form-item label="违纪扣分" label-width="120" label-position="right">
      <el-input v-model.number="violation.score" type="number" placeholder="请输入违纪的扣分"/>
    </el-form-item>
    <el-form-item>
      <el-row justify="space-evenly" style="width: 100%">
        <el-button type="primary" @click="violationConfirm">确认</el-button>
        <el-button type="info" @click="violationCancel">取消</el-button>
      </el-row>
    </el-form-item>
  </el-dialog>

</template>
<style scoped>

</style>