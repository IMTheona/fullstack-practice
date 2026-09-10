import {createRouter, createWebHistory} from 'vue-router'
//导入组件
import LoginVue from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

//路由规则
const routes = [
    {path: '/login',component: LoginVue}, 
    {path: '/',component: Layout}
]

//创建路由对象
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router
