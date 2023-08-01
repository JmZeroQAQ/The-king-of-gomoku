<template>
  <el-card class="result-board">
    <div class="container">
      <div class="content">{{ winner === user.name ? "胜 利" : "失 败" }}</div>
      <div style="text-align: center">
        <el-button
          class="button"
          round
          type="success"
          @click="$emit('resetGame')"
          >再来一把</el-button
        >
      </div>
    </div>
  </el-card>
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
.result-board {
  height: 75vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba($color: #fff, $alpha: 0.1);
}

.content {
  font-size: 62px;
  /* letter-spacing: 20px; */
  font-weight: 800;
  text-align: center;
}

.button {
  margin-top: 20px;
  padding: 30px;
}
</style>
