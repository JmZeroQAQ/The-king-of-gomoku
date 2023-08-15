<template>
  <el-row justify="center" :gutter="20">
    <el-col :span="6" class="profile-card">
      <el-card>
        <div class="avatar">
          <img
            src="https://ranunculus.top/media/images/20221111175900100.png"
            alt=""
          />

          <div class="username">{{ user.name }}</div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="14">
      <el-card>
        <h1 style="text-align: center">我的对局</h1>
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
              <el-button @click="getRecordsList(1)" circle
                ><el-icon><Refresh /></el-icon
              ></el-button>
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
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";
import { getMyRecords } from "@/apis/record";
import { ElMessage } from 'element-plus';

const userStore = useUserStore();
const { user, token } = storeToRefs(userStore);

const loading = ref(true);
const records = ref([]);

async function getRecordsList(page) {
  const data = await getMyRecords(token.value, page);
  if (data.message === "success") {
    records.value = data.records;
    loading.value = false;

    ElMessage({
      message: "获取记录成功",
      type: "success",
    });
  } else {
    ElMessage.error(data.message);
  }
}

onMounted(() => {
  getRecordsList(1);
});

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

<style lang="scss" scoped>
.profile-card {
  .avatar {
    img {
      display: block;
      max-width: 300px;
      margin: 0 auto;
      border: 1px solid #dedfe0;
      border-radius: 5px;
    }

    .username {
      margin-top: 20px;
      text-align: center;
      font-weight: 600;
    }
  }
}
</style>
