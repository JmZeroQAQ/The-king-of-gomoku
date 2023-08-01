<template>
  <div class="home">
    <div class="home-content">
        <h2>WELLCOME TO</h2>
        <h1>The King Of Gomoku</h1>
        <p>
          亲自出马，或者创造Bot来击败对方，获得胜利吧！
        </p>
        <div class="start-button">
          <el-button round>
            <el-icon color="#409EFF" size="1.4rem"><StarFilled /></el-icon>
            即刻开始
          </el-button>
        </div>
    </div>
    <div class="home-image">
      <img src="https://ranunculus.top/media/images/20221111175900100.png" alt="">
    </div>
  </div>
</template>

<script setup>


</script>

<style lang="scss" scoped>
.home {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 75vh;

    .home-content {
      max-width: 600px;
      padding: 0 50px 0;

      h2 {
        font-size: 32px;
        text-align: center;
      }

      h1 {
        margin-top: 10px;
        font-size: 46px;
      }

      p {
        margin-top: 10px;
        text-align: center;
      }

      .start-button {
        margin-top: 10px;
        text-align: center;

        .el-button {
          font-size: 18px;
          padding: 20px;
        }
      }
    }

    .home-image {
      img {
        height: 320px;
        width: 320px;
        object-fit: cover;
        border-radius: 50%;
      }
    }
}
</style>
