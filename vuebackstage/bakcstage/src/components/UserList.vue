<template>
  <div>
    <button @click="getFoodList">查询言溪题库</button>
  </div>
</template>

<script setup>
import { ref,getCurrentInstance } from 'vue';
import axios from 'axios';
const {proxy} = getCurrentInstance()


</script>
