// store/index.js
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('userStore', {
    state: () => ({
        userId: '',
        userName: '',
        userPhone: '',
        userSex: '',
        userBirthday: '',
        userAge: 0,
        avatar: '',
        token: '',
        // 如果有其他字段也可以继续加，比如登录状态等
    }),

    actions: {
        // ✅ 通用更新方法
        updateUser(payload) {
            Object.assign(this, payload)
        },

        // ✅ 登出清空
        logout() {
            this.$reset()
            localStorage.removeItem('userStore')
        },
    },

    // ✅ 开启持久化
    persist: true,
})
