import { defineStore } from 'pinia';

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

    function setChessPiece(x, y, value) {
        gameMap[x][y] = value;
    }

    return {
        gameMap,
        setChessPiece,
    }
})