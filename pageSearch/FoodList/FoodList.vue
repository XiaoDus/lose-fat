<template>
	<view class="content">
		<view v-for="(item, index) in foods" :key="index">
			<uni-list-item @click="getFoodById(item.foodId)" clickable :title="item.name" :note="item.calory + '千卡/100克'" link>
				<template v-slot:footer>
					<uni-icons style="display: flex; align-items: center" type="smallcircle-filled" size="15" :color="iconColor[item.healthLevel]"></uni-icons>
				</template>
			</uni-list-item>
		</view>
	</view>
</template>

<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app';
import { getCurrentInstance, ref, reactive, nextTick } from 'vue';
const { $request } = getCurrentInstance().appContext.config.globalProperties;

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
const foods = ref<FOODS[]>([]);
const getFoodsByClassId = async (id: number): Promise<void> => {
	//显示加载框
	uni.showToast({
		icon: 'loading',
		title: '加载中',
		duration: 2000
	});
	const res = await $request('/foods/get_foodbyclass/?id=' + id, 'GET');
	if (res.code === '200') {
		foods.value = res.data;
		console.log(res.data);
	}
};
//根据foodid获取食物信息
const getFoodById = async (foodId: string): Promise<void> => {
	const res = await $request('/foods/food_message?foodId=' + foodId, 'GET');
	if (res.code == '200') {
		const foodMessage = res.data;

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
onLoad((option) => {
	uni.setNavigationBarTitle({
		title: option.name
	});
	getFoodsByClassId(option.id);
});
</script>

<style lang="scss"></style>
