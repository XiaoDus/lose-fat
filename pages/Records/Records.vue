<template>
	<view class="body">
		<view class="echarts">
			<l-echart ref="chartRef" @finished="init"></l-echart>
		</view>

		<view class="setWeigth" @click="goWeightList">
			<view class="top-title">
				<view class="title">
					体重记录
					<uni-icons type="right" size="20"></uni-icons>
				</view>

				<view @click.stop>
					<picker mode="selector" :range="weightList" @change="changeHeight">
						<uni-icons class="icons" type="plus-filled" color="green" size="30"></uni-icons>
					</picker>
				</view>
			</view>
			<view class="bottom-weigth">
				<text class="weigth">
					今日体重：
					<text>{{ todayWeight }}</text>
					斤
				</text>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { getCurrentInstance, reactive, ref } from 'vue';
import LEchart from '@/uni_modules/lime-echart/components/l-echart/l-echart.vue';
import * as echarts from 'echarts';
import { onShow } from '@dcloudio/uni-app';
const goWeightList = (): void => {
	uni.navigateTo({
		url: '../../pageWeightList/WeightList/WeightList'
	});
};

//获取当前日期函数
const getNowFormatDate = (): string => {
	let date = new Date(),
		year = date.getFullYear(), //获取完整的年份(4位)
		month: number | string = date.getMonth() + 1, //获取当前月份(0-11,0代表1月)
		strDate: number | string = date.getDate(); // 获取当前日(1-31)
	if (month < 10) month = `0${month}`; // 如果月份是个位数，在前面补0
	if (strDate < 10) strDate = `0${strDate}`; // 如果日是个位数，在前面补0

	return `${year}-${month}-${strDate}`;
};
const todayTime: string = getNowFormatDate();
const todayWeight = ref<string>('--');
// echarts
const chartRef = ref(null);
const data = reactive({
	option: {
		xAxis: {
			type: 'category',
			data: [],
			axisTick: {
				alignWithLabel: true // true：标签位于刻度线正下方；false：标签位于2个刻度线中间
			},
			axisLabel: {
				//x轴文字的配置
				show: true,
				interval: 0 //使x轴文字显示全
			}
		},
		yAxis: {
			name: '单位（斤）',
			type: 'value',
			interval: 10,
			min: 70
		},
		series: [
			{
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},

				data: [150, 230, 224, 218, 135, 147, 260],
				type: 'line'
			}
		]
	}
});

onShow(() => {
	// 组件能被调用必须是组件的节点已经被渲染到页面上
	setTimeout(async () => {
		if (!chartRef.value) return;
		getWeigthList();
	}, 0);
});
const { $request } = getCurrentInstance().appContext.config.globalProperties;
interface SETDATA {
	data: number | string;
}
const setData = reactive<SETDATA>({
	data: 1
});

//生成体重选择器列表
const setWeight = (): string[] => {
	let list = [];
	for (let i = 80; i <= 300; i++) {
		list.push(i + ' 斤');
	}
	return list;
};
//体重选择器列表
const weightList: string[] = setWeight();
//获取体重记录列表
const getWeigthList = async (): Promise<void> => {
	const res = await $request('/user_weight_data', 'GET');
	let seriesData = [];
	let xAxisData = [];
	if (res.code === '200') {
		//获取最后七次体重数据
		const list = res.data.slice(-7);
		for (let i = 0; i < list.length; i++) {
			if (list[i].xAxisData === todayTime) {
				todayWeight.value = list[i].seriesData;
			}

			seriesData.push(list[i].seriesData);
			xAxisData.push(list[i].xAxisData.substring(5));
		}
		//给eacharts数据赋值
		data.option.xAxis.data = xAxisData;
		data.option.series[0].data = seriesData;
		const myChart = await chartRef.value.init(echarts);
		myChart.setOption(data.option);
	}
};
//身高选择器
const changeHeight = (e): void => {
	const weight = weightList[e.detail.value].slice(0, -2);
	setData.data = weight;
	todayWeight.value = weight;
	setTodayWeigth();
};
//设置今日体重
const setTodayWeigth = (): void => {
	$request('/user_weight_data', 'POST', setData).then((res) => {
		if (res.code === '200') {
			getWeigthList();
		}
	});
};
</script>

<style lang="scss" scoped>
@mixin border15-bgColor_fff {
	border-radius: 15px;
	background-color: #ffffff;
}

.body {
	height: 100vh;
	padding: 15px;
	background-color: #f8f8f8;
}

.echarts {
	@include border15-bgColor_fff;
}

.setWeigth {
	height: 100px;
	margin-top: 15px;
	padding: 15px;
	@include border15-bgColor_fff;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	.top-title {
		display: flex;
		justify-content: space-between;

		.title {
			font-size: 18px;
			font-weight: bolder;
		}

		.icons {
			vertical-align: middle;
		}
	}

	.bottom-weigth {
		.weigth {
			font-size: 13px;

			text {
				font-weight: bold;
				font-size: 18px;
			}
		}
	}
}
</style>
