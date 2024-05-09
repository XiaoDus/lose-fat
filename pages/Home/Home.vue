<template>
	<view class="center">
		<view class="bg center">
			<view class="bg-radius"></view>
			<view class="logo" :hover-class="!login ? 'logo-hover' : ''">
				<image class="logo-img" @click="loginOrUpdate" :src="avatarUrl"></image>
				<view class="logo-title">
					<text class="uer-name">{{ userInfo != null ? userInfo.userName : '请登录' }}</text>
				</view>
				<view class="count" v-if="login">
					<view class="count-item" v-for="(i, index) in message" :key="index">
						<text class="count-item-num">{{ i.value }}</text>
						<text class="count-item-text">{{ i.text }}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="bg-gray center">
			<view class="center-list">
				<navigator :url="userInfo != null ? '../../pageHome/EditUser/EditUser' : '../../pageLogin/Login/Login'">
					<view style="border-top: 10px solid #f4f4f4" class="center-list-item border-bottom">
						<view class="list-icon color-1"><img class="img" :src="`http://${proxy.$host}/file/download/gerenziliao.png`" alt="" /></view>
						<text class="list-text">信息管理</text>
						<view class="navigat-arrow"><uni-icons type="right" size="20"></uni-icons></view>
					</view>
				</navigator>

				<view class="center-list-item">
					<view class="list-icon color-1"><img class="img" :src="`http://${proxy.$host}/file/download/tongzhi.png`" alt="" /></view>
					<text class="list-text">新消息通知</text>
					<view class="navigat-arrow"><uni-icons type="right" size="20"></uni-icons></view>
				</view>
			</view>
			<view class="center-list">
				<view class="center-list-item border-bottom">
					<view class="list-icon color-1"><img class="img" :src="`http://${proxy.$host}/file/download/wenhao.png`" alt="" /></view>
					<text class="list-text">帮助与反馈</text>
					<view class="navigat-arrow"><uni-icons type="right" size="20"></uni-icons></view>
				</view>
				<view class="center-list-item">
					<view class="list-icon color-1"><img class="img" :src="`http://${proxy.$host}/file/download/yinsitiaokuan.png`" alt="" /></view>
					<text class="list-text">服务条款及隐私</text>
					<view class="navigat-arrow"><uni-icons type="right" size="20"></uni-icons></view>
				</view>
			</view>
		</view>
		<!-- 提示信息弹窗 -->
		<view>
			<uni-popup ref="messageInfo" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script setup>
import { getCurrentInstance, onMounted, ref } from 'vue';
const { proxy } = getCurrentInstance();
import { onShow } from '@dcloudio/uni-app';
const { $request } = getCurrentInstance().appContext.config.globalProperties;

const getUser = () => {
	$request('/user/getUser', 'POST').then((res) => {
		if (res.code === '200') {
			userInfo.value = res.data;
			//登录状态
			login.value = true;
			uni.setStorage({
				key: 'user',
				data: userInfo.value
			});
			avatarUrl.value = userInfo.value.avatar;
			message.value = [
				{
					value: userInfo.value.userHeight,
					text: '身高'
				},
				{
					value: userInfo.value.userWeight,
					text: '体重'
				},
				{
					value: userInfo.value.standardWeigth,
					text: '标准体重'
				}
			];
		} else {
			login.value = false;
			avatarUrl.value = 'http://' + proxy.$host + '/file/download/login.png';
		}
	});
};
onShow(() => {
	getUser();
	// 	login.value = uni.getStorageSync('token').length > 0 ? true : false;
	// 	userInfo.value = uni.getStorageSync('user') ? uni.getStorageSync('user') : null;
	// 	avatarUrl.value =
	// 		userInfo.value != null
	// 			? userInfo.value.avatar != null
	// 				? userInfo.value.avatar
	// 				: 'http://' + proxy.$host + '/file/download/login.png'
	// 			: 'http://' + proxy.$host + '/file/download/login.png';
	// 	message.value =
	// 		userInfo.value != null
	// 			? [
	// 					{
	// 						value: userInfo.value.userHeight,
	// 						text: '身高'
	// 					},
	// 					{
	// 						value: userInfo.value.userWeight,
	// 						text: '体重'
	// 					},
	// 					{
	// 						value: userInfo.value.standardWeigth,
	// 						text: '标准体重'
	// 					}
	// 			  ]
	// 			: null;
});
// onMounted(() => {
// 	login.value = uni.getStorageSync('token').length > 0 ? true : false;
// });
const messageInfo = ref(null);
const msgType = ref('success');
const messageText = ref('');
const login = ref(false);
const userInfo = ref(null);
const avatarUrl = ref('');
const message = ref(null);
const token = uni.getStorageSync('token');

