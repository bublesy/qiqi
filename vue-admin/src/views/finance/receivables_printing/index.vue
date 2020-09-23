<template>
  <div class="app-container">
    <el-button @click="toBack">返回</el-button>
    <el-button v-print="'#printTest'" type="primary">打印</el-button>
    <el-button type="primary" @click="toExcel">导出</el-button>
    <div id="printTest" class="biaoge">
      <h2 style="text-align:center;padding-top:10px">客户应收款总表</h2>
      <p style="text-align:center;  line-height: 2px;">打印日期:{{ data }}</p>
      <el-table
        :data="tableData"
        border
        style="width: 400,margin-top:20px"
      >
        <el-table-column
          prop="name"
          label="客户"
        />
        <el-table-column
          prop="outNo"
          label="2012年-05月"
        />
        <el-table-column
          prop="modelNo"
          label="2012年-06月"
        />
        <el-table-column
          prop="boxType"
          label="2012年-07月"
        />
        <el-table-column
          prop="unit"
          label="2012年-08月"
        />
        <el-table-column
          prop="unit"
          label="合计"
        />
      </el-table>
    </div>
  </div>
</template>

<script>
import { export2Excel } from '@/utils/common'
export default window.$crudCommon({
  name: 'Receivables_printing',
  data() {
    return {
      tableData: [],
      a: []
    }
  },
  created: function() {
    this.tableData = this.$route.params.data
    var aData = new Date()
    console.log(aData) // Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
    this.data =
      aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    // console.log(this.data) // 2019-8-20
  },
  methods: {
    // 返回
    toBack() {
      this.$router.push('/finance/receivables')
    },
    // 打印功能
    toExcel() {
      var list = this.tableData
      const th = ['客户', '前期', '2012年-04月', '2012年-05月', '2012年-06月', '2012年-07月', '2012年-08月', '2012年-09月', '合计']
      const filterVal = ['customer', 'early', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'total']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '客户应收款总表')
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
