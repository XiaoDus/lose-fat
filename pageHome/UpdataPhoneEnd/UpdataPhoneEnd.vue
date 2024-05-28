<template>
	<view class="content">
		<view class="title">
			<view class="big-text">更换新的手机号</view>
		</view>
		<view class="input" style="justify-content: start">
			<view class="text" style="font-size: 18px; width: 30px">+86</view>
			<view class="code">
				<input
					type="number"
					v-model="ssmMessage.phone"
					style="font-size: 18px; padding: 10px"
					maxlength="11"
					placeholder-style="font-size:16px;font-weight: 300;"
					placeholder="请输入手机号"
				/>
			</view>
		</view>
		<view class="input">
			<view class="code">
				<input
					type="number"
					@input="changeInput"
					v-model="ssmMessage.code"
					style="font-size: 18px; padding: 10px"
					maxlength="4"
					placeholder-style="font-size:16px;font-weight: 300;"
					placeholder="请输入手机验证码"
				/>
			</view>
			<view class="text" @click="getSmsCode">{{ captchaText }}</view>
		</view>

		<view class="button">
			<button @click="goUpdataPhoneEnd" :class="!nextButton ? 'next-button-true' : 'next-button-false'" type="default" :disabled="nextButton">确定</button>
		</view>
	</view>
</template>
<script setup lang="ts">
import { getCurrentInstance, ref } from 'vue';
interface SSMMESSAGE {
	phone: string;
	code: number;
}
const ssmMessage = ref<SSMMESSAGE>({
	phone: null,
	code: null
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
	if (ssmMessage.value.code.toString().length === 4) {
		nextButton.value = false;
	} else {
		nextButton.value = true;
	}
};
//获取验证码
const captchaText = ref<string>('获取验证码');
const timer = ref<any>(null);
const getSmsCode = async () => {
	let pattern = /^1[3-9]\d{9}$/;
	ssmMessage.value.phone = trim(ssmMessage.value.phone);
	if (pattern.test(ssmMessage.value.phone)) {
		const res = await $request('/user/getCode?phone=' + ssmMessage.value.phone, 'POST');
		let number: number = 61;
		if (res.code === '200') {
			timer.value = setInterval(() => {
				if (number == 0) {
					clearInterval(timer.value);
					captchaText.value = '重新获取验证码';
					return;
				}
				number -= 1;
				captchaText.value = number + '秒';
			}, 1000);
		}
	} else {
		uni.showToast({
			icon: 'error',
			title: '手机号格式错误！'
		});
	}
};
//更改手机号
const goUpdataPhoneEnd = async () => {
	const res = await $request('/user/updatePhone', 'POST', ssmMessage.value);
	if (res.code === '200') {
		uni.showToast({
			icon: 'success',
			title: '更改成功！'
		});
		uni.setStorageSync('user', res.data);
		uni.setStorageSync('token', res.data.token);
		uni.redirectTo({
			url: '/pageHome/Account/Account'
		});
	} else {
		uni.showToast({
			icon: 'none',
			title: res.message
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
		display: flex;
		justify-content: space-between;
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
