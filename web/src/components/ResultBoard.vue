<template>
  <div class="backdrop">
    <el-card class="card">
      <div class="content">
        <div class="win-content" v-if="winner === user.name">胜 利</div>
        <div class="lose-content" v-else>失 败</div>
      </div>
      <div class="bottom-menu">
        <el-button size="large" type="primary" @click="$emit('resetGame')"
          ><el-icon size="1.2rem"><RefreshRight /></el-icon>再来一把</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { useGameStore } from "@/store/game";
import { useUserStore } from "@/store/user";

import { storeToRefs } from "pinia";

const gameStore = useGameStore();
const userStore = useUserStore();
const { winner } = storeToRefs(gameStore);
const { user } = storeToRefs(userStore);
</script>

<style lang="scss" scoped>
.backdrop {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 60px;
  left: 0;
  background-color: rgba($color: #000000, $alpha: 0.1);

  .card {
    position: relative;
    top: 40%;
    left: 50%;
    width: 25vw;
    opacity: 0;
    animation: slideBottom 1s ease forwards;

    .content {
      font-size: 46px;
      /* letter-spacing: 20px; */
      font-weight: 800;
      text-align: center;
      margin-bottom: 20px;

      .win-content {
        color: #67C23A;
      }

      .lose-content {
        color: #f56c6c;
      }
    }

    .bottom-menu {
      text-align: center;
    }
  }
}

@keyframes slideBottom {
  0% {
    transform: translate(-50%, -65%);
  }

  100% {
    transform: translate(-50%, -50%);
    opacity: 1;
  }
}
</style>
