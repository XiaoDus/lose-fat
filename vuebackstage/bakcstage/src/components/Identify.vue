<template>
  <div class="captcha-container">
    <canvas  ref="captchaCanvas" width="110" height="30" @click="getVerificationCode"></canvas>
  </div>
</template>

<script setup>
import {ref, onMounted, getCurrentInstance} from 'vue';
//获取全局变量
const {proxy} = getCurrentInstance()
import CryptoJS from "crypto-js";

const captchaCanvas = ref(null);

// 字体列表
const fonts = [
  'Arial', 'Verdana', 'Georgia', 'Courier New', 'Times New Roman', 'Tahoma', 'Impact', 'Comic Sans MS'
];


// 在画布上绘制验证码
const drawCaptcha = (text) => {
  const canvas = captchaCanvas.value;
  const ctx = canvas.getContext('2d');
  const fontSize = 20;

  // 清空画布
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // 设置随机背景颜色，避免与文本和干扰线相近
  const bgColor = getRandomBackgroundColor();
  canvas.style.backgroundColor = bgColor;

  // 添加干扰线
  addComplexNoise(ctx, canvas.width, canvas.height);

  // 计算每个字符的宽度并确保不会超出画布
  const spacing = calculateSpacing(text, fontSize, canvas.width);

  let x = 0; // 初始x坐标
  for (let i = 0; i < text.length; i++) {
    const font = getRandomFont();
    const randomSize = Math.floor(Math.random() * 10) + fontSize; // 随机字体大小
    const randomAngle = (Math.random() - 0.5) * 0.5; // 随机旋转角度，最大0.5度

    // 设置随机字体、大小、旋转
    ctx.font = `${randomSize}px ${font}`;
    const textColor = getRandomTextColor(bgColor); // 确保文本颜色与背景不重合
    ctx.fillStyle = textColor;

    ctx.save(); // 保存当前状态
    ctx.translate(x + randomSize / 2, 25); // 平移到文字位置
    ctx.rotate(randomAngle); // 旋转
    ctx.fillText(text[i], 0, 0); // 绘制文字
    ctx.restore(); // 恢复状态

    x += randomSize + spacing; // 根据计算的间隔调整字符位置
  }
};

// 计算字符间隔，使得验证码均匀分布且不超出画布
const calculateSpacing = (text, fontSize, canvasWidth) => {
  const totalWidth = Array.from(text).reduce((width) => {
    const randomSize = Math.floor(Math.random() * 10) + fontSize;
    return width + randomSize;
  }, 0);

  const remainingSpace = canvasWidth - totalWidth; // 计算剩余空间
  const spacing = remainingSpace / (text.length + 1); // 根据字符数量和剩余空间计算间隔

  // 调整间隔，减小间距
  return spacing > 2 ? spacing - 5 : spacing;
};


// 获取随机字体
const getRandomFont = () => {
  return fonts[Math.floor(Math.random() * fonts.length)];
};

// 获取随机背景颜色，避免与文本颜色相近
const getRandomBackgroundColor = () => {
  const r = Math.floor(Math.random() * 256);
  const g = Math.floor(Math.random() * 256);
  const b = Math.floor(Math.random() * 256);

  // 防止背景与文本颜色相同
  if (r < 60 && g < 60 && b < 60) {
    return `rgb(${r + 60}, ${g + 60}, ${b + 60})`;
  }
  return `rgb(${r}, ${g}, ${b})`;
};

// 获取随机文本颜色，避免与背景颜色相重合
const getRandomTextColor = (bgColor) => {
  const bgColorRgb = bgColor.match(/\d+/g);
  let textColor;

  do {
    textColor = `rgb(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)})`;
  } while (isColorSimilar(bgColorRgb, textColor)); // 确保文本颜色与背景不相似

  return textColor;
};

// 判断两个颜色是否相似
const isColorSimilar = (bgColorRgb, textColor) => {
  const textColorRgb = textColor.match(/\d+/g);
  const threshold = 100; // 定义颜色相似的阈值
  const rDiff = Math.abs(bgColorRgb[0] - textColorRgb[0]);
  const gDiff = Math.abs(bgColorRgb[1] - textColorRgb[1]);
  const bDiff = Math.abs(bgColorRgb[2] - textColorRgb[2]);

  return rDiff < threshold && gDiff < threshold && bDiff < threshold;
};

// 添加复杂的干扰线
const addComplexNoise = (ctx, width, height) => {
  const lineCount = 10; // 干扰线数量
  const dotCount = 30; // 随机点的数量
  const arcCount = 5; // 圆形弧线数量

  // 绘制干扰线
  for (let i = 0; i < lineCount; i++) {
    const x1 = Math.random() * width;
    const y1 = Math.random() * height;
    const x2 = Math.random() * width;
    const y2 = Math.random() * height;

    // 随机线条颜色和宽度
    ctx.strokeStyle = getRandomLineColor(); // 随机线条颜色
    ctx.lineWidth = Math.random() * 2 + 1;
    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.stroke();
  }

  // 绘制随机点
  for (let i = 0; i < dotCount; i++) {
    const x = Math.random() * width;
    const y = Math.random() * height;

    // 随机点的颜色和大小
    ctx.fillStyle = getRandomLineColor(); // 随机点颜色
    ctx.beginPath();
    ctx.arc(x, y, Math.random() * 2 + 1, 0, Math.PI * 2);
    ctx.fill();
  }

  // 绘制圆形弧线
  for (let i = 0; i < arcCount; i++) {
    const x = Math.random() * width;
    const y = Math.random() * height;
    const radius = Math.random() * 20 + 10;
    const startAngle = Math.random() * Math.PI * 2;
    const endAngle = startAngle + Math.random() * Math.PI;

    // 随机弧线的颜色和透明度
    ctx.strokeStyle = getRandomLineColor(); // 随机弧线颜色
    ctx.lineWidth = Math.random() * 2 + 1;
    ctx.beginPath();
    ctx.arc(x, y, radius, startAngle, endAngle);
    ctx.stroke();
  }
};

// 获取随机线条颜色
const getRandomLineColor = () => {
  return `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.3)`;
};
const verificationCode = ref('')
const getVerificationCode = async () => {
  const verification =await proxy.request.get("/login/verification",{ withCredentials: true })
  if (verification.code !== '200'){
    getVerificationCode()
    return
  }
  const secretKey = import.meta.env.VITE_APP_SECRET_KET;  // 使用与后端相同的密钥
  const decryptedBytes = CryptoJS.AES.decrypt(
      verification.data,
      CryptoJS.enc.Utf8.parse(secretKey),
      {
        mode: CryptoJS.mode.ECB,  // 与加密时的模式一致
        padding: CryptoJS.pad.Pkcs7,  // 填充方式与加密时一致
      }
  );
  // 解密后的内容转为UTF-8字符串
  const decryptedData = decryptedBytes.toString(CryptoJS.enc.Utf8);
  verificationCode.value = decryptedData
  drawCaptcha(verificationCode.value)
}
defineExpose({ getVerificationCode })
// 在组件挂载时生成验证码
onMounted(() => {
  getVerificationCode()
  if (verificationCode.value) {
    drawCaptcha(verificationCode.value)
  }
});
</script>

<style scoped>
.captcha-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

canvas {
  border: 1px solid #d9d9d9;
  border-radius: 0 6px 6px 0;
}

button {
  padding: 5px 10px;
  font-size: 16px;
  cursor: pointer;
}
</style>
