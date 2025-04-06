<template>
  <div class="container">
    <a-table class="table" :data-source="List" :loading="List.length === 0" :columns="columns[props.columnsName]"
             :scroll="{ y: 622 }"
             :pagination="{
                defaultPageSize:100,
                simple:true,
                position:['bottomCenter']

             }"
             size="small" :bordered="true">
      <template #title>
        <div class="title">
          <div class="title-text">{{ tableTitle }}</div>
          <div class="uploadBtn">
            <a-button v-if="props.columnsName === 'file'" @click="uploadFileOpen = true">上传图片</a-button>
          </div>
        </div>

      </template>
      <template
          #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
      >
        <div style="padding: 8px">
          <a-input
              ref="searchInput"
              :placeholder="`Search ${column.dataIndex}`"
              :value="selectedKeys[0]"
              style="width: 188px; margin-bottom: 8px; display: block"
              @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
              @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
          />
          <a-button
              type="primary"
              size="small"
              style="width: 90px; margin-right: 8px"
              @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
          >
            <template #icon>
              <Search style="vertical-align: sub"/>
            </template>
            搜索
          </a-button>
          <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
            Reset
          </a-button>
        </div>
      </template>
      <template #bodyCell="{ text, record,index,column }">
      <span v-if="state.searchText && state.searchedColumn === column.dataIndex">
        <template
            v-for="(fragment, i) in text
            .toString()
            .split(new RegExp(`(?<=${state.searchText})|(?=${state.searchText})`, 'i'))"
        >
          <mark
              v-if="fragment.toLowerCase() === state.searchText.toLowerCase()"
              :key="i"
              class="highlight"
          >
            {{ fragment }}
          </mark>
          <template v-else>{{ fragment }}</template>
        </template>
      </span>
        <span v-else-if="column.dataIndex === 'healthLevel'" class="healthLevel">
           <span v-if="Number(text) === 1"><relieved-face theme="filled" size="15" fill="#46db63"
                                                          style="vertical-align: middle;margin-right: 7px"/>健康</span>
           <span v-else-if="Number(text) === 2"><neutral-face theme="filled" size="15" fill="#f5a623"
                                                              style="vertical-align: middle;margin-right: 7px"/>良好</span>
           <span v-else-if="Number(text) === 3"><emotion-unhappy theme="filled" size="15" fill="#d0021b"
                                                                 style="vertical-align: middle;margin-right: 7px"/>不健康</span>
        </span>
        <span v-else-if="column.dataIndex === 'name'" class="healthLevel">
            <a-typography-paragraph copyable>
    <a @click="() => {setVisible(true,API_URL+'/file/download/'+text)}">{{ text }}</a>
  </a-typography-paragraph>
        </span>
        <span v-else-if="column.dataIndex === 'directory'" class="healthLevel">
    {{ text === true ? '是' : '否' }}
        </span>
        <span v-else-if="column.dataIndex === 'size'" class="healthLevel">
    {{ bytesToMB(text) }}
        </span>
        <span v-else-if="column.dataIndex === 'className'" class="className">
            <rice v-if="text === '主食类'" theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                  style="vertical-align: middle;"/>
            <turkey v-else-if="text === '肉蛋类'" theme="multi-color" size="15"
                    :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle;"/>
            <vicia-faba v-else-if="text === '大豆及制品'" theme="multi-color" size="15"
                        :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <vegetables v-else-if="text === '蔬菜菌藻类'" theme="multi-color" size="15"
                        :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle;"/>
            <apple-one v-else-if="text === '水果类'" theme="multi-color" size="15"
                       :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle;"/>
            <milk-one v-else-if="text === '奶类'" theme="multi-color" size="15"
                      :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle;"/>
            <measuring-cup v-else-if="text === '油脂类'" theme="multi-color" size="15"
                           :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <nut v-else-if="text === '坚果类'" theme="multi-color" size="15"
                 :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <garlic v-else-if="text === '调味品'" theme="multi-color" size="15"
                    :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <Cola v-else-if="text === '饮料类'" theme="multi-color" size="15"
                  :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <candy v-else-if="text === '零食及冷饮'" theme="multi-color" size="15"
                   :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
            <Other v-else-if="text === '其他'" theme="multi-color" size="15"
                   :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']" style="vertical-align: middle; "/>
          {{ text }}
          </span>
        <span v-else-if="column.dataIndex === 'edit'" class="editBtn">
            <a-button v-if="props.columnsName !== 'file'" size="small" :icon="h(Editor)" class="btn-text"
                      @click="controlsBtn('edit',record,index)">修改</a-button>
          <a-popconfirm title="确认删除食物吗？" placement="topLeft" @confirm="controlsBtn('delete',record,index)">
            <template #icon><alarm theme="outline" size="15" fill="red" style="vertical-align: text-top"/></template>
            <a-button type="primary" danger size="small" :icon="h(DeleteFive)" class="btn-text">删除</a-button>
          </a-popconfirm>
          </span>
      </template>
      <template #footer>数据：{{ List.length }}条</template>

    </a-table>
  </div>

  <!-- 修改用户信息 -->
  <a-modal v-model:open="editUserOpen" title="修改用户信息" :footer="null">
    <div>
      <a-form
          ref="editUserRef"
          :model="editUserForm"
          name="basic"
          :label-col="{ span: 4 }"
          :wrapper-col="{ span: 20 }"
          :rules="EditUserRules"
          autocomplete="off"
          @finish="handleEditUserOk"
      >
        <a-row>
          <a-col :span="24">
            <a-form-item required label="用户名" name="userName">
              <a-input allowClear v-model:value="editUserForm.userName"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item ref="userPhoneRef" label="手机号" name="userPhone">
              <a-input allowClear type="phone" :maxlength="11" v-model:value="editUserForm.userPhone"/>
            </a-form-item>
          </a-col>
          <a-col :span="10" :offset="2">
            <a-form-item label="性别" name="userSex">
              <a-radio-group style="margin-left: 10px" v-model:value="editUserForm.userSex" button-style="solid">
                <a-radio-button value="男">男</a-radio-button>
                <a-radio-button value="女">女</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="10" :offset="2">
            <a-form-item label="生日" name="userBirthday">
              <a-date-picker placeholder="选择日期" style="margin-left: 10px"
                             v-model:value="editUserForm.userBirthday"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="头像地址" name="avatar">
              <a-input allowClear v-model:value="editUserForm.avatar"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item :wrapper-col="{ span: 14, offset: 8 }">
          <a-button type="primary" html-type="submit" class="btn-text" :loading="editUserLoading">
            <template #icon>
              <save style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#fff"/>
            </template>
            保存
          </a-button>
          <a-button style="margin-left: 10px" @click="resetUserEditForm">
            <template #icon>
              <close-one style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#000"/>
            </template>
            取消
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
  <!-- 修改用户信息 end-->

  <!-- 修改食物信息 -->
  <a-modal v-model:open="editFoodOpen" title="修改用户信息" :footer="null">
    <div>
      <a-form
          ref="editFoodRef"
          :model="editFoodForm"
          name="basic"
          :label-col="{ span: 4 }"
          :wrapper-col="{ span: 20 }"
          :rules="EditUserRules"
          autocomplete="off"
          @finish="handleEditFoodOk"
      >
        <a-row>
          <a-col :span="24">
            <a-form-item required label="食物名称" name="name">
              <a-input allowClear v-model:value="editFoodForm.name"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="食物分类" name="classId">
              <a-select v-model:value="editFoodForm.classId" placeholder="请选择食物分类">
                <a-select-option value="1">
                  <rice theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                        style="vertical-align: middle;margin-right: 7px"/>
                  主食类
                </a-select-option>
                <a-select-option value="2">
                  <turkey theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                          style="vertical-align: middle;margin-right: 7px"/>
                  肉蛋类
                </a-select-option>
                <a-select-option value="3">
                  <vicia-faba theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                              style="vertical-align: middle;margin-right: 7px"/>
                  大豆及制品
                </a-select-option>
                <a-select-option value="4">
                  <vegetables theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                              style="vertical-align: middle;margin-right: 7px"/>
                  蔬菜菌藻类
                </a-select-option>
                <a-select-option value="5">
                  <apple-one theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                             style="vertical-align: middle;margin-right: 7px"/>
                  水果类
                </a-select-option>
                <a-select-option value="6">
                  <milk-one theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                            style="vertical-align: middle;margin-right: 7px"/>
                  奶类
                </a-select-option>
                <a-select-option value="7">
                  <measuring-cup theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                                 style="vertical-align: middle;margin-right: 7px"/>
                  油脂类
                </a-select-option>
                <a-select-option value="8">
                  <nut theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                       style="vertical-align: middle;margin-right: 7px"/>
                  坚果类
                </a-select-option>
                <a-select-option value="9">
                  <garlic theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                          style="vertical-align: middle;margin-right: 7px"/>
                  调味品
                </a-select-option>
                <a-select-option value="10">
                  <cola theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                        style="vertical-align: middle;margin-right: 7px"/>
                  饮料类
                </a-select-option>
                <a-select-option value="11">
                  <candy theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                         style="vertical-align: middle;margin-right: 7px"/>
                  零食及冷饮
                </a-select-option>
                <a-select-option value="12">
                  <other theme="multi-color" size="15" :fill="['#333' ,'#2F88FF' ,'#FFF' ,'#43CCF8']"
                         style="vertical-align: middle;margin-right: 7px"/>
                  其他
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="健康等级" name="healthLevel">
              <a-select v-model:value="editFoodForm.healthLevel" placeholder="请选择健康等级">
                <a-select-option value="1">
                  <relieved-face theme="filled" size="15" fill="#46db63"
                                 style="vertical-align: middle;margin-right: 7px"/>
                  健康
                </a-select-option>
                <a-select-option value="2">
                  <neutral-face theme="filled" size="15" fill="#f5a623"
                                style="vertical-align: middle;margin-right: 7px"/>
                  良好
                </a-select-option>
                <a-select-option value="3">
                  <emotion-unhappy theme="filled" size="15" fill="#d0021b"
                                   style="vertical-align: middle;margin-right: 7px"/>
                  不健康
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="卡路里" name="calory">
              <a-input allowClear v-model:value="editFoodForm.calory"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item :wrapper-col="{ span: 14, offset: 8 }">
          <a-button type="primary" html-type="submit" class="btn-text" :loading="editFoodLoading">
            <template #icon>
              <save style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#fff"/>
            </template>
            保存
          </a-button>
          <a-button style="margin-left: 10px" @click="resetFoodEditForm">
            <template #icon>
              <close-one style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#000"/>
            </template>
            取消
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
  <!-- 修改食物信息 end-->

  <!-- 文件管理  -->
  <div>
    <a-image
        :width="200"
        :style="{ display: 'none' }"
        :preview="{
        visible,
        onVisibleChange: setVisible,
      }"
        :src="imageUrl"
    />
  </div>
  <a-modal v-model:open="uploadFileOpen" title="上传图片" :footer="null" destroyOnClose>
    <div>
      <a-upload-dragger
          name="file"
          :maxCount="1"
          :headers="{'token':token}"
          :action="API_URL+'/file/adminUpload'"
          list-type="picture"
          @change="handleChange"
      >
        <p class="ant-upload-drag-icon">
          <inbox-outlined></inbox-outlined>
        </p>
        <p class="upload-text">单击或拖动文件到此区域进行上传</p>
      </a-upload-dragger>

    </div>
  </a-modal>
  <!-- 文件管理 end-->

  <!-- 题库 -->
  <a-modal v-model:open="editKnowledgeOpen" title="修改用户信息" :footer="null">
    <div>
      <a-form
          ref="editKnowledgeRef"
          :model="editKnowledgeForm"
          name="basic"
          :label-col="{ span: 4 }"
          :wrapper-col="{ span: 20 }"
          :rules="EditUserRules"
          autocomplete="off"
          @finish="handleKnowledgeFoodOk"
      >
        <a-row>
          <a-col :span="24">
            <a-form-item required label="食物名称" name="question">
              <a-textarea auto-size v-model:value="editKnowledgeForm.question"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item required label="食物名称" name="correctAnswer">
              <a-textarea auto-size v-model:value="editKnowledgeForm.correctAnswer"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item required label="食物名称" name="wrongAnswer">
              <a-textarea auto-size v-model:value="editKnowledgeForm.wrongAnswer"/>
            </a-form-item>
          </a-col>

        </a-row>
        <a-form-item :wrapper-col="{ span: 14, offset: 8 }">
          <a-button type="primary" html-type="submit" class="btn-text" :loading="editKnowledgeLoading">
            <template #icon>
              <save style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#fff"/>
            </template>
            保存
          </a-button>
          <a-button style="margin-left: 10px" @click="resetKnowledgeEditForm">
            <template #icon>
              <close-one style="vertical-align: sub;margin-right: 2px" theme="outline" size="15" fill="#000"/>
            </template>
            取消
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
  <!-- 题库 end-->
