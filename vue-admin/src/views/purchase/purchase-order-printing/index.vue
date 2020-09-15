<template>
  <el-container>
    <el-main>
      <div id="print">
        <h3 align="center">{{ }}</h3>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <h1 align="center">采购订单</h1>
        <span style="margin-left:60px">供方:{{ }}</span>
        <br>
        <span style="margin-left:60px">电话:{{ }}</span>
        <span style="margin-left:80%">No:{{ }}</span>
        <br>
        <span style="margin-left:60px">传真:{{ }}</span>
        <span style="margin-left:80%">日期:{{ }}</span>

        <el-table
          ref="multipleTable"
          :summary-method="getSummaries"
          :data="tableData"
          stripe
          highlight-current-row
          show-summary
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
        <br>
        <span style="margin-left:60px">备注:{{ }}</span>
        <br>
        <Br />
        <span style="margin-left:60px">如供方对以上各项确认无误,请在确认栏签字并回传;<br>
          <span style="margin-left:60px"> 如有疑问,速与我方联系!
            <span style="margin-left:666px">供方确认:{{ }}</span>
            <span style="margin-left:888px">采购方确认:{{ }}</span>
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
export default {
  name: 'PurchaseOrderPrinting',
  mixins: [initData],
  data() {
    return {
      tableData: [],
      form: {}
    }
  },
  methods: {
    // 返回
    toBack() {
      this.$router.push('/purchase_order')
    },
    /**
     * 表格合计
     */
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价'
          return
        }
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
          sums[index] += ' 元'
        } else {
          sums[index] = 'N/A'
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
