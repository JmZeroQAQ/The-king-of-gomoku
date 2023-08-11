<template>
  <el-row justify="center">
    <el-col :span="18">
      <el-card>
        <h1 style="text-align: center">我的Bot</h1>
        <el-table
          :data="botData"
          style="width: 100%"
          max-height="100vh - 180px"
        >
          <el-table-column align="center" label="创建时间" prop="createTime" />
          <el-table-column align="center" label="Bot名字" prop="botName" />
          <el-table-column align="center" label="描述" prop="botDescription" />
          <el-table-column align="center" label="上次修改" prop="modifyTime" />
          <el-table-column align="center" label="操作">
            <template #header>
              <el-button
                type="primary"
                size="small"
                @click="createVisible = true"
                >新增Bot</el-button
              >
            </template>
            <template #default="scope">
              <el-button type="primary" size="small">修改</el-button>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-dialog v-model="createVisible" :show-close="false" width="70%">
        <template #header="{ titleId, titleClass }">
          <div class="my-header">
            <h4 :id="titleId" :class="titleClass">创建Bot</h4>
          </div>
        </template>

        <el-form :model="bot">
          <el-form-item label="Bot名字">
            <el-input
              v-model="bot.name"
              placeholder="请输入Bot名字"
              maxlength="100"
              clearable
            />
          </el-form-item>
        </el-form>

        <el-form-item label="Bot描述">
          <el-input
            v-model="bot.description"
            placeholder="请输入Bot描述"
            maxlength="100"
            type="textarea"
          />
        </el-form-item>

        <el-form-item label="Bot代码">
          <el-input
            v-model="bot.content"
            placeholder="请输入Bot代码"
            maxlength="100"
            type="textarea"
          />
        </el-form-item>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="createVisible = false">Cancel</el-button>
            <el-button type="primary"> 提交 </el-button>
          </span>
        </template>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive } from "vue";

const createVisible = ref(false);

const bot = reactive({
  name: "",
  description: "",
  content: "",
});

const botData = ref([
  {
    createTime: "2023-8-8 09:29",
    botName: "bot1",
    botDescription: "12312312312312",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot2",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot3",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot4",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot5",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot1",
    botDescription: "123123123123123",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot2",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot3",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot4",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
  {
    createTime: "2023-8-8 09:29",
    botName: "bot5",
    botDescription: "hello world",
    modifyTime: "2023-8-8 09:29",
  },
]);
</script>

<style lang="scss" scoped></style>
