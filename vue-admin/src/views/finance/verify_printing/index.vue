<template>
  <div class="app-container">

    <el-button @click="toBack">返回</el-button>
    <el-button v-print="'#printTest'" type="primary">打印</el-button>
    <el-button type="primary" @click="toExcel">导出</el-button>

    <div id="printTest">
      <div>
        <!-- <h2 style="text-align:center">海宁中奇纸箱包装厂</h2> -->
        <p style="text-align:center;margin-top:-10px">地址：{{ name }}&nbsp;&nbsp;&nbsp;&nbsp;电话：{{ name }}</p>
      </div>
      <div class="jie">
        <h2 style="text-align:center">2012年09月份月结对账单</h2>
        <p>客户：{{ name }}</p>
        <p>电话：{{ name }}</p>
        <p>传真：{{ name }}</p>
        <p class="dy">打印日期：{{ data }}</p>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 400,margin-top:20px"
      >
        <el-table-column
          prop="shipDate"
          label="出货日期"
        />
        <el-table-column
          prop="outNo"
          label="出货单号"
        />
        <el-table-column
          prop="modelNo"
          label="物品单号/款号"
        />
        <el-table-column
          prop="boxType"
          label="箱型"
        />
        <el-table-column
          prop="unit"
          label="长x宽x高"
        />
        <el-table-column
          prop="orderNum"
          label="数量"
        />
        <el-table-column
          prop="perPrice"
          label="单价"
        />
        <el-table-column
          prop="money"
          label="金额"
        />
      </el-table>
    </div>
  </div>
</template>

<script>
// 引入打印
import { export2Excel } from '@/utils/common'
export default window.$crudCommon({
  name: 'Verify_printing',
  data() {
    return {
    // 表单数据
      tableData: [

      ]
    }
  },
  created: function() {
    this.tableData = this.$route.params.data
    this.name = this.$route.params.data.[0].name
    console.log(this.tableData)
    var aData = new Date()
    console.log(aData) // Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)

    this.data =
      aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    console.log(this.data) // 2019-8-20
  },
  methods: {
    // 返回
    toBack() {
      this.$router.push('/finance/verify')
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    },
    // 打印功能
    toExcel() {
      var list = this.tableData
      const th = ['出货日期', '出货单号', '物品单号/款号', '箱型', '长x宽x高', '数量', '单价', '金额']
      const filterVal = ['data', 'shipment', 'goods', 'type', 'long', 'number', 'price', 'money']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '对账明细打印单')
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
  name: 'finance/verify_printing', // 模块名字
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
.jie{
  width: 100%;
  height: 120px;
  // border-bottom:1px solid #717171;
}
.jie>p{
  line-height: 10px;
}
.dy{
  float: left;
  margin: -50px 0 0 45%  ;
}
table{
  margin-top: 5px;
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
