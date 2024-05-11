import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue( {
	...App
} )
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import {
	Request
} from "./utils/request.js"
// const host = "127.0.0.1:3000"
export function createApp() {
	const app = createSSRApp( App )
	app.config.globalProperties.$request = Request;
	// app.config.globalProperties.$host = 'http://1el9898253.oicp.vip/';
	app.config.globalProperties.$host = 'http://10.201.133.232:3000';
	// app.config.globalProperties.$host = 'http://127.0.0.1:3000';

	return {
		app
	}
}
// #endif