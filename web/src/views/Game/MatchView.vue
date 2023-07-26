<template>
  <div class="match-view">
    <el-row justify="center">
      <el-col :span="12">
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
              <el-button @click="matchEvent" type="primary">{{ matchInfo }}</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import anonymousImage from "@/assets/images/anonymous.png";
import { WEB_SOCKET_URL } from "@/apis/env";
import { onMounted, onUnmounted, ref } from "vue";
import { useGameStore } from "@/store/game";

const userStore = useUserStore();
const { user, token } = storeToRefs(userStore);

const gameStore = useGameStore();
const { opponent, setOpponent, setWebSocket } = gameStore;

let webSocket = null;

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
    if(data.event === "match-found") {
        setOpponent(data);
    } else if(data.event === "move") {

    }
  }
});

onUnmounted(() => {
    // 组件卸载时关闭webSocket连接
    webSocket.close();
})


const matchInfo = ref("开始匹配");
function matchEvent() {
    if(matchInfo.value === "开始匹配") {
        matchInfo.value = "取消匹配";
        webSocket.send(JSON.stringify({
            event: "start-matching",
        }));
    } else {
        matchInfo.value = "开始匹配";
        webSocket.send(JSON.stringify({
            event: "stop-matching",
        }));
    }
}

</script>

<style lang="scss" scoped>
.match-view {
  margin-top: 6vh;
}

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
