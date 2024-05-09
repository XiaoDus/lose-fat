<template>
	<view style="background-color: #f8f8f8">
		<view>
			<uni-calendar :start-date="'2020-01-01'" :end-date="'2029-12-31'" :insert="true" :selected="selected" @change="change" />
		</view>
	</view>
</template>

<script setup lang="ts">
import { getCurrentInstance, reactive, ref } from 'vue';
import { onShow, onPullDownRefresh } from '@dcloudio/uni-app';

const { $request } = getCurrentInstance().appContext.config.globalProperties;
interface EDITWEIGHT {
	time: string;
	data: number;
}
const editWeight = reactive<EDITWEIGHT>({
	time: '',
	data: 0
});
interface WEIGHTLIST {
	seriesData: string;
	xAxisData: string;
}
const weightList = ref<WEIGHTLIST[]>(null);
interface SELECTED {
	date: string;
	info: string;
	data?: {
		custom: string;
		name: string;
	};
}
//日历中数据
const selected = ref<SELECTED[]>([]);
const getWeigthList = async () => {
	const res = await $request('/user_weight_data', 'GET');

	if (res.code === '200') {
		weightList.value = res.data.reverse();
		weightList.value.forEach((e) => {
			selected.value.push({ date: e.xAxisData, info: e.seriesData + '斤' });
		});
	}
};
onShow(() => {
	getWeigthList();
});
const getDate = () => {
	const dd = new Date();

	const y = dd.getFullYear();
	const m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1; // 获取当前月份的日期，不足10补0
	const d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate(); // 获取当前几号，不足10补0
	return {
		fullDate: y + '-' + m + '-' + d,
		year: y,
		month: m,
		date: d,
		day: dd.getDay()
	};
};
//日历
const change = (e): void => {
	editWeight.time = e.fulldate;
	const weight: string = e.extraInfo.info ? e.extraInfo.info.slice(0, -1) : '';
	const today: string = getDate().fullDate;
	if (Date.parse(e.fulldate) <= Date.parse(today)) {
		//判断选择日期是否小于今天
		uni.showModal({
			title: '提示',
			content: weight,
			editable: true,
			placeholderText: '请输入至多两位小数的体重',
			success: function (res) {
				//正则表达式验证是否为纯数字
				const t: RegExp = /^\d+(\.\d{1,2})?$/;
				if (res.confirm) {
					if (t.test(res.content)) {
						editWeight.data = Number(res.content);
						$request('/user_weight_data/add_weight', 'POST', editWeight).then((res) => {
							if (res.code === '200') {
								// getWeigthList();
								editSelect(editWeight);
								uni.showToast({
									icon: 'success',
									title: '修改成功',
									duration: 2000
								});
							}
						});
					} else {
						uni.showToast({
							icon: 'error',
							title: '格式错误',
							duration: 2000
						});
					}
				}
			}
		});
	}
};
//添加或修改体重后修改日历中数据
const editSelect = (value: EDITWEIGHT) => {
	selected.value.forEach((e) => {
		if (e.date === value.time) {
			return (e.info = value.data + '斤');
		}
	});
	selected.value.push({ date: value.time, info: value.data + '斤' });
};
//下拉刷新
onPullDownRefresh(() => {
	getWeigthList();
	setTimeout(() => {
		uni.stopPullDownRefresh();
	}, 2000);
});
</script>

<style lang="scss">
.list {
	background-color: white;
	height: 25px;
	margin-bottom: 2px;
	padding: 15px;
	display: flex;
	justify-content: space-between;

	.time {
		font-size: 20px;
		font-weight: 300;
	}

	.weight {
		color: #11df82;
	}
}
</style>
