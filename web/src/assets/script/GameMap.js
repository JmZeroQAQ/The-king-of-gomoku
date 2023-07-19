import { BaseGameObject } from "./BaseGameObject";
import { ChessPiece } from "./ChessPiece";
import { useGameStore } from '@/store/game';

export class GameMap extends BaseGameObject {
    constructor(ctx, parent) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        // 单位长度
        this.scale = 0;
        // 棋盘大小
        this.rows = 12;
        this.cols = 12;

        // 步数, 偶数步为黑子回合,奇数步为白子回合
        this.step = 0;

        // 获取地图
        this.store = useGameStore();
        this.gameMap = JSON.parse(JSON.stringify(this.store.gameMap));

        // 棋盘中的棋子
        this.chessPieces = [];
    }

    addListeningEvents() {
        this.ctx.canvas.focus();

        this.ctx.canvas.addEventListener("click", e => {
            const x = parseInt(e.offsetY / this.scale);
            const y = parseInt(e.offsetX / this.scale);

            if(this.gameMap[x][y] !== 0) return ;
            
            if(this.step % 2 === 0) {
                this.store.setChessPiece(x, y, 1);
            } else {
                this.store.setChessPiece(x, y, 2);
            }
            this.step++;
        });
    }

    start() {
        this.addListeningEvents();
    }

    udpate_size() {
        this.scale = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.scale * this.cols;
        this.ctx.canvas.height = this.scale * this.rows;
    }

    udpateChessPiece() {
        for(let i = 0; i < this.rows; i++) {
            for(let j = 0; j < this.cols; j++) {
                if(this.gameMap[i][j] !== this.store.gameMap[i][j]) {
                    const x = i;
                    const y = j;
                    if(this.store.gameMap[i][j] === 1) {
                        this.chessPieces.push(new ChessPiece({x, y, step: this.step, color: "black"}, this));
                    } else if(this.store.gameMap[i][j] === 2) {
                        this.chessPieces.push(new ChessPiece({x, y, step: this.step, color: "white"}, this));
                    }
                    this.gameMap = JSON.parse(JSON.stringify(this.store.gameMap));
                }
            }
        }
    }

    update() {
        this.udpate_size();
        // 添加新放置的棋子
        this.udpateChessPiece();
        this.render();
    }

    render() {
        const color_even = "#046A9E", color_odd = "#7DB6BF";
        for(let r = 0; r < this.rows; r++) {
            for(let c = 0; c < this.cols; c++) {
                if((r + c) % 2 == 0) {
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.scale, r * this.scale, this.scale, this.scale);
            }
        }
    }
}