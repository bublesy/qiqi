<template>
  <div style="margin:30px">
    <p class="font">订单出货日报表</p>
    <span style="margin-left:10%">制表:</span>
    <span style="margin-left:400px">打印日期:</span><br>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="客户名称" width="120" />
      <el-table-column prop="name" label="出货日期" width="120" />
      <el-table-column prop="name" label="出货单号" width="120" />
      <el-table-column prop="name" label="箱型" width="120" />
      <el-table-column prop="name" label="出货数量" width="120" />
      <el-table-column prop="name" label="单价" width="120" />
      <el-table-column prop="name" label="金额" width="120" />
      <el-table-column prop="name" label="回签状态" width="120" />
    </el-table>
    <el-pagination
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
export default {
  name: 'ProDaily',
  data() {
    return {
      tableData: [],
      page: 1,
      size: 10,
      total: 0
    }
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
