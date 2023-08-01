<template>
  <el-row justify="center" :gutter="20">
    <el-col :span="14">
      <el-card>
        <h1 class="title">排行榜</h1>
        <el-table :data="filterTableData" style="width: 100%" max-height="74vh">
          <el-table-column label="No." prop="name" />
          <el-table-column label="昵称" prop="name" />
          <el-table-column label="Rating" prop="rating" />
          <el-table-column label="场次" prop="counts" />
          <el-table-column label="胜率" prop="win" />
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="6">
        <el-card>
            <div class="my-rank">
                <h3>我的排名</h3>
                <div>
                    <span class="label">昵称: </span> 
                    <span>JmZeroQAQ</span>
                </div>
                <div>
                    <span class="label">排名: </span>
                    <span>No.1</span>
                </div>
                <div>
                    <span class="label">Rating: </span>
                    <span>2000</span>
                </div>
                <div>
                    <span class="label">场次: </span>
                    <span>900</span>
                </div>
                <div>
                    <span class="label">胜率: </span>
                    <span>56%</span>
                </div>
            </div>
            
        </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
const filterTableData = [
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
    {
        name: "123",
        rating: "1500",
        counts: 301,
        win: "55%",
    },
]
</script>

<style lang="scss" scoped>
.title {
    text-align: center;
}

.my-rank {
    h3 {
        text-align: center;
    }

    .label {
        font-weight: 600;
    }
}
</style>
