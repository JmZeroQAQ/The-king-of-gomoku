<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <!-- main 路由出口 -->
        <router-view v-slot="{ Component }">
          <transition name="el-fade-in-linear" mode="out-in">
            <keep-alive
              :include="['RecordListView', 'ProfileView', 'RanklistView', 'BotView']"
            >
              <component :is="Component" :key="$route.path"/>
            </keep-alive>
          </transition>
        </router-view>
      </el-main>
      <!-- <el-footer>
        
      </el-footer> -->
    </el-container>
  </div>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue";
</script>

<style lang="scss" scoped>
.common-layout::before {
  content: "";
  width: 100vw;
  height: 100vh;
  background-image: url("https://ranunculus.top/media/images/2023081516480112.png");
  background-size: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -100;
}
</style>
