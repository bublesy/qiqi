export default (safe) => {
  // safe => vue的this对象
  return {
    columnBtn: false,
    refreshBtn: false,
    saveBtn: true,
    updateBtn: true,
    cancelBtn: true,
    addBtn: true,
    delBtn: false,
    editBtn: true,
    viewBtn: false,
    printBtn: false,
    excelBtn: true,
    searchShow: false,
    page: true,
    border: true,
    align: 'center',
    menuAlign: 'center',
    column: [
      {
        label: '日期',
        prop: 'name',
        type: 'datetime',
        format: 'yyyy 年 MM 月 dd 日',
        rules: [{
          required: true,
          message: '请输入日期',
          trigger: 'blur'
        }]
      },
      {
        label: '供方',
        prop: 'code',
        search: true,
        rules: [{
          required: true,
          message: '请输入供方名称',
          trigger: 'blur'
        }]
      },
      // {
      //   label: '开单员',
      //   prop: 'c',
      //   addDisplay: false,
      //   editDisplay: false,
      //   rules: [{
      //     required: true,
      //     message: '请输入开单员 ',
      //     trigger: 'blur'
      //   }]
      // },
      {
        label: '原始单号',
        prop: 'b',
        rules: [{
          required: true,
          pattern: '^[0-9]*$ ',
          message: '请输入存数字',
          trigger: 'blur'
        }] },
      {
        label: '备注',
        prop: 'cd',
        rules: [{
          required: true,
          message: '请输入备注',
          trigger: 'blur'
        }]
      }, // {
      //   label: '品名规格',
      //   prop: 'e',
      //   hide: true,
      //   rules: [{
      //     required: true,
      //     message: '请输入品名规格',
      //     trigger: 'blur'
      //   }]
      // },
      // {
      //   label: '单位',
      //   prop: 'f',
      //   addDisabled: true,
      //   editDisabled: true,
      //   hide: true,
      //   rules: [{
      //     required: true,
      //     message: '请输入单位',
      //     trigger: 'blur'
      //   }]
      // },
      // {
      //   label: '数量',
      //   prop: 'g',
      //   hide: true,
      //   rules: [{
      //     required: true,
      //     message: '请输入数量',
      //     trigger: 'blur'
      //   }]
      // },
      // {
      //   label: '单价',
      //   prop: 'h',
      //   hide: true,
      //   rules: [{
      //     required: true,
      //     message: '请输入单价',
      //     trigger: 'blur'
      //   }]
      // },
      // {
      //   label: '金额',
      //   prop: 'k',
      //   hide: true,
      //   rules: [{
      //     required: true,
      //     message: '请输入金额',
      //     trigger: 'blur'
      //   }]
      // }
      // {
      //   label: '开单员',
      //   prop: 'code',
      //   search: true,
      //   hide: true,
      //   addDisplay: false,
      //   editDisplay: false,
      //   rules: [{
      //     required: true,
      //     message: '请输入开单员',
      //     trigger: 'blur'
      //   }]
      // },

      {
        label: '',
        labelWidth: '0px',
        prop: 'myTable',
        hide: true,
        formslot: true,
        formWidth: '500px'
      }
      // 树
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'tree',
      //   dicData: [{
      //     label: '选项1',
      //     value: 0,
      //     children: [{
      //       label: '选项3',
      //       value: 2
      //     }, {
      //       label: '选项4',
      //       value: 3
      //     }]
      //   }, {
      //     label: '选项2',
      //     value: 1
      //   }]
      // }
      // 颜色
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'color'
      // }
      // 数字
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'number',
      //   precision: '2',
      //   controlsPosition: true
      // }
      // 图标
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'icon',
      //   iconList: [{
      //     label: '基本图标',
      //     list: ['el-icon-info', 'el-icon-error', 'el-icon-error', 'el-icon-success', 'el-icon-warning', 'el-icon-question']
      //   }, {
      //     label: '方向图标',
      //     list: ['el-icon-info', 'el-icon-back', 'el-icon-arrow-left', 'el-icon-arrow-down', 'el-icon-arrow-right', 'el-icon-arrow-up']
      //   }, {
      //     label: '符号图标',
      //     list: ['el-icon-plus', 'el-icon-minus', 'el-icon-close', 'el-icon-check']
      //   }]
      // }
      // 地图
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'map'
      // }
      // 数组
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'array'
      // }
      // 单选
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'radio',
      //   dicData: [{
      //     label: '选项1',
      //     value: 0
      //   }, {
      //     label: '选项2',
      //     value: 1
      //   }]
      // }
      // 多选
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'checkbox',
      //   dicData: [{
      //     label: '选项1',
      //     value: 0
      //   }, {
      //     label: '选项2',
      //     value: 1
      //   }]
      // }
      // 开关
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'switch',
      //   dicData: [{
      //     label: '关',
      //     value: 0
      //   }, {
      //     label: '开',
      //     value: 1
      //   }]
      // }
      // 日期时间
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'datetime'
      // }
      // 时间
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'time'
      // }
      // 评分
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'rate'
      // }
      // 滑动框
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'slider'
      // }
      // 级联选择器
      // {
      //   label: '描述',
      //   prop: 'remark',
      //   type: 'cascader',
      //   multiple: true,
      //   dicData: [{
      //     value: 'zhinan',
      //     label: '指南',
      //     children: [{
      //       value: 'shejiyuanze',
      //       label: '设计原则',
      //       children: [{
      //         value: 'yizhi',
      //         label: '一致'
      //       }, {
      //         value: 'fankui',
      //         label: '反馈'
      //       }]
      //     }]
      //   }]
      // }
    ]
  }
}
