<template>
  <a-row justify="center" align="middle" class="login-container">
    <a-col :span="13" style="display: flex; justify-content: center;">
      <img  style="border-radius: 8px 0 0 8px;flex: 1" src="../assets/img.png" alt="" width="100%" height="400px">
      <div class="login-box" style="width: 330px">
<!--        <h2 class="login-title">欢迎登录</h2>-->
        <a-form
            layout="vertical"
            @finish="onLogin"
            ref="formRef"
            :model="formState"
            :rules="rules"
            class="login-form"
        >
          <a-form-item
              label="请输入用户名/手机号"
              name="username"
          >
            <a-input :maxlength="11" placeholder="请输入用户名/手机号" v-model:value="formState.username" />
          </a-form-item>

          <a-form-item
              label="请输入您的密码"
              name="password"
          >
            <a-input-password placeholder="请输入密码" v-model:value="formState.password" />
          </a-form-item>
          <a-form-item
              label="请输入验证码"
              name="verification"
          >
            <div class="verification">
              <a-input class="verification-input" placeholder="请输入验证码" :maxlength="4" v-model:value="formState.verification" />
              <Identity ref="IdentityChild"/>
            </div>

          </a-form-item>

          <a-form-item>
            <a-button type="primary" html-type="submit" block>登录</a-button>
          </a-form-item>
        </a-form>
      </div>
    </a-col>


  </a-row>
</template>

<script setup>
import {ref, reactive, getCurrentInstance} from 'vue'
import Identity from '../components/Identify.vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue';
const router = useRouter()
const {proxy} = getCurrentInstance()
const IdentityChild = ref(null)
const formRef = ref();
const rules = {
  username: [
    { required: true, message: '请输入用户名/手机号!' },
    {  min: 6,max:11, message: '用户名/手机号长度不能小于11位!' },
  ],
  password: [{ required: true, message: '请输入密码!' }],
  verification: [{ required: true,min:4, message: '请输入验证码!' }],
}
const formState = reactive({
  username: '',
  password: '',
  verification:''
});
const onLogin = async (values) => {
 const res = await proxy.request.post('/user/login',values,{ withCredentials: true })
  if(res.code === '200'){
    message.success('登录成功');
    router.push('/index')
  }else if(res.code === '400'){
    IdentityChild.value.getVerificationCode()
    message.error(res.msg)
  }
}

</script>

<style scoped lang="scss">
.login-container {
  min-height: 100vh;
  background-color: #f0f2f5;
}

.login-box {
  background: #fff;
  padding: 40px;
  border-radius: 0 8px 8px 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
}

.login-form {
  max-width: 100%;
}
.verification{
  display: flex;
  justify-content: center;
  .verification-input{
    border-radius:  6px 0  0 6px;

    border-right: 0;
  }
}
</style>