//登录或更换头像
const loginOrUpdate = () => {
	if (!login.value) {
		uni.navigateTo({
			url: '../../pageLogin/Login/Login'
		});
	} else {
		uni.chooseMedia({
			count: 1,
			mediaType: ['image'],
			sourceType: ['album'],
			success: (res) => {
				uni.uploadFile({
					url: 'http://' + proxy.$host + '/file/upload', //服务器请求接口地址
					filePath: res.tempFiles[0].tempFilePath, //选择的本地路径地址
					fileType: 'image',
					name: 'file',
					header: {
						Authorization: token
					},
					success: (res) => {
						if (res.statusCode === 200) {
							const user = uni.getStorageSync('user');
							user.avatar = res.data;
							uni.setStorageSync('user', user);
							avatarUrl.value = res.data;
						} else {
							msgType.value = 'error';
							messageText.value = '修改失败，请稍后再试！';
							messageInfo.value.open();
						}
					}
				});
			}
		});
	}
};
</script>

<style>
@font-face {
	font-family: texticons;
	font-weight: normal;
	font-style: normal;
}

page,
view {
	display: flex;
}

page {
	background-size: 100vw 40vh;
	background-repeat: no-repeat;
	background-image: url(http://127.0.0.1:3000/file/download/loginbg.png);
}

.center {
	flex-direction: column;
}
</style>
<style lang="scss" scoped>
$head-color: #fff;
$white-color: #fff;
$main-color: #333;
$radius: 0rpx;
$border-color: #efefef;
$color-1: #007aff;
$color-2: #4cd964;
$color-3: #f0ad4e;
$color-4: #dd524d;
$list-item-height: 100rpx;
$list-margin: 20rpx;
$width: calc(100vw - 0rpx);
$head-height: 550rpx;
$head-width: 120vw;
$margin-left: -60vw;

.bg {
	width: 100vw;
	height: 500rpx;
	overflow: hidden;
	position: relative;
	box-sizing: border-box;

	.bg-radius {
		background-color: $head-color;
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		width: $head-width;
		height: $head-height;
		box-sizing: border-box;
		position: absolute;
		left: 50%;
		margin-left: $margin-left;
		top: 200rpx;
		z-index: 8;
	}

	.logo {
		background-color: transparent;
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		width: 100vw;
		box-sizing: border-box;
		flex-direction: column;
		align-items: center;
		position: absolute;
		left: 0;
		top: 120rpx;
		z-index: 9;

		.logo-img {
			width: 140rpx;
			height: 140rpx;
			border-radius: 50%;
			border: solid 10rpx #efefef;
		}

		.logo-title {
			height: 150rpx;
			flex: 1;
			align-items: center;
			justify-content: space-between;
			flex-direction: row;

			.uer-name {
				height: 60rpx;
				line-height: 60rpx;
				font-size: 34rpx;
				color: #333;
			}
		}

		.count {
			margin: 30rpx auto 0rpx;
			width: $width;

			.count-item {
				flex-direction: column;
				flex: 1;
				text-align: center;

				.count-item-num {
					color: $main-color;
					font-size: 36rpx;
					font-weight: 500;
				}

				.count-item-text {
					color: $main-color;
					font-size: 28rpx;
					font-weight: 400;
					margin-top: 10rpx;
				}
			}
		}
	}
}

.center-list {
	background-color: #ffffff;
	margin: 0 auto $list-margin;
	width: $width;
	flex-direction: column;
	border-radius: $radius;

	.center-list-item {
		height: $list-item-height;
		width: 100%;
		box-sizing: border-box;
		flex-direction: row;
		padding: 0rpx 20rpx;

		.list-icon {
			height: $list-item-height;
			margin-right: 10px;
			display: flex;
			flex-direction: column;
			align-items: center;
			align-content: center;
			justify-content: center;
			.img {
				width: 20px;
				height: 20px;
			}
		}

		.color-1 {
			color: $color-1;
		}

		.color-2 {
			color: $color-2;
		}

		.color-3 {
			color: $color-3;
		}

		.color-4 {
			color: $color-4;
		}

		.list-text {
			height: $list-item-height;
			line-height: $list-item-height;
			font-size: 32rpx;
			color: #555;
			flex: 1;
			text-align: left;
		}

		.navigat-arrow {
			height: $list-item-height;
			line-height: $list-item-height;
			width: 40rpx;
			font-size: 30rpx;
			color: #999;
			text-align: right;
			font-family: texticons;
		}
	}

	.border-bottom {
		border-bottom-width: 1rpx;
		border-color: $border-color;
		border-bottom-style: solid;
	}
}

.center-nav {
	background-color: #ffffff;
	padding-top: 1rpx;
	margin: 20rpx auto $list-margin;
	width: $width;
	flex-direction: column;
	border-radius: $radius;

	.center-nav-title {
		padding: 25rpx 30rpx;
		font-size: 34rpx;
		color: #333;
		font-weight: 400;
		border-bottom: solid 1rpx $border-color;
	}

	.center-nav-content {
		height: 180rpx;
		display: flex;
		align-items: center;

		.center-nav-item {
			flex-direction: column;
			flex: 1;
			text-align: center;

			.center-nav-item-icon {
				color: #333;
				font-size: 48rpx;
				font-weight: 500;
				font-family: texticons;
			}

			.center-nav-item-text {
				color: #666;
				font-size: 30rpx;
				font-weight: 400;
				margin-top: 15rpx;
			}
		}
	}
}

.margin-top {
	margin-top: 20rpx;
	min-height: 230rpx;
}

.bg-gray {
	background: #f4f4f4;
	height: calc(100vh - 435rpx);
}
</style>
