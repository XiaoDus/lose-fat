import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import request from "@/utils/request";
const app = createApp(App);
//使用axios
app.config.globalProperties.request = request
app.use(store).use(router).use(Antd).mount('#app')
