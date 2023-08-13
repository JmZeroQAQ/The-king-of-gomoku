<template>
  <div class="gamemap">
    <div class="notice">
      <div class="notice-card">
        <div class="notice-content">{{ getContent() }}</div>
        <div :class="`notice-chess ${current_color}`"></div>
      </div>
    </div>
    <div ref="parent" class="map">
      <canvas ref="canvas" tabindex="0"></canvas>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted } from "vue";
import { GameMap } from "@/assets/script/GameMap";
import { useGameStore } from "@/store/game";
import { storeToRefs } from "pinia";

const props = defineProps({
  current_step: Number,
  isRecord: Boolean,
  players: Object,
});

const parent = ref(null);
const canvas = ref(null);
const current_color = ref("black");
const gameStore = useGameStore();
const { color } = storeToRefs(gameStore);

watch(
  () => props.current_step,
  (newVal) => {
    if (newVal % 2 == 0) current_color.value = "black";
    else current_color.value = "white";
  }
);

function getContent() {
  if (props.isRecord) {
    if (props.current_step % 2 === 0) return props.players.aName;
    else return props.players.bName;
  } else {
    if (
      (props.current_step % 2 === 0 && color.value === "black") ||
      (props.current_step % 2 === 1 && color.value === "white")
    )
      return "你的回合";
    else return "对手回合";
  }
}

let gameMap = null;
onMounted(() => {
  gameMap = new GameMap(canvas.value.getContext("2d"), parent.value);
});

// 卸载时把地图以及棋子清空
onUnmounted(() => {
  gameMap.destroy();
});
</script>

<style lang="scss" scoped>
.gamemap {
  /* background-color: antiquewhite; */
  height: 80vh;
  background-color: transparent;
  display: flex;
  justify-content: center;

  .map {
    width: 65%;
    height: 100%;

    canvas {
      outline: none;
    }
  }

  .notice {
    margin-top: 10%;
    margin-right: 5%;
    .notice-card {
      width: 130px;
      padding: 10px;
      background-color: rgba($color: #eebe77, $alpha: 0.4);
      border-radius: 10px;
      overflow: hidden;

      .notice-content {
        font-size: 20px;
        font-weight: 600;
        text-align: center;
        user-select: none;
      }

      .notice-chess {
        margin: 10px auto 0;
        width: 15px;
        height: 15px;
        border-radius: 50%;
        transition: background-color 0.5s ease;
      }

      .black {
        background-color: black;
      }

      .white {
        background-color: white;
      }
    }
  }
}
</style>
