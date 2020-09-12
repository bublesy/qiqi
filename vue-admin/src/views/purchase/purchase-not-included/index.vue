<template>
  <el-container>
    <el-main>
      <div id="print">
        <el-header align="center">采购未进一览表</el-header>
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
          <el-table-column prop="taskNumber" label="任务编号" />
          <el-table-column prop="customerName" label="客户名称" />
          <el-table-column prop="paperSize" label="纸片尺寸(MM)纸长 X 纸宽">
            <template slot-scope="scope">
              {{ scope.row.paperLength }}{{ scope.row.paperWidth }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" width="140" />
          <el-table-column prop="purQuantity" label="购入数量" />
          <el-table-column prop="quaNotEnt" label="未进数量" />
          <el-table-column prop="supplier" label="供方" />
          <el-table-column prop="orderDate" label="订货日期" />
          <el-table-column prop="purchaseDate" label="进货日期" />
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
  name: 'PurchaseNotIncluded',
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
