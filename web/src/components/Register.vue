<template>
  <div>
    <div class="login-header">
      <h1>注册</h1>

      <el-divider />
    </div>

    <div class="login-body">
      <el-space
        class="login-input"
        direction="vertical"
        size="large"
        :fill="true"
      >
        <div class="login-msg">
          <el-alert
            :title="alertNotice"
            :type="alertType"
            :closable="false"
            show-icon
            :effect="getAlertEffect()"
          />
        </div>

        <el-input
          v-model="username"
          size="large"
          placeholder="用户名"
          clearable
        />
        <el-input
          v-model="password"
          size="large"
          type="password"
          placeholder="请输入密码"
          show-password
        />
        <el-input
          v-model="confirmed_password"
          size="large"
          type="password"
          placeholder="确认密码"
          show-password
        />
      </el-space>

      <el-row class="login-button-container" justify="center">
        <el-col :span="10">
          <el-button
            class="login-button-container-button"
            size="large"
            type="primary"
            @click="registerEvent"
            v-loading="registerLoading"
            :disabled="registerDisabled"
            >注册</el-button
          >
        </el-col>
      </el-row>

      <el-row>
        <el-col class="register-link">
          <el-link
           type="primary"
           @click="() => $emit('setState', 'login')"
           >登录</el-link>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { register } from '@/apis/register';

const registerLoading = ref(false);
const registerDisabled = ref(false);

const alertNotice = ref("注册账号");
const username = ref("");
const password = ref("");
const confirmed_password = ref("");

// alert的类型
const alertType = ref("info");

const emit = defineEmits(["setState"]);


async function registerEvent() {
  registerLoading.value = true;
  registerDisabled.value = true;
  const data = await register(username.value, password.value, confirmed_password.value);
  
  if(data.message === "success") {
    registerLoading.value = false;
    alertNotice.value = "账号注册成功";
    alertType.value = "success";

    setTimeout(() => {
      emit("setState", "login");
    }, 1000);

  } else {
    registerLoading.value = false;
    registerDisabled.value = false;
    alertNotice.value = data.message;
    alertType.value = "error";
  }
}

function getAlertEffect() {
  if(alertType.value === "info") return "light";
  return "dark";
}

</script>

<style lang="scss" scoped>
.el-alert {
  transition: all 1s ease;
}

.login-header {
    padding-left: 20px;
}

.login-header h1 {
  text-align: center;
  color: #337ECC;
}

.login-body {
  padding-left: 40px;
  padding-right: 20px;
}

.login-input {
    width: 100%;
}

.login-input input {
    width: 100%;
}

.login-button-container {
    margin-top: 20px;
}

.login-button-container-button {
    width: 100%;
    padding: 10px;
}

.register-link {
  margin-top: 5px;
  display: flex;
  justify-content: flex-end;
}
</style>