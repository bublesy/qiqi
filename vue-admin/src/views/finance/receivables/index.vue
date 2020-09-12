<template>
  <div class="app-container">
    <el-select v-model="value" placeholder="请选择月份">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-select v-model="value" placeholder="请选择客户">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-button type="primary">确定</el-button>
    <el-button type="primary">打印</el-button>
    <el-button type="primary">导出</el-button>
    <el-pagination
      style="float:right"
      small
      layout="prev, pager, next"
      :total="50"
    />
    <div class="biaoge">
      <h2 style="text-align:center;padding-top:10px">客户应收款总表</h2>
      <p style="text-align:center;  line-height: 2px;">打印日期:2020-09-10</p>
      <table>
        <tr>
          <th>客户</th>
          <th>前期</th>
          <th>2010-04</th>
          <th>2012-05</th>
          <th>2012-06</th>
          <th>2012-07</th>
          <th>2012-08</th>
          <th>2012-09</th>
        </tr>
        <tr>
          <td>12</td>
          <td>202</td>
          <td>48</td>
          <td>A646A</td>
          <td>2323</td>
          <td>889</td>
          <td>0</td>
          <td>0.00</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>

export default window.$crudCommon({
  name: 'Receivables',
  data() {
    return {
      a: []
    }
  },
  created() {
  },
  methods: {
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
    addAfter() {
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
    delAfter() {}
  }
}, {
  name: 'finance/receivables', // 模块名字
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
.biaoge{
  // padding-top: 5px;
  width: 100%;
  height: 80px;
  border-bottom:1px solid #717171;
}
table{
  margin-top: 30px;
  width: 100%;
  color: black;

}
tr{
  width: 9%;
  line-height: 25px;
  text-align: center;
}
td{
  width: 9%;
  text-align: center;
  height: 30px;
}
</style>
