const BASE_GAME_OBJECTS = [];

export class BaseGameObject {
    constructor() {
        BASE_GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    start() { // 第一帧执行一次

    }

    update() { // 每一帧执行一次,除了第一帧

    }

    onDestroy() { // 删除之前执行一次

    }

    destroy() {
        this.onDestroy();
        for(let i in BASE_GAME_OBJECTS) {
            const obj = BASE_GAME_OBJECTS[i];
            if(obj == this) {
                BASE_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}

let last_timestamp; // 上一次执行的时间

const step = timestamp => {
    for(let obj of BASE_GAME_OBJECTS) {
        if(!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    requestAnimationFrame(step);
}

requestAnimationFrame(step);