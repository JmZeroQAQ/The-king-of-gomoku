<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <!-- main 路由出口 -->
        <router-view></router-view>
      </el-main>
      <!-- <el-footer>
        
      </el-footer> -->
    </el-container>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
</script>

<style lang="scss" scoped>
.common-layout::before {
  content: "";
  width: 100vw;
  height: 100vh;
  background-image: url("@/assets/images/background.png");
  background-size: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -100;
}
</style>
