<template>
  <div class="dashboard">
    <!-- 总览卡片 -->
    <div class="card-container">
      <div
          class="card"
          v-for="(item, index) in overviewData"
          :key="index"
          @click="changeKey(item.keys)"
      >
        <div class="icon-container">
          <component :is="item.icon" class="icon" />
        </div>
        <div class="text-container">
          <div class="title">{{ item.title }}</div>
          <div class="count">{{ item.count }}</div>
        </div>
      </div>
    </div>
    <!-- 食物类别分布 -->
    <div class="chart-container">
      <div class="chart-header">
        <h3>食物类别分布</h3>
      </div>
      <div id="pieChart" class="chart"></div>
    </div>
    <!-- 用户增长趋势图 -->
    <div class="chart-container">
      <div class="chart-header">
        <h3>用户增长趋势</h3>
      </div>
      <div id="lineChart" class="chart"></div>
    </div>


  </div>
</template>

<script setup>
import {ref, onMounted, h,getCurrentInstance} from 'vue'
import * as echarts from 'echarts'
import {AvocadoOne, FileCabinet, PeoplesTwo, ThinkingProblem} from '@icon-park/vue-next'
const {proxy} = getCurrentInstance()
// 总览数据
const overviewData = ref([
  {
    title: '用户数量',
    keys: ['2'],
    count: 1200,
    icon: () => h(PeoplesTwo, {theme: 'multi-color', size: 30, fill: ['#333', '#2F88FF', '#FFF', '#43CCF8']})
  },
  {
    title: '食物种类',
    keys: ['3'],
    count: 25,
    icon: () => h(AvocadoOne, {theme: 'multi-color', size: 30, fill: ['#333', '#2F88FF', '#FFF', '#43CCF8']})
  },
  {
    title: '文件数量',
    keys: ['4'],
    count: 80,
    icon: () => h(FileCabinet, {theme: 'multi-color', size: 30, fill: ['#333', '#2F88FF', '#FFF', '#43CCF8']})
  },
  {
    title: '题库条目',
    keys: ['5'],
    count: 150,
    icon: () => h(ThinkingProblem, {theme: 'multi-color', size: 30, fill: ['#333', '#2F88FF', '#FFF', '#43CCF8']})
  },
])

// 父子通信
const props = defineProps({selectedKeys: Array})
const emit = defineEmits(['update:selectedKeys'])

function changeKey(newKey) {
  emit('update:selectedKeys', newKey)
}

// 用户增长趋势图数据
const chartData = ref([
  {date: '2025-01-01', count: 100},
  {date: '2025-02-01', count: 200},
  {date: '2025-03-01', count: 300},
])

// 食物分类数据（可替换为后端接口）
const foodCategoryData = ref([
  {name: '主食类', value: 30},
  {name: '肉蛋类', value: 20},
  {name: '大豆及制品', value: 10},
  {name: '蔬菜菌藻类', value: 40},
  {name: '水果类', value: 25},
  {name: '奶类', value: 12},
  {name: '油脂类', value: 8},
  {name: '坚果类', value: 9},
  {name: '调味品', value: 6},
  {name: '饮料类', value: 7},
  {name: '零食及冷饮', value: 5},
  {name: '其他', value: 3},
])

// 初始化图表
onMounted(async () => {
  const res = await proxy.request.get("/home")
  overviewData.value[0].count = res.data.userCount
  overviewData.value[1].count = res.data.foodCount
  overviewData.value[2].count = res.data.fileCount
  overviewData.value[3].count = res.data.knowledgeCount
  foodCategoryData.value = res.data.foodCategoryDistribution
  console.log(res)
  // 折线图
  const lineChart = echarts.init(document.getElementById('lineChart'))
  lineChart.setOption({
    xAxis: {
      type: 'category',
      data: chartData.value.map(item => item.date),
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: chartData.value.map(item => item.count),
        type: 'line',
        smooth: true,
        areaStyle: {},
      },
    ],
  })

  // 饼图
  const pieChart = echarts.init(document.getElementById('pieChart'))
  pieChart.setOption({
    tooltip: {
      trigger: 'item',
    },
    legend: {
      top: 'bottom',
    },
    series: [
      {
        name: '食物分类',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 8,
          borderColor: '#fff',
          borderWidth: 2,
        },
        label: {
          show: true,
          formatter: '{b}: {d}%',
        },
        data: foodCategoryData.value,
      },
    ],
  })
})
</script>

<style scoped lang="scss">
.dashboard {
  padding: 20px;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: space-evenly;
}

.card {
  width: 200px;
  height: 120px;
  padding: 16px;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    background-color: #f5faff;
  }

  .icon-container {
    margin-bottom: 12px;

    .icon {
      font-size: 30px;
      transition: transform 0.3s ease;
    }
  }

  .text-container {
    text-align: center;

    .title {
      font-weight: bold;
      font-size: 16px;
    }

    .count {
      font-size: 20px;
      color: #2f88ff;
      font-weight: 600;
    }
  }
}

.chart-container {
  width: 100%;
  margin-top: 28px;
}

.chart-header h3 {
  font-size: 1.2em;
  font-weight: bold;
}

.chart {
  width: 100%;
  height: 400px;
}
</style>
