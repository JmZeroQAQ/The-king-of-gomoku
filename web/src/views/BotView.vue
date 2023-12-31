<template>
  <el-row justify="center">
    <el-col :span="18">
      <el-card>
        <h1 style="text-align: center">我的Bot</h1>
        <el-table
          :data="botData"
          v-loading="botListLoading"
          style="width: 100%"
          max-height="100vh - 180px"
        >
          <el-table-column align="center" label="创建时间" prop="createTime" />
          <el-table-column align="center" label="Bot名字" prop="title" />
          <el-table-column align="center" label="描述" prop="description" />
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
              <el-button
                type="primary"
                size="small"
                @click="modifyBotOnClick(scope.row)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="small"
                @click="removeBotOnClick(scope.row.id)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-dialog
        v-model="createVisible"
        :show-close="false"
        width="70%"
        top="8vh"
      >
        <template #header="{ titleId, titleClass }">
          <div class="my-header">
            <h4 :id="titleId" :class="titleClass">创建Bot</h4>
          </div>
        </template>

        <el-form :model="bot">
          <el-form-item label="Bot名字">
            <el-input
              v-model="bot.title"
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
          <VAceEditor
            v-model:value="bot.content"
            class="editor"
            lang="c_cpp"
            theme="textmate"
            :options="{
              fontSize: 16,
              wrap: true,
              showPrintMargin: false,
            }"
            style="height: 300px; width: 100%"
          />
        </el-form-item>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="createVisible = false">Cancel</el-button>
            <el-button
              :disabled="addBotLoading"
              v-loading="addBotLoading"
              type="primary"
              @click="addBotOnClick"
            >
              提交
            </el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog
        v-model="updateVisible"
        :show-close="false"
        width="70%"
        top="8vh"
      >
        <template #header="{ titleId, titleClass }">
          <div class="my-header">
            <h4 :id="titleId" :class="titleClass">修改Bot</h4>
          </div>
        </template>

        <el-form :model="bot">
          <el-form-item label="Bot名字">
            <el-input
              v-model="updatedBot.title"
              placeholder="请输入Bot名字"
              maxlength="100"
              clearable
            />
          </el-form-item>
        </el-form>

        <el-form-item label="Bot描述">
          <el-input
            v-model="updatedBot.description"
            placeholder="请输入Bot描述"
            maxlength="100"
            type="textarea"
          />
        </el-form-item>

        <el-form-item label="Bot代码">
          <VAceEditor
            v-model:value="updatedBot.content"
            class="editor"
            lang="c_cpp"
            theme="textmate"
            :options="{
              fontSize: 16,
              wrap: true,
              showPrintMargin: false,
            }"
            style="height: 300px; width: 100%"
          />
        </el-form-item>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="updateVisible = false">Cancel</el-button>
            <el-button
              v-loading="updateBotLoading"
              :disabled="updateBotLoading"
              type="primary"
              @click="updateBotOnClick"
            >
              修改
            </el-button>
          </span>
        </template>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import { addBot } from "@/apis/addBot";
import { getBotList } from "@/apis/getBotList";
import { removeBot } from "@/apis/removeBot";
import { updateBot } from "@/apis/updateBot";
import { VAceEditor } from "vue3-ace-editor";
import { ElMessage } from "element-plus";

import "ace-builds/src-min-noconflict/mode-c_cpp";
import "ace-builds/src-min-noconflict/theme-textmate";
import "ace-builds/src-min-noconflict/ext-language_tools";

// 创建bot的模态框
const createVisible = ref(false);

// 加载动画控制
const botListLoading = ref(true);
const addBotLoading = ref(false);
const updateBotLoading = ref(false);

const bot = reactive({
  title: "",
  description: "",
  content: `#include <iostream>

using namespace std;
const int N = 20;

int color;
int rows, cols;
int result;

int a[N][N];

int main() {
    cin >> color;
    cin >> rows >> cols;
    for(int i = 0; i < rows; i++)
        for(int j = 0; j < cols; j++)
            cin >> a[i][j];
            
    cout << result << endl;
    return 0;
}
`,
});

const userStore = useUserStore();
const { token } = storeToRefs(userStore);

const refreshBots = async () => {
  const data = await getBotList(token.value);
  if (data.message === "success") {
    botData.value = data.bots;
    botListLoading.value = false;
  }
};

onMounted(() => {
  refreshBots();
});

const addBotOnClick = async () => {
  addBotLoading.value = true;
  const data = await addBot(token.value, bot);
  if (data.message === "success") {
    addBotLoading.value = false;
    createVisible.value = false;

    ElMessage({
      message: "添加Bot成功",
      type: "success",
    });

    // 刷新Bot列表
    refreshBots();

    bot.title = "";
    bot.description = "";
    bot.content = "";
  } else {
    addBotLoading.value = false;
    ElMessage.error(data.message);
  }
};

// 更新Bot的模态框
const updateVisible = ref(false);

const updatedBot = reactive({
  title: "",
  description: "",
  content: "",
  id: -1,
});

// 打开模态框
const modifyBotOnClick = (bot) => {
  updatedBot.title = bot.title;
  updatedBot.description = bot.description;
  updatedBot.content = bot.content;
  updatedBot.id = bot.id;

  updateVisible.value = true;
};

const updateBotOnClick = async () => {
  // 加载动画打开
  updateBotLoading.value = true;

  const data = await updateBot(token.value, updatedBot);
  if (data.message === "success") {
    // 关闭模态框
    updateVisible.value = false;
    // 关闭加载动画
    updateBotLoading.value = false;

    ElMessage({
      message: "修改Bot成功",
      type: "success",
    });

    updatedBot.title = "";
    updatedBot.description = "";
    updatedBot.content = "";
    updatedBot.id = -1;

    refreshBots();
  } else {
    ElMessage.error(data.message);
    updateBotLoading.value = false;
  }
};

const removeBotOnClick = async (botId) => {
  botListLoading.value = true;
  const data = await removeBot(token.value, botId);
  if (data.message === "success") {
    ElMessage({
      message: "删除Bot成功",
      type: "success",
    });
    // 刷新Bot列表
    refreshBots();
  } else {
    botListLoading.value = false;
    ElMessage.error(data.message);
  }
};

const botData = ref([]);
</script>

<style lang="scss" scoped>
.editor {
  border: 1px solid #e9e9eb;
}
</style>
