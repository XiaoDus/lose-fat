<template>
	<view class="contain">
		<uni-card class="topCard">
			<template v-slot:title>
				<view class="top">
					<view class="name">{{ foodMessage.name }}</view>
					<view class="extra">
						<view class="tips-suggest">
							<uni-icons style="display: flex; align-items: center" type="smallcircle-filled" size="12" :color="cardColor[foodMessage.healthLight].icon"></uni-icons>
							<span class="tips" :style="cardColor[foodMessage.healthLight].text">{{ foodMessage.healthTips }}</span>
							<span class="suggest">{{ foodMessage.healthSuggest }}</span>
						</view>
					</view>
				</view>
			</template>
			<view slot="actions" class="card-actions">
				<view class="card-actions-item" @click="editCollect">
					<uni-icons :type="CollectIconType[collect].icon" size="18" color="#999"></uni-icons>
					<text class="card-actions-item-text">{{ CollectIconType[collect].text }}</text>
				</view>
				<view class="card-actions-item" @click="compare">
					<uni-icons type="eye" size="18" color="#999"></uni-icons>
					<text class="card-actions-item-text">对比</text>
				</view>
				<view class="card-actions-item" @click="actionsClick('评论')">
					<uni-icons type="plus" size="18" color="#999"></uni-icons>
					<text class="card-actions-item-text">记录</text>
				</view>
			</view>
		</uni-card>
		<uni-card class="card" title="营养信息" spacing="15px" extra="每「100克」可食用部">
			<view class="food-message">
				<view class="foodChart">
					<view class="chart"><l-echart ref="protein" @finished="init"></l-echart></view>
					<view class="chart"><l-echart ref="joule" @finished="init"></l-echart></view>
					<view class="chart"><l-echart ref="calory" @finished="init"></l-echart></view>
				</view>
				<view class="foodChart">
					<view class="chart"><l-echart ref="sugar" @finished="init"></l-echart></view>
					<view class="chart"><l-echart ref="fat" @finished="init"></l-echart></view>
					<view class="chart"><l-echart ref="carbohydrate" @finished="init"></l-echart></view>
				</view>
			</view>
			<view class="list">
				<uni-table border stripe emptyText="暂无更多数据">
					<!-- 表头行 -->
					<uni-tr>
						<uni-th align="center">项目</uni-th>
						<uni-th align="center">每「100克」可食用部</uni-th>
					</uni-tr>
					<!-- 表格数据行 -->
					<uni-tr v-if="foodMessage.calory != ''">
						<uni-td align="center">热量</uni-td>
						<uni-td align="center">{{ foodMessage.calory }}{{ foodMessage.caloryUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.protein != ''">
						<uni-td align="center">蛋白质</uni-td>
						<uni-td align="center">{{ foodMessage.protein }}{{ foodMessage.proteinUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.fat != ''">
						<uni-td align="center">脂肪</uni-td>
						<uni-td align="center">{{ foodMessage.fat }}{{ foodMessage.fatUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.carbohydrate != ''">
						<uni-td align="center">碳水化合物</uni-td>
						<uni-td align="center">{{ foodMessage.carbohydrate }}{{ foodMessage.carbohydrateUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.fiberDietary != ''">
						<uni-td align="center">膳食纤维</uni-td>
						<uni-td align="center">{{ foodMessage.fiberDietary }}{{ foodMessage.fiberDietaryUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.sugar != ''">
						<uni-td align="center">糖</uni-td>
						<uni-td align="center">{{ foodMessage.sugar }}{{ foodMessage.sugarUnit }}</uni-td>
					</uni-tr>

					<uni-tr v-if="foodMessage.natrium != ''">
						<uni-td align="center">钠</uni-td>
						<uni-td align="center">{{ foodMessage.natrium }}{{ foodMessage.natriumUnit }}</uni-td>
					</uni-tr>
				</uni-table>

				<view class="moreMessage" @click="moreMessage">
					<view class="text">更多营养信息</view>
				</view>
			</view>
		</uni-card>
	</view>
</template>

<script setup>
import { onLoad } from '@dcloudio/uni-app';
import { reactive, ref, onMounted, getCurrentInstance } from 'vue';
const { $request } = getCurrentInstance().appContext.config.globalProperties;
import LEchart from '@/uni_modules/lime-echart/components/l-echart/l-echart.vue';
import uCard from '../../uni_modules/uni-card/components/uni-card/uni-card.vue';
import uTable from '../../uni_modules/uni-table/components/uni-table/uni-table.vue';
import * as echarts from 'echarts';
// echarts
const protein = ref(null);
const joule = ref(null);
const calory = ref(null);
const sugar = ref(null);
const fat = ref(null);
const carbohydrate = ref(null);
const init = ref(null);
const gaugeData = reactive({
	// 蛋白质
	protein: [
		{
			value: 0,
			name: '蛋白质',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	],
	//能量
	joule: [
		{
			value: 0,
			name: '能量',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	],
	//热量
	calory: [
		{
			value: 0,
			name: '热量',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	],
	//糖
	sugar: [
		{
			value: 0,
			name: '糖',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	],
	//脂肪
	fat: [
		{
			value: 0,
			name: '脂肪',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	],
	//碳水化合物
	carbohydrate: [
		{
			value: 0,
			name: '碳水化合物',
			title: {
				offsetCenter: ['0%', '130%']
			},
			detail: {
				valueAnimation: true,
				offsetCenter: ['0%', '0%']
			}
		}
	]
});
const data = reactive({
	//蛋白质
	protein: {
		series: [
			{
				data: gaugeData.protein,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	},
	//能量
	joule: {
		series: [
			{
				data: gaugeData.joule,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	},
	//热量
	calory: {
		series: [
			{
				data: gaugeData.calory,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	},
	//糖
	sugar: {
		series: [
			{
				data: gaugeData.sugar,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	},
	//脂肪
	fat: {
		series: [
			{
				data: gaugeData.fat,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	},
	//碳水化合物
	carbohydrate: {
		series: [
			{
				data: gaugeData.carbohydrate,
				type: 'gauge',
				startAngle: 90,
				endAngle: -270,
				pointer: {
					show: false
				},
				progress: {
					show: true,
					overlap: false,
					roundCap: true,
					clip: false,
					itemStyle: {
						borderWidth: 1,
						borderColor: '#464646'
					}
				},
				axisLine: {
					lineStyle: {
						width: 10
					}
				},
				splitLine: {
					show: false,
					distance: 0,
					length: 10
				},
				axisTick: {
					show: false
				},
				axisLabel: {
					show: false,
					distance: 50
				},
				// data: gaugeData,
				title: {
					fontSize: 13
				},
				detail: {
					width: 20,
					height: 10,
					fontSize: 10,
					color: 'inherit',
					formatter: ''
				}
			}
		]
	}
});

const collect = ref(0);
//收藏icon样式（1:已收藏，0:未收藏）
const CollectIconType = reactive({
	1: { icon: 'star-filled', text: '已收藏' },
	0: { icon: 'star', text: '收藏' }
});
//添加收藏
const editCollect = async () => {
	const res = await $request('/collect/edit_collect?foodId=' + foodMessage.value.foodId, 'GET');
	if (res.code === '500') {
		uni.showToast({
			title: '收藏失败',
			duration: 2000
		});
	} else {
		collect.value = res.data;
	}
};

const cardColor = reactive({
	1: {
		icon: '#70c995',
		text: 'color:#70c995'
	},
	2: {
		icon: '#f7cc4f',
		text: 'color:#f7cc4f'
	},
	3: {
		icon: 'red',
		text: 'color:red'
	}
});
//食物信息
const foodMessage = ref(null);
onLoad(() => {
	uni.$on('foodMessage', (data) => {
		uni.setNavigationBarTitle({
			title: data.name
		});
		foodMessage.value = data;
		collect.value = data.collect;
	});
});

onMounted(() => {
	// 组件能被调用必须是组件的节点已经被渲染到页面上
	setTimeout(async () => {
		gaugeData.protein[0].value = foodMessage.value.protein;
		data.protein.series[0].detail.formatter = foodMessage.value.protein + foodMessage.value.proteinUnit;
		//能量
		gaugeData.joule[0].value = (foodMessage.value.joule / 100).toFixed(2);
		data.joule.series[0].detail.formatter = Math.round(foodMessage.value.joule) + foodMessage.value.jouleUnit;
		//热量
		gaugeData.calory[0].value = (foodMessage.value.calory / 10).toFixed(2);
		data.calory.series[0].detail.formatter = Math.round(foodMessage.value.calory) + foodMessage.value.caloryUnit;
		//糖
		gaugeData.sugar[0].value = foodMessage.value.sugar;
		data.sugar.series[0].detail.formatter = Math.round(foodMessage.value.sugar) + foodMessage.value.sugarUnit;
		// 脂肪
		gaugeData.fat[0].value = foodMessage.value.fat;
		data.fat.series[0].detail.formatter = Math.round(foodMessage.value.fat) + foodMessage.value.fatUnit;
		// 碳水化合物
		gaugeData.carbohydrate[0].value = foodMessage.value.carbohydrate;
		data.carbohydrate.series[0].detail.formatter = Math.round(foodMessage.value.carbohydrate) + foodMessage.value.carbohydrateUnit;

		if (!protein.value) return;
		const proteinChart = await protein.value.init(echarts);
		proteinChart.setOption(data.protein);

		if (!joule.value) return;
		const jouleChart = await joule.value.init(echarts);
		jouleChart.setOption(data.joule);

		if (!calory.value) return;
		const caloryChart = await calory.value.init(echarts);
		caloryChart.setOption(data.calory);

		if (!sugar.value) return;
		const sugarChart = await sugar.value.init(echarts);
		sugarChart.setOption(data.sugar);

		if (!fat.value) return;
		const fatChart = await fat.value.init(echarts);
		fatChart.setOption(data.fat);

		if (!carbohydrate.value) return;
		const carbohydrateChart = await carbohydrate.value.init(echarts);
		carbohydrateChart.setOption(data.carbohydrate);
	}, 500);
});

//更多信息
const moreMessage = () => {
	uni.navigateTo({
		url: '/pageSearch/moreMessage/moreMessage',
		success: () => {
			uni.$emit('moreMessage', foodMessage.value);
		}
	});
};

//对比
const compare = () => {
	uni.navigateTo({
		url: '../FoodCompare/FoodCompare?&whoFood=1',
		success: () => {
			uni.$emit('Food', foodMessage.value);
		}
	});
};
</script>

<style lang="scss" scoped>
.contain {
	.topCard {
		.top {
			display: flex;
			justify-content: space-between;

			.name {
				margin-top: 20px;
				font-size: 13px;
			}

			.extra {
				display: flex;
				flex-direction: column;

				.tips-suggest {
					display: flex;
					$border: #909399 1px solid;
					padding: 7px 10px;
					background-color: #f0fefc;
					border-bottom-left-radius: 25px;
					transform: translateX(10px);

					.tips {
						padding: 0 5px;
						font-size: 12px;
					}

					.suggest {
						padding: 0 5px;
						font-size: 12px;
						color: #909399;
					}
				}
			}
		}

		.card-actions {
			display: flex;
			justify-content: space-around;
			border-top: #919398 1px solid;

			.card-actions-item {
				padding: 5px 10px;

				.card-actions-item-text {
					vertical-align: 2px;
				}
			}
		}
	}

	.card {
		.moreMessage {
			margin: 20px;
			display: flex;
			justify-content: center;

			.text {
				padding: 10px 20px;
				border: 1px solid #919398;
				border-radius: 25px;
				text-align: center;
				width: 100px;
			}
		}

		.food-message {
			// background-color: #f8f8f8;
			// height: 100vh;
			padding: 15px;
			display: flex;
			flex-direction: column;

			.foodChart {
				display: flex;
				justify-content: space-around;

				.chart {
					width: 100px;
					height: 120px;
				}
			}
		}
	}
}
</style>