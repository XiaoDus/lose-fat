<template>
  <div class="container">
    <a-layout >
      <a-layout-header :style="{padding: '0 15px 0 15px'}">
        <div class="header">
          <h2>轻身助手管理后台</h2>
          <div class="avatar">
            <a-dropdown placement="bottomRight" arrow>
              <a-avatar size="large" :src="API_URL+userInfo['avatar']"></a-avatar>
              <template #overlay>
                <a-menu>
                  <a-menu-item >
                    <div class="menu" style="display: flex;align-items: center;gap: 5px;" @click="showLoginOutConfirm">
                      <logout theme="outline" size="15" fill="#333" />
                      <a style="margin-top: -3px;color: black">退出登录</a></div>
                  </a-menu-item>
                  <a-menu-item>
                    <div class="menu" style="display: flex;align-items: center;gap: 5px;">
                      <User theme="outline" size="15" fill="#333" />
                      <a style="margin-top: -3px;color: black" href="javascript:;">个人信息</a>
                    </div>

                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>


          </div>
        </div>
      </a-layout-header>

      <a-layout>
        <a-layout-sider theme="light" breakpoint="lg" collapsed-width="0">
          <a-menu
              id="dddddd"
              v-model:openKeys="openKeys"
              v-model:selectedKeys="selectedKeys"
              mode="inline"
              :items="items"
          ></a-menu>
        </a-layout-sider>
        <a-layout>
          <a-layout-content>
            <div class="content">
              <TableComponents v-if="selectedKeys[0] == '2'" columns-name="user" />
              <TableComponents v-else-if="selectedKeys[0] == '3'"  columns-name="food" />
              <TableComponents v-else-if="selectedKeys[0] == '4'"  columns-name="file" />
              <TableComponents v-else-if="selectedKeys[0] == '5'"  columns-name="file" />
              <TableComponents v-else-if="selectedKeys[0] == '6'"  columns-name="file" />
            </div>
          </a-layout-content>
<!--          <a-layout-footer :style="{ textAlign: 'center',zIndex:1 }">Ant Design ©2018 Created by Ant UED</a-layout-footer>-->
        </a-layout>


      </a-layout>
    </a-layout>
  </div>
</template>
<script setup>
import {reactive, ref, watch, VueElement, h, onMounted} from 'vue';
import TableComponents from '../components/TableComponents.vue';
import {
  Layers,
  HomeTwo,
  FileEditingOne,
  PeoplesTwo,
  AvocadoOne,
  ThinkingProblem,
  Carousel,
  User,
  Logout
} from '@icon-park/vue-next';

import {useCounterStore} from '../store/index.js'
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode } from 'vue';
import {useRouter} from 'vue-router'
const router = useRouter()
const store = useCounterStore()
const userInfo = ref(store.$state)
const API_URL = import.meta.env.VITE_APP_API_URL_DEV
const selectedKeys = ref(['1']);
const openKeys = ref(['sub2']);
const showLoginOutConfirm = () => {
  Modal.confirm({
    title: '确定退出登录吗?',
    icon: createVNode(ExclamationCircleOutlined),
    content: '确认退出登录吗？',
    okText: 'Yes',
    okType: 'danger',
    cancelText: 'No',
    onOk() {
      store.logout()
      router.replace('/login')
    },
    onCancel() {
      console.log('Cancel');
    },
  });
};
function getItem(
    label,
    key,
    icon,
    children,
    type,
) {
  return {
    key,
    icon,
    children,
    label,
    type,
  };
}

const items = reactive([
  getItem('首页', '1', () => h(HomeTwo)),
  getItem('信息管理', 'sub2', () => h(Layers), [
    getItem('用户管理', '2', () => h(PeoplesTwo)),
    getItem('食物管理', '3', () => h(AvocadoOne)),
    getItem('文件管理', '4', () => h(FileEditingOne)),
    getItem('题库管理', '5', () => h(ThinkingProblem)),
    getItem('轮播图管理', '6', () => h(Carousel)),
  ]),

  {type: 'divider'},

  getItem('管理员', '9', () => h(User),),

]);



watch(openKeys, val => {
  console.log('openKeys', val);
});

// onMounted()
</script>

<style lang="scss" scoped>
.container {
  .header {
    display: flex;
    justify-content: space-between;

    h2 {
      color: white;
    }

    .avatar {
      //padding: 0 10px 0 20px;

    }

  }
}
</style>