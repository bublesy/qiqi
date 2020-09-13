<template>
  <div class="app-container">
    <div class="xz" />
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
    <el-button v-print="'#printTest'" type="primary">打印</el-button>
    <el-pagination
      style="float:right;"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="1"
      layout="prev, pager, next, jumper"
      :total="10"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <div id="printTest" class="biaoge">
      <h2 style="text-align:center">供方月结对账单</h2>
      <p>供方:佳宏包装</p>
      <p style="text-align:center;margin-top:-40px">打印日期:{{ value }}</p>
      <table>
        <tr>
          <th>任务编号</th>
          <th>入厂日期</th>
          <th>采购序号</th>
          <th>材质</th>
          <th>纸长</th>
          <th>纸宽</th>
          <th>购入数量</th>
          <th>退货数量</th>
          <th>退货金额</th>
          <th>单价</th>
          <th>账款年月</th>
        </tr>
        <tr>
          <td>12</td>
          <td>2020-09-09</td>
          <td>480</td>
          <td>A646A</td>
          <td>2323</td>
          <td>889</td>
          <td>0</td>
          <td>0.00</td>
          <td>6.55</td>
          <td>550</td>
          <td>2020-09-10</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>

export default window.$crudCommon({
  name: 'Supplier',
  data() {
    return {
      a: []
    }
  },
  created: function() {
    var aData = new Date()
    console.log(aData) // Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)

    this.value =
      aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    console.log(this.value) // 2019-8-20
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
  name: 'finance/supplier', // 模块名字
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
  width: 100%;
  height: 80px;
  border-bottom:1px solid #717171;
}
table{
  width: 100%;
  color: black;
  margin-top: 30px
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
