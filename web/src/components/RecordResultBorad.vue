<template>
  <div class="backdrop">
    <el-card class="card">
      <h1 class="title">Game Over</h1>
      <div class="result-content">
        <div class="player">
          A: <span class="player-name">{{ players.aName }}</span>
        </div>
        <div class="player">
          B: <span class="player-name">{{ players.bName }}</span>
        </div>
        <div class="player-win">
          Win: <span class="player-name-win">{{ players.winnerName }}</span>
        </div>
      </div>

      <div class="bottom-menu">
        <el-button @click="goBackOnClick"
          ><el-icon size="1.2rem"><ArrowLeftBold /></el-icon>返回</el-button
        >
        <el-button type="primary" @click="$emit('startRecord')"
          ><el-icon size="1.2rem"><RefreshRight /></el-icon>再次观看</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const props = defineProps({
  players: Object,
});

const router = useRouter();
function goBackOnClick() {
  router.push({
    path: "/recordList/",
  });
}
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

    .title {
      text-align: center;
      color: #f56c6c;
      font-size: 42px;
    }

    .result-content {
      padding: 0 10px;
      .player {
        margin-top: 5px;
        font-size: 20px;
        color: #303133;

        .player-name {
          font-weight: 600;
          color: #303133;
        }
      }

      .player-win {
        margin-top: 5px;
        font-size: 20px;
        color: #67c23a;

        .player-name-win {
          font-weight: 600;
        }
      }
    }

    .bottom-menu {
      margin-top: 15px;
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
