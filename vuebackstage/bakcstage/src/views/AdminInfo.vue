<template>
  <a-card
      bordered
      class="max-w-5xl mx-auto mt-6 shadow-md rounded-2xl bg-white relative"
      style="height: 92vh"
  >
    <div class="flex justify-between items-center mb-6" style="display: flex;justify-content: space-between;">
      <h2 class="text-xl font-bold">管理员个人信息</h2>
      <a-button type="primary" size="middle" @click="isEditModalOpen = true">
        编辑信息
      </a-button>
    </div>

    <div class="grid grid-cols-[auto,1fr] gap-6 items-start">
      <!-- 头像上传 -->
      <a-upload
          name="file"
          :action="API_URL + '/file/adminUpload'"
          :show-upload-list="false"
          :before-upload="beforeUpload"
          :headers="{ token: user.token }"
          @change="handleAvatarChange"
      >
        <template #default>
          <div
              class="w-[96px] h-[96px] rounded-lg overflow-hidden bg-gray-100 flex items-center justify-center cursor-pointer border border-dashed hover:border-blue-500 transition"
          >
            <a-avatar
                :src="user.avatar ? API_URL + user.avatar : defaultAvatar"
                :size="96"
                shape="square"
                style="pointer-events: none"
            />
          </div>
        </template>
      </a-upload>

      <!-- 信息区块 -->
      <div class="w-full space-y-4" style="margin-top: 20px">
        <h3 class="text-lg font-semibold">基本信息</h3>
        <a-descriptions
            bordered
            :column="2"
            size="middle"
            layout="horizontal"
            class="rounded-xl overflow-hidden"
        >
          <a-descriptions-item label="用户名">{{ user.userName }}</a-descriptions-item>
          <a-descriptions-item label="手机号">{{ user.userPhone }}</a-descriptions-item>
          <a-descriptions-item label="性别">{{ user.userSex }}</a-descriptions-item>
          <a-descriptions-item label="生日">{{ user.userBirthday }}</a-descriptions-item>
          <a-descriptions-item label="年龄">{{ user.userAge }} 岁</a-descriptions-item>
        </a-descriptions>

        <a-divider />

        <a-descriptions title="身体指标" bordered :column="2" size="middle">
          <a-descriptions-item label="身高">{{ user.userHeight }}</a-descriptions-item>
          <a-descriptions-item label="体重">{{ user.userWeight }}</a-descriptions-item>
          <a-descriptions-item label="标准体重">{{ user.standardWeigth }}</a-descriptions-item>
          <a-descriptions-item label="基础代谢">{{ user.basalMetabolism }}</a-descriptions-item>
        </a-descriptions>
      </div>
    </div>

    <!-- 编辑弹窗 -->
    <a-modal
        v-model:open="isEditModalOpen"
        title="编辑个人信息"
        @ok="handleEditSave"
        :confirm-loading="isSaving"
        width="500px"
        :maskClosable="false"
        ok-text="保存"
        cancel-text="取消"
    >
      <a-form
          ref="formRef"
          :model="editForm"
          :rules="rules"
          layout="vertical"
          class="pt-2"
      >
        <a-form-item label="用户名" name="userName">
          <a-input v-model:value="editForm.userName" />
        </a-form-item>
        <a-form-item label="手机号" name="userPhone">
          <a-input v-model:value="editForm.userPhone" />
        </a-form-item>
        <a-form-item label="性别" name="userSex">
          <a-select v-model:value="editForm.userSex" allow-clear>
            <a-select-option value="男">男</a-select-option>
            <a-select-option value="女">女</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="生日" name="userBirthday">
          <a-date-picker
              v-model:value="editForm.userBirthday"
              value-format="YYYY-MM-DD"
              class="w-full"
          />
        </a-form-item>
        <a-form-item label="年龄" name="userAge">
          <a-input-number
              v-model:value="editForm.userAge"
              :min="0"
              :max="120"
              class="w-full"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-card>
</template>

<script setup>
import { reactive, getCurrentInstance, ref, watch } from 'vue';
import { useCounterStore } from '../store/index.js';
import { message } from 'ant-design-vue';

const { proxy } = getCurrentInstance();
const API_URL = import.meta.env.VITE_APP_API_URL_DEV;
const defaultAvatar = 'https://via.placeholder.com/96x96?text=Avatar';
const useStore = useCounterStore();
const user = reactive(useStore.$state);

const isEditModalOpen = ref(false);
const isSaving = ref(false);
const formRef = ref(null);

const editForm = reactive({
  userName: '',
  userPhone: '',
  userSex: '',
  userBirthday: '',
  userAge: 0,
});

watch(isEditModalOpen, (open) => {
  if (open) {
    Object.assign(editForm, {
      userName: user.userName,
      userPhone: user.userPhone,
      userSex: user.userSex,
      userBirthday: user.userBirthday,
      userAge: user.userAge,
    });
  }
});

const rules = {
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  userPhone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' },
  ],
  userSex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  userBirthday: [{ required: true, message: '请选择生日', trigger: 'change' }],
  userAge: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
};

const handleEditSave = () => {
  formRef.value
      .validate()
      .then(() => {
        isSaving.value = true;
        setTimeout(async () => {
          editForm['userId'] = user.userId;
          const res = await proxy.request.post('/user/edit', {
            ...editForm,
          });
          if (res.code === '200') {
            Object.assign(user, editForm);
            isEditModalOpen.value = false;
            isSaving.value = false;
            message.success('信息已保存');
          }
        }, 1000);
      })
      .catch(() => {
        message.warning('请检查表单输入');
      });
};

const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/');
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isImage) {
    message.error('只能上传图片文件');
  }
  if (!isLt2M) {
    message.error('图片大小不能超过 2MB');
  }
  return isImage && isLt2M;
};

const handleAvatarChange = async (info) => {
  if (info.file.status === 'done') {
    const responsePath = info.file.response;
    if (responsePath) {
      user.avatar = responsePath;
      useStore.$patch({ avatar: responsePath });
      try {
        await proxy.request.post('/user/edit', {
          ...user,
          avatar: responsePath,
        });
        message.success('头像上传成功并已保存到数据库');
      } catch (error) {
        message.error('头像已上传，但保存数据库失败');
        console.error('保存头像失败：', error);
      }
    } else {
      message.warning('上传返回的路径为空');
    }
  } else if (info.file.status === 'error') {
    message.error('上传失败');
  }
};
</script>

<style scoped>
:deep(.ant-descriptions-bordered .ant-descriptions-item-label) {
  background-color: #f6f6f6;
  font-weight: 500;
}
:deep(.ant-descriptions-bordered) {
  border-radius: 12px;
  overflow: hidden;
}
</style>
