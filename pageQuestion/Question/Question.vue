<template>
	<view class="content">
		<!-- 每日一答 -->
		<view class="question" v-if="q">
			<uni-card class="card" :title="'问题：' + q.question">
				<view :class="style.A" @click.once="selectAnswer(randomAnswer[0], 'A')">A: {{ randomAnswer[0] }}</view>
				<view :class="style.B" @click.once="selectAnswer(randomAnswer[1], 'B')">B: {{ randomAnswer[1] }}</view>
				<view class="result" v-if="userAnswer">
					<view class="text">{{ userAnswer }}</view>
					<view class="analyze">正确答案： {{ q.correctAnswer }}</view>
				</view>
			</uni-card>
		</view>
	</view>
</template>

<script setup lang="ts">
import { onLoad } from '@dcloudio/uni-app';
import { number } from 'echarts';
import { reactive, ref } from 'vue';
interface QUESTION {
	id: number;
	question: string;
	wrongAnswer: string;
	correctAnswer: string;
}
//问题
const q = ref<QUESTION>(null);
//答案数组
const answer = ref<string[]>([]);
//随机答案数组

const randomAnswer = ref<string[]>([]);
onLoad((): void => {
	//获取question
	uni.$on('question', (option: QUESTION): void => {
		q.value = option;
		answer.value.push(q.value.correctAnswer);
		answer.value.push(q.value.wrongAnswer);
		//随机设置答案
		const index = Math.random() * answer.value.length;
		const v = answer.value.splice(index, 1)[0];
		randomAnswer.value.push(v);
		randomAnswer.value.push(answer.value[0]);
	});
});
//回答
interface STYLE {
	A: string[];
	B: string[];
}
const style = reactive({
	A: ['answer'],
	B: ['answer']
});
//用户回答选项
const userAnswer = ref<string>(null);
const selectAnswer = (select: string, index: string): void => {
	style.A = ['answer'];
	style.B = ['answer'];
	if (select === q.value.correctAnswer) {
		userAnswer.value = '回答正确：' + index;
		style[index] = ['answer', 'success'];
	} else {
		userAnswer.value = '回答错误：' + index;
		style[index] = ['answer', 'err'];
	}
};
</script>

<style lang="scss">
.err {
	background-color: red;
	color: white;
}
.success {
	background-color: #61d375;
}
.content {
	height: 100vh;
	background-color: #f8f8f8;
	padding: 15px;
	.question {
		.card {
			.uni-card {
				margin: 0 !important;
				padding: 10px !important;
			}
			.answer {
				padding: 10px 15px;
				font-size: 16px;
				border: 1px solid rgb(243, 245, 249);
				margin-top: 20px;
				border-radius: 30px;
				box-shadow: 1px 1px 1px 0px rgba(0, 0, 0, 0.3);
				transition: 0.2s;
				&:active {
					box-shadow: none;
				}
			}
			.result {
				.text {
					margin-top: 30px;
				}
				.analyze {
					margin-top: 20px;
				}
			}
		}
	}
}
</style>
