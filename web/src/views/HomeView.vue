<template>
  <div class="home">
    <div class="home-content">
      <h2>WELLCOME TO</h2>
      <h1>The King Of Gomoku</h1>
      <p>亲自出马，或者创造Bot来击败对方，获得胜利吧！</p>
      <div class="start-button">
        <el-button @click="startGameOnClick" round>
          <el-icon color="#409EFF" size="1.4rem"><StarFilled /></el-icon>
          即刻开始
        </el-button>
      </div>
    </div>
    <div class="home-image">
      <img
        src="https://ranunculus.top/media/images/20221111175900100.png"
        alt=""
      />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

function startGameOnClick() {
  router.push({ path: "/game/" });
}
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

      opacity: 0;
      animation: slideBottom 1s ease forwards;
      animation-delay: 0.7s;
    }

    h1 {
      margin-top: 10px;
      font-size: 46px;

      opacity: 0;
      animation: slideLeft 1s ease forwards;
      animation-delay: 0.7s;
    }

    p {
      margin-top: 10px;
      text-align: center;

      opacity: 0;
      animation: slideTop 1s ease forwards;
      animation-delay: 0.5s;
    }

    .start-button {
      margin-top: 10px;
      text-align: center;
      opacity: 0;
      animation: slideTop 1s ease forwards;
      animation-delay: 1s;

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

      opacity: 0;
      animation: zoomIn 1s ease forwards, floatImage 4s ease-in-out infinite;
      animation-delay: 1s, 2s;
    }
  }
}

@keyframes slideBottom {
  0% {
    transform: translateY(-100px);
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes slideLeft {
  0% {
    transform: translateX(100px);
  }

  100% {
    transform: translateX(0);
    opacity: 1;
  }
}

@keyframes slideTop {
  0% {
    transform: translateY(100px);
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes zoomIn {
  0% {
    transform: scale(0);
  }

  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes floatImage {
  0% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-24px);
  }

  100% {
    transform: translateY(0);
  }
}
</style>
