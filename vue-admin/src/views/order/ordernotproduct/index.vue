<template>
  <div id="printTest" style="margin:30px">
    <p class="font">订单未产总表</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="交货日期:">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="客户名称:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="任务编号:">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="">
        <el-button size="mini" type="primary">查询</el-button>
        <el-button type="warning" size="mini" @click="print">批量打印</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button><br>
      </el-form-item>
    </el-form>
    <span style="margin-left:25%">制表:</span>
    <span style="margin-left:400px">打印日期:{{ now }}</span><hr>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="客户名称" width="120" />
      <el-table-column prop="no" label="任务编号" width="120" />
      <el-table-column prop="customer_no" label="客户单号" width="120" />
      <el-table-column prop="model_no" label="款号" width="120" />
      <el-table-column prop="box_type" label="箱型" width="120" />
      <el-table-column prop="material" label="材质" width="120" />
      <el-table-column prop="supplier" label="供方" width="120" />
      <el-table-column prop="name" label="来料数量" width="120" />
      <el-table-column prop="name" label="尺寸" width="120" />
      <el-table-column prop="name" label="订单数量" width="120" />
      <el-table-column prop="name" label="已产数量" width="120" />
      <el-table-column prop="name" label="未产数量" width="120" />
      <el-table-column prop="name" label="交货日期" width="120" />
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="singlePrint(scope.row)">打印</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { export2Excel } from '@/utils/common'
import { getOrderNotProduct } from '@/api/order/ordernotproduct'
export default {
  name: 'ProDaily',
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        page: 1,
        size: 10
      },
      select: [],
      now: null
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ name: 'hc' })
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
  },
  methods: {
    initTable() {
      getOrderNotProduct(this.form).then(res => {
        console.log(res)
      })
    },
    handleSizeChange(size) {
      this.size = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = page
      this.initTable()
    },
    toExcel() {
      var list = this.tableData
      const th = ['客户名称', '出货日期', '出货单号', '箱型', '出货数量', '单价', '金额', '回签状态']
      const filterVal = ['code', 'name', 'limitPaperLength']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '箱类设定')
    },
    print() {
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/noProductOrder',
        query: this.select
      })
    },
    handleSelectionChange(select) {
      this.select = select
    },
    singlePrint(row) {
      var list = []
      list.push(row)
      this.$router.push({
        path: '/noProductOrder',
        query: list
      })
    }
  }
}
</script>

<style scoped>
.font{
  font-weight: bold;
  width:400px;
  height: 100px;
  line-height: 100px;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  /* text-align: center; */
  margin-left: 40%;
  margin-bottom: 0%;
}
</style>
