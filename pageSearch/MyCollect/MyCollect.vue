<template>
	<view class="content">
		<view class="empty" v-if="myCollectList.length == 0">
			<img class="img" :src="`${proxy.$host}/file/download/empty.png`" alt="" />
			<text class="text">暂无收藏</text>
		</view>
		<view class="content" v-else>
			<view v-for="(item, index) in myCollectList" :key="index">
				<uni-list-item @click="getFoodById(item.foodId)" clickable :title="item.name" :note="item.calory + '千卡/100克'" link>
					<template v-slot:footer>
						<uni-icons style="display: flex; align-items: center" type="smallcircle-filled" size="15" :color="iconColor[item.healthLevel]"></uni-icons>
					</template>
				</uni-list-item>
			</view>
			<!-- <wd-loadmore custom-class="loadmore" :state="loadmoreText" /> -->
		</view>
	</view>
</template>

<script setup lang="ts">
import { getCurrentInstance, reactive, ref } from 'vue';
import { onLoad, onPullDownRefresh } from '@dcloudio/uni-app';
const { $request } = getCurrentInstance().appContext.config.globalProperties;
const { proxy } = getCurrentInstance();
onLoad(() => {
	getMyCollectList();
});
onPullDownRefresh(() => {
	getMyCollectList();
});
interface ICONCOLOR {
	'1': string;
	'2': string;
	'3': string;
}
const iconColor = reactive<ICONCOLOR>({
	'1': '#70c995',
	'2': '#f7cc4f',
	'3': 'red'
});
interface MYCOLLECTLIST {
	calory: string;
	classId: number;
	foodId: string;
	healthLevel: string;
	name: string;
}
const myCollectList = ref<MYCOLLECTLIST[]>([]);
//获取我的收藏
const getMyCollectList = async () => {
	const res = await $request('/collect/MyCollectList', 'GET');
	if (res.code === '200') {
		uni.stopPullDownRefresh();
		myCollectList.value = res.data;
	}
};
//根据foodid获取食物信息
const getFoodById = async (foodId: string): Promise<void> => {
	const res = await $request('/foods/food_message?foodId=' + foodId, 'GET');
	if (res.code == '200') {
		const foodMessage = res.data;
		//获取收藏信息
		const collect = await $request('/collect/get_collect?foodId=' + foodId, 'GET');
		foodMessage.collect = collect.data;

		uni.navigateTo({
			url: '../FoodMessage/FoodMessage',
			success: (res) => {
				uni.$emit('foodMessage', foodMessage);
			}
		});
	}
};
</script>

<style lang="scss">
:deep(.loadmore) {
	background-color: #f4f4f4;
	margin: 20px 0;
}
.empty {
	width: 100vw;
	height: 80vh;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	.img {
		width: 180px;
		height: 150px;
	}
	.text {
		color: #a6a6a6;
		padding: 20px 0;
	}
}
</style>
