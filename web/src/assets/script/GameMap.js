import { BaseGameObject } from "./BaseGameObject";
import { ChessPiece } from "./ChessPiece";
import { useGameStore } from "@/store/game";
import { storeToRefs } from "pinia";

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

    this.gameStore = useGameStore();

    // 棋盘中的棋子
    this.chessPieces = [];

    // 棋子颜色
    this.blackChessColor = "black";
    this.whiteChessColor = "white";
  }

  addListeningEvents() {
    this.ctx.canvas.focus();

    const { color, webSocket } = storeToRefs(this.gameStore);
    this.ctx.canvas.addEventListener("click", (e) => {
      const x = parseInt(e.offsetY / this.scale);
      const y = parseInt(e.offsetX / this.scale);

      if (color.value === "black" && this.step % 2 === 0) {
        webSocket.value.send(
          JSON.stringify({
            event: "move",
            position: this.cols * x + y,
          })
        );
      } else if (color.value === "white" && this.step % 2 === 1) {
        webSocket.value.send(
          JSON.stringify({
            event: "move",
            position: this.cols * x + y,
          })
        );
      }
    });
  }

  start() {
    this.addListeningEvents();
  }

  // 实时更新棋盘大小
  udpate_size() {
    this.scale = parseInt(
      Math.min(
        this.parent.clientWidth / this.cols,
        this.parent.clientHeight / this.rows
      )
    );
    this.ctx.canvas.width = this.scale * this.cols;
    this.ctx.canvas.height = this.scale * this.rows;
  }

  setChessPieceIsRenderMark(x, y) {
    for (const chessPiece of this.chessPieces) {
      if (chessPiece.x === x && chessPiece.y === y) {
        chessPiece.isRenderMark = true;
        break;
      }
    }
  }

  udpateChessPiece() {
    const { position, isUpdated, gameStat, winSet } = storeToRefs(
      this.gameStore
    );

    const { setGameStat, setIsUpdated } = this.gameStore;

    if (gameStat.value === "running") {
      if (isUpdated.value === true) {
        // 表示已经读取过更新了
        setIsUpdated(false);

        const x = parseInt(position.value / this.cols);
        const y = parseInt(position.value % this.cols);

        // 更新黑色方棋子
        if (this.step % 2 === 0) {
          this.chessPieces.push(
            new ChessPiece(
              { x, y, color: this.blackChessColor, step: this.step },
              this
            )
          );
        } else {
          // 更新白色方棋子
          this.chessPieces.push(
            new ChessPiece(
              { x, y, color: this.whiteChessColor, step: this.step },
              this
            )
          );
        }

        // 开始下一回合
        this.step++;
      }
    } else if (gameStat.value === "over") {
      setGameStat("idle");

      for (let i = 0; i < winSet.value.length; i++) {
        const x = parseInt(winSet.value[i] / this.cols);
        const y = parseInt(winSet.value[i] % this.cols);
        console.log("x: " + x + " y: " + y);

        this.setChessPieceIsRenderMark(x, y);
      }

      return;
    } else if (gameStat.value === "idle") {
      return;
    }
  }

  update() {
    this.udpate_size();
    // 添加新放置的棋子
    this.udpateChessPiece();
    this.render();
  }

  // 渲染棋盘
  render() {
    const color_even = "#eebe77",
      color_odd = "#f8e3c5";
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if ((r + c) % 2 == 0) {
          this.ctx.fillStyle = color_even;
        } else {
          this.ctx.fillStyle = color_odd;
        }
        this.ctx.fillRect(
          c * this.scale,
          r * this.scale,
          this.scale,
          this.scale
        );
      }
    }
  }
}
