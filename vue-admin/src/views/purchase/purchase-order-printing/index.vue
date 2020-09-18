<template>
  <el-container>
    <el-main>
      <div id="print">
        <h3 align="center">{{ }}</h3>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <h1 align="center">采购订单</h1>
        <span style="margin-left:60px">供方:{{ fullName }}</span>
        <br>
        <span style="margin-left:60px">电话:{{ mobilePhone }}</span>
        <span style="margin-left:86%">No:{{ documentsNo }}</span>
        <br>
        <span style="margin-left:60px">传真:{{ fax }}</span>
        <span style="margin-left:80%">日期:{{ billingDate }}</span>

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
          <el-table-column prop="taskNumber" label="任务编号" />
          <el-table-column prop="material" label="材质" />
          <el-table-column prop="ridgeType" label="楞型" />
          <el-table-column prop="paperSize" label="纸片尺寸(MM)纸长 X 纸宽"/>
          <el-table-column prop="parPreSpe" label="分压规格(MM)" />
          <el-table-column prop="batching" label="纸板面积" />
          <el-table-column prop="squarePrice" label="平方价" />
          <el-table-column prop="unitPrice" label="单价 元/片" />
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
// import { listByIds } from '@/api/purchase/purchase'
export default {
  name: 'PurchaseOrderPrinting',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      form: {},
      fullName:"",
      billingDate:"",
      mobilePhone:"",
      documentsNo:"",
      fax:"",
      billingDate:"",
      remark:"",
      data:[]
    }
  },
  created(){
    //  this.$route.query.ids

    //  this.ids = this.$route.query.ids
     console.log("b",this.$route.query.data);
     this.data=this.$route.query.data
     console.log(this.data);
    //  this.ids = JSON.parse(this.$route.query.ids)
     this.getList()
  },
  methods: {
    getList(){
    this.tableData=this.data
    console.log(this.tableData);
     this.tableData.forEach(a=>{
          a.totalPrice=a.unitPrice*a.purchaseQuantity
          a.paperSize = a.paperWidth*a.paperLength
          this.fullName = a.fullName
          this.billingDate=a.billingDate
          this.mobilePhone=a.mobilePhone
          this.documentsNo=a.documentsNo
          this.fax=a.fax
          this.remark=a.remark
        })
    },

    // //加载页面的值
    // getList(){
    //   listByIds({ids:this.ids}).then(res=>{
    //     this.tableData=res.list
    //     this.tableData.forEach(a=>{
    //       a.totalPrice=a.unitPrice*a.purchaseQuantity
    //       a.paperSize = a.paperWidth*a.paperLength
    //       this.fullName = a.fullName
    //       this.billingDate=a.billingDate
    //       this.mobilePhone=a.mobilePhone
    //       this.documentsNo=a.documentsNo
    //       this.fax=a.fax
    //       this.remark=a.remark
    //     })
    //     this.pagination.total = res.total
    //   })
    // },
    // 返回
    toBack() {
      this.$router.push('/purchase_order')
    },

  }
}
</script>

<style scoped>
i {
  cursor: pointer;
}
</style>
