<template>
  <div style="margin:30px">
    <p class="font">客户订单日报表</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <!-- <el-form-item label="创建日期:">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item> -->
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
      <el-table-column prop="name" label="任务编号" width="120" />
      <el-table-column prop="name" label="款号" width="120" />
      <el-table-column prop="name" label="材质" width="120" />
      <el-table-column prop="name" label="订单尺寸" width="120" />
      <el-table-column prop="name" label="订单数量" width="120" />
      <el-table-column prop="name" label="单价" width="120" />
      <el-table-column prop="name" label="金额" width="120" />
      <el-table-column prop="name" label="交货日期" width="120" />
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="singlePrint(scope.row)">打印</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { export2Excel } from '@/utils/common'
import { getDaily } from '@/api/order/daily'
export default {
  name: 'Daily',
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        page: 1,
        size: 10,
        date: null
      },
      select: []
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
      getDaily(this.form).then(res => {
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
      console.log('aaa')
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/dailyOrder',
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
        path: '/dailyOrder',
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
