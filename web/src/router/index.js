import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';

export const routes = [
    {
        path: "/",
        component: HomeView,
        name: "home"
    },
];

const router = createRouter({
    routes,
    history: createWebHistory(),
});

export default router;