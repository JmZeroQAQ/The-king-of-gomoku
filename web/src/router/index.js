import { createRouter, createWebHistory } from "vue-router";
import BaseLayout from "@/layout/BaseLayout.vue";

export const routes = [
  {
    path: "/",
    redirect: "/home/",
  },
  {
    path: "/home/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/home/HomeView.vue'),
        name: "home",
    }],
  },
  {
    path: "/game/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/game/GameView.vue'),
    }],
  },
  {
    path: "/profile/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/profile/ProfileView.vue'),
    }],
  },
  {
    path: "/login/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/login/LoginView.vue'),
    }],
  },
  {
    path: "/404/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/404/NotFoundView.vue'),
    }],
  },
  // 未匹配的全部重定向到404页面
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
];

const router = createRouter({
  routes,
  history: createWebHistory(),
});

export default router;
