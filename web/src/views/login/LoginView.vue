<template>
  <el-row class="login-container" justify="center">
    <el-col :lg="12" :span="20">
      <el-card>
        <el-row>
          <el-col :span="12">
            <div class="image-container">
              <div class="image-header">
                <div class="image-header-msg msg-welcome">WELCOME</div>
                <div class="image-header-msg msg-join-us">JOIN US</div>
              </div>
              <div class="image-body">
                <div class="login-image"></div>
              </div>
            </div>
          </el-col>
          <el-col :span="12">
            <Login 
              v-if="state === 'login'" 
              @setState="setState"
            />
            <Register 
              v-if="state === 'register'" 
              @setState="setState"
            />
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import Login from '@/components/Login.vue';
import Register from '@/components/Register.vue';
import { ref } from 'vue';

const state = ref("login");

function setState(newState) {
  state.value = newState;
}

</script>

<style lang="scss" scoped>
.login-container {
  margin-top: 20px;
}

.image-container {
  height: 400px;

  background-color: #79bbff;
  border-radius: 8px;
}

.image-header {
  padding: 20px 0;
}

.image-header-msg {
  text-align: center;
  color: #f4f4f5;
}

.msg-welcome {
  font-size: 24px;
  font-weight: 800;
  letter-spacing: 2px;
}

.msg-join-us {
  font-size: 16px;
  font-weight: 500;
}

.image-body {
  margin-top: 16px;
  display: flex;
  justify-content: center;
  align-self: center;
}

.login-image {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background-color: #dedfe0;
}
</style>
