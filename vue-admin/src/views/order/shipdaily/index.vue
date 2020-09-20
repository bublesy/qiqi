<template>
  <div id="printTest" style="margin:30px">
    <p class="font">出货日报表</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="出货日期:">
        <el-date-picker
          v-model="form.deliveryDate"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="客户名称:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="出货单号:">
        <el-input v-model="form.outNo" />
      </el-form-item>
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
      <el-table-column prop="shipDate" label="出货日期" width="120" />
      <el-table-column prop="outNo" label="出货单号" width="120" />
      <el-table-column prop="boxType" label="箱型" width="120" />
      <el-table-column prop="sendNum" label="出货数量" width="120" />
      <el-table-column prop="perPrice" label="单价" width="120" />
      <el-table-column prop="money" label="金额" width="120" />
      <el-table-column prop="sign" label="回签状态" width="120" />
      <!-- <template slot-scope="scope">
          <span v-if="scope.row.sign === true">已回签</span>
          <span v-else>未回签</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="singlePrint(scope.row)">打印</el-button>
          <el-button type="success" size="mini" @click="updated(scope.row.id)">编辑</el-button>
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
    <edItDialog :id="id" :dialog="edItDialog" @init="initTable" />
  </div>
</template>

<script>
import { export2Excel } from '@/utils/common'
import { getShipDaily } from '@/api/order/shipdaily'
import { getUser } from '@/api/order/customerOrder'
import edItDialog from '@/views/order/shipdaily/edit'
export default {
  name: 'ProDaily',
  components: { edItDialog },
  data() {
    return {
      edItDialog: {
        show: false
      },
      tableData: [],
      total: 0,
      form: {
        page: 1,
        count: 10
      },
      select: [],
      name: '',
      id: ''
    }
  },
  created() {
    getUser().then(res => {
      this.name = res.nickname
    })
    this.initTable()
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
  },
  methods: {
    updated(id) {
      this.edItDialog.show = true
      this.id = id
    },
    query() {
      this.initTable()
    },
    initTable() {
      getShipDaily(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(x => {
          x.sign = x.sign === true ? '已回签' : '未回签'
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
      const th = ['客户名称', '出货日期', '出货单号', '箱型', '出货数量', '单价', '金额', '回签状态']
      const filterVal = ['name', 'deliveryDate', 'outNo', 'boxType', 'sendNum', 'perPrice', 'money', 'sign']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '箱类设定')
    },
    print() {
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/shipOrder',
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
        path: '/shipOrder',
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
