<template>
  <el-container>
    <el-main>
      <h1 align="center">仓库管理</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
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
          <!-- <el-table-column v-show="true" prop="deliveryQuantity" label="送货数量" width="140" />
          <el-table-column v-show="true" prop="alreadyDeliveryQuantity" label="已送数量" width="140" /> -->
          <el-table-column v-show="true" prop="stayDeliveryQuantity" label="待送数量" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" width="140" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" width="160" />
          <el-table-column v-show="true" prop="position" label="原料仓位" width="140" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="损耗数量" />
          <el-table-column v-show="true" prop="surplusNum" label="剩余数量" width="160" />
          <!-- <el-table-column v-show="true" prop="productNum" label="已产数量" width="140" /> -->
          <!-- <el-table-column v-show="true" prop="outDate" label="送货日期" width="160" />
          <el-table-column v-show="true" prop="outNo" label="送货单号" width="160" />
          <el-table-column label="操作" width="500px">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="scope.row.deliveryQuantity!==null ?true : false" @click="purAdd(scope)">新增送货数量</el-button>
              <el-button type="primary" size="small" :disabled="(scope.row.deliveryQuantity!==null ?false : true) || (scope.row.alreadyDeliveryQuantity===parseInt(scope.row.orderQuantity) ?true : false)" @click="modifyPur(scope)">编辑送货数量</el-button>
              <el-button type="warning" size="small" :disabled="(scope.row.alreadyDeliveryQuantity===parseInt(scope.row.orderQuantity) ?true : false) || (parseInt(scope.row.deliveryQuantity)===0 ?true : false) " @click="printing(scope)">生成送货单</el-button>
            </template>
          </el-table-column> -->
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
      <!-- 新增/编辑送货数量 -->
      <el-dialog :title="titleType+'送货数量'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="80px">

          <el-form-item label="入仓单号:" prop="warehouseNo">
            <el-input v-model="formAdd.warehouseNo" disabled />
          </el-form-item>
          <el-form-item label="订单数量:" prop="orderQuantity">
            <el-input v-model="formAdd.orderQuantity" disabled />
          </el-form-item>
          <el-form-item label="已产数量:" prop="productNum">
            <el-input v-model="formAdd.productNum" disabled />
          </el-form-item>
          <el-form-item label="送货数量:" prop="deliveryQuantity">
            <el-input v-model="formAdd.deliveryQuantity" @blur="deliveryChange" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/warehouse/warehouse'
import { add } from '@/api/warehouse/warehouse'
import { getById } from '@/api/warehouse/warehouse'
import { upState } from '@/api/end-product/product'
import { getCustomerById } from '@/api/basedata/customer'

export default {
  name: 'CardboardList',
  mixins: [initData],
  data() {
    return {
      form: {
        time: ''
      },
      formAdd: { differencesNum: '' },
      tableData: [],
      addTableData: [],
      purAddVisible: false,
      purRules: {
        checkNum: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryQuantity: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      drawerFor: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    deliveryChange() {
      var a = parseInt(this.formAdd.deliveryQuantity)
      var c = parseInt(this.formAdd.orderQuantity)

      if (this.formAdd.productNum === null || this.formAdd.productNum === 0) {
        var b = 0
      } else {
        b = parseInt(this.formAdd.productNum)
      }
      console.log(this.formAdd.productNum)
      if (a > b || a > c) {
        this.$message.error('送货数量不能大于已仓产数量或者订单数量！！')
        this.formAdd.deliveryQuantity = c
        return
      }
    },
    loadData() {
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        console.log(res)
        this.tableData = res.list
        this.tableData.forEach(a => {
          var c = parseInt(a.position)
          if (a.deliveryQuantity === null) {
            a.surplusNum = c
          } else {
            var d = parseInt(a.deliveryQuantity)
            a.surplusNum = c - d
          }
          getCustomerById(a.customerId).then(data => {
            this.$set(a, 'customerName', data.name)
          })
          a.stayDeliveryQuantity = a.orderQuantity - a.alreadyDeliveryQuantity
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
        this.formAdd.differencesNum = this.formAdd.checkNum - this.formAdd.purchaseQuantity
      })
    },
    // 打印
    printing(scope) {
      this.multipleSelection.push(scope.row)
      let flag = true
      this.multipleSelection.forEach(a => {
        if (a.deliveryQuantity === null) {
          this.$message.error('请先填写送货数量！！')
          flag = false
        }
      })
      if (flag) {
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        upState(idList).then(res => {
        })
        this.$router.push({
          path: '/cardboard_product',
          query: { 'data': this.multipleSelection }
        })
      }
    },
    // 生成送货单
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '生成'
      // 新增初始化数据
      this.formAdd = {}
      this.$set(this.formAdd, 'id', scope.row.id)
      this.$set(this.formAdd, 'warehouseNo', scope.row.warehouseNo)
      // this.$set(this.formAdd, 'deliveryQuantity', scope.row.orderQuantity)
      this.$set(this.formAdd, 'orderQuantity', scope.row.orderQuantity)
      this.$set(this.formAdd, 'productNum', scope.row.productNum)
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
