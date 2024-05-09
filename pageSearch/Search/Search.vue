<template>
	<view class="constain">
		<view class="search">
			<uni-icons @click="search" class="icon" type="search" color="#77b3f8" size="30"></uni-icons>
			<uni-easyinput
				styles="{height: 50px,border-radius: 25px,}"
				@input="search"
				@confirm="search"
				primaryColor="#9c9c9c"
				trim="true"
				v-model="key"
				:inputBorder="false"
				placeholder="请输入食物名称"
				@clear="clearSearchList"
			/>
		</view>

		<view class="searchList" v-if="isSearchList">
			<view v-for="(item, index) in searchList" :key="index">
				<uni-list-item clickable :title="item.name" @click="getFoodById(item.foodId)" :note="item.calory + '千卡/100克'" link>
					<template v-slot:footer>
						<uni-icons style="display: flex; align-items: center" type="smallcircle-filled" size="15" :color="iconColor[item.healthLevel]"></uni-icons>
					</template>
				</uni-list-item>
			</view>
		</view>
		<view v-if="!isSearchList" class="storage_hot">
			<view class="searchStorage">
				<view class="top">
					<view class="title">历史搜索</view>
					<view class="title—icon" @click="clearSearchStorage"><uni-icons type="trash-filled" size="18" color="#6e6e6e;"></uni-icons></view>
				</view>

				<view class="values">
					<li v-for="(item, index) in storageSearch" :key="index" class="value" @click="searchNow(item)">{{ item }}</li>
				</view>
			</view>
			<view class="hotStorage">
				<text>热门搜索</text>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { reactive, ref, getCurrentInstance } from 'vue';
import { onLoad } from '@dcloudio/uni-app';

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
//历史搜索
const storageSearch = ref<string[]>([]);

onLoad((option): void => {
	const storage: string[] = uni.getStorageSync('searchStorage');
	url.value = option.url ? option.url : 'FoodMessage';
	if (storage) {
		storageSearch.value = storage;
	} else {
		uni.setStorage({
			key: 'searchStorage',
			data: []
		});
		storageSearch.value = [];
	}
});
const key = ref<string>('');
interface SEARCHLIST {
	calory: string;
	classId: number;
	foodId: string;
	healthLevel: string;
	name: string;
}
const searchList = ref<SEARCHLIST[]>([]);
const timer = ref<any>(null);
const isSearchList = ref<boolean>(false);

const search = (): void => {
	//防抖
	if (timer.value != null) {
		clearTimeout(timer.value);
	}
	timer.value = setTimeout(async (): Promise<void> => {
		clearTimeout(timer.value);

		if (key.value.length > 0) {
			const res = await $request('/foods/getfoodbykey?key=' + key.value, 'GET');
			if (res.code === '200') {
				searchList.value = res.data;

				if (searchList.value != null) {
					isSearchList.value = true;
				} else {
					isSearchList.value = false;
				}
			}
		}
	}, 2000);
};
const searchNow = async (value: string): Promise<void> => {
	key.value = value;
	const res = await $request('/foods/getfoodbykey?key=' + value, 'GET');
	if (res.code === '200') {
		searchList.value = res.data;
		if (searchList.value != null) {
			isSearchList.value = true;
		} else {
			isSearchList.value = false;
		}
	}
};
//清空搜索的食物列表
const clearSearchList = () => {
	searchList.value = [];
	isSearchList.value = false;
};
//获取到食物信息后跳转的页面（FoodMessage：食物信息页面，FoodCompare：食物对比页面）
const url = ref<string>('FoodMessage');
//根据foodid获取食物信息
const getFoodById = async (foodId): Promise<void> => {
	const res = await $request('/foods/food_message?foodId=' + foodId, 'GET');
	if (res.code == '200') {
		const foodMessage = res.data;
		//缓存中是否存在用户搜索的食物名称，存在时则不会重复保存
		if (storageSearch.value.indexOf(foodMessage.name) == -1) {
			storageSearch.value.push(foodMessage.name);
			uni.setStorage({
				key: 'searchStorage',
				data: storageSearch.value.reverse()
			});
		}
		const collect = await $request('/collect/get_collect?foodId=' + foodId, 'GET');
		foodMessage.collect = collect.data;
		if (url.value === 'FoodMessage') {
			uni.navigateTo({
				url: '../FoodMessage/FoodMessage',
				success: (res) => {
					uni.$emit('foodMessage', foodMessage);
					key.value = '';
					clearSearchList();
				}
			});
		} else if (url.value === 'FoodCompare') {
			uni.navigateBack({
				success: () => {
					uni.$emit('getFoodmessage', foodMessage);
				}
			});
		}
	}
};

//清空历史搜索
const clearSearchStorage = (): void => {
	storageSearch.value = [];
	uni.removeStorage({
		key: 'searchStorage'
	});
};
</script>

<style lang="scss">
.placeholder {
	color: #6e6e6e;
}

.constain {
	background-color: #f8f8f8;
	height: 100vh;
	padding: 15px;

	.search {
		padding: 0 10px;
		display: flex;
		border-radius: 25px;
		height: 50px;
		text-align: left;
		background-color: #fff;
		line-height: 50px;
		align-items: center;

		input {
			margin-left: 10px;
		}

		.icon {
			position: relative;
			top: 3px;
		}
	}

	.searchList {
		background-color: #fff;
		margin-top: 20px;
		padding: 0 20px;
	}

	.storage_hot {
		margin: 20px 15px;
		.searchStorage {
			@mixin title_font {
				color: #6e6e6e;
				font-size: 13px;
			}

			@mixin searchStorage_value {
				display: flex;
				flex-wrap: wrap;
			}

			.title {
				@include title_font;
			}
			.top {
				display: flex;
				justify-content: space-between;
			}
			.values {
				@include searchStorage_value;
				width: 90vw;
				padding: 3px 0;
				.value {
					list-style-type: none;
					padding: 5px 15px;
					@include title_font;
				}
			}
		}
	}
}
</style>