<template>
  <el-card>
    <h1 style="text-align: center">对局记录</h1>
    <el-table :data="records" style="width: 100%" max-height="100vh - 180px">
      <el-table-column align="center" label="PlayerA" prop="aName" />
      <el-table-column align="center" label="PlayerB" prop="bName" />
      <el-table-column align="center" label="Winner" prop="winnerName" />
      <el-table-column align="center" label="Date" prop="create_time" />
      <el-table-column align="center">
        <template #header>
          <div>回放</div>
        </template>
        <template #default="scope">
          <el-button type="primary" size="small">回放</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { getAllList } from "@/apis/record";

const records = ref([]);

async function getRecordsList(page) {
  const data = await getAllList(page);
  if(data.message === "success") {
    records.value = data.records;
    console.log(data.records);
  } else {
    console.log(data.message);
  }
}

onMounted(() => {
  getRecordsList(1);
});

</script>

<style lang="scss" scoped>
</style>
