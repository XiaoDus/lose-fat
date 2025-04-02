import {createRouter, createWebHistory} from 'vue-router'
import { getActivePinia } from "pinia";
import {useCounterStore} from '../store/index.js'
import {message} from 'ant-design-vue';
const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => {
            return import('../views/Login.vue')
        }
    },
    {
        path: '/index',
        name: 'index',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: function () {
            return import('../views/Index.vue')
        }
    },
    { path: '/', redirect: '/index' },
    { path: '/:pathMatch(.*)*', redirect: '/index' } // 未匹配的路径跳转到 404
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})
// 验证登录状态
router.beforeEach(async (to) => {
    const pinia = getActivePinia();
    const userStore = useCounterStore(pinia);
    const token = userStore.$state.token;
    if (!token && to.name !== 'Login') {
        message.error('请先登录！');
        return {name: 'Login'}
    }else if (to.name == 'Login'){
        return true;
    }

})
export default router
