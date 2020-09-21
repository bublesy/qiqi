<template>
  <el-container>
    <el-main>
      <div id="print">
        <p align="center">海宁市中奇纸箱包装厂</p>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <h1 align="center">定作送货单</h1>
        <span style="margin-left:60px">收货单位:{{ fullName }}</span>
        <br>
        <br>
        <el-table
          ref="multipleTable"
          :data="tableData"
          stripe
          highlight-current-row
          show-summary
          style="width: 100%"
        >
          <el-table-column width="50px" align="center" />
          <el-table-column prop="taskNumber" label="订单编号" />
          <el-table-column prop="specifications" label="规格(mm)" />
          <el-table-column prop="orderQuantity" label="订单量" />
          <el-table-column prop="deliveryQuantity" label="送货量" />
          <el-table-column prop="unitPrice" label="单价" />
          <el-table-column prop="amount" label="金额" />
          <el-table-column prop="remark" label="备注" />
        </el-table>
        <br>
        <span style="margin-left:60px">备注:{{ remark }}</span>
        <br>
        <Br />
        <span style="margin-left:444px">送单人:{{ nickname }}</span>
        <span style="margin-left:385px">收货单位(签章):{{ }}</span>
        <span style="margin-left:888px">经手人:{{ }}</span>
        <br>
        <Br />
        <span style="margin-left:60px">
          1.本定作送货单是合同的组成部分,如无书面合同,本定作送货单代合同.
        </span>
        <span style="margin-left:20%">
          2.收货单位如发现质量问题.请将原产品三日内退回,收货方使用过的产品供方概不负责.
        </span>
        <br>
        <span style="margin-left:60px">
          3.本定作送货单之价款如双方发生纠纷,合同履行地为供方所在地.
        </span>
        <span style="margin-left:22%">
          4.本定作送货单经收货方签字之后生效.
        </span>

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
  name: 'EndProductListDeliveryNote',
  mixins: [initData],
  data() {
    return {
      tableData: [],
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
        a.amount = a.unitPrice * a.deliveryQuantity
        this.billingDate = a.billingDate
        this.documentsNo = a.documentsNo
        this.remark = a.remark
        this.customerId = a.customerId
        this.createdBy = a.createdBy
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
    // 返回
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
