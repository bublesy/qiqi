<template>
  <div class="app-container">
    <avue-crud
      v-model="form"
      v-bind="bindVal"
      :page.sync="page"
      v-on="onEvent"
    >
      <!-- 自定义菜单 -->
      <!-- <template slot="TableForm">
        <avue-crud ref="crud" :option="mOption" :data="mData" style="width:1000px" @row-update="addUpdate" />
      </template> -->
      <template slot="menu" slot-scope="scope">
        <el-popconfirm title="确认删除这条数据吗？" style="margin-left:10px;" @onConfirm="handleDel( scope.row, scope.index )">
          <el-button slot="reference" type="text" icon="el-icon-delete" size="small">&nbsp;删除</el-button>
        </el-popconfirm>
      </template>
    </avue-crud>
  </div>
</template>

<script>
// accessories  means
import { removeRole } from '@/api/accessories/picking'
export default window.$crudCommon({
  inject: ['reloadTag'],
  name: 'Picking',
  data() {
    return {
      a: [],
      mData: []
      // mOption: {
      //   columnBtn: false,
      //   refreshBtn: false,
      //   addBtn: false,
      //   editBtn: false,
      //   addRowBtn: true,
      //   cellBtn: true,
      //   searchShow: false,
      //   menuWidth: 380,
      //   column: [{
      //     label: '品名规格',
      //     prop: 'name',
      //     cell: true,
      //     type: 'select',
      //     dicData: [{
      //       label: '选项1',
      //       value: 0
      //     }, {
      //       label: '选项2',
      //       value: 1
      //     }],
      //     rules: [{
      //       required: true,
      //       message: '请输入规格名字',
      //       trigger: 'blur'
      //     }]
      //   },
      //   {
      //     label: '单位',
      //     prop: 'unit',
      //     cell: true,
      //     type: 'select',
      //     dicData: [{
      //       label: '选项1',
      //       value: 0
      //     }, {
      //       label: '选项2',
      //       value: 1
      //     }],
      //     rules: [{
      //       required: true,
      //       message: '请输入单位名字',
      //       trigger: 'blur'
      //     }]
      //   },
      //   {
      //     label: '数量',
      //     prop: 'number',
      //     cell: true,
      //     rules: [{
      //       required: true,
      //       message: '请输入原始编号',
      //       trigger: 'blur',
      //       pattern: /^\d{1,}$/
      //     }]
      //   }
      //   ]
      // }
    }
  },
  created() {
  },
  methods: {
    // 删除方法
    handleDel(row, index) {
      removeRole(row.id).then(response => {
        if (response) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        } else {
          this.$message.error('删除失败')
        }
      }).finally(() => {
        this.reloadTag()
      })
    },
    onLoadTable({ page, value, data }, callback) {
      // 首次加载去查询对应的值
      if (value) {
        this.$message.success('首次查询' + value)
        callback({
          id: '0',
          name: '张三',
          sex: '男'
        })
        return
      }
      if (data) {
        this.$message.success('搜索查询参数' + JSON.stringify(data))
      }
      if (page) {
        this.$message.success('分页参数' + JSON.stringify(page))
      }
      // 分页查询信息
      callback({
        total: 2,
        data: [{
          id: '0',
          name: '张三',
          sex: '男'
        }, {
          id: '1',
          name: '李四',
          sex: '女'
        }]
      })
    },
    setVal() {
      this.a = [{
        label: '选项1',
        value: 0
      }, {
        label: '选项2',
        value: 1
      }]
    },

    beforeOpen(done, type) {
      this.setVal()
      done()
    },

    // 列表前操作方法
    listBefore() {
    },

    // 列表后操作方法
    listAfter() {
    },

    // 新增前操作方法
    addBefore() {
      // this.form.createUser = 'small'
    },
    // 新增后操作方法
    addAfter(val) {
      if (val) {
        this.$message.success('新增成功')
      } else {
        this.$message.error('新增成功')
      }
    },

    // 修改前操作方法
    updateBefore() {
      // this.form.updateUser = 'small'
    },

    // 修改后操作方法
    updateAfter() {},

    // 删除前操作方法
    delBefore() {},

    // 删除后操作方法
    delAfter(val) {
      if (val) {
        this.$message.success('修改成功')
      } else {
        this.$message.error('修改失败')
      }
    }
  }
}, {
  name: 'accessories/picking', // 模块名字
  list: 'getRoles', // 列表接口名字
  update: 'editRole', // 更新接口名字
  add: 'addRole', // 新增接口名字
  del: 'removeRole', // 删除接口名字
  rowKey: 'id', // 主键
  pageNumber: 'pageNumber', // 页码
  pageSize: 'pageSize', // 页数
  total: 'total', // 总页数
  data: 'list'// 列表属性
})
</script>
<style lang="scss" scoped>
</style>