</template>

<script setup>
import dayjs from 'dayjs'
import {useCounterStore} from '../store/index.js'
import {
  Filter,
  Search,
  Editor,
  DeleteFive,
  Alarm,
  Save,
  CloseOne,
  RelievedFace,
  NeutralFace,
  EmotionUnhappy,
  Rice,
  Turkey,
  ViciaFaba,
  Vegetables,
  AppleOne,
  MilkOne,
  MeasuringCup,
  Other,
  Candy,
  Nut,
  Garlic,
  Cola
} from "@icon-park/vue-next";
import {InboxOutlined} from '@ant-design/icons-vue';
import {ref, reactive, h, getCurrentInstance, onMounted, nextTick} from "vue";
import {message} from "ant-design-vue";

const store = useCounterStore()
const token = store.$state.token
const {proxy} = getCurrentInstance()
const API_URL = import.meta.env.VITE_APP_API_URL_DEV
const props = defineProps({
  columnsName: {
    type: String,
    required: true
  }
})
const tableTitle = ref('')
const state = reactive({
  searchText: '',
  searchedColumn: '',
});
const searchInput = ref();
const handleSearch = (selectedKeys, confirm, dataIndex) => {
  confirm();
  state.searchText = selectedKeys[0];
  state.searchedColumn = dataIndex;
};
const handleReset = clearFilters => {
  clearFilters({confirm: true});
  state.searchText = '';
};
const columns = ref({
  user: [
    {
      title: '用户名',
      dataIndex: 'userName',
      key: 'userName',
      width: 150,
      customFilterDropdown: true,
      filterIcon: () => h(Search),
      onFilter: (value, record) => record.userName?.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownOpenChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    },

    {
      title: '性别',
      dataIndex: 'userSex',
      key: 'userSex',
      width: 100,
      filterIcon: () => h(Filter),
      filters: [
        {
          text: '男',
          value: '男',
        },
        {
          text: '女',
          value: '女',
        },
      ],
      onFilter: (value, record) => record.userSex === value,
    },
    {
      title: '手机号',
      dataIndex: 'userPhone',
      key: 'userPhone',
      width: 150,
      customFilterDropdown: true,
      filterIcon: () => h(Search),
      onFilter: (value, record) =>
          record.userPhone?.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownOpenChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '生日',
      dataIndex: 'userBirthday',
      key: 'userBirthday',
      width: 150,
    },
    {
      title: '头像地址',
      dataIndex: 'avatar',
      key: 'avatar',
      width: 300
    },
    {
      title: '操作',
      dataIndex: 'edit',
      key: 'edit',
      align: 'center',
      width: 100,
    },

  ],
  food: [
    {
      title: '食物名称',
      dataIndex: 'name',
      key: 'name',
      width: 250,
      customFilterDropdown: true,
      filterIcon: () => h(Search),
      onFilter: (value, record) => record.name?.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownOpenChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '食物分类',
      dataIndex: 'className',
      key: 'className',
      width: 150,
      filterIcon: () => h(Filter),
      filters: [
        {
          text: '主食类',
          value: '主食类',
        },
        {
          text: '肉蛋类',
          value: '肉蛋类',
        }, {
          text: '大豆及制品',
          value: '大豆及制品',
        }, {
          text: '蔬菜菌藻类',
          value: '蔬菜菌藻类',
        }, {
          text: '水果类',
          value: '水果类',
        }, {
          text: '奶类',
          value: '奶类',
        }, {
          text: '油脂类',
          value: '油脂类',
        }, {
          text: '坚果类',
          value: '坚果类',
        }, {
          text: '调味品',
          value: '调味品',
        }, {
          text: '饮料类',
          value: '饮料类',
        }, {
          text: '零食及冷饮',
          value: '零食及冷饮',
        }, {
          text: '其他',
          value: '其他',
        },
      ],
      onFilter: (value, record) => record.className === value,
    },
    {
      title: '健康等级',
      dataIndex: 'healthLevel',
      key: 'healthLevel',
      width: 150,
      filterIcon: () => h(Filter),
      filters: [
        {
          text: '健康',
          value: 1,
        },
        {
          text: '良好',
          value: 2,
        }, {
          text: '不健康',
          value: 3,
        },
      ],
      onFilter: (value, record) => record.healthLevel === value,
    },
    {
      title: '卡路里',
      dataIndex: 'calory',
      key: 'calory',
      width: 150,
    },
    {
      title: '操作',
      dataIndex: 'edit',
      key: 'edit',
      align: 'center',
      width: 100,
    },
  ],
  file: [
    {
      title: '文件名',
      dataIndex: 'name',
      key: 'name',
      width: 200,
    },
    {
      title: '是否是文件夹',
      dataIndex: 'directory',
      key: 'directory',
      width: 300
    },
    {
      title: '大小',
      dataIndex: 'size',
      key: 'size',
      width: 150,
    },
    {
      title: '操作',
      dataIndex: 'edit',
      key: 'edit',
      align: 'center',
      width: 100,
    },
  ],
  knowledge: [
    {
      title: '问题',
      dataIndex: 'question',
      key: 'question',
      width: 200,
    },
    {
      title: '错误答案',
      dataIndex: 'wrongAnswer',
      key: 'wrongAnswer',
      width: 200,
    },
    {
      title: '正确答案',
      dataIndex: 'correctAnswer',
      key: 'correctAnswer',
      width: 200,
    },
    {
      title: '被选择的次数',
      dataIndex: 'selectNumber',
      key: 'selectNumber',
      width: 70,
    },
    {
      title: '操作',
      dataIndex: 'edit',
      key: 'edit',
      align: 'center',
      width: 100,
    }]
})
const controlsBtn = (status, record, index) => {
  if (status === 'edit') {
    switch (props.columnsName) {
      case "user" :
        showEditUserModal(record, index);
        break;
      case "food":
        showEditFoodModal(record, index);
        break;
      case "knowledge":
        showEditKnowledgeModal(record, index);
        break;

    }
  } else if (status === 'delete') {
    switch (props.columnsName) {
      case "user" :
        deleteUser(record.userId, index);
        break;
      case "food":
        deleteFood(record.foodId, index);
        break;
      case "file":
        deleteFile(record.name, index);
        break;
      case "knowledge":
        deleteKnowledge(record.id, index);
        break;
    }
  }

}
const List = ref([])
// ----------------------  用户信息  ------------------------------------
const getUserList = async () => {
  const res = await proxy.request.post('/user/list')
  if (res.code === '200') {
    List.value = res.data
  }

}
// 删除用户
const deleteUser = async (id, index) => {
  const res = await proxy.request.delete(`/user/delete/${id}`)
  if (res.code === '200') {
    List.value.splice(index, 1);
    message.success('删除成功！')
  }
}
// 修改用户
const editUserRef = ref(null)
const editUserIndex = ref(null)
const editUserOpen = ref(false)
const editUserForm = ref({
  userName: '',
  userPhone: '',
  userSex: '',
  userBirthday: '',
  avatar: '',
  userId: '',
})
const showEditUserModal = (record, index) => {
  editUserForm.value.userName = record.userName
  editUserForm.value.userPhone = record.userPhone
  editUserForm.value.userSex = record.userSex
  editUserForm.value.userBirthday = record.userBirthday !== null ? dayjs(record.userBirthday) : ''

  editUserForm.value.avatar = record.avatar
  editUserForm.value.userId = record.userId
  editUserOpen.value = true
  editUserIndex.value = index
}
// 手机号验证规则
const validatePhone = async (_, value) => {
  const phoneRegex = /^1[3-9]\d{9}$/; // 适用于中国大陆手机号
  if (!value) {
    return Promise.reject("手机号不能为空");
  }
  if (!phoneRegex.test(value)) {
    return Promise.reject("请输入有效的手机号");
  }
  return Promise.resolve();
};
const userPhoneRef = ref(null);
const EditUserRules = {
  userName: [
    {required: true, message: '请输入用户名', trigger: 'change'},
    {max: 8, message: '用户名最多八位', trigger: 'change'},
  ],
  userPhone: [
    {validator: validatePhone, trigger: "blur"}
  ],

}
const resetUserEditForm = () => {
  editUserRef.value.resetFields();
  editUserOpen.value = false
}
const editUserLoading = ref(false)
const handleEditUserOk = async (values) => {
  editUserLoading.value = true
  const res = await proxy.request({
    url: '/user/edit',
    method: 'post',
    data: editUserForm.value
  })
  if (res.code === '200') {
    setTimeout(async () => {
      message.success('保存成功!')

      List.value[editUserIndex.value] = {...editUserForm.value};
      List.value[editUserIndex.value].userBirthday = dayjs(editUserForm.value.userBirthday).format('YYYY-MM-DD')
      editUserLoading.value = false
      editUserOpen.value = false
      await nextTick()
    }, 1000)
  } else {
    setTimeout(() => {
      message.error(res.msg)
      if (res.code === '409') {
        nextTick(() => {
          const inputEl = userPhoneRef.value?.$el?.querySelector("input").parentElement;
          if (inputEl) {
            inputEl.style.border = "1px solid red"; // 变红
            inputEl.classList.add("shake"); // 添加抖动动画
            setTimeout(() => {
              inputEl.classList.remove("shake"); // 移除动画
              inputEl.style.border = "1px solid #d9d9d9";
            }, 500);
          }
        });
      }
      editUserLoading.value = false
    }, 1000)
  }
};
// ----------------------  用户信息  end ------------------------------------

