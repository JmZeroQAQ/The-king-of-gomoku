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
        component: () => import('@/views/HomeView.vue'),
        name: "home",
        meta: {
          // 是否需要登录后才可以进入
          isAuth: false,
        }
    }],
  },
  {
    path: "/game/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/GameView.vue'),
        meta: {
          // 是否需要登录
          isAuth: true,
        }
    }],
  },
  {
    path: "/record/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/RecordView.vue'),
        meta: {
          // 是否需要登录
          isAuth: true,
        }
    }],
  },
  {
    path: "/ranklist/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/RanklistView.vue'),
        meta: {
          // 是否需要登录
          isAuth: true,
        }
    }],
  },
  {
    path: "/profile/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/ProfileView.vue'),
        meta: {
          // 是否需要登录后才可以进入
          isAuth: true,
        }
    }],
  },
  {
    path: "/login/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/LoginView.vue'),
        meta: {
          // 是否需要登录后才可以进入
          isAuth: false,
        }
    }],
  },
  {
    path: "/404/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/NotFoundView.vue'),
        meta: {
          // 是否需要登录后才可以进入
          isAuth: false,
        }
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
