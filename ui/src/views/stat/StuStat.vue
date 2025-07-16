<script setup>
import * as echarts from 'echarts'
import {onMounted, onUnmounted, reactive, ref, useTemplateRef} from "vue";
import statService from "@/api/stat.js";
import stat from "@/api/stat.js";

const countRef = useTemplateRef("countRef");
const degreeRef = useTemplateRef("degreeRef");

const countChart = ref(null)
const degreeChart = ref(null)

const initEcharts = () => {
  countChart.value = echarts.init(countRef.value);
  degreeChart.value = echarts.init(degreeRef.value);
}

const statData = reactive({
  dataList: [],
  clazzList: [],
  pieData: []
})

const fetchStatData = async () => {
  let response = await statService.queryStuCount();
  if (response.code === 1) {
    statData.dataList = response.data.dataList
    statData.clazzList = response.data.clazzList
  } else {
    console.log("学生班级统计数据获取失败")
  }

  response = await statService.queryStuDegree();
  if (response.code === 1) {
    statData.pieData = response.data
  } else {
    console.log("学生学历统计数据获取失败")
  }
  console.log(statData.clazzList)
  console.log(statData.dataList)
  console.log(statData.pieData)
}
const fillEcharts = () => {
  countChart.value.setOption({
    title: {
      text: "班级统计"
    },
    tooltip: {},
    xAxis: {
      data: statData.clazzList
    },
    yAxis: {},
    series: [
      {
        name: "人数",
        type: 'bar',
        data: statData.dataList
      }
    ]
  })

  degreeChart.value.setOption({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: statData.pieData
      }
    ]
  })
}

const destroyEcharts = () => {
  countChart.value.dispose()
  degreeChart.value.dispose()
}

onMounted(async () => {
  await fetchStatData()
  initEcharts()
  fillEcharts()
})

onUnmounted(() => {
  destroyEcharts()
})
/* */


</script>

<template>
  <el-container>
    <el-main>
      <el-row justify="space-around">
        <div ref="countRef" style="width: 1000px;height: 500px;"/>

      </el-row>
      <el-row>

        <div ref="degreeRef" style="width: 500px;height: 500px;"/>
      </el-row>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>