import {h, ref} from "vue";
import {Filter, Search} from "@icon-park/vue-next";

export const columns = ref({
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

})