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
            title="注册账号"
            type="info"
            :closable="false"
            show-icon="info"
          />
        </div>

        <el-input
          v-model="user_account"
          size="large"
          placeholder="用户名"
          clearable
        />
        <el-input
          v-model="user_password"
          size="large"
          type="password"
          placeholder="请输入密码"
          show-password
        />
        <el-input
          v-model="user_confirm_password"
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

const user_account = ref("");
const user_password = ref("");
const user_confirm_password = ref("");

</script>

<style lang="scss" scoped>
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