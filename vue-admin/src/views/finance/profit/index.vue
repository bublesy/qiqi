<template>
  <el-container>
    <el-main>
      <h1 align="center">毛利估算</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="日期:">
          <el-date-picker
            v-model="form.time"
            type="month"
            placeholder="选择月"
            value-format="yyyy-MM"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="toExcel">导出</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border=""
        >
          <el-table-column v-show="true" prop="name" label="客户名称" />
          <el-table-column v-show="true" prop="no" label="任务编号" />
          <el-table-column v-show="true" prop="customerNo" label="客户单号" />
          <el-table-column v-show="true" prop="boxType" label="箱型" />
          <el-table-column v-show="true" prop="modelNo" label="款号" />
          <el-table-column v-show="true" prop="length" label="长" />
          <el-table-column v-show="true" prop="width" label="宽" />
          <el-table-column v-show="true" prop="height" label="高" />
          <el-table-column v-show="true" prop="unit" label="单位" />
          <el-table-column v-show="true" prop="orderNum" label="订单数量" />
          <el-table-column v-show="true" prop="perPrice" label="单价" />
          <el-table-column v-show="true" prop="amount" label="金额" />
          <el-table-column v-show="true" prop="discount" label="折扣" width="100px">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.discount" :disabled="scope.row.pid !==null ?false:true" :controls="false" style="width: 80%;" @blur="discountChange" />%
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="discountAmount" label="优惠后金额" width="100" />
          <el-table-column v-show="true" prop="quantity" label="采购数量" />
          <el-table-column v-show="true" prop="costPrice" label="成本价" />
          <el-table-column v-show="true" prop="costAmount" label="成本金额" />
          <el-table-column v-show="true" prop="profit" label="毛利" />
          <el-table-column v-show="true" prop="pbilling" label="开单日期" width="180" />
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button :disabled="scope.row.pid !==null ?false:true" size="mini" type="primary" @click="preservation(scope)">保存</el-button>
            </template>
          </el-table-column>

        </el-table>
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          :current-page="pagination.page"
          :page-size="pagination.size"
          align="center"
          @size-change="sizeChange"
          @current-change="pageChange"
        />
      </div>

    </el-main>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import { export2Excel } from '@/utils/common'
import { mlist, updated } from '@/api/finance/profit'
export default {
  name: 'Verify',
  mixins: [initData],
  data() {
    return {
      form: {
        time: '',
        page: 1,
        count: 10,
        quantityOverdue: '',
        customerName: '',
        name: '',
        discount: 100,
        profit: '',

        no: '',
        customerNo: '',
        boxType: '',
        modelNo: '',
        length: '',
        width: '',
        unit: '',
        orderNum: '',
        height: '',
        perPrice: '',
        amount: '',
        costPrice: '',
        costAmount: '',
        pbilling: '',
        quantity: ''
      },
      formAdd: { },
      // 表单数据
      tableData: [
        { discount: 100 },
        { quantity: '' }
      ],
      formUpdate: {}
    }
  },
  created() {
    this.init()
  },
  methods: {
    discountChange() {
      this.tableData.forEach(a => {
        a.profit = a.discountAmount - a.costAmount
        if (a.discount === 100) {
          a.discountAmount = a.amount
        } else {
          a.discountAmount = a.amount * (a.discount / 100)
        }
      })
    },
    // 修改
    preservation(scope) {
      // console.log(scope.row)
      this.formUpdate.id = scope.row.pid
      this.formUpdate.discount = scope.row.discount
      this.formUpdate.discountAmount = scope.row.discountAmount
      this.formUpdate.profit = scope.row.profit
      updated(this.formUpdate).then(res => {
        // this.tableData = res
        if (res) {
          this.$message.success('成功')
        } else {
          this.$message.error('失败')
        }
        this.loadData()
      })
    },
    // 获取列表数据
    loadData() {
      this.form.page = this.pagination.page
      this.form.count = this.pagination.size
      mlist(this.form).then(res => {
        console.log(res)
        this.tableData = res.list
        this.tableData.forEach(a => {
          // a.discount = 100
          if (a.discount === null) {
            a.discount = 100
          }

          console.log(a.discount)
          a.amount = a.orderNum * a.perPrice
          a.costAmount = a.costPrice * a.position
          a.profit = a.discountAmount - a.costAmount
          if (a.discount === 100) {
            a.profit = a.amount - a.costAmount
          }
          if (a.position === '0') {
            a.quantity = a.endProductPos
          }
          if (a.endProductPos === '0') {
            a.quantity = a.position
          }
          if (a.discount === 100) {
            a.discountAmount = a.amount
          }
        })
        this.pagination.total = res.total
        // console.log(res)
        // console.log(this.tableData.orderNum)
      })
    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['客户名称', '任务编号', '客户单号', '箱型', '款号', '长', '宽', '高', '单位', '订单数量', '单价', '金额', '折扣', '折扣金额', '采购数量', '成本价', '成本金额', '毛利', '开单日期']
      const filterVal = ['name', 'no', 'customerNo', 'boxType', 'modelNo', 'length', 'width', 'height', 'unit', 'orderNum', 'perPrice', 'amount', 'discount', 'discountAmount', 'purchaseQuantity', 'costPrice', 'costAmount', 'profit', 'pbilling']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '毛利估算表')
    }
  }
}
</script>
<style scoped lang="scss">
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
