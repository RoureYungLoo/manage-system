<script setup>

import {ref} from "vue";

/*模版引用*/
const aaa = ref(null)


const form = ref({})

const handler = () => {
  console.log(aaa)
}
</script>

<template>


  <form action="#" method="get" ref="aaa">
    <input type="text" v-model="form.name">
    <button @click.prevent="handler">button</button>
  </form>
</template>

<style scoped>

</style>