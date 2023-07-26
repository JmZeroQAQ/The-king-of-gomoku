<template>
  <div class="match-view">
    <el-row justify="center">
      <el-col :span="12">
        <el-card>
          <div class="user-body">
            <div class="self">
                <img class="user-image" :src="user.avatar" alt="">
                <div class="user-name">
                    {{ user.name }}
                </div>
            </div>
            <div class="message">
                <h1>PK</h1>
            </div>
            <div class="opponent">
                <img class="user-image" :src="anonymousImage" alt="">
                <div class="user-name">
                    {{ user.name }}
                </div>
            </div>
          </div>
          <div class="footer">
            <div class="button-container">
                <el-button type="primary" >开始匹配</el-button>
            </div>
            
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useUserStore } from '@/store/user';
import { storeToRefs } from 'pinia';
import anonymousImage from '@/assets/images/anonymous.png';

const userStore = useUserStore();
const { user } = storeToRefs(userStore);

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
