<template>
    <div ref="parent" class="gamemap">
        <canvas ref="canvas" tabindex="0"></canvas>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { GameMap } from '@/assets/script/GameMap';

const parent = ref(null);
const canvas = ref(null);

onMounted(() => {
    const gameMap = new GameMap(canvas.value.getContext('2d'), parent.value);
});

</script>

<style lang="scss" scoped>
.gamemap {
    /* background-color: antiquewhite; */
    height: 80vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.gamemap>canvas {
    outline: none;
}
</style>