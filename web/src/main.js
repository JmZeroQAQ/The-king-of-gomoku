import { createApp } from 'vue'
import '@/assets/reset.scss'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router/index'; //导入路由
import { createPinia } from 'pinia'

const app = createApp(App);
app.use(router);

const pinia = createPinia();
app.use(pinia);

app.use(ElementPlus);

for(const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.mount('#app');
