<template>
  <!-- 路由出口 -->
  <router-view></router-view>
</template>

<script setup>
import { useUserStore } from '@/store/user';
import { getToken } from '@/utils/storage';

const userStore = useUserStore();
const { setToken, asyncGetInfo, setIsAuth } = userStore;

// 将令牌从localStorage中取出
const token = getToken();
if(token) {
  setToken(token);

  // 获取用户信息
  asyncGetInfo();
  setIsAuth(true);
}


</script>

<style lang="scss" scoped>

</style>