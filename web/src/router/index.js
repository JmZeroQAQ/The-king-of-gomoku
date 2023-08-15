import { createRouter, createWebHistory } from "vue-router";
import BaseLayout from "@/layout/BaseLayout.vue";
import { useUserStore } from "@/store/user";
import { storeToRefs } from "pinia";

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
          requiresAuth: false,
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
          requiresAuth: true,
        }
    }],
  },
  {
    path: "/recordlist/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/RecordListView.vue'),
        meta: {
          // 是否需要登录
          requiresAuth: false,
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
          requiresAuth: false,
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
          requiresAuth: false,
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
          requiresAuth: true,
        }
    }],
  },
  {
    path: "/bot/",
    component: BaseLayout,
    children: [{
        path: "",
        component: () => import('@/views/BotView.vue'),
        meta: {
          // 是否需要登录后才可以进入
          requiresAuth: true,
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
          requiresAuth: false,
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
          requiresAuth: false,
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

// 路由守卫来鉴别页面权限
router.beforeEach((to, from) => {
  const userStore = useUserStore();
  const { isAuth } = storeToRefs(userStore);

  if(to.meta.requiresAuth && !isAuth.value) {
    return {
      path: "/login/",
      query: { redirect: to.fullPath },
    }
  }

  return true;
});

export default router;
