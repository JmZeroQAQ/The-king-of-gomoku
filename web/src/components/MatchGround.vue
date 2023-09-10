<template>
  <div class="match-container">
    <el-card>
      <div class="user-body">
        <div class="self">
          <img class="user-image" :src="user.avatar" alt="" />
          <div class="user-name">
            {{ user.name }}
          </div>
        </div>
        <div class="message">
          <h1 style="text-align: center">PK</h1>

          <div class="bot-select">
            <el-select
              v-model="botValue"
              placeholder="出战方式"
              size="large"
              :disabled="selectDisabled"
            >
              <el-option :key="-1" :label="'亲自出马'" :value="-1" />
              <el-option
                v-for="bot in botList"
                :key="bot.id"
                :label="bot.title"
                :value="bot.id"
              />
            </el-select>
          </div>
        </div>
        <div class="opponent">
          <img
            class="user-image"
            :src="gameStat === 'running' ? opponent.avatar : 'https://ranunculus.top/media/images/202308151648088.png'"
            alt=""
          />
          <div class="user-name">
            {{ gameStat === "running" ? opponent.name : "???" }}
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
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useGameStore } from "@/store/game";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import { getBotList } from "@/apis/getBotList";

const userStore = useUserStore();
const { user, token } = storeToRefs(userStore);

const gameStore = useGameStore();
const { webSocket, opponent, gameStat } = storeToRefs(gameStore);
const { setIsBot } = gameStore;

const matchInfo = ref("开始匹配");
function matchEvent() {
  if (matchInfo.value === "开始匹配") {
    // 记录是否是bot
    if (botValue.value !== -1) setIsBot(true);
    else setIsBot(false);

    matchInfo.value = "取消匹配";
    selectDisabled.value = true;

    webSocket.value.send(
      JSON.stringify({
        event: "start-matching",
        bot_id: botValue.value,
      })
    );
  } else {
    matchInfo.value = "开始匹配";
    selectDisabled.value = false;
    webSocket.value.send(
      JSON.stringify({
        event: "stop-matching",
      })
    );
  }
}

const selectDisabled = ref(false);
const botValue = ref(-1);
const botList = ref([]);

async function getBots() {
  const data = await getBotList(token.value);
  botList.value = data.bots;
}

onMounted(() => {
  getBots();
});

onUnmounted(() => {
  if(matchInfo.value === "取消匹配") {
    webSocket.value.send(
      JSON.stringify({
        event: "stop-matching",
      })
    );
  }
})
</script>

<style lang="scss" scoped>
.match-container {
  width: 100%;
  height: 75vh;
  display: flex;
  align-items: center;
}

.el-card {
  width: 100%;
  background-color: rgba($color: #fff, $alpha: 0.8);

  opacity: 0;
  animation: slideBottom 1s ease forwards;
  animation-delay: .2s;
}

.user-body {
  display: flex;
  justify-content: space-around;

  .message {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .bot-select {
      margin-top: 20px;
    }
  }

  .user-name {
    text-align: center;
    margin-top: 10px;
    font-size: 20px;
    font-weight: 600;
  }
}

.user-image {
  width: 180px;
  height: 180px;
  object-fit: cover;
  border-radius: 50%;
}

.button-container {
  text-align: center;
}

@keyframes slideBottom {
  0% {
    transform: translateY(-100px);
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
