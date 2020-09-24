
<template>
  <el-container>
    <el-main>
      <div id="print">
        <h1 align="center">采购未进一览表</h1>
        <span style="margin-left:60px">制表人:{{ nickname }}</span>
        <span style="margin-left:40%">打印日期:{{ today }}</span>
        <br>
        <br>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <el-table
          ref="multipleTable"
          :data="tableData"
          stripe
          border
          highlight-current-row
          style="width: 100%"
        >
          <el-table-column width="50px" align="center" />
          <el-table-column prop="no" label="任务编号" />
          <el-table-column prop="name" label="客户名称" />
          <el-table-column prop="paperSize" label="纸片尺寸(MM)纸长 X 纸宽" />
          <el-table-column prop="orderNum" label="订单数量" width="140" />
          <el-table-column prop="purQuantity" label="购入数量" />
          <el-table-column prop="quaNotEnt" label="未进数量" />
          <el-table-column prop="supplier" label="供方" />
          <el-table-column prop="orderDate" label="订货日期" />
          <el-table-column prop="pbilling" label="进货日期" />
        </el-table>
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          align="center"
          :current-page="pagination.page"
          :page-size="pagination.size"
          @size-change="sizeChange"
          @current-change="pageChange"
        />
      </div>
    </el-main>
  </el-container>
</template>

<script scope>
import initData from '@/mixins/initData'
import { getById } from '@/api/supplier/supplier'
import { getNameById } from '@/api/purchase/purchase'

export default {
  name: 'PurchaseNotIncluded',
  mixins: [initData],
  data() {
    return {
      tableData: [{
        taskNumber: '1'
      }],
      form: {},
      createdBy: '',
      nickname: '',
      today: '',
      spplierId: ''
    }
  },
  created() {
    this.data = this.$route.query.data
    this.getList()
  },
  methods: {
    getList() {
      this.tableData = this.data
      this.tableData.forEach(a => {
        a.totalPrice = a.unitPrice * a.purchaseQuantity
        a.paperSize = a.paperWidth * a.paperLength
        a.purQuantity = a.purchaseQuantity
        a.quaNotEnt = a.orderNum - a.purchaseQuantity
        this.billingDate = a.billingDate
        this.documentsNo = a.documentsNo
        a.purchaseDate = a.billingDate
        this.remark = a.remark
        this.spplierId = a.supplierId
        this.createdBy = a.id
        this.getSupplier()
        this.getName()
      })
      this.today = new Date()
      this.pagination.total = this.tableData.length
    },
    // 加载供应商
    getSupplier() {
      getById(this.spplierId).then(res => {
        this.tableData.forEach(a => {
          a.supplier = res.fullName
        })
      })
    },
    // 价值名称
    getName() {
      getNameById(this.createdBy).then(res => {
        this.nickname = res
      })
    },
    // 返回
    toBack() {
      this.$router.push('/purchase_order')
    }
  }
}
</script>

<style scoped>
i {
  cursor: pointer;
}
</style>
