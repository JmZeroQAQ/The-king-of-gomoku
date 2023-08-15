<template>
  <div>
    <div class="login-header">
      <h1>登录</h1>

      <el-divider />
    </div>

    <div class="login-body">
      <el-space
        class="login-input"
        direction="vertical"
        size="large"
        :fill="true"
      >
        <div>
          <!-- 提示信息 -->
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
      </el-space>

      <el-row class="login-button-container" justify="center">
        <el-col :span="10">
          <el-button
            class="login-button-container-button"
            size="large"
            type="primary"
            @click="loginEvent"
            v-loading="loginLoading"
            :disabled="loginDisabled"
            >登录</el-button
          >
        </el-col>
      </el-row>

      <el-row>
        <el-col class="register-link">
          <el-link type="primary" @click="() => $emit('setState', 'register')"
            >注册一个账号</el-link
          >
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onUnmounted } from "vue";
import { login } from "@/apis/login";
import { useUserStore } from "@/store/user";
import { useRouter, useRoute } from "vue-router";
import { setToken as setTokenToLocalStorage } from "@/utils/storage";

const router = useRouter();
const route = useRoute();

const loginLoading = ref(false);
const loginDisabled = ref(false);

const alertNotice = ref("请输入账号密码");
const username = ref("");
const password = ref("");

// alert的类型
const alertType = ref("info");

// 路由跳转定时器的Id
let routerSkipId = null;
onUnmounted(() => {
  if (routerSkipId !== null) {
    clearTimeout(routerSkipId);
    routerSkipId = null;
  }
});

async function loginEvent() {
  loginLoading.value = true;
  loginDisabled.value = true;
  const data = await login(username.value, password.value);

  if (data.message === "success") {
    const { setToken, asyncGetInfo, setIsAuth } = useUserStore();

    setToken(data.token);
    // 将令牌存放到localStorage中
    setTokenToLocalStorage(data.token);
    // 获取用户信息
    asyncGetInfo();

    loginLoading.value = false;
    alertNotice.value = "登录成功!";
    alertType.value = "success";

    // 登录成功1.2s后跳转
    routerSkipId = setTimeout(() => {
      setIsAuth(true);
      
      if (route.query.redirect != null) {
        router.push({ path: route.query.redirect });
      } else {
        router.push({ name: "home" });
      }
    }, 1200);
  } else {
    loginLoading.value = false;
    loginDisabled.value = false;
    
    alertNotice.value = data.message;
    alertType.value = "error";
  }
}

function getAlertEffect() {
  if (alertType.value === "info") return "light";
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
  color: #337ecc;
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
