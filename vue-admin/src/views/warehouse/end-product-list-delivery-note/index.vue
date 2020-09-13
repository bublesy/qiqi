<template>
  <el-container>
    <el-main>
      <div id="print">
        <p align="center">海宁市中奇纸箱包装厂</p>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#print'" type="primary">打印</el-button>
        <el-header align="center">定作送货单</el-header>
        <span style="margin-left:60px">收货单位:{{ }}</span>
        <span style="margin-left:80%">No:{{ }}</span>
        <br>
        <span style="margin-left:60px">电话:{{ }}</span>
        <span style="margin-left:80%">送货日期:{{ }}</span>

        <br>
        <span style="margin-left:60px">地址-:{{ }}</span>
        <span style="margin-left:80%">车号:{{ }}</span>

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
          <el-table-column prop="taskNumber" label="订单编号" />
          <el-table-column prop="serialNumber" label="物品编号" />
          <el-table-column prop="typeNo" label="款号" />
          <el-table-column prop="serialName" label="物品名称" />
          <el-table-column prop="specifications" label="规格(mm)">
            <template slot-scope="scope">
              {{ scope.row.paperLength }} x {{ scope.row.paperWidth }} x {{ scope.row.paperHeight }}
            </template>
          </el-table-column>
          <el-table-column prop="orderQuantity" label="订单量" />
          <el-table-column prop="deliveryVolume" label="送货量" />
          <el-table-column prop="unitPrice" label="单价" />
          <el-table-column prop="amount" label="金额" />
          <el-table-column prop="remark" label="备注" />
        </el-table>
        <br>
        <span style="margin-left:60px">备注:{{ }}</span>
        <br>
        <Br />
        <span style="margin-left:60px">制单:{{ }}<br>
          <span style="margin-left:444px">送单人:{{ }}</span>
          <span style="margin-left:385px">收货单位(签章):{{ }}</span>
          <span style="margin-left:888px">经手人:{{ }}</span>
        </span>
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
export default {
  name: 'EndProductListDeliveryNote',
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
      this.$router.push('/end_product_list')
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