// ----------------------  食物信息  ----------------------------------------
const editFoodOpen = ref(false)
const editFoodLoading = ref(false)
const editFoodRef = ref(null)
const editFoodIndex = ref(0)
const editFoodForm = ref({})
const showEditFoodModal = (record, index) => {
  editFoodForm.value = {...record}
  editFoodForm.value.classId = String(editFoodForm.value.classId)
  editFoodForm.value.healthLevel = String(editFoodForm.value.healthLevel)
  editFoodOpen.value = true
  editFoodIndex.value = index
}
// 删除用户
const deleteFood = async (id, index) => {
  const res = await proxy.request.delete(`/food/delete/${id}`)
  if (res.code === '200') {
    List.value.splice(index, 1);
    message.success('删除成功！')
  }
}
const handleEditFoodOk = async (values) => {
  editFoodLoading.value = true
  const res = await proxy.request({
    url: '/food/edit',
    method: 'post',
    data: editFoodForm.value
  })
  if (res.code === '200') {
    setTimeout(async () => {
      message.success('保存成功!')
      List.value[editFoodIndex.value] = {...editFoodForm.value};
      switch (editFoodForm.value.classId) {
        case '1':
          List.value[editFoodIndex.value]['className'] = '主食类';
          break;
        case '2':
          List.value[editFoodIndex.value]['className'] = '肉蛋类';
          break;
        case '3':
          List.value[editFoodIndex.value]['className'] = '大豆及制品';
          break;
        case '4':
          List.value[editFoodIndex.value]['className'] = '蔬菜菌藻类';
          break;
        case '5':
          List.value[editFoodIndex.value]['className'] = '水果类';
          break;
        case '6':
          List.value[editFoodIndex.value]['className'] = '奶类';
          break;
        case '7':
          List.value[editFoodIndex.value]['className'] = '油脂类';
          break;
        case '8':
          List.value[editFoodIndex.value]['className'] = '坚果类';
          break;
        case '9':
          List.value[editFoodIndex.value]['className'] = '调味品';
          break;
        case '10':
          List.value[editFoodIndex.value]['className'] = '饮料类';
          break;
        case '11':
          List.value[editFoodIndex.value]['className'] = '零食及冷饮';
          break;
        case '12':
          List.value[editFoodIndex.value]['className'] = '其他';
          break;
      }
      editFoodLoading.value = false
      editFoodOpen.value = false
      await nextTick()
    }, 1000)
  } else {
    setTimeout(() => {
      message.error(res.msg)
      editFoodLoading.value = false
    }, 1000)
  }
};
const resetFoodEditForm = () => {
  editFoodRef.value.resetFields();
  editFoodOpen.value = false
}
const getFoodList = async () => {
  const res = await proxy.request.post('food/list')
  if (res.code === '200') {
    List.value = res.data
  }
}
// ----------------------  食物信息 end ----------------------------------------
// ----------------------  文件管理  -------------------------------------------
const visible = ref(false);
const imageUrl = ref('')
const uploadFileOpen = ref(false);
const handleChange = (info) => {
  const status = info.file.status;
  if (status !== 'uploading') {
    console.log(info.file, info.fileList);
  }
  if (status === 'done') {
    message.success(`${info.file.name} 上传成功！`);
    getFileList();
  } else if (status === 'error') {
    message.error(`${info.file.name} 上传失败！.`);
  }
};
const setVisible = (value, url) => {
  if (url === true) {
    visible.value = value;
    return
  }

  visible.value = value;
  imageUrl.value = url
};

