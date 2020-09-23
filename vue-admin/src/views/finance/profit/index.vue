<template>
  <el-container>
    <el-main>
      <h1 align="center">毛利估算</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="客户名称:">
          <el-input v-model="form.name" clearable @clear="loadData" />
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
          <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" />
          <el-table-column v-show="true" prop="orderNum" label="订单数量" />
          <el-table-column v-show="true" prop="perPrice" label="单价" />
          <el-table-column v-show="true" prop="orderNum" label="金额">
            <template slot-scope="scope">
              {{ scope.row.orderNum* scope.row.perPrice }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="discount" label="折扣" width="100px">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.discount" :controls="false" style="width: 80%;" />%
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="discountAmount" label="折扣金额">
            <template slot-scope="scope">
              {{ scope.row.orderNum* scope.row.perPrice * scope.row.discount / 100 }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="costPrice" label="成本价" />
          <el-table-column v-show="true" label="成本金额">
            <template slot-scope="scope">
              {{ scope.row.costPrice* scope.row.orderNum }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="profit" label="毛利">
            <template slot-scope="scope">
              {{ scope.row.orderNum* scope.row.discount / 100- scope.row.costPrice* scope.row.orderNum }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="pbilling" label="开单日期" />
          <el-table-column v-show="true" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="updated(scope)">保存</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->

        <!-- <template slot-scope="scope">
            {{ scope.row.orderNum* scope.row.perPrice }}
          </template> -->

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
      <!-- 新增/编辑对账明细单 -->
      <!-- <el-dialog :title="titleType+'对账明细表'" :visible.sync="purAddVisible" :close-on-click-modal="false">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
          <el-form-item label="客户名称" prop="supplier">
            <el-input v-model="formAdd.customerName" disabled>/>
            </el-input></el-form-item>
          <el-form-item label="客户电话" prop="pricing">
            <el-select v-model="formAdd.pricing">
              <el-option
                v-for="item in pricingFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="出货日期" prop="billingTime">
            <el-date-picker
              v-model="formAdd.billingTime"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="出货单号">
            <el-select v-model="formAdd.customerName" size="mini">
              <el-option
                v-for="item in customerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="物品单号/款号">
            <el-input v-model="formAdd.ridgeType" />
          </el-form-item>

          <el-form-item label="箱型">
            <el-input v-model="formAdd.parPreSpe" />
          </el-form-item>

          <el-form-item label="长x宽x高">
            <el-input v-model="formAdd.material" />
          </el-form-item>

          <el-form-item
            label="数量"
            :rules="[
              { required: true, message: '数量不能为空'},
              { type: 'number', message: '数量必须为数字值'}
            ]"
          >
            <el-input v-model="formAdd.paperLength" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.paperWidth" />
          </el-form-item>

          <el-form-item label="金额">
            <el-input v-model="formAdd.orderQuantity" />
          </el-form-item>

        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog> -->

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
        name: ''
      },
      formAdd: { },
      // 表单数据
      tableData: [

      ]
    }
  },
  created() {
    this.init()
    // list().then(res => {
    //   console.log(res)
    // })
  },
  methods: {
    updated(scope) {
      console.log(scope)
    },
    // 获取列表数据
    loadData() {
      this.form.page = this.pagination.page
      this.form.count = this.pagination.size
      mlist(this.form).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total
        console.log(res)
        console.log(this.tableData.orderNum)
      })
    },
    toQuery() {

    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['客户名称', '任务编号', '客户单号', '箱型', '款号', '长', '高', '单位', '采购数量', '订单数量', '单价', '金额', '折扣', '折扣金额', '成本价', '成本金额', '毛利', '开单日期']
      const filterVal = ['code', 'name', 'limitPaperLength']
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
