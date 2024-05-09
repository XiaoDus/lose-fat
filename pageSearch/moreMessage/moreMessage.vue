<template>
	<view class="contain">
		<uni-card class="card" :title="foodMessage.name" spacing="15px" :sub-title="foodMessage.healthTips" :extra="foodMessage.healthSuggest">
			<uni-table border stripe emptyText="暂无更多数据">
				<!-- 表头行 -->
				<uni-tr>
					<uni-th align="center">项目</uni-th>
					<uni-th align="center">每「100克」可食用部</uni-th>
				</uni-tr>
				<!-- 表格数据行 -->
				<uni-tr v-if="foodMessage.joule != ''">
					<uni-td align="center">能量</uni-td>
					<uni-td align="center">{{foodMessage.joule}}{{foodMessage.jouleUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.calory != ''">
					<uni-td align="center">热量</uni-td>
					<uni-td align="center">{{foodMessage.calory}}{{foodMessage.caloryUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.protein != ''">
					<uni-td align="center">蛋白质</uni-td>
					<uni-td align="center">{{foodMessage.protein}}{{foodMessage.proteinUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.fat != ''">
					<uni-td align="center">脂肪</uni-td>
					<uni-td align="center">{{foodMessage.fat}}{{foodMessage.fatUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.carbohydrate != ''">
					<uni-td align="center">碳水化合物</uni-td>
					<uni-td align="center">{{foodMessage.carbohydrate}}{{foodMessage.carbohydrateUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.fiberDietary != ''">
					<uni-td align="center">膳食纤维</uni-td>
					<uni-td align="center">{{foodMessage.fiberDietary}}{{foodMessage.fiberDietaryUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.sugar != ''">
					<uni-td align="center" >糖</uni-td>
					<uni-td align="center">{{foodMessage.sugar}}{{foodMessage.sugarUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.natrium != ''">
					<uni-td align="center">钠</uni-td>
					<uni-td align="center">{{foodMessage.natrium}}{{foodMessage.natriumUnit}}</uni-td>
				</uni-tr>
				
				<uni-tr v-if="foodMessage.saturatedFat != ''">
					<uni-td align="center">饱和脂肪</uni-td>
					<uni-td align="center">{{foodMessage.saturatedFat}}{{foodMessage.saturatedFatUnit}}</uni-td>
				</uni-tr>
				
				<uni-tr v-if="foodMessage.fattyAcid != ''">
					<uni-td align="center">反式脂肪</uni-td>
					<uni-td align="center">{{foodMessage.fattyAcid}}{{foodMessage.fattyAcidUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.mufa != ''">
					<uni-td align="center">单不饱和脂肪</uni-td>
					<uni-td align="center">{{foodMessage.fattyAcid}}{{foodMessage.mufaUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.pufa != ''">
					<uni-td align="center">多不饱和脂肪</uni-td>
					<uni-td align="center">{{foodMessage.pufa}}{{foodMessage.pufaUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.cholesterol != ''">
					<uni-td align="center">胆固醇</uni-td>
					<uni-td align="center">{{foodMessage.cholesterol}}{{foodMessage.cholesterolUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.alcohol != ''">
					<uni-td align="center">酒精</uni-td>
					<uni-td align="center">{{foodMessage.alcohol}}{{foodMessage.alcoholUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminA != ''">
					<uni-td align="center">维生素A</uni-td>
					<uni-td align="center">{{foodMessage.vitaminA}}{{foodMessage.vitaminAUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.carotene != ''">
					<uni-td align="center">胡萝卜素</uni-td>
					<uni-td align="center">{{foodMessage.carotene}}{{foodMessage.caroteneUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminC != ''">
					<uni-td align="center">维生素C</uni-td>
					<uni-td align="center">{{foodMessage.vitaminC}}{{foodMessage.vitaminCUnit}}</uni-td>
				</uni-tr><uni-tr v-if="foodMessage.vitaminD != ''">
					<uni-td align="center">维生素D</uni-td>
					<uni-td align="center">{{foodMessage.vitaminD}}{{foodMessage.vitaminDUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminE != ''">
					<uni-td align="center">维生素E</uni-td>
					<uni-td align="center">{{foodMessage.vitaminE}}{{foodMessage.vitaminEUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminK != ''">
					<uni-td align="center">维生素E</uni-td>
					<uni-td align="center">{{foodMessage.vitaminK}}{{foodMessage.vitaminKUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.thiamine != ''">
					<uni-td align="center">维生素B1</uni-td>
					<uni-td align="center">{{foodMessage.thiamine}}{{foodMessage.thiamineUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.lactoflavin != ''">
					<uni-td align="center">维生素B2</uni-td>
					<uni-td align="center">{{foodMessage.lactoflavin}}{{foodMessage.lactoflavinUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminB6 != ''">
					<uni-td align="center">维生素B6</uni-td>
					<uni-td align="center">{{foodMessage.vitaminB6}}{{foodMessage.vitaminB6Unit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.vitaminB12 != ''">
					<uni-td align="center">维生素B12</uni-td>
					<uni-td align="center">{{foodMessage.vitaminB12}}{{foodMessage.vitaminB12Unit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.niacin != ''">
					<uni-td align="center">烟酸</uni-td>
					<uni-td align="center">{{foodMessage.niacin}}{{foodMessage.niacinUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.folacin != ''">
					<uni-td align="center">叶酸</uni-td>
					<uni-td align="center">{{foodMessage.folacin}}{{foodMessage.folacinUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.pantothenic != ''">
					<uni-td align="center">泛酸</uni-td>
					<uni-td align="center">{{foodMessage.pantothenic}}{{foodMessage.pantothenicUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.biotin != ''">
					<uni-td align="center">生物素</uni-td>
					<uni-td align="center">{{foodMessage.biotin}}{{foodMessage.biotinUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.choline != ''">
					<uni-td align="center">胆碱</uni-td>
					<uni-td align="center">{{foodMessage.choline}}{{foodMessage.cholineUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.calcium != ''">
					<uni-td align="center">钙</uni-td>
					<uni-td align="center">{{foodMessage.calcium}}{{foodMessage.calciumUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.phosphor != ''">
					<uni-td align="center">磷</uni-td>
					<uni-td align="center">{{foodMessage.phosphor}}{{foodMessage.phosphorUnit}}</uni-td>
				</uni-tr>
				
				<uni-tr v-if="foodMessage.kalium != ''">
					<uni-td align="center">钾</uni-td>
					<uni-td align="center">{{foodMessage.kalium}}{{foodMessage.kaliumUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.magnesium != ''">
					<uni-td align="center">镁</uni-td>
					<uni-td align="center">{{foodMessage.magnesium}}{{foodMessage.magnesiumUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.iron != ''">
					<uni-td align="center">铁</uni-td>
					<uni-td align="center">{{foodMessage.iron}}{{foodMessage.ironUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.zinc != ''">
					<uni-td align="center">锌</uni-td>
					<uni-td align="center">{{foodMessage.zinc}}{{foodMessage.zincUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.iodine != ''">
					<uni-td align="center">碘</uni-td>
					<uni-td align="center">{{foodMessage.iodine}}{{foodMessage.iodineUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.selenium != ''">
					<uni-td align="center">硒</uni-td>
					<uni-td align="center">{{foodMessage.selenium}}{{foodMessage.seleniumUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.copper != ''">
					<uni-td align="center">铜</uni-td>
					<uni-td align="center">{{foodMessage.copper}}{{foodMessage.copperUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.fluorine != ''">
					<uni-td align="center">氟</uni-td>
					<uni-td align="center">{{foodMessage.fluorine}}{{foodMessage.fluorineUnit}}</uni-td>
				</uni-tr>
				<uni-tr v-if="foodMessage.manganese != ''">
					<uni-td align="center">锰</uni-td>
					<uni-td align="center">{{foodMessage.manganese}}{{foodMessage.manganeseUnit}}</uni-td>
				</uni-tr>
				
			</uni-table>
		</uni-card>
	</view>
</template>

<script setup>
	import {
		ref,
	} from 'vue';
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import uCard from '../../uni_modules/uni-card/components/uni-card/uni-card.vue'
	import uTable from '../../uni_modules/uni-table/components/uni-table/uni-table.vue'
	const foodMessage = ref(null)
	onLoad(() => {
		uni.$on("moreMessage", (data) => {
			uni.setNavigationBarTitle({
				title: data.name
			})
			foodMessage.value = data
		})
	})
</script>

<style lang="scss">
</style>