// 删除文件
const deleteFile = async (url, index) => {
  const urlUuid = url.split("/").pop();
  const res = await proxy.request.delete('/file/delete/' + urlUuid)
  if (res.code === '200') {
    List.value.splice(index, 1);
    message.success('删除成功！')
  } else {
    message.error(res.msg)
  }
}
const bytesToMB = (bytes, decimal = 2) => {
  if (typeof bytes !== 'number' || isNaN(bytes)) {
    throw new TypeError('bytes 必须是数字类型');
  }
  if (bytes < 0) {
    throw new RangeError('bytes 不能为负数');
  }

  const KB = 1024;
  const MB = 1024 * 1024;

  if (bytes >= MB) {
    return `${(bytes / MB).toFixed(decimal)} MB`;
  } else if (bytes >= KB) {
    return `${(bytes / KB).toFixed(decimal)} KB`;
  } else {
    return `${bytes} B`;
  }
}
const getFileList = async () => {
  const res = await proxy.request.post('file/list')
  if (res.code === '200') {
    List.value = res.data

  }
}
// ----------------------  文件管理 end ----------------------------------------
// ----------------------  题库管理 --------------------------------------------
const editKnowledgeOpen = ref(false)
const editKnowledgeLoading = ref(false)
const editKnowledgeRef = ref(null)
const editKnowledgeIndex = ref(0)
const editKnowledgeForm = ref({})
const showEditKnowledgeModal = (record, index) => {
  editKnowledgeForm.value = {...record}
  editKnowledgeForm.value.classId = String(editKnowledgeForm.value.id)
  editKnowledgeForm.value.question = String(editKnowledgeForm.value.question)
  editKnowledgeForm.value.correctAnswer = String(editKnowledgeForm.value.correctAnswer)
  editKnowledgeForm.value.wrongAnswer = String(editKnowledgeForm.value.wrongAnswer)
  editKnowledgeOpen.value = true
  editKnowledgeIndex.value = index
}
const handleKnowledgeFoodOk = async (values) => {
  editKnowledgeLoading.value = true
  const res = await proxy.request({
    url: '/knowledge/edit',
    method: 'post',
    data: editKnowledgeForm.value
  })
  if (res.code === '200') {
    setTimeout(async () => {
      message.success('保存成功!')
      List.value[editKnowledgeIndex.value] = {...editKnowledgeForm.value};
      editKnowledgeLoading.value = false
      editKnowledgeOpen.value = false
      await nextTick()
    }, 1000)
  } else {
    setTimeout(() => {
      message.error(res.msg)
      editFoodLoading.value = false
    }, 1000)
  }
};
const resetKnowledgeEditForm = () => {
  editKnowledgeRef.value.resetFields();
  editKnowledgeOpen.value = false
}
const deleteKnowledge = async (id, index) => {
  const res = await proxy.request.delete(`/knowledge/delete/${id}`)
  if (res.code === '200') {
    List.value.splice(index, 1);
    message.success('删除成功！')
  }
}
const getKnowledgeList = async () => {
  const res = await proxy.request.post('knowledge/list')
  if (res.code === '200') {
    List.value = res.data

  }
}
// ----------------------  题库管理 end ----------------------------------------

