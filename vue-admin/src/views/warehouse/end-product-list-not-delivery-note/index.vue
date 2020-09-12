<template>
  <el-container>
    <el-main>
      <div id="print">
        <el-header align="center">成品库存待送货情况表</el-header>
        <span style="margin-left:60px">制表人:{{ }}</span>
        <span style="margin-left:40%">打印日期:{{ }}</span>
        <br>
        <br>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <el-table
          ref="multipleTable"
          :summary-method="getSummaries"
          :data="tableData"
          stripe
          border
          highlight-current-row
          style="width: 100%"
        >
          <el-table-column width="50px" align="center" />
          <el-table-column prop="customerName" label="客户名称" />
          <el-table-column prop="customerNo" label="客户单号" />
          <el-table-column prop="taskNumber" label="任务编号" />
          <el-table-column prop="material" label="材质" />
          <el-table-column prop="specifications" label="规格(mm)">
            <template slot-scope="scope">
              {{ scope.row.paperLength }} x {{ scope.row.paperWidth }} x {{ scope.row.paperHeight }}
            </template>
          </el-table-column>
          <el-table-column prop="orderQuantity" label="订单量" />
          <el-table-column prop="warehousingQuantity" label="入仓数量" />
          <el-table-column prop="deliveredQuantity" label="已送数量" />
          <el-table-column prop="stayDeliveredQuantity" label="待送数量" />
          <el-table-column prop="unitPrice" label="单价" />
          <el-table-column prop="amount" label="金额" />
          <el-table-column prop="deliveryDate" label="交货日期" />
          <el-table-column prop="typeNo" label="款号" />
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
export default {
  name: 'EndProductListNotDeliveryNote',
  mixins: [initData],
  data() {
    return {
      tableData: [{
        taskNumber: '1'
      }],
      form: {}
    }
  },
  methods: {
    /**
     * 表格合计
     */
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总计'
          return
        }
        if (index === 2 || index === 3 || index === 4 || index === 5) {
          const values = data.map(item => Number(item[column.property]))
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr)
              if (!isNaN(value)) {
                return prev + curr
              } else {
                return prev
              }
            }, 0)
            sums[index] = parseFloat(sums[index] / 100).toFixed(2)
          } else {
            sums[index] = ''
          }
        }
      })
      return sums
    }
  }
}
</script>

<style scoped>
i {
  cursor: pointer;
}
</style>
