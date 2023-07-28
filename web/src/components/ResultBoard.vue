<template>
    <el-card>
        <div class="content">{{ loserName === user.name ? 'Lose' : 'Win' }}</div>
    </el-card>
</template>

<script setup>
import { useGameStore } from '@/store/game';
import { useUserStore } from '@/store/user';

import { storeToRefs } from 'pinia';

const gameStore = useGameStore();
const userStore = useUserStore();
const { loserName } = storeToRefs(gameStore);
const { user } = storeToRefs(userStore);

</script>

<style lang="scss" scoped>

.content {
    font-size: 36px;
    font-weight: 800;
}

</style>