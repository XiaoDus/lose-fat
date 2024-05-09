<template>
	<view class="content">
		<!-- 搜索 -->
		<view class="top-search">
			<navigator url="../../pageSearch/Search/Search" hover-class="none">
				<button type="default">
					<uni-icons style="margin-right: 10px" type="search" color="#77b3f8" size="30"></uni-icons>
					<text>搜索食物热量</text>
				</button>
			</navigator>
		</view>

		<!-- 宫格 -->
		<view class="grid">
			<navigator url="/pageSearch/Search/Search" hover-class="none">
				<view class="box">
					<img class="icon" :src="`http://${proxy.$host}/file/download/tizhongchaxun.png`" alt="" />
					<view class="text">热量查询</view>
				</view>
			</navigator>
			<navigator url="/pageSearch/FoodSort/FoodSort">
				<view class="box">
					<img class="icon" :src="`http://${proxy.$host}/file/download/fenlei.png`" alt="" />
					<view class="text">食物分类</view>
				</view>
			</navigator>

			<navigator url="/pages/Records/Records" open-type="switchTab" hover-class="none">
				<view class="box">
					<img class="icon" :src="`http://${proxy.$host}/file/download/jilu.png`" alt="" />
					<view class="text">体重记录</view>
				</view>
			</navigator>

			<navigator url="/pageSearch/FoodCompare/FoodCompare" hover-class="none">
				<view class="box">
					<img class="icon" :src="`http://${proxy.$host}/file/download/shujuduibi.png`" alt="" />
					<view class="text">食物对比</view>
				</view>
			</navigator>
		</view>
		<!-- 轮播图 -->
		<view>
			<view class="uni-margin-wrap">
				<swiper class="swiper" autoplay="true" indicator-dots="true" interval="3000" duration="700" circular="true">
					<swiper-item v-for="(item, index) in carousel" :key="index">
						<view class="swiper-item">
							<img class="carousel" :src="`http://${proxy.$host}${item.url}`" alt="" />
						</view>
					</swiper-item>
				</swiper>
			</view>
		</view>

		<!-- 每日一答 -->
		<view class="question" v-if="question != null">
			<view class="card" @click="answerQuestion">
				<uni-card title="每日一答" extra=">">
					<text class="uni-body">{{ question.question }}</text>
				</uni-card>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { reactive, ref, getCurrentInstance } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
const { $request } = getCurrentInstance().appContext.config.globalProperties;

const { proxy } = getCurrentInstance();
//获取轮播图图片
interface CAROUSEL {
	id: null;
	url: string;
}
const carousel = ref<CAROUSEL[]>([]);

onLoad((): void => {
	getCarousel();
	getQuestion();
});

const getCarousel = async (): Promise<void> => {
	const res = await $request('/carousel', 'GET');
	if (res.code === '200') {
		carousel.value = res.data;
	} else {
		uni.showToast({
			icon: 'error',
			title: '错误！'
		});
	}
};
//获取每日一答问题
interface QUESTION {
	id: number;
	question: string;
	wrongAnswer: string;
	correctAnswer: string;
}
const question = ref<QUESTION>(null);
const getQuestion = async (): Promise<void> => {
	const res = await $request('/knowledge', 'GET');
	if (res.code === '200') {
		question.value = res.data;
	}
};
const answerQuestion = (): void => {
	uni.navigateTo({
		url: '/pageQuestion/Question/Question',
		success: () => {
			uni.$emit('question', question.value);
		}
	});
};
</script>

<style lang="scss">
.content {
	height: 100vh;
	background-color: #f8f8f8;
	padding: 15px;
	.top-search {
		button {
			border: 1px solid #77b3f8;
			border-radius: 25px;
			height: 50px;
			text-align: left;
			background-color: #fff;
			line-height: 50px;

			text {
				vertical-align: top;
				font-weight: 300;
				color: #6e6e6e;
			}
		}
	}

	.grid {
		padding: 10px;
		display: flex;
		justify-content: space-around;
		.icon {
			width: 40px;
			height: 40px;
			border-radius: 50%;
			padding: 5px;
		}
		.box {
			padding: 10px;
			display: flex;
			justify-content: center;
			align-items: center;
			flex-direction: column;
			.text {
				font-size: 13px;
			}
		}
	}
	.swiper {
		height: 200px;
		.swiper-item {
			.carousel {
				width: 100%;
				height: 200px;
			}
		}
	}
	.question {
		margin-top: 20px;
		.uni-card {
			margin: 0 !important;
			padding: 10px !important;
		}
		.uni-body {
			font-style: 18px;
		}
	}
}
</style>
