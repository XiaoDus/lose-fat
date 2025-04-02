import {defineStore} from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate';
export const useCounterStore = defineStore('userStore', {
    state: () => ({

    }),
    actions: {
        logout() {
            // 重置状态
            this.$reset();
            // 清除持久化存储的数据
            localStorage.removeItem('userStore');  // 自动清除持久化的数据
        },
    },
    persist:true
})
