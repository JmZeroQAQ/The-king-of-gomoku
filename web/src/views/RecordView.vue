<template>
  <el-row justify="center">
    <el-col :span="18">
      <GameMap v-if="gameShow" :current_step="current_step" />
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from "vue";
import GameMap from "@/components/GameMap.vue";
import { getRecordInfo } from "@/apis/record";
import { useGameStore } from "@/store/game";
import { useRoute } from "vue-router";

const gameStore = useGameStore();
const { setIsBot, setGameStat, setWinSet, updatePosition } = gameStore;

const current_step = ref(0);
const recordInfo = {
  historySteps: null,
  winSet: null,
  aName: "",
  bName: "",
  winnerName: "",
};
const gameShow = ref(false);

function startRecord() {
  setIsBot(true);
  setGameStat("running");
  gameShow.value = true;
  console.log(recordInfo.historySteps);

  let k = 0;
  const intervalId = setInterval(() => {
    if (k >= recordInfo.historySteps.length) {
      clearInterval(intervalId);
      setGameStat("over");
      setWinSet(recordInfo.winSet);
    } else {
      updatePosition(recordInfo.historySteps[k]);
      current_step.value++;
    }
    k++;
  }, 1500);
}

async function getRecord(recordId) {
  const data = await getRecordInfo(recordId);
  if (data.message === "success") {
    recordInfo.historySteps = JSON.parse(data.historySteps);
    recordInfo.winSet = JSON.parse(data.win_set);
    recordInfo.aName = data.aName;
    recordInfo.bName = data.bName;
    recordInfo.winnerName = data.winnerName;

    startRecord();

  } else {
    console.log(data.message);
  }
}

const route = useRoute();
onMounted(() => {
  getRecord(route.query.recordId);
});
</script>

<style lang="scss" scoped></style>
