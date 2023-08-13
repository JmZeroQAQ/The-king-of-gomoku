<template>
  <el-menu
    :default-active="activeIndex"
    class="navbar"
    mode="horizontal"
    :ellipsis="false"
    @select="handleSelect"
    :router="true"
  >
    <el-menu-item index="/home/" class="logo">TOKG</el-menu-item>
    <el-menu-item index="/game/" class="nav-item">游戏</el-menu-item>
    <el-menu-item index="/ranklist/" class="nav-item">排行榜</el-menu-item>
    <el-menu-item index="/recordlist/" class="nav-item">历史对局</el-menu-item>
    <div class="flex-grow" />
    <el-sub-menu v-if="isAuth" index="1">
      <template #title>
        <span class="username">{{ user.name }}</span>
      </template>
      <el-menu-item index="/profile/">个人信息</el-menu-item>
      <el-menu-item index="/bot/">我的Bot</el-menu-item>
      <el-menu-item @click="logout">退出</el-menu-item>
    </el-sub-menu>
    <el-menu-item v-else index="/login/" class="login">
      登录/注册
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";

const userStore = useUserStore();

const { isAuth, user } = storeToRefs(userStore);
const { logout } = userStore;

const activeIndex = ref("/home/");
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath);
};
</script>

<style lang="scss" scoped>
.navbar {
  z-index: 100;
}

.flex-grow {
  flex-grow: 1;
}

.logo {
  font-weight: 800;
  font-size: 22px;
  color: #409eff;
}

.nav-item {
  color: #303133;
  letter-spacing: 1px;
}

.username {
  font-weight: 600;
  color: #409eff;
}

.login {
  font-size: 16px;
  font-weight: 600;
  color: #409eff;
}
</style>
