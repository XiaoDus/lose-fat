<template>
	<view class="contaier">
		<view class="top-bg">
			<view class="text-white text-bold text-xxxl">轻身助手</view>
			<view class="margin-top-xs text-white">欢迎使用，请先登录</view>
		</view>

		<view class="input-box padding-lr">
			<!-- 密码登录 -->
			<form v-if="selectWay == 'password'">
				<view class="cu-form-group">
					<view class="title">手机号</view>
					<input placeholder-style="color:#fff;" v-model="passwordMessage.phone" />
				</view>
				<view class="cu-form-group solid-bottom">
					<view class="title" style="word-spacing: 0.5em">密 码</view>
					<input placeholder-style="color:#fff;" style="width: 115px" password v-model="passwordMessage.pwd" />
				</view>
			</form>
			<!-- 验证码登录 -->
			<form v-if="selectWay == 'ssm'">
				<view class="cu-form-group">
					<view class="title">手机号</view>
					<input v-model="ssmMessage.phone" />
				</view>
				<view class="cu-form-group solid-bottom">
					<view class="title">验证码</view>
					<input v-model="ssmMessage.code" type="number" style="width: 115px" />
					<button :disabled="getCodeBtn" class="cu-btn bg-login-zl shadow" @click="getSmsCode">{{ getCode }}</button>
				</view>
			</form>
		</view>

		<view class="padding login-btn">
			<button class="cu-btn block round bg-login-zl margin-tb-sm lg" @click="login">登 录</button>
			<view v-if="selectWay == 'ssm'" @click="selectWay = 'password'" class="return">返回密码登录</view>
		</view>

		<view class="other-login">
			<view class="title">
				<view class="border"></view>
				<view class="text">其他方式登录</view>
				<view class="border"></view>
			</view>
			<view style="display: flex; justify-content: center">
				<img class="img" :src="`http://${proxy.$host}/file/download/ssm.png`" @click="selectWay = 'ssm'" />
				<img class="img" :src="`http://${proxy.$host}/file/download/wx.png`" />
			</view>
		</view>
		<view>
			<!-- 提示信息弹窗 -->
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script setup>
import { getCurrentInstance, reactive, ref } from 'vue';
const { $request } = getCurrentInstance().appContext.config.globalProperties;
const { proxy } = getCurrentInstance();
const msgType = ref('success');
const messageText = ref('');
const message = ref(null);
// 登录方式
const selectWay = ref('password');
//密码登录
const passwordMessage = reactive({
	phone: null,
	pwd: null
});
//验证码登录
const ssmMessage = reactive({
	phone: null,
	code: null
});
const getCode = ref('验证码');
const getCodeBtn = ref(false);
const getSmsCode = () => {
	$request('/user/getCode?phone=' + ssmMessage.phone, 'POST').then((res) => {
		if (res.code === '200') {
			getCode.value = 60;
			getCodeBtn.value = true;
			let timer = setInterval(() => {
				getCode.value -= 1;
				if (getCode.value == 0) {
					clearInterval(timer);
					getCodeBtn.value = false;
					getCode.value = '验证码';
				}
			}, 1000);
		} else {
			msgType.value = 'error';
			messageText.value = res.message;
			message.value.open();
		}
	});
};

//登录请求
const login = () => {
	if (selectWay.value == 'password') {
		$request('/user/password_login', 'POST', passwordMessage).then((res) => {
			if (res.code === '200') {
				uni.setStorageSync('user', res.data);
				uni.setStorageSync('token', res.data.token);
				uni.switchTab({
					url: '../../pages/index/index'
				});
			} else if (res.code === '400') {
				msgType.value = 'error';
				messageText.value = res.message;
				message.value.open();
			}
		});
	} else {
		$request('/user/sms_login', 'POST', ssmMessage).then((res) => {
			if (res.code === '200') {
				uni.setStorageSync('user', res.data);
				uni.setStorageSync('token', res.data.token);
				uni.switchTab({
					url: '../../pages/index/index'
				});
			} else if (res.code === '400') {
				msgType.value = 'error';
				messageText.value = res.message;
				message.value.open();
			}
		});
	}
};
</script>

<style lang="less" scoped>
.contaier {
	height: 100vh;
	background-color: #ffffff;
	color: #ffffff;
}

.top-bg {
	width: 750rpx;
	background-image: url(http://127.0.0.1:3000/file/download/loginbg.png);
	height: 420rpx;
	padding-top: 50px;
	background-size: 100%;
	background-repeat: no-repeat;
	text-align: center;
	font-size: 25px;
}

.cu-form-group {
	margin: 20px;
	padding: 20px;
	border-radius: 25px;
	background-image: linear-gradient(45deg, #727cfb, #46d0ed);
	display: flex;
	color: #ffffff;

	input {
		color: #ffffff;
		margin-left: 10px;
		padding: 0 10px;
		border-left: 1px solid rgba(255, 255, 255, 0.5);
	}

	.bg-login-zl {
		width: 100px;
		font-size: 15px;
		background-image: linear-gradient(45deg, #727cfb, #46d0ed);
		color: #ffffff;
	}
}

.solid-bottom {
	align-items: center;
	height: 40px;
	padding: 10px 20px;
}

.login-btn {
	padding: 20px;

	.cu-btn {
		border-radius: 25px;
		background-image: linear-gradient(45deg, #727cfb, #46d0ed);
		color: #ffffff;
	}
}

.other-login {
	margin-top: 10px;

	.title {
		padding: 0 30px;
		display: flex;
		justify-content: space-evenly;

		.border {
			padding: 0 20px;
			height: 1px;
			width: 40px;
			border-top: 1px solid darkgray;
		}

		.text {
			margin-top: -10px;
			color: darkgray;
			font-size: 14px;
		}
	}

	.img {
		width: 40px;
		height: 40px;
		margin: 10px 10px;
	}
}

.return {
	font-size: 10px;
	color: darkgray;
	margin-right: 0;
	text-align: right;
	margin-top: 10px;
}
</style>
