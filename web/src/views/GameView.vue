<template>
  <el-row justify="center">
    <el-col :span="18">
      <GameMap
        v-if="currentComponent === 'game'"
        :current_step="current_step"
      />
      <MatchGround v-else-if="currentComponent === 'match'" />
      <ResultBoard
        v-else-if="currentComponent === 'result'"
        @resetGame="resetGame"
      />
    </el-col>
  </el-row>
</template>

<script setup>
import GameMap from "@/components/GameMap.vue";
import MatchGround from "@/components/MatchGround.vue";
import ResultBoard from "@/components/ResultBoard.vue";

import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import { useGameStore } from "@/store/game";
import { WEB_SOCKET_URL } from "@/apis/env";
import { onMounted, onUnmounted, ref } from "vue";

const userStore = useUserStore();
const { token } = storeToRefs(userStore);

const gameStore = useGameStore();
const {
  setOpponent,
  setWebSocket,
  setColor,
  updatePosition,
  cleanPosition,
  setWinSet,
  setGameStat,
  setWinner,
} = gameStore;

function resetGame() {
  currentComponent.value = "match";
  current_step.value = 0;
  cleanPosition();
}

let webSocket = null;

const currentComponent = ref("match");
const current_step = ref(0);

onMounted(() => {
  const webSocketUrl = WEB_SOCKET_URL + token.value + "/";
  webSocket = new WebSocket(webSocketUrl);

  webSocket.onopen = () => {
    console.log("connected");
    setWebSocket(webSocket);
  };

  webSocket.onclose = () => {
    console.log("disconnected");
    setWebSocket(null);
  };

  webSocket.onmessage = (msg) => {
    const data = JSON.parse(msg.data);
    console.log(data);

    if (data.event === "match-found") {
      setOpponent(data);
      setColor(data.color);
      setGameStat("running");

      // 1.2s后跳转到游戏界面
      setTimeout(() => {
        currentComponent.value = "game";
      }, 1200);
    } else if (data.event === "move") {
      updatePosition(data.newPosition);
      current_step.value++;
    } else if (data.event === "result") {
      // 保存 造成胜利棋子的集合
      setWinner(data.name);
      setWinSet(data.winSet);
      setGameStat("over");

      setTimeout(() => {
        currentComponent.value = "result";
      }, 6000);
    }
  };
});

onUnmounted(() => {
  // 组件卸载时关闭webSocket连接
  webSocket.close();
});
</script>

<style lang="scss" scoped></style>
