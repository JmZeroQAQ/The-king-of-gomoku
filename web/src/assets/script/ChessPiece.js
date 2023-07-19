import { BaseGameObject } from "./BaseGameObject"

export class ChessPiece extends BaseGameObject {
    constructor(info, gameMap) {
        super();

        this.x = info.x;
        this.y = info.y;
        this.color = info.color;
        this.currentStep = info.step;
        this.gameMap = gameMap;

        // 是否画出红点标记
        this.isRenderMark = false;
    }

    start() {

    }

    update() {
        this.render();
        if(this.isRenderMark) this.renderMark();
    }

    renderMark() {
        ctx.fillStyle = "red";
        ctx.fillRect(drawX - scale * 0.1, drawY - scale * 0.1, scale * 0.2, scale * 0.2);
    }

    render() {
        const ctx = this.gameMap.ctx;
        const scale = this.gameMap.scale;
        const halfScale = scale / 2;
        const drawX = this.y * scale + halfScale;
        const drawY = this.x * scale + halfScale

        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.arc(drawX, drawY, halfScale * 0.95, 0, Math.PI * 2);
        ctx.fill();
        
        // 当放置完棋子后在中心显示红点
        if(this.currentStep === this.gameMap.step) {
            ctx.fillStyle = "red";
            ctx.fillRect(drawX - scale * 0.1, drawY - scale * 0.1, scale * 0.2, scale * 0.2);
        }
    }
}