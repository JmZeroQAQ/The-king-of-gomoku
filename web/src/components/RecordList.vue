<template>
  <el-card>
    <h1 style="text-align: center">对局记录</h1>
    <el-table :data="filterTableData" style="width: 100%" max-height="100vh - 180px">
      <el-table-column align="center" label="PlayerA" prop="nameA" />
      <el-table-column align="center" label="PlayerB" prop="nameB" />
      <el-table-column align="center" label="Winner" prop="winName" />
      <el-table-column align="center" label="Date" prop="date" />
      <el-table-column align="center">
        <template #header>
          <el-input
            v-model="search"
            size="small"
            placeholder="Type to search"
          />
        </template>
        <template #default="scope">
          <el-button type="primary" size="small">回放</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { computed, ref } from "vue";

const search = ref("");
const filterTableData = computed(() =>
  tableData.filter(
    (data) =>
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase())
  )
);
const handleEdit = (index, row) => {
  console.log(index, row);
};
const handleDelete = (index, row) => {
  console.log(index, row);
};

const tableData = [
  {
    nameA: "JmZero",
    nameB: "Mogu",
    winName: "JmZero",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "QAQ",
    nameB: "zxc",
    winName: "zxc",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "123",
    nameB: "asd",
    winName: "123",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "JmZero",
    nameB: "Mogu",
    winName: "JmZero",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "QAQ",
    nameB: "zxc",
    winName: "zxc",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "123",
    nameB: "asd",
    winName: "123",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "JmZero",
    nameB: "Mogu",
    winName: "JmZero",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "QAQ",
    nameB: "zxc",
    winName: "zxc",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "123",
    nameB: "asd",
    winName: "123",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "JmZero",
    nameB: "Mogu",
    winName: "JmZero",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "QAQ",
    nameB: "zxc",
    winName: "zxc",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "123",
    nameB: "asd",
    winName: "123",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "JmZero",
    nameB: "Mogu",
    winName: "JmZero",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "QAQ",
    nameB: "zxc",
    winName: "zxc",
    date: "2023-07-29 15:17",
  },
  {
    nameA: "123",
    nameB: "asd",
    winName: "123",
    date: "2023-07-29 15:17",
  },
];
</script>

<style lang="scss" scoped>
</style>
