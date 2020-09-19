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
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <!-- <el-button size="mini" type="primary" @click="add">新增</el-button> -->
    <el-button type="warning" size="mini" @click="print">批量打印</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-table
      re
      f="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="date" label="排期日期" width="120" />
      <el-table-column prop="productDay" label="生产天数" width="120" />
      <el-table-column prop="no" label="任务编号" width="120" />
      <el-table-column prop="name" label="客户简介" width="120" />
      <el-table-column prop="boxType" label="箱型" width="120" />
      <el-table-column prop="material" label="材质" width="120" />
      <el-table-column prop="stare" label="楞型" width="120" />
      <el-table-column prop="cartonSize" label="纸箱尺寸" width="150">
        <template slot-scope="scope">
          {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
        </template>
      </el-table-column>
      <el-table-column prop="paperLength" label="纸长" width="120" />
      <el-table-column prop="paperWidth" label="纸宽" width="120" />
      <el-table-column prop="pressureSpecification" label="分压规格" width="120" />
      <el-table-column prop="unit" label="单位" width="120" />
      <el-table-column prop="orderNum" label="订单数量" width="120" />
      <el-table-column prop="productNum" label="成品数量" width="120" />
      <el-table-column prop="deliveryDate" label="交货日期" width="180" />
      <el-table-column prop="isSchedule" label="是否排期" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.isSchedule === true">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
          <el-button type="success" size="mini" @click="singlePrint(scope.row)">打印</el-button>
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
    <addDialog :dialog="addDialog" />
    <editDialog :id="id" :dialog="editDialog" @init="initTable" />
  </div>
</template>

<script>
import { export2Excel } from '@/utils/common'
import addDialog from '@/views/order/schedule/add'
import editDialog from '@/views/order/schedule/edit'
import { getSchedule, delSchedule } from '@/api/order/schedule'
export default {
  components: { addDialog, editDialog },
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        count: 10,
        page: 1
      },
      addDialog: {
        show: false
      },
      editDialog: {
        show: false
      },
      select: [],
      id: ''
    }
  },
  created() {
    this.initTable()
  },
  methods: {
    initTable() {
      getSchedule(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(x => {
          x.cartonSize = x.length + 'X' + x.width + 'X' + x.height
          x.isSchedule = x.isSchedule === true ? '是' : '否'
        })
        this.total = res.total
      })
    },
    query() {
      this.initTable()
    },
    // add() {
    //   this.addDialog.show = true
    // },
    updated(id) {
      this.id = id
      this.editDialog.show = true
    },
    deleted(id) {
      delSchedule(id).then(res => {
        if (res) {
          this.initTable()
        }
      })
    },
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
      const th = ['排期日期', '生产天数', '任务编号', '客户简介', '箱型', '材质', '楞型', '纸箱尺寸', '纸长', '纸宽', '分压规格', '单位', '订单数量', '成品数量', '交货日期', '是否排期']
      const filterVal = ['date', 'productDay', 'no', 'name', 'boxType', 'material', 'stare', 'cartonSize', 'paperLength', 'paperWidth', 'pressureSpecification', 'unit', 'orderNum', 'productNum', 'deliveryDate', 'isSchedule']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '生产排期表')
    },
    print() {
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/scheduleOrder',
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
        path: '/scheduleOrder',
        query: list
      })
    },
    orderAgain() {

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

