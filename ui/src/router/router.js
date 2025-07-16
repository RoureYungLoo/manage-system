import {createRouter, createWebHistory} from "vue-router";


const routes = [
  {
    path: "/",
    component: () => import("@/views/Layout.vue"),
    redirect: '/index',
    children: [
      {path: "/emp", component: () => import('@/views/emp/index.vue')},
      {path: "/dept", component: () => import('@/views/dept/index.vue')},
      {path: "/stu", component: () => import('@/views/student/index.vue')},
      {path: "/class", component: () => import('@/views/clazz/index.vue')},
      {path: "/index", component: () => import('@/views/index.vue')},
      {path: "/empstat", component: () => import('@/views/stat/EmpStat.vue')},
      {path: "/stustat", component: () => import('@/views/stat/StuStat.vue')},
      {path: "/logstat", component: () => import('@/views/stat/LogStat.vue')},
    ]
  },
  {path: "/login", component: () => import('@/views/Login.vue')},
]

const router = createRouter({
    history: createWebHistory(),
    routes
  }
);

export default router