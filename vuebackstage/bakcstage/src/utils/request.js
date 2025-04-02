import axios from 'axios'
import { getActivePinia } from "pinia";
import {useCounterStore} from '../store/index.js'
import {message} from 'ant-design-vue';
import router from '../router/index.js';
const request = axios.create({
    //baseURL: 'http://8.137.39.90:9090',
    baseURL: import.meta.env.VITE_APP_API_URL_DEV,
    timeout: 5000,
    withCredentials: true, // 让请求自动携带 Cookie
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    const pinia = getActivePinia();
    const userStore = useCounterStore(pinia);
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    if (userStore){
        config.headers['token'] = userStore.$state.token;  // 设置请求头
    }

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (res.code === '401'){
            message.error('登录已过期，请重新登录');
            // localStorage.removeItem('token'); // 清除本地 token
            router.replace("/login")
            // return
        }
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        return Promise.reject(error)
    }
)


export default request

