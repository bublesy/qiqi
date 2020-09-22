<template>
  <div id="printTest" style="margin:30px">
    <el-card>
      <p class="font">生产日报表</p>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
        <el-form-item label="交货日期:">
          <el-date-picker
            v-model="form.deliveryDate"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-form-item label="排期日期:">
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
        <!-- <el-form-item label="是否生产:">
        <el-checkbox v-model="form.isPro" />
      </el-form-item> -->
        <el-form-item label="">
          <el-button size="mini" type="primary" @click="query">查询</el-button>
          <el-button type="warning" size="mini" @click="print">批量打印</el-button>
          <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button><br>
        </el-form-item>
      </el-form>
      <span style="margin-left:25%">制表:{{ name }}</span>
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
        <el-table-column prop="modelNo" label="款号" width="120" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="cartonSize" label="订单尺寸" width="120">
          <template slot-scope="scope">
            {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="productNum" label="已产数量" width="120" />
        <el-table-column prop="perPrice" label="单价" width="120" />
        <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="deliveryDate" label="交货日期" width="120" />
        <el-table-column prop="date" label="排期日期" width="120" />
        <!-- <el-table-column prop="name" label="是否生产" width="120" /> -->
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

    </el-card></div>
</template>

<script>
import { export2Excel } from '@/utils/common'
import { getProDaily } from '@/api/order/prodaily'
import { getUser } from '@/api/order/customerOrder'
export default {
  name: 'ProDaily',
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        page: 1,
        count: 10
      },
      select: [],
      now: null,
      name: ''
    }
  },
  created() {
    this.initTable()
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    getUser().then(res => {
      this.name = res.nickname
    })
  },
  methods: {
    query() {
      this.initTable()
    },
    initTable() {
      getProDaily(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(x => {
          x.cartonSize = x.length + 'X' + x.width + 'X' + x.height
        })
        this.total = res.total
      })
    },
    handleSizeChange(size) {
      this.form.count = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.form.page = page
      this.initTable()
    },
    toExcel() {
      var list = this.tableData
      const th = ['客户名称', '任务编号', '款号', '箱型', '订单尺寸', '订单数量', '已产数量', '单价', '金额', '交货日期', '排期日期']
      const filterVal = ['name', 'no', 'modelNo', 'boxType', 'cartonSize', 'orderNum', 'productNum', 'perPrice', 'money', 'deliveryDate', 'date']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '生产日报表')
    },
    print() {
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/prodailyOrder',
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
        path: '/prodailyOrder',
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
