<template>
  <el-container>
    <el-main>
      <div id="print">
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <h1 align="center">采购订单</h1>
        <h3 align="center">{{ name }}</h3>
        <span style="margin-left:60px">供方:{{ fullName }}</span>
        <br>
        <span style="margin-left:60px">电话:{{ mobilePhone }}</span>
        <span style="margin-left:65%">No:{{ documentsNo }}</span>
        <br>
        <span style="margin-left:60px">传真:{{ fax }}</span>
        <span style="margin-left:70%">打印日期:{{ dateFormat(new Date()) }}</span>
        <p />
        <br>
        <el-table
          ref="multipleTable"
          :data="tableData"
          stripe
          highlight-current-row
          border=""
          show-summary
          style="width: 100%"
        >
          <el-table-column width="50px" align="center" />
          <el-table-column prop="no" label="任务编号" />
          <el-table-column prop="material" label="材质" />
          <el-table-column prop="stare" label="楞型" />
          <el-table-column prop="pressureSpecification" label="分压规格(MM)" />
          <el-table-column prop="paperSize" label="纸片尺寸(MM)纸长 X 纸宽">
            <template>
              {{ length }}  X {{ width }}
            </template>
          </el-table-column>
          <el-table-column prop="costPrice" label="成本价 元/片" />
          <el-table-column prop="purchaseQuantity" label="数量 (片)" />
          <el-table-column prop="totalPrice" label="总价 (元)" />
          <el-table-column prop="deliveryDate" label="交货日期" />
        </el-table>
        <br>
        <span style="margin-left:60px">备注:{{ remark }}</span>
        <br>
        <Br />
        <span style="margin-left:60px">如供方对以上各项确认无误,请在确认栏签字并回传;<br>
          <span style="margin-left:60px"> 如有疑问,速与我方联系!
            <span style="margin-left:666px">供方确认:</span>
            <span style="margin-left:888px">采购方确认:</span>
          </span>
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
import { getById } from '@/api/supplier/supplier'
import { list } from '@/api/basedata/firm'

export default {
  name: 'PurchaseOrderPrinting',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      form: {},
      fullName: '',
      mobilePhone: '',
      documentsNo: '',
      fax: '',
      remark: '',
      spplierId: '',
      billingDate: '',
      date: '',
      length: '',
      width: '',
      name: ''

    }
  },
  created() {
    this.data = this.$route.query.data
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
        a.totalPrice = a.costPrice * a.purchaseQuantity
        this.documentsNo = a.documentsNo
        this.remark = a.remark
        this.spplierId = a.supplierId
        this.width = a.width
        this.length = a.length
      })
      this.pagination.total = this.tableData.length
      this.getSupplier()
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

    // 加载供应商
    getSupplier() {
      getById(this.spplierId).then(res => {
        this.fullName = res.fullName
        this.mobilePhone = res.mobilePhone
        this.fax = res.fax
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
