<template>
  <el-row justify="center">
    <el-col :span="14">
      <el-card>
        <h1 class="title">排行榜</h1>
        <el-table
          v-loading="loading"
          :data="rankList"
          style="width: 100%"
          max-height="74vh"
        >
          <el-table-column label="No.">
            <template #default="scope">
              <div>{{ rankList.indexOf(scope.row) + 1 }}</div>
            </template>
          </el-table-column>
          <el-table-column label="昵称">
            <template #default="scope">
              <div class="username">{{ scope.row.name }}</div>
            </template>
          </el-table-column>
          <el-table-column label="rating">
            <template #default="scope">
              <div class="rating">{{ scope.row.rating }}</div>
            </template>
          </el-table-column>
          <el-table-column label="场次" prop="counts" />
          <el-table-column label="胜场" prop="win_counts" />
          <el-table-column label="胜率">
            <template #default="scope">
              <div class="winrate">{{ getWinRate(scope.row) }}</div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getRankList } from "@/apis/rankList";

const loading = ref(true);
const rankList = ref([]);

async function getList(page) {
  const data = await getRankList(page);
  if (data.message === "success") {
    rankList.value = data.rank_list;
    loading.value = false;
  } else {
    console.log(data.message);
  }
}

onMounted(() => {
  getList(1);
});

function getWinRate(user) {
  if (user.counts === 0) return "0%";

  const winRate = (user.win_counts / user.counts).toFixed(2);
  return `${winRate * 100}%`;
}
</script>

<style lang="scss" scoped>
.title {
  text-align: center;
}

.username {
  font-weight: 600;
}

.rating {
    color: #67C23A;
    font-weight: 600;
}

.winrate {
    color: #409EFF;
    font-weight: 600;
}
</style>
