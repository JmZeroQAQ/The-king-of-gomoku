<template>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
    >
      <el-menu-item index="0" class="logo">TOKG</el-menu-item>
      <div class="flex-grow" />
      <el-sub-menu index="1">
        <template #title>JmZeroQAQ</template>
        <el-menu-item index="1-1">个人信息</el-menu-item>
        <el-menu-item index="1-2">item two</el-menu-item>
        <el-menu-item index="1-3">item three</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </template>

<script setup>
import { ref } from 'vue';

const activeIndex = ref('1');
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}
</script>

<style lang="scss" scoped>

.flex-grow {
  flex-grow: 1;
}

.logo {
    font-weight: 800;
    font-size: 18px;
    color: #409EFF;
}
</style>