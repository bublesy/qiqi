<template>
  <el-container>
    <el-main>
      <div id="print">
        <h1 align="center">成品库存待送货情况表</h1>
        <span style="margin-left:60px">制表人:{{ nickname }}</span>
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
          <el-table-column prop="customerName" label="客户名称" />
          <el-table-column prop="taskNumber" label="任务编号" />
          <el-table-column prop="material" label="材质" />
          <el-table-column prop="specifications" label="规格(mm)" />
          <el-table-column prop="orderQuantity" label="订单量" />
          <el-table-column prop="deliveryQuantity" label="入仓数量" />
          <el-table-column prop="stayDeliveredQuantity" label="已送数量" />
          <el-table-column prop="deliveryQuantity" label="待送数量" />
          <el-table-column prop="unitPrice" label="单价" />
          <el-table-column prop="amount" label="金额" />
          <el-table-column prop="deliveryDate" label="交货日期" />
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
import { getCustomerById } from '@/api/basedata/customer'
import { getNamesById } from '@/api/purchase/purchase'

export default {
  name: 'EndProductListNotDeliveryNote',
  mixins: [initData],
  data() {
    return {
      tableData: [{
        taskNumber: '1'
      }],
      form: {},
      data: [],
      billingDate: '',
      customerId: '',
      fullName: '',
      remark: '',
      createdBy: '',
      nickname: ''
    }
  },
  created() {
    this.data = this.$route.query.data
    console.log(this.data)
    this.getList()
  },
  methods: {
    getList() {
      this.tableData = this.data
      this.tableData.forEach(a => {
        console.log(a)
        a.amount = a.unitPrice * a.deliveryQuantity
        this.billingDate = a.billingDate
        this.documentsNo = a.documentsNo
        this.remark = a.remark
        this.customerId = a.customerId
        this.createdBy = a.createdBy
        a.stayDeliveredQuantity = a.deliveryQuantity - a.deliveryQuantity
        a.specifications = a.length * a.width
      })
      this.pagination.total = this.tableData.length
      this.getCustomer()
      this.getName()
    },
    // 加载客户
    getCustomer() {
      getCustomerById(this.customerId).then(res => {
        this.fullName = res.name
        this.remark = res.remark
      })
    },
    // 价值名称
    getName() {
      getNamesById(this.createdBy).then(res => {
        console.log(res)
        this.nickname = res
      })
    },
    toBack() {
      this.$router.push('/end_product_list')
    }
  }
}
</script>

<style scoped>
i {
  cursor: pointer;
}
</style>
