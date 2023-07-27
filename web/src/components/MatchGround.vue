<template>
  <el-card>
    <div class="user-body">
      <div class="self">
        <img class="user-image" :src="user.avatar" alt="" />
        <div class="user-name">
          {{ user.name }}
        </div>
      </div>
      <div class="message">
        <h1>PK</h1>
      </div>
      <div class="opponent">
        <img class="user-image" :src="anonymousImage" alt="" />
        <div class="user-name">
          {{ opponent.name }}
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="button-container">
        <el-button @click="matchEvent" type="primary">{{
          matchInfo
        }}</el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup>

import anonymousImage from "@/assets/images/anonymous.png";
import { ref } from "vue";
import { useGameStore } from "@/store/game";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";

const userStore = useUserStore();
const { user } = userStore;

const gameStore = useGameStore();
const { webSocket, opponent } = storeToRefs(gameStore);

const matchInfo = ref("开始匹配");
function matchEvent() {
  if (matchInfo.value === "开始匹配") {
    matchInfo.value = "取消匹配";
    webSocket.value.send(
      JSON.stringify({
        event: "start-matching",
      })
    );
  } else {
    matchInfo.value = "开始匹配";
    webSocket.value.send(
      JSON.stringify({
        event: "stop-matching",
      })
    );
  }
}
</script>

<style lang="scss" scoped>

.user-body {
  display: flex;
  justify-content: space-around;

  .message {
    display: flex;
    align-items: center;
  }

  .user-name {
    text-align: center;
    margin-top: 10px;
    font-size: 20px;
    font-weight: 600;
  }
}

.user-image {
  width: 240px;
  height: 240px;
  object-fit: cover;
  border-radius: 50%;
}

.button-container {
  text-align: center;
}
</style>
