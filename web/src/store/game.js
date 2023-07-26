import { defineStore } from 'pinia';
import { reactive } from 'vue';

export const useGameStore = defineStore('gameStore', () => {
    const gameMap = [];
    // 初始化地图
    // 1: 黑子, 2: 白子, 0: 空白
    for(let i = 0; i < 12; i++) {
        const tmp = [];
        for(let j = 0; j < 12; j++) {
            tmp.push(0);
        }
        gameMap.push(tmp);
    }

    let webSocket = null;
    const opponent = reactive({
        name: "",
        avatar: "",
        rating: "",
    });

    function setChessPiece(x, y, value) {
        gameMap[x][y] = value;
    }

    function setWebSocket(newWebSocket) {
        webSocket = newWebSocket;
    }

    function setOpponent(newOpponent) {
        opponent.name = newOpponent.name;
        opponent.avatar = newOpponent.avatar;
        opponent.rating = newOpponent.rating;
    }

    return {
        gameMap,
        opponent,
        webSocket,
        setWebSocket,
        setChessPiece,
        setOpponent,
    }
})