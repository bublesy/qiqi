<template>
  <el-container>
    <el-main>
      <div id="print">
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <span style="margin-left:70%">打印日期:{{ dateFormat(new Date()) }}</span>
        <h1 align="center">定作送货单</h1>
        <h3 align="center">{{ name }}</h3>
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
          <el-table-column prop="customerNo" label="订单编号" />
          <el-table-column prop="orderNum" label="订单量" />
          <el-table-column prop="alreadyDeliveryQuantity" label="送货量" />
          <el-table-column prop="perPrice" label="单价" />
          <el-table-column prop="money" label="订单金额" />
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
import { getNamesById } from '@/api/purchase/purchase'
import { list } from '@/api/basedata/firm'

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
      nickname: '',
      date: new Date(),
      name: ''
    }
  },
  created() {
    this.data = this.$route.query.data
    console.log('a', this.data)
    this.getList()
  },
  methods: {
    dateFormat: function(time) {
      var date = new Date(time)
      var year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      // 拼接
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    },
    getList() {
      this.tableData = this.data
      this.tableData.forEach(a => {
        a.amount = a.unitPrice * a.deliveryQuantity
        this.billingDate = a.billingDate
        this.documentsNo = a.documentsNo
        this.remark = a.remark
        this.customerId = a.customerId
        this.createdBy = a.createdBy
        this.fullName = a.name
      })
      this.pagination.total = this.tableData.length
      this.getName()
      list().then(res => {
        var firmList = []
        firmList = res.list
        firmList.forEach(a => {
          if (a.isFirm === true) {
            this.name = a.name
          }
        })
      })
    },
    // 名称
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
