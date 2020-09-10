<template>
  <div style="padding:30px">
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="schedule" label="排期日期" width="120" />
      <el-table-column prop="taskNo" label="任务编号" width="120" />
      <el-table-column prop="customerInfo" label="客户简介" width="120" />
      <el-table-column prop="boxType" label="箱型" width="120" />
      <el-table-column prop="material" label="材质" width="120" />
      <el-table-column prop="stare" label="楞型" width="120" />
      <el-table-column prop="cartonSize" label="纸箱尺寸" width="120" />
      <el-table-column prop="paperLength" label="纸长" width="120" />
      <el-table-column prop="paperWidth" label="纸宽" width="120" />
      <el-table-column prop="pressureSpecification" label="分压规格" width="120" />
      <el-table-column prop="unit" label="单位" width="120" />
      <el-table-column prop="orderNum" label="订单数量" width="120" />
      <el-table-column prop="productNum" label="成品数量" width="120" />
      <el-table-column prop="delivery" label="交货日期" width="120" />
      <el-table-column prop="isSchedule" label="是否排班" width="120" />
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
  data() {
    return {
      tableData: [],
      total: 0,
      size: 10,
      page: 1

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

<style>

</style>
