<template>
  <el-card>
    <el-row justify="space-around">
      <el-col :span="10" class="ranklist">
        <RecordList />
      </el-col>
      <el-col :span="12" class="user">
        <HomeUser />
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import RecordList from "@/components/RecordList.vue";
import HomeUser from "@/components/HomeUser.vue";
</script>

<style lang="scss" scoped>  
</style>
