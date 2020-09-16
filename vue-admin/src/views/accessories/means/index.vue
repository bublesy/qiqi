<template>
  <div class="app-container">
    <avue-crud
      v-model="form"
      v-bind="bindVal"
      :page.sync="page"
      :close-on-click-modal="false"
      v-on="onEvent"
    />
  </div>
</template>

<script>
import { removeRole } from '@/api/accessories/means'
import { export2Excel } from '@/utils/common'
export default window.$crudCommon({
  inject: ['reloadTag'],
  data() {
    return {
      a: []
    }
  },
  created() {
  },
  methods: {
    toExcel() {
      var list = this.tableData
      const th = ['编码', '名称', '限定最大纸长']
      const filterVal = ['code', 'name', 'limitPaperLength']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '箱类设定')
    },
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
          name: '',
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
        this.$message.error('新增失败')
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
    delAfter() {

    }
  }
}, {
  name: 'accessories/means', // 模块名字
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
