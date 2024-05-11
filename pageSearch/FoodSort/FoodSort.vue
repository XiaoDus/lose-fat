<template>
	<view class="content">
		<view class="empty" v-if="isEmpty">
			<img class="img" :src="`${proxy.$host}/file/download/empty.png`" alt="" />
			<text class="text">暂无数据</text>
		</view>
		<view class="food-class" v-else>
			<view v-for="(item, index) in foodClasses" :key="index">
				<uni-list-item
					showArrow
					clickable
					:title="item.name"
					:to="`../FoodList/FoodList?id=${item.id}&name=${item.name}`"
					:thumb="`${proxy.$host}/file/download/${item.icon}`"
					thumb-size="lg"
				></uni-list-item>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { getCurrentInstance, ref, reactive } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
const { $request } = getCurrentInstance().appContext.config.globalProperties;
const { proxy } = getCurrentInstance();

//获取食物分类
interface FOODCLASSES {
	icon: string;
	id: number;
	name: string;
}
const foodClasses = ref<FOODCLASSES[]>(null);
const isEmpty = ref<boolean>(true);

//获取食物分类
const getFoodClass = async (): Promise<void> => {
	const res = await $request('/foods/get_food_class', 'GET');
	if (res.code === '200') {
		foodClasses.value = res.data;
		isEmpty.value = false;
	}
	console.log(foodClasses.value);
};
onLoad(() => {
	getFoodClass();
});
</script>

<style lang="scss">
.content {
	background-color: #f8f8f8;
	height: 100vh;
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
}
</style>
