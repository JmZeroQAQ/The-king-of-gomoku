<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <!-- body 路由出口 -->
        <router-view></router-view>
      </el-main>
      <el-footer>
        
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
</script>

<style lang="scss" scoped>
</style>
