import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import request from "./utils/request.js";
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';


const app = createApp(App);
//使用axios
app.config.globalProperties.request = request
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate);
app.use(router).use(Antd).use(pinia).mount('#app')
