<template>
	<view class="content">
		<view class="title">
			<view class="big-text">验证当前登录账号的手机号</view>
			<view class="small-text">需要验证你的手机号进行下一步操作</view>
		</view>
		<view class="pwdPhone">
			{{ pwdPhone }}
		</view>
		<view class="input">
			<view class="code">
				<input
					type="number"
					v-model="ssmMessage.code"
					@input="changeInput"
					style="font-size: 16px; padding: 10px"
					maxlength="4"
					placeholder-style="font-size:16px;font-weight: 300;"
					placeholder="请输入手机验证码"
				/>
			</view>
			<view class="text" @click="getSmsCode">
				<button class="btn" :disabled="btnChange">{{ captchaText }}</button>
			</view>
		</view>
		<view class="button">
			<button @click="goUpdataPhoneEnd" :class="!nextButton ? 'next-button-true' : 'next-button-false'" type="default" :disabled="nextButton">下一步</button>
		</view>
	</view>
</template>

<script setup lang="ts">
import { reactive, ref, getCurrentInstance } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
onLoad((option) => {
	ssmMessage.value.phone = option.phone;
	pwdPhone.value = option.pwdPhone;
	pageUrl.value = option.pageUrl;
});

const { $request } = getCurrentInstance().appContext.config.globalProperties;
//下一页路径
const pageUrl = ref<string>('');
//控制下一步按钮
const nextButton = ref<boolean>(true);
const changeInput = () => {
	if (ssmMessage.value.code.toString().length === 4) {
		nextButton.value = false;
	} else {
		nextButton.value = true;
	}
};
const pwdPhone = ref<string>('');
interface SSMMESSAGE {
	phone: string;
	code: number;
}
const ssmMessage = ref<SSMMESSAGE>({
	phone: null,
	code: null
});
//获取验证码
const captchaText = ref<string>('获取验证码');
const btnChange = ref<boolean>(false);
const timer = ref<any>(null);
const getSmsCode = async () => {
	const res = await $request('/user/getCode?phone=' + ssmMessage.value.phone, 'POST');
	let number: number = 61;
	if (res.code === '200') {
		btnChange.value = true;
		timer.value = setInterval(() => {
			if (number == 0) {
				btnChange.value = false;
				clearInterval(timer.value);
				captchaText.value = '重新获取验证码';
				return;
			}
			number -= 1;
			captchaText.value = number + '秒';
		}, 1000);
	}
};
//验证手机号
const goUpdataPhoneEnd = async () => {
	const res = await $request('/user/verifyPhone', 'POST', ssmMessage.value);
	if (res.code === '200') {
		ssmMessage.value.code = null;
		uni.navigateTo({
			url: '/pageHome/' + pageUrl.value + '/' + pageUrl.value
		});
	} else {
		uni.showToast({
			icon: 'error',
			title: res.message
		});
	}
};
</script>

<style lang="scss" scoped>
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
	.pwdPhone {
		margin-top: 30px;
		font-size: 23px;
		font-weight: 400;
	}
	.input {
		margin-top: 20px;

		border-bottom: #9c9c9c 1px solid;
		display: flex;
		justify-content: space-between;
		align-items: center;
		.text {
			font-size: 13px;
			font-weight: 400;
			padding: 10px;
			width: 40%;
			text-align: right;
			.btn {
				font-size: 13px;
				font-weight: 400;
				border: none;
				text-align: right;
				&::after {
					border: none;
				}
			}
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
