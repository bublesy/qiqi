<template>
  <div id="printTest" style="margin:30px">
    <p class="font">生产日报表</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="创建日期:">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="">
        <el-button size="mini" type="primary">查询</el-button>
        <el-button v-print="'#printTest'" type="warning">打印</el-button>
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
    >
      <el-table-column type="index" width="55" />
      <el-table-column prop="name" label="客户名称" width="120" />
      <el-table-column prop="name" label="任务编号" width="120" />
      <el-table-column prop="name" label="款号" width="120" />
      <el-table-column prop="name" label="箱型" width="120" />
      <el-table-column prop="name" label="订单尺寸" width="120" />
      <el-table-column prop="name" label="订单数量" width="120" />
      <el-table-column prop="name" label="已产数量" width="120" />
      <el-table-column prop="name" label="单价" width="120" />
      <el-table-column prop="name" label="金额" width="120" />
      <el-table-column prop="name" label="是否生产" width="120" />
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
export default {
  name: 'ProDaily',
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        page: 1,
        size: 10
      }
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ name: 'hc' })
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
  },
  methods: {
    initTable() {},
    handleSelectionChange() {},
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