onMounted(() => {
  switch (props.columnsName) {
    case "user" :
      tableTitle.value = '用户管理'
      getUserList();
      break;
    case "food":
      tableTitle.value = '食物管理'
      getFoodList();
      break;
    case "file":
      tableTitle.value = '文件管理'
      getFileList();
      break;
    case "knowledge":
      tableTitle.value = '题库管理'
      getKnowledgeList();
      break;
  }
})
</script>

<style scoped lang="scss">
.container {
  height: 90vh;
  overflow: hidden;
  overflow-y: auto;
  margin: 10px;
  border-radius: 8px;

  .table {
    border-radius: 8px;

    .title {
      .title-text {
        font-size: 1.3em;
        font-weight: bold;
      }

      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }
}

.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0;
}

.editBtn {
  display: flex;
  justify-content: space-evenly;

  .btn-text {
    :nth-child(1) {
      gap: 8px;
      vertical-align: text-bottom;
    }
  }

}

.shake {
  animation: shake 0.3s ease-in-out;
}

@keyframes shake {
  0%, 100% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  50% {
    transform: translateX(5px);
  }
  75% {
    transform: translateX(-5px);
  }
}

.selectComponentClass {
  width: 200px;
}

.upload-list-inline :deep(.ant-upload-list-item) {
  float: left;
  width: 200px;
  margin-right: 8px;
}

.upload-list-inline [class*='-upload-list-rtl'] :deep(.ant-upload-list-item) {
  float: right;
}

.upload-text {
  padding: 10px;
}
</style>
