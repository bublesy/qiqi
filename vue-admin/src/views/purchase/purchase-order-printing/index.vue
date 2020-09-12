<template>
  <el-container>
    <el-main>
      <div>
        <el-header align="center">采购订单</el-header>
        <el-form :inline="true" :model="form" size="mini">
          <el-form-item label="供方:">
            <el-input v-model="form.documentsNo" />
          </el-form-item>
          <el-form-item label="No">
            <el-input v-model="form.No"> {{ }}   </el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="form.customerName" />
          </el-form-item>
          <el-form-item label="传真">
            <el-input v-model="form.customerName" />
          </el-form-item>
          <el-form-item label="日期" prop="deliveryTime">
            <el-date-picker
              v-model="form.time"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
        </el-form>
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
          <el-table-column prop="material" label="材质" />
          <el-table-column prop="ridgeType" label="楞型" />
          <el-table-column prop="paperSize" label="纸片尺寸(MM)纸长 X 纸宽">
            <template slot-scope="scope">
              {{ scope.row.paperLength }}{{ scope.row.paperWidth }}
            </template>
          </el-table-column>
          <el-table-column prop="parPreSpe" label="分压规格(MM)" />
          <el-table-column prop="paperboardArea" label="纸板面积" />
          <el-table-column prop="squarePrice" label="平方价" />
          <el-table-column prop="nickname" label="单价 元/片" />
          <el-table-column prop="unitPrice" label="数量 (片)" />
          <el-table-column prop="totalPrice" label="总价 (元)" />
          <el-table-column prop="deliveryTime" label="交货日期" />
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
  name: 'PurchaseOrderPrinting',
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
