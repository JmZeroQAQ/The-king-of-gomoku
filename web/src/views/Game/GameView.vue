<template>
  <el-row justify="center">
    <el-col :span="12">
      <GameMap v-if="currentComponent === 'game'" />
      <MatchGround v-else-if="currentComponent === 'match'" />
    </el-col>
  </el-row>
</template>

<script setup>
import GameMap from "@/components/GameMap.vue";
import MatchGround from "@/components/MatchGround.vue";

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
  setWinSet,
  setGameStat,
} = gameStore;

const { gameStat } = storeToRefs(gameStore);

let webSocket = null;

const currentComponent = ref("match");

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
    console.log(gameStat.value);

    if (data.event === "match-found") {
      setOpponent(data);
      setColor(data.color);
      setGameStat("running");

      setTimeout(() => {
        currentComponent.value = "game";
      }, 1200);
    } else if (data.event === "move" && gameStat.value === "running") {
        console.log("123");
      updatePosition(data.newPosition);
    } else if (data.event === "result" && gameStat.value === "running") {
      // 保存 造成胜利棋子的集合
      setWinSet(data.winSet);
      setGameStat("over");
    }
  };
});

onUnmounted(() => {
  // 组件卸载时关闭webSocket连接
  webSocket.close();
});
</script>

<style lang="scss" scoped></style>
