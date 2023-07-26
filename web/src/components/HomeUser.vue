<template>
  <el-card>
    <div class="profile">
      <div class="user-avatar">
        <img :src="user.avatar" alt="avatar" />
        <div class="user-name">{{ user.name }}</div>
      </div>
      <div class="user-info">
        <div class="text-message">
          Rating: <span>{{ user.rating }}</span>
        </div>
        <div class="text-message">排名: <span>1</span></div>
        <div class="text-message">游戏对局总数: <span>100</span></div>
        <div class="text-message">胜率: <span>55%</span></div>
      </div>
    </div>
  </el-card>

  <div class="game">
    <div class="button-container">
      <el-button
       class="game-button"
       type="primary"
       @click="matchEvent"
       >匹配对战</el-button>
    </div>

    <div class="button-container">
      <el-button class="game-button" type="primary">人机对战</el-button>
    </div>

    <div class="button-container">
      <el-button class="game-button" type="primary">Bot设置</el-button>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";

const router = useRouter();
const userStore = useUserStore();
const { user } = storeToRefs(userStore);

function matchEvent() {
  router.push({path: "/match/"});
}

</script>

<style lang="scss" scoped>
.profile {
  display: flex;
  justify-content: space-around;

  .user-avatar img {
    max-width: 350px;
    border-radius: 5px;
  }

  .user-name {
    margin-top: 5px;
    text-align: center;
    font-size: 20px;
    font-weight: 500;
  }

  .user-info .text-message {
    font-size: 28px;
    font-weight: 600;
    margin-bottom: 20px;
  }
}
.game {
  margin-top: 20px;

  .button-container {
    text-align: center;
  }

  .game-button {
    width: 200px;
    margin-bottom: 20px;
    padding: 40px;
  }
}
</style>
