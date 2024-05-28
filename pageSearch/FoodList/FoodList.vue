<template>
	<view class="empty" v-if="isEmpty">
		<img class="img" :src="`${proxy.$host}/file/download/empty.png`" alt="" />
		<text class="text">暂无数据</text>
	</view>
	<view class="content" v-else>
		<view v-for="(item, index) in foods" :key="index">
			<uni-list-item @click="getFoodById(item.foodId)" clickable :title="item.name" :note="item.calory + '千卡/100克'" link>
				<template v-slot:footer>
					<uni-icons style="display: flex; align-items: center" type="smallcircle-filled" size="15" :color="iconColor[item.healthLevel]"></uni-icons>
				</template>
			</uni-list-item>
		</view>
		<wd-loadmore custom-class="loadmore" :state="loadmoreText" />
	</view>
</template>

<script setup lang="ts">
import { onLoad, onReachBottom } from '@dcloudio/uni-app';
import { getCurrentInstance, ref, reactive } from 'vue';
const { $request } = getCurrentInstance().appContext.config.globalProperties;
const { proxy } = getCurrentInstance();
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
//获取分类下的食物
interface FOODS {
	foodId: string;
	name: string;
	healthLevel: string;
	calory: string;
	classId: number;
}
const isEmpty = ref<boolean>(true);
const foods = ref<FOODS[]>([]);
const foodId = ref<number>(null);
const pageNumber = ref<number>(1);
const loadmoreText = ref<string>('loading');
const getFoodsByClassId = async (): Promise<void> => {
	const res = await $request('/foods/get_foodbyclass/?id=' + foodId.value + '&pageNum=' + pageNumber.value, 'GET');
	if (res.code === '200') {
		if (res.data.records.length > 0) {
			foods.value.push(...res.data.records);
			isEmpty.value = false;
		} else {
			loadmoreText.value = 'finished';
		}
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

//屏幕滑倒底部
onReachBottom(() => {
	pageNumber.value++;
	getFoodsByClassId();
});
onLoad((option) => {
	uni.setNavigationBarTitle({
		title: option.name
	});
	foodId.value = option.id;
	getFoodsByClassId();
});
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
