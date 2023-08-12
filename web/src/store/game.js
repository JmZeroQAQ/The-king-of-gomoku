import { defineStore } from 'pinia';
import { reactive, ref } from 'vue';

export const useGameStore = defineStore('gameStore', () => {
    const position = ref(-1);
    const isUpdated = ref(false);
    const color = ref("");
    const gameStat = ref("idle"); // 三种状态: idle, running, over;
    // 存储获胜的棋子
    const winSet = ref(null);

    const webSocket = ref(null);

    const opponent = reactive({
        name: "",
        avatar: "",
        rating: "",
    });

    const isBot = ref(false);

    const winner = ref("");

    function setIsBot(flag) {
        isBot.value = flag;
    }

    function setWinner(newName) {
        winner.value = newName;
    }

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

    function setIsUpdated(flag) {
        isUpdated.value = flag;
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
        setIsUpdated,
        winner,
        setWinner,
        isBot,
        setIsBot,
    }
})