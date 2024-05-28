<template>
	<view class="content">
		<view class="title">
			<view class="big-text">更换新的密码</view>
		</view>
		<view class="input" style="justify-content: start">
			<input type="text" v-model="editPwd.newPwd" style="font-size: 18px; padding: 10px" placeholder-style="font-size:16px;font-weight: 300;" placeholder="请输入新密码" />
		</view>
		<view class="input">
			<input
				type="text"
				@input="changeInput"
				v-model="editPwd.rePwd"
				style="font-size: 18px; padding: 10px"
				placeholder-style="font-size:16px;font-weight: 300;"
				placeholder="再次确认密码"
			/>
		</view>

		<view class="button">
			<button @click="savePwd" :class="!nextButton ? 'next-button-true' : 'next-button-false'" type="default" :disabled="nextButton">修改密码</button>
		</view>
	</view>
</template>
<script setup lang="ts">
import { getCurrentInstance, ref } from 'vue';
interface EDITPWD {
	rePwd: string;
	newPwd: string;
}

const editPwd = ref<EDITPWD>({
	rePwd: null,
	newPwd: null
});
const { $request } = getCurrentInstance().appContext.config.globalProperties;
//code输入时
//去空格
const trim = (str) => {
	var reg = /[\t\r\f\n\s]*/g;
	return str.replace(reg, '');
};
const nextButton = ref<boolean>(true);
const changeInput = () => {
	if (editPwd.value.rePwd.length != 0) {
		nextButton.value = false;
	} else {
		nextButton.value = true;
	}
};
const savePwd = async () => {
	if (editPwd.value.newPwd === editPwd.value.rePwd) {
		const res = await $request('/user/updateUserPwd', 'POST', editPwd.value);
		if (res.code === '200') {
			uni.showToast({
				title: '修改成功',
				duration: 2000
			});
			setTimeout(() => {
				uni.redirectTo({
					url: '/pageHome/Account/Account'
				});
			}, 2500);
		} else {
			uni.showToast({
				icon: 'none',
				title: res.message
			});
		}
	} else {
		uni.showToast({
			icon: 'error',
			title: '新旧密码不一致'
		});
	}
};
</script>

<style lang="scss">
.content {
	height: 100vh;
	background-color: #f8f8f8;
	padding: 20px;
	.title {
		.big-text {
			font-size: 22px;
			font-weight: 300;
		}
		.small-text {
			margin-top: 10px;
			font-size: 12px;
			color: #9c9c9c;
		}
	}
	.input {
		margin-top: 20px;
		border-bottom: #9c9c9c 1px solid;
		.text {
			font-size: 13px;
			font-weight: 400;
			padding: 10px;
			width: 40%;
			text-align: right;
		}
	}
	.button {
		margin-top: 30px;
		.next-button-true {
			background-color: #6567e8;
			border-radius: 25px;
			color: white;
		}
		.next-button-false {
			background-color: #9ca6d8;
			border-radius: 25px;
			color: #e0e4fd;
		}
	}
}
</style>
