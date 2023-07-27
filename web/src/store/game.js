import { defineStore } from 'pinia';
import { reactive, ref } from 'vue';

export const useGameStore = defineStore('gameStore', () => {
    const position = ref(-1);
    const isUpdated = ref(false);
    const color = ref("");
    const gameStat = ref("idle"); // 三种状态: idle, running, over;
    const winSet = ref(null);

    const webSocket = ref(null);

    const opponent = reactive({
        name: "",
        avatar: "",
        rating: "",
    });

    function setColor(newColor) {
        color.value = newColor;
    }

    function setGameStat(newStat) {
        gameStat.value = newStat;
    }

    function updatePosition(newPosition) {
        position.value = newPosition;
        isUpdated.value = true;
    }

    function setWinSet(newSet) {
        winSet.value = newSet;
    }

    function setWebSocket(newWebSocket) {
        webSocket.value = newWebSocket;
    }

    function setOpponent(newOpponent) {
        opponent.name = newOpponent.name;
        opponent.avatar = newOpponent.avatar;
        opponent.rating = newOpponent.rating;
    }

    return {
        opponent,
        webSocket,
        setWebSocket,
        updatePosition,
        setOpponent,
        setColor,
        isUpdated,
        position,
        color,
        winSet,
        setWinSet,
        gameStat,
        setGameStat,
    }
})