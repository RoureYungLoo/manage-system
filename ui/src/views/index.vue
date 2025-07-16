<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";

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

defineEmits("changeImage")
</script>

<template>
  <el-image :src="imgUrl" @click="change"/>
</template>

<style scoped>

</style>