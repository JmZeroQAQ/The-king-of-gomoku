<template>
  <el-row justify="center">
    <el-col :span="18">
      <GameMap :current_step="current_step" />
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import GameMap from '@/components/GameMap.vue';
import { getRecordInfo } from '@/apis/record';

const current_step = ref(0);

async function getRecord(recordId) {
    const data = await getRecordInfo(20);
    console.log(data);
}

onMounted(() => {
    getRecord();
});

</script>

<style lang="scss" scoped></style>
