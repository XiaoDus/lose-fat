<template>
	<view>
		<form>
			<view class="cu-form-group">
				<view class="title">手机号</view>
				<input v-model="ssmMessage.phone" />
			</view>
			<view class="cu-form-group solid-bottom">
				<view class="title">验证码</view>
				<input v-model="ssmMessage.code" type="number" style="width: 115px;" />
				<button :disabled="getCodeBtn" class='cu-btn bg-login-zl shadow'
					@click="getSmsCode">{{getCode}}</button>
			</view>
		</form>
		
		<view class="padding login-btn ">
			<button class="cu-btn block round bg-login-zl margin-tb-sm lg" @click="updataPhone">确认更换</button>
		</view>
		<!-- 提示信息弹窗 -->
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script setup>
	import {
		reactive,
		ref,
		getCurrentInstance
	} from 'vue';
	import uniPopup from "../../uni_modules/uni-popup/components/uni-popup/uni-popup.vue"

	
	
	const {
		$request
	} = getCurrentInstance().appContext.config.globalProperties
	const ssmMessage = reactive({
		phone: null,
		code: null
	})
	//消息提示框
	const msgType = ref("success")
	const messageText = ref("")
	const message = ref(null)
	const getCode = ref("验证码")
	const getCodeBtn = ref(false)
	const getSmsCode = () => {
		$request("/user/getCode?phone=" + ssmMessage.phone, "POST").then(res => {
			if (res.code === "200") {
				getCode.value = 60
				getCodeBtn.value = true
				let timer = setInterval(() => {
					getCode.value -= 1
					if (getCode.value == 0) {
						clearInterval(timer)
						getCodeBtn.value = false
						getCode.value = "验证码"
					}
				}, 1000)
			} else {
				msgType.value = "error"
				messageText.value = res.message
				message.value.open()
			}
		})
	}
	
	const updataPhone = ()=>{
		$request("/user/updatePhone", "POST", ssmMessage).then(res => {
			if (res.code === "200") {
				uni.setStorageSync("user", res.data)
				uni.setStorageSync("token", res.data.token)
				msgType.value = "success"
				messageText.value = "更改成功！"
				message.value.open()
				setTimeout(()=>{
					uni.navigateBack()
				},2000)
				
				
			} else if (res.code === "400") {
				msgType.value = "error"
				messageText.value = res.message
				message.value.open()
			}
		})
	}
</script>

<style lang="less">
	.cu-form-group {
		margin: 20px;
		padding: 20px;
		border-radius: 25px;
		background-image: linear-gradient(45deg, #727CFB, #46D0ED);
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
			background-image: linear-gradient(45deg, #727CFB, #46D0ED);
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
			background-image: linear-gradient(45deg, #727CFB, #46D0ED);
			color: #ffffff;
		}
	
	}
</style>