<template>
  <el-container>
    <el-main>
      <h1 align="center">仓库库存盘点管理</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="盘点时间:">
          <el-date-picker
            v-model="form.checkTime"
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="fullCheck">整页盘点</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="deliveryStatus" label="发货状态" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="carryTo" label="过账" width="140" />
          <el-table-column v-show="true" prop="noType" label="单据类型" width="140" />
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" width="140" />
          <!-- <el-table-column v-show="true" prop="deliveryQuantity" label="送货数量" width="140" /> -->
          <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" width="140" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" width="160" />
          <el-table-column v-show="true" prop="position" label="入库数量" width="140" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="损耗数量" />
          <el-table-column v-show="true" prop="surplusNum" label="剩余数量" width="160" />
          <el-table-column v-show="true" prop="checkDate" label="盘点日期" width="160" />
          <el-table-column label="操作" width="500px">
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="purAdd(scope)">新增库存盘点</el-button>
              <!-- <el-button type="primary" size="small" @click="modifyPur(scope)">编辑库存盘点</el-button> -->
              <el-button type="warning" size="small" @click="printing(scope)">生成盘点单</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
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
      <!-- 新增/编辑盘点数量 -->
      <el-dialog :title="titleType+'盘点数量'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="100px">

          <el-form-item label="入仓单号:" prop="warehouseNo">
            <el-input v-model="formAdd.warehouseNo" disabled />
          </el-form-item>
          <el-form-item label="库存数量:" prop="position">
            <el-input v-model="formAdd.position" disabled />
          </el-form-item>
          <el-form-item label="盘点数量:" prop="checkNum">
            <el-input v-model="formAdd.checkNum" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 新增/编辑盘点数量 -->
      <el-dialog :title="titleType+'盘点数量'" :visible.sync="fullCheckVisible">
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" />
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" />
          <el-table-column v-show="true" prop="position" label="入库数量" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="损耗数量" />
          <el-table-column v-show="true" prop="surplusNum" label="剩余库存数量">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.surplusNum" :controls="false" style="width:60px" size="mini" />
            </template>>
          </el-table-column>
          <el-table-column v-show="true" prop="checkDate" label="盘点日期" />
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="CheckNo">取 消</el-button>
          <el-button size="small" type="primary" @click="CheckOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/warehouse/warehouse'
import { add, check } from '@/api/warehouse/warehouse'
import { getById } from '@/api/warehouse/warehouse'
// import { upState } from '@/api/end-product/product'
import { getCustomerById } from '@/api/basedata/customer'

export default {
  name: 'StockCheck',
  mixins: [initData],
  data() {
    return {
      form: {
        checkTime: ''
      },
      formAdd: { differencesNum: '' },
      tableData: [],
      addTableData: [],
      purAddVisible: false,
      purRules: {
        checkNum: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      drawerFor: [],
      printingDis: false,
      fullCheckVisible: false,
      ids: [],
      surplusNums: [],
      formCheck: {}
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 打印
    printing(scope) {
      this.multipleSelection.push(scope.row)
      this.$router.push({
        path: '/stock_check_print',
        query: { 'data': this.multipleSelection }
      })
    },
    wholePrinting() {
      this.$router.push({
        path: '/stock_check_print',
        query: { 'data': this.tableData }
      })
    },
    CheckNo() {
      this.fullCheckVisible = false
    },
    CheckOk() {
      this.ids = []
      this.surplusNums = []
      this.tableData.forEach(a => {
        this.ids.push(a.id)
        this.surplusNums.push(a.surplusNum)
      })
      this.formCheck.ids = this.ids
      this.formCheck.surplusNums = this.surplusNums
      console.log(this.formCheck)
      check(this.formCheck).then(res => {
        if (res) {
          this.$message.success(this.titleType + '成功')
          location.reload()
        } else {
          this.$message.error(this.titleType + '失败')
        }
      })
      this.fullCheckVisible = false
    },
    fullCheck() {
      this.fullCheckVisible = true
      this.tableData.forEach(a => {
        var c = parseInt(a.position)
        if (a.deliveryQuantity === null) {
          a.surplusNum = c
        } else {
          var d = parseInt(a.deliveryQuantity)
          a.surplusNum = c - d
        }
      })
    },
    loadData() {
      this.queryParams.checkTime = this.form.checkTime
      if (this.queryParams.checkTime === null) {
        this.$set(this.queryParams, 'checkTime', '')
      }
      list(this.queryParams).then(res => {
        console.log(res)
        this.tableData = res.list
        this.tableData.forEach(a => {
          getCustomerById(a.customerId).then(data => {
            this.$set(a, 'customerName', data.name)
          })
          var c = parseInt(a.position)
          if (a.deliveryQuantity === null) {
            a.surplusNum = c
          } else {
            var d = parseInt(a.deliveryQuantity)
            a.surplusNum = c - d
          }

          a.stayDeliveryQuantity = a.orderQuantity - a.alreadyDeliveryQuantity
          var j = parseInt(a.orderQuantity)
          if (a.alreadyDeliveryQuantity === j) {
            this.printingDis = true
          }
        })
        this.pagination.total = res.total
      })
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 编辑盘点单
    modifyPur(scope) {
      this.purAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAdd = res
        // this.formAdd.checkNum = res.position
      })
    },
    // 生成盘点单
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '生成'
      // 新增初始化数据
      this.formAdd = {}
      this.$set(this.formAdd, 'id', scope.row.id)
      this.$set(this.formAdd, 'warehouseNo', scope.row.warehouseNo)
      console.log(scope.row.surplusNum)
      this.$set(this.formAdd, 'checkNum', scope.row.surplusNum)
      this.$set(this.formAdd, 'position', scope.row.position)
    },
    // 取消
    purAddNo() {
      this.purAddVisible = false
      this.addTableData = []
    },
    purAddOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              location.reload()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
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
  .el-dialog__header {
    border: 0;
  }
</style>
