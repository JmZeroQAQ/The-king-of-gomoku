<template>
  <el-card>
    <h1 style="text-align: center">近期对局</h1>
    <el-table
      v-loading="loading"
      :data="records"
      style="width: 100%"
      max-height="100vh - 180px"
    >
      <el-table-column align="center" label="PlayerA" prop="aName" />
      <el-table-column align="center" label="PlayerB" prop="bName" />
      <el-table-column align="center" label="Winner" prop="winnerName" />
      <el-table-column align="center" label="Date" prop="create_time" />
      <el-table-column align="center">
        <template #header>
          <el-button
            v-loading="refreshLoading"
            :disabled="refreshLoading"
            @click="refreshList"
            
          >
            <el-icon><Refresh /></el-icon>刷新
          </el-button>
        </template>
        <template #default="scope">
          <el-button
            @click="openRecordOnClick(scope.row.record_id)"
            type="primary"
            size="small"
            >回放</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getAllList } from "@/apis/record";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const loading = ref(true);
const refreshLoading = ref(false);
const records = ref([]);

async function getRecordsList(page) {
  const data = await getAllList(page);
  if (data.message === "success") {
    records.value = data.records;
    loading.value = false;
    refreshLoading.value = false;

    ElMessage({
      message: "获取近期对局成功",
      type: "success",
    });
  } else {
    refreshLoading.value = false;
    ElMessage.error(data.message);
  }
}

onMounted(() => {
  getRecordsList(1);
});

function refreshList() {
  refreshLoading.value = true;
  getRecordsList(1);
}

const router = useRouter();

const openRecordOnClick = (recordId) => {
  router.push({
    path: "/record/",
    query: {
      recordId,
    },
  });
};
</script>

<style lang="scss" scoped></style>
