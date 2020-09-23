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
          <el-table-column v-show="true" prop="deliveryQuantity" label="送货数量" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="库存数量" width="140" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" width="160" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" width="160" />
          <el-table-column v-show="true" prop="position" label="仓位" width="140" />
          <el-table-column v-show="true" prop="position" label="实际库存数量" width="140" />
          <el-table-column v-show="true" prop="checkDate" label="盘点日期" width="160" />
          <el-table-column label="操作" width="500px">
            <template slot-scope="scope">
              <el-link type="primary" size="small" :disabled="scope.row.deliveryQuantity!==null ?true : false" @click="purAdd(scope)">新增送货数量</el-link>
              <el-link type="primary" size="small" :disabled="(scope.row.deliveryQuantity!==null ?false : true)||(scope.row.checkDate===null ?false : true)" @click="modifyPur(scope)">编辑送货数量</el-link>
              <el-link type="warning" size="small" :disabled="(scope.row.deliveryQuantity!==null ?false : true)||(scope.row.checkDate===null ?false : true)" @click="printing(scope)">生成送货单</el-link>
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
      <!-- 新增/编辑送货数量 -->
      <el-dialog :title="titleType+'送货数量'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="80px">

          <el-form-item label="入仓单号:" prop="warehouseNo">
            <el-input v-model="formAdd.warehouseNo" disabled />
          </el-form-item>
          <el-form-item label="送货数量:" prop="deliveryQuantity">
            <el-input v-model="formAdd.deliveryQuantity" />
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
        checkNum: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
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
    // 打印
    printing(scope) {
      this.multipleSelection.push(scope.row)
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
    },
    checkNumChange() {
      this.$set(this.formAdd, 'differencesNum', this.formAdd.checkNum - this.formAdd.purchaseQuantity)
    },
    loadData() {
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.tableData.forEach(a => {
          getCustomerById(a.customerId).then(data => {
            this.$set(a, 'customerName', data.name)
          })
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
    // 生成送货单
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '生成'
      // 新增初始化数据
      this.formAdd = {}
      this.$set(this.formAdd, 'id', scope.row.id)
      this.$set(this.formAdd, 'warehouseNo', scope.row.warehouseNo)
      this.$set(this.formAdd, 'deliveryQuantity', scope.row.purchaseQuantity)
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
              this.$refs[purForm].resetFields()
              this.loadData()
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
