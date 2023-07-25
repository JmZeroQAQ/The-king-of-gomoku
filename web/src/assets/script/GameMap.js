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

        // 获胜棋子的位置
        this.winPosition = [];

        // 游戏是否结束
        this.isOver = false;
        // 获胜的棋子的方向
        this.dResult = []; // 0 表示水平方向，1表示竖直方向，2表示左下方向，3表示右下方向
    }

    addListeningEvents() {
        this.ctx.canvas.focus();

        this.ctx.canvas.addEventListener("click", e => {
            
            const x = parseInt(e.offsetY / this.scale);
            const y = parseInt(e.offsetX / this.scale);

            if(this.gameMap[x][y] !== 0 || this.isOver) return ;
            
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
                        this.chessPieces.push(new ChessPiece({x, y, step: this.step, color: "#303133"}, this));
                    } else if(this.store.gameMap[i][j] === 2) {
                        this.chessPieces.push(new ChessPiece({x, y, step: this.step, color: "#FAFCFF"}, this));
                    }
                    this.gameMap = JSON.parse(JSON.stringify(this.store.gameMap));
                }
            }
        }
    }

    setChessPieceIsRenderMark(x, y) {
        for(const chessPiece of this.chessPieces) {
            if(chessPiece.x === x && chessPiece.y === y) {
                chessPiece.isRenderMark = true;
                break;
            }
        }
    }

    // 检查局面
    checkSituation() {
        for(let i = 0; i < 12; i++) {
            for(let j = 0; j < 12; j++) {
                if(this.gameMap[i][j] !== 0) {
                    const dx = 1, dy = 1;
                    const currentValue = this.gameMap[i][j];

                    // 判断水平方向
                    for(let k = 0; k < 4; k++) {
                        const ny = j + dy * (k + 1);
                        if(ny >= this.cols || this.gameMap[i][ny] !== currentValue) break;
                        if(k === 3) this.dResult.push(0);
                    }
                    // 判断竖直方向
                    for(let k = 0; k < 4; k++) {
                        const nx = i + dx * (k + 1);
                        if(nx >= this.rows || this.gameMap[nx][j] !== currentValue) {
                            break;
                        }
                        if(k === 3) {
                            this.dResult.push(1);
                        }
                    }

                    // 判断左下方向
                    for(let k = 0; k < 4; k++) {
                        const nx = i + dx * (k + 1);
                        const ny = j - dy * (k + 1);
                        if(nx >= this.rows || ny < 0 ||  this.gameMap[nx][ny] !== currentValue) {
                            break;
                        }
                        if(k === 3) {
                            this.dResult.push(2);
                        }
                    }

                    // 判断右下方向
                    for(let k = 0; k < 4; k++) {
                        const nx = i + dx * (k + 1);
                        const ny = j + dy * (k + 1);
                        if(nx >= this.rows || ny >= this.cols || this.gameMap[nx][ny] !== currentValue) {
                            break;
                        }
                        if(k === 3) {
                            this.dResult.push(3);
                        }
                    }

                    if(this.dResult.length > 0) {
                        this.isOver = true;

                        this.setChessPieceIsRenderMark(i, j);
                        for(const d of this.dResult) {
                            if(d === 0) {
                                for(let k = 0; k < 4; k++) {
                                    const ny = j + dy * (k + 1);
                                    this.setChessPieceIsRenderMark(i, ny);
                                }
                            } else if(d === 1) {
                                for(let k = 0; k < 4; k++) {
                                    const nx = i + dx * (k + 1);
                                    this.setChessPieceIsRenderMark(nx, j);
                                }
                            } else if(d === 2) {
                                for(let k = 0; k < 4; k++) {
                                    const nx = i + dx * (k + 1);
                                    const ny = j - dy * (k + 1);
                                    this.setChessPieceIsRenderMark(nx, ny);
                                }
                            } else if(d === 3) {
                                for(let k = 0; k < 4; k++) {
                                    const nx = i + dx * (k + 1);
                                    const ny = j + dy * (k + 1);
                                    this.setChessPieceIsRenderMark(nx, ny);
                                }
                            }
                        }
                        this.dResult = [];
                    }
                }
            }
        }
    }

    update() {
        this.udpate_size();
        if(this.isOver === false) {
            // 添加新放置的棋子
            this.udpateChessPiece();
            // 判断局面
            this.checkSituation();
        }
        this.render();
        
    }

    render() {
        const color_even = "#eebe77", color_odd = "#f8e3c5";
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