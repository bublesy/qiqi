<template>
  <div id="printTest" style="padding:30px">
    <p class="font">生产排期表</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="排期日期:">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="任务编号:" label-width="180">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="客户简介:">
        <el-input v-model="form.as" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
    <el-button v-print="'#printTest'" type="warning" size="mini">打印</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-table
      re
      f="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
    >
      <el-table-column type="index" width="55" />
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
      <el-table-column prop="isSchedule" label="是否排期" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
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
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        count: 10,
        page: 1
      }
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ schedule: 'aa' })
  },
  methods: {
    query() {
      this.initTable()
    },
    add() {},
    updated(id) {},
    deleted() {},
    initTable() {},
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.form.count = size
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

<style lang="scss" scoped>
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
</style>>

