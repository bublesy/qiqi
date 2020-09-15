<template>
  <div class="app-container">
    <avue-crud
      v-model="form"
      v-bind="bindVal"
      :page.sync="page"
      v-on="onEvent"
    >
      <!-- 自定义菜单 -->
      <template slot="myTableForm">
        <avue-crud ref="crud" :option="myOption" :data="myData" style="width:1200px" @row-update="addUpdate" />
      </template>
      <template slot="menu" slot-scope="scope">
        <el-popconfirm title="确认删除这条数据吗？" style="margin-left:10px;" @onConfirm="handleDel( scope.row, scope.index )">
          <el-button slot="reference" type="text" icon="el-icon-delete" size="small">&nbsp;删除</el-button>
        </el-popconfirm>
      </template>
    </avue-crud>
  </div>
</template>

<script>
import { removeRole } from '@/api/accessories/warehousing'
export default window.$crudCommon({
  name: 'Warehousing',
  inject: ['reloadTag'],
  data() {
    return {
      a: [],
      myData: [],
      myOption: {
        addBtn: false,
        refreshBtn: false,
        columnBtn: false,
        editBtn: false,
        addRowBtn: true,
        cellBtn: true,
        searchShow: false,
        menuWidth: 380,
        column: [{
          label: '品名规格',
          prop: 'name',
          cell: true,
          width: 165,
          type: 'select',
          dicData: [{
            label: '收据',
            value: 0
          }, {
            label: '选项2',
            value: 1
          }],
          rules: [{
            required: true,
            message: '请输入规格名字',
            trigger: 'blur'
          }]
        },
        {
          label: '单位',
          prop: 'unit',
          disabled: true,
          width: 165,
          cell: true,
          type: 'select',
          dicData: [{
            label: '选项1',
            value: 0
          }, {
            label: '选项2',
            value: 1
          }],
          rules: [{
            required: true,
            message: '请选择单位',
            trigger: 'blur'
          }]
        },
        {
          label: '数量',
          prop: 'number',
          width: 165,
          cell: true,
          rules: [{
            required: true,
            message: '请输入数量',
            trigger: 'blur',
            pattern: '^[0-9]*$ '
          }]
        },
        {
          label: '单价',
          prop: 'univalent;',
          width: 140,
          cell: true
        },
        {
          label: '金额',
          width: 160,
          prop: 'money',
          cell: true
        }]
      }
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
    handleRowDBLClick(row, event) {
      this.$refs.crud.rowEdit(row, row.$index)
    },
    addUpdate(form, index, done, loading) {
      done()
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
          name: '',
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
    updateAfter(val) {
      if (val) {
        this.$message.success('修改成功')
      } else {
        this.$message.error('修改失败')
      }
    },

    // 删除前操作方法
    delBefore() {},

    // 删除后操作方法
    delAfter() {}

  }
}, {
  name: 'accessories/warehousing', // 模块名字
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
