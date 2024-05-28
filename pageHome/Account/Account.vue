<template>
	<view class="content">
		<!-- 手机号 -->
		<view class="phone">
			<uni-list>
				<uni-list-item @click="goUpdataPhone" clickable showArrow title="关联手机号" :rightText="phone" />
				<uni-list-item clickable showArrow @click="goUpdataPwd" title="修改密码" rightText="********" />
				<uni-list-item @click="deleteUser" clickable showArrow title="注销账号" rightText="注销后无法恢复,请谨慎操作" />
			</uni-list>
		</view>
		<view class="exit-login">
			<view class="btn" @click="exitLogin">退出登录</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { getCurrentInstance, ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
const { $request } = getCurrentInstance().appContext.config.globalProperties;

//获取用户信息
interface USERINFO {
	avatar: string;
	basalMetabolism: string;
	standardWeigth: string;
	token?: string;
	userAge: number;
	userBirthday: string;
	userHeight: string;
	userName: string;
	userPhone: string;
	userSex: string;
	userWeight: string;
}
const useriInfo = ref<USERINFO>(null);
const phone = ref<string>('');
const getUserInfo = async (): Promise<void> => {
	const res = await $request('/user/getUser', 'POST');
	if (res.code === '200') {
		useriInfo.value = res.data;
	}
	const phoneNumber: string = useriInfo.value.userPhone;
	phone.value = phoneNumber.slice(0, 3) + '****' + phoneNumber.slice(-4);
};
//切换验证手机号页面
const goUpdataPhone = () => {
	uni.showModal({
		title: '确认更换手机号？',
		content: '更换后现手机号' + phone.value + '将不能用于登录',
		cancelText: '我再想想',
		confirmText: '确认更换',
		success: function (res) {
			if (res.confirm) {
				uni.navigateTo({
					url: '/pageHome/VerifyPhone/VerifyPhone?phone=' + useriInfo.value.userPhone + '&pwdPhone=' + phone.value + '&pageUrl=UpdataPhoneEnd'
				});
			}
		}
	});
};
//修改密码
const goUpdataPwd = () => {
	uni.showModal({
		content: '确认更换密码吗？',
		cancelText: '我再想想',
		confirmText: '确认更换',
		confirmColor: '#E04350',
		success: function (res) {
			if (res.confirm) {
				uni.navigateTo({
					url: '/pageHome/VerifyPhone/VerifyPhone?phone=' + useriInfo.value.userPhone + '&pwdPhone=' + phone.value + '&pageUrl=EditPwd'
				});
			}
		}
	});
};
//注销账号
const deleteUser = () => {
	uni.showModal({
		title: '警告',
		content: '注销后' + phone.value + '绑定的账号无法恢复',
		cancelText: '我再想想',
		confirmText: '确认注销',
		confirmColor: '#E04350',
		success: async function (res) {
			if (res.confirm) {
				const res = await $request('/user/deleteUser', 'POST');
				if (res.code === '200') {
					uni.removeStorageSync('user');
					uni.removeStorageSync('token');
					uni.showToast({
						title: '注销成功',
						duration: 2000,
						success: () => {
							setTimeout(() => {
								uni.switchTab({
									url: '/pages/Home/Home'
								});
							}, 2500);
						}
					});
				} else {
					uni.showToast({
						icon: 'error',
						title: res.meaasge
					});
				}
			}
		}
	});
};
//退出登录
const exitLogin = () => {
	uni.showModal({
		confirmColor: '#E04350',
		confirmText: '确认退出',
		content: '是否确认退出？',
		success: (res) => {
			if (res.confirm) {
				uni.removeStorageSync('user');
				uni.removeStorageSync('token');
				uni.switchTab({
					url: '/pages/Home/Home'
				});
			}
		}
	});
};
onLoad(() => {
	getUserInfo();
});
</script>

<style lang="scss">
.content {
	background-color: #f8f8f8;
	height: 100vh;
	.phone {
		height: 90%;
		.uni-list-item {
			height: 60px;
		}
	}
	.exit-login {
		padding: 20px;
		background-color: white;
		.btn {
			height: 50px;
			line-height: 50px;
			text-align: center;
			border-radius: 25px;
			border: 1px solid #e04350;
			color: #e04350;
			font-weight: 500;
		}
	}
}
</style>
