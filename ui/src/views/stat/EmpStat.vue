<script setup>

import * as echarts from "echarts";
import {onBeforeUnmount, onMounted, reactive, ref, unref, useTemplateRef} from "vue";
import statService from "@/api/stat.js";
import stat from "@/api/stat.js";

const jobStat = useTemplateRef("jobStat")
const genderStatRef = useTemplateRef("genderStat")

const chart = ref(null)
const genderChart = ref(null)

/* 员工职位统计 */
const statData = reactive({
  job: {
    dataList: [],
    jobList: []
  },
  gender: []
})

const fetchStatData = async () => {
  let res = await statService.queryEmpJobStat();
  statData.job.dataList = res.data.dataList
  statData.job.jobList = res.data.jobList

  res = await statService.queryEmpGender()
  statData.gender = res.data
  initEcharts()
  fillEcharts()
}

/**/

const initEcharts = () => {
  chart.value = echarts.init(jobStat.value);
  genderChart.value = echarts.init(genderStatRef.value)
}

const fillEcharts = () => {
  chart.value.setOption({
    title: {
      text: "职位统计"
    },
    tooltip: {},
    xAxis: {
      data: statData.job.jobList
    },
    yAxis: {},
    series: [
      {
        name: "人数",
        type: 'bar',
        data: statData.job.dataList
      }
    ]
  })

  genderChart.value.setOption(
      {
        title: {
          text: "性别统计"
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            // name: '性别统计',
            type: 'pie',
            radius: ['30%', '70%'],
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
                fontSize: 28,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: statData.gender
          }
        ]
      }
  )

  /* 响应式布局 */
  window.addEventListener('resize', handleResize)
}

const destroyEcharts = () => {
  chart.value.dispose()
  genderChart.value.dispose()
  window.removeEventListener('resize', handleResize)
}
onMounted(() => {
  fetchStatData()
})

const handleResize = () => {
  chart.value.resize()
  genderChart.value.resize()
}

onBeforeUnmount(() => {
  destroyEcharts()
})

</script>

<template>
  <el-container>
    <el-header>
      <h3>员工统计</h3>
    </el-header>
    <el-main>
      <el-row justify="space-evenly">
        <!-- 职位统计 -->
        <div ref="jobStat" style="width: 400px;height: 400px"></div>
        <!-- 性别统计 -->
        <div ref="genderStat" style="width: 400px;height: 400px"></div>
      </el-row>
    </el-main>
  </el-container>

</template>

<style scoped>

</style>