<script setup>


import {ref} from "vue";
import axios from "axios";

const user = ref({
  username: "lisi",
  age: 18,
  hobbies: [
    {
      name: "swim", id: 1001
    },
    {
      name: "sing", id: 1002
    }
  ]
})

const showInfo = () => {
  alert(JSON.stringify(user.value))
}


const fetchData = () => {
  axios(
      "https://web-server.itheima.net/emps/list"
  ).then((res) => {
    console.log(res)
  })
}
</script>

<template>
  <h3>文章管理</h3>
  <button @click="showInfo">showInfo</button>
  <button @click="fetchData">FetchData</button>
</template>

<style scoped>

</style>