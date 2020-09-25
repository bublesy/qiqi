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
          @selection-change="handleSelectionChange"
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
              <el-input-number v-model="scope.row.discount" :controls="false" style="width: 80%;" @blur="discountChange" />%
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="discountAmount" label="折扣金额" />
          <el-table-column v-show="true" prop="position" label="采购数量" />
          <el-table-column v-show="true" prop="costPrice" label="成本价" />
          <el-table-column v-show="true" prop="costAmount" label="成本金额" />
          <el-table-column v-show="true" prop="profit" label="毛利" />
          <el-table-column v-show="true" prop="pbilling" label="开单日期" />
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
import { mlist, profit } from '@/api/finance/profit'
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
        discount: 100

      },
      formAdd: { },
      // 表单数据
      tableData: [
        { discount: 100 }
      ]
    }
  },
  created() {
    this.init()
    this.tableData.discount = 100
  },
  methods: {
    discountChange() {
      this.tableData.forEach(a => {
        a.profit = a.amount - a.costAmount - a.discountAmount
        if (a.discount === 100) {
          a.discountAmount = 0
        } else {
          a.discountAmount = a.amount * (a.discount / 100)
        }
      })
    },
    // 获取列表数据
    loadData() {
      this.form.page = this.pagination.page
      this.form.count = this.pagination.size
      mlist(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(a => {
          a.discount = 100

          a.amount = a.orderNum * a.perPrice
          a.costAmount = a.costPrice * a.position
          a.profit = a.amount - a.costAmount - a.discountAmount
        })
        this.pagination.total = res.total
        // console.log(res)
        console.log(this.tableData.orderNum)
      })
    },
    // 修改数据
    updated(scope) {
      // console.log(scope)
      profit(this.form).then(res => {
        this.tableData = res
        mlist()
      })
    },

    toQuery() {

    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['客户名称', '任务编号', '客户单号', '箱型', '款号', '长', '宽', '高', '单位', '订单数量', '单价', '金额', '折扣', '折扣金额', '采购数量', '成本价', '成本金额', '毛利', '开单日期']
      const filterVal = ['name', 'no', 'customerNo', 'boxType', 'modelNo', 'length', 'width', 'height', 'unit', 'orderNum', 'perPrice', 'amount', 'discount', 'discountAmount', 'purchaseQuantity', 'costPrice', 'costAmount', 'profit', 'pbilling']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '毛利估算表')
    },
    // 选择打印
    selectPrinting() {
      if (this.form.carryTo === '已过期') {
        this.$router.push('/purchase_not_included_overdue')
      } else if (this.form.carryTo === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
          this.$router.push('/purchase_order_printing')
        }
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.carryTo === '已过期') {
        this.$router.push('/purchase_not_included_overdue')
      } else if (this.form.carryTo === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        this.$router.push('/purchase_order_printing')
      }
    },
    // 打印
    printing() {
      this.$router.push('/finance/verify_printing')
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 删除
    drop() {
      this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 编辑订单
    modifyPur(row) {
      this.purAddVisible = true
      this.titleType = '编辑'
    },
    // 新增订单
    purAdd() {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
    },
    // 取消
    purAddNo() {
      this.purAddVisible = false
      this.addTableData = []
    },
    purAddOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          this.purAddVisible = false
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
