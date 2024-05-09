<template>
	<view class="constain">
		<view class="compare" style="width: 100vw">
			<uni-card>
				<uni-table style="100vw" border :stripe="true" emptyText="暂无更多数据">
					<!-- 表头行 -->
					<uni-tr>
						<uni-th v-if="food_1 != null">
							<view class="btn-th" @click="selectFood(1)">{{ food_1.name }}</view>
						</uni-th>
						<uni-th v-else>
							<view class="btn-th" @click="selectFood(1)">点击添加</view>
						</uni-th>
						<uni-th>
							<view class="th" style="font-size: 20px; font-size: 30px; font-family: fantasy">vs</view>
						</uni-th>
						<uni-th v-if="food_2 != null">
							<view class="btn-th" @click="selectFood(2)">{{ food_2.name }}</view>
						</uni-th>
						<uni-th v-else>
							<view class="btn-th" @click="selectFood(2)">点击添加</view>
						</uni-th>
					</uni-tr>
					<!-- 表格数据行 -->
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.healthTips }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>健康等级</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.healthTips }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.joule }}{{ food_1.jouleUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>能量</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.joule }}{{ food_2.jouleUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.calory }}{{ food_1.caloryUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>热量</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.calory }}{{ food_2.caloryUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.protein }}{{ food_1.proteinUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>蛋白质</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.protein }}{{ food_2.proteinUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.fat }}{{ food_1.fatUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>脂肪</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.fat }}{{ food_2.fatUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.carbohydrate }}{{ food_1.carbohydrateUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>碳水化合物</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.carbohydrate }}{{ food_2.carbohydrateUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.fiberDietary }}{{ food_1.fiberDietaryUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>膳食纤维</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.fiberDietary }}{{ food_2.fiberDietaryUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.sugar }}{{ food_1.sugarUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>糖</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.sugar }}{{ food_2.sugarUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.natrium }}{{ food_1.natriumUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>钠</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.natrium }}{{ food_2.natriumUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>

					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.saturatedFat }}{{ food_1.saturatedFatUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>饱和脂肪</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.saturatedFat }}{{ food_2.saturatedFatUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>

					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.fattyAcid }}{{ food_1.fattyAcidUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>反式脂肪</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.fattyAcid }}{{ food_2.fattyAcidUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.fattyAcid }}{{ food_1.mufaUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>单不饱和脂肪</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.fattyAcid }}{{ food_2.mufaUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.pufa }}{{ food_1.pufaUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>多不饱和脂肪</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.pufa }}{{ food_2.pufaUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.cholesterol }}{{ food_1.cholesterolUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>胆固醇</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.cholesterol }}{{ food_2.cholesterolUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.alcohol }}{{ food_1.alcoholUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>酒精</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.alcohol }}{{ food_2.alcoholUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminA }}{{ food_1.vitaminAUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素A</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminA }}{{ food_2.vitaminAUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.carotene }}{{ food_1.caroteneUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>胡萝卜素</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.carotene }}{{ food_2.caroteneUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminC }}{{ food_1.vitaminCUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素C</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminC }}{{ food_2.vitaminCUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminD }}{{ food_1.vitaminDUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素D</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminD }}{{ food_2.vitaminDUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminE }}{{ food_1.vitaminEUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素E</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminE }}{{ food_2.vitaminEUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminK }}{{ food_1.vitaminKUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素E</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminK }}{{ food_2.vitaminKUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.thiamine }}{{ food_1.thiamineUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素B1</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.thiamine }}{{ food_2.thiamineUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.lactoflavin }}{{ food_1.lactoflavinUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素B2</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.lactoflavin }}{{ food_2.lactoflavinUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminB6 }}{{ food_1.vitaminB6Unit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素B6</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminB6 }}{{ food_2.vitaminB6Unit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.vitaminB12 }}{{ food_1.vitaminB12Unit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>维生素B12</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.vitaminB12 }}{{ food_2.vitaminB12Unit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.niacin }}{{ food_1.niacinUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>烟酸</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.niacin }}{{ food_2.niacinUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.folacin }}{{ food_1.folacinUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>叶酸</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.folacin }}{{ food_2.folacinUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.pantothenic }}{{ food_1.pantothenicUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>泛酸</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.pantothenic }}{{ food_2.pantothenicUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.biotin }}{{ food_1.biotinUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>生物素</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.biotin }}{{ food_2.biotinUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.choline }}{{ food_1.cholineUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>胆碱</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.choline }}{{ food_2.cholineUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.calcium }}{{ food_1.calciumUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>钙</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.calcium }}{{ food_2.calciumUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.phosphor }}{{ food_1.phosphorUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>磷</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.phosphor }}{{ food_2.phosphorUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>

					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.kalium }}{{ food_1.kaliumUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>钾</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.kalium }}{{ food_2.kaliumUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.magnesium }}{{ food_1.magnesiumUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>镁</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.magnesium }}{{ food_2.magnesiumUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.iron }}{{ food_1.ironUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>铁</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.iron }}{{ food_2.ironUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.zinc }}{{ food_1.zincUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>锌</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.zinc }}{{ food_2.zincUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.iodine }}{{ food_1.iodineUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>碘</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.iodine }}{{ food_2.iodineUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.selenium }}{{ food_1.seleniumUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>硒</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.selenium }}{{ food_2.seleniumUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.copper }}{{ food_1.copperUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>铜</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.copper }}{{ food_2.copperUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.fluorine }}{{ food_1.fluorineUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>氟</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.fluorine }}{{ food_2.fluorineUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
					<uni-tr>
						<uni-td v-if="food_1 != null">{{ food_1.manganese }}{{ food_1.manganeseUnit }}</uni-td>
						<uni-td v-else></uni-td>
						<uni-td>锰</uni-td>
						<uni-td v-if="food_2 != null">{{ food_2.manganese }}{{ food_2.manganeseUnit }}</uni-td>
						<uni-td v-else></uni-td>
					</uni-tr>
				</uni-table>
			</uni-card>
		</view>
	</view>
</template>

<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app';
import { getCurrentInstance, ref, reactive } from 'vue';
const { $request } = getCurrentInstance().appContext.config.globalProperties;

interface FOOD {
	alcohol: string;
	alcoholUnit: string;
	biotin: string;
	biotinUnit: string;
	calcium: string;
	calciumUnit: string;
	calory: string;
	caloryUnit: string;
	carbohydrate: string;
	carbohydrateUnit: string;
	carotene: string;
	caroteneUnit: string;
	cholesterol: string;
	cholesterolUnit: string;
	choline: string;
	cholineUnit: string;
	copper: string;
	copperUnit: string;
	fat: string;
	fatUnit: string;
	fattyAcid: string;
	fattyAcidUnit: string;
	fiberDietary: string;
	fiberDietaryUnit: string;
	fluorine: string;
	fluorineUnit: string;
	folacin: string;
	folacinUnit: string;
	foodId: string;
	healthLight: number;
	healthSuggest: string;
	healthTips: string;
	id: number;
	iodine: string;
	iodineUnit: string;
	iron: string;
	ironUnit: string;
	joule: string;
	jouleUnit: string;
	kalium: string;
	kaliumUnit: string;
	lactoflavin: string;
	lactoflavinUnit: string;
	magnesium: string;
	magnesiumUnit: string;
	manganese: string;
	manganeseUnit: string;
	mufa: string;
	mufaUnit: string;
	name: string;
	natrium: string;
	natriumUnit: string;
	niacin: string;
	niacinUnit: string;
	pantothenic: string;
	pantothenicUnit: string;
	phosphor: string;
	phosphorUnit: string;
	protein: string;
	proteinUnit: string;
	pufa: string;
	pufaUnit: string;
	saturatedFat: string;
	saturatedFatUnit: string;
	selenium: string;
	seleniumUnit: string;
	sugar: string;
	sugarUnit: string;
	thiamine: string;
	thiamineUnit: string;
	vitaminA: string;
	vitaminAUnit: string;
	vitaminB6: string;
	vitaminB6Unit: string;
	vitaminB12: string;
	vitaminB12Unit: string;
	vitaminC: string;
	vitaminCUnit: string;
	vitaminD: string;
	vitaminDUnit: string;
	vitaminE: string;
	vitaminEUnit: string;
	vitaminK: string;
	vitaminKUnit: string;
	zinc: string;
	zincUnit: string;
}
//对比的食物信息
const food_1 = ref<FOOD>(null);
const food_2 = ref<FOOD>(null);
//需要给哪个食物列赋值（1:food_1，2:food_2）
const foodNumber = ref<number>(2);
//获取食物信息
const getFoodMsg = async (foodId: string, whoFood: number): Promise<void> => {
	const res = await $request('/foods/food_message?foodId=' + foodId, 'GET');
	if (res.code == '200') {
		whoFood == 1 ? (food_1.value = res.data) : (food_2.value = res.data);
	}
};

//选择食物
const selectFood = (number: number): void => {
	foodNumber.value = number;
	// showDrawer.value.open();
	uni.navigateTo({
		url: '/pageSearch/Search/Search?url=FoodCompare',
		success: () => {
			uni.$on('getFoodmessage', (value: any): void => {
				foodNumber.value == 1 ? (food_1.value = value) : (food_2.value = value);
			});
		}
	});
};

onLoad((option) => {
	foodNumber.value = option.whoFood;
	uni.$on('Food', (value) => {
		foodNumber.value == 1 ? (food_1.value = value) : (food_2.value = value);
	});
});
</script>

<style lang="scss">
.constain {
	padding: 10;
}

.btn-th {
	width: 89px;
	font-size: 12px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.uni-table-td {
	font-size: 13px !important;
}
</style>
