<template>
  <el-container>
    <el-main>
      <h1 align="center">成品仓库</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="成品送货:">
          <el-select v-model="form.carryTo" :clearable="true">
            <el-option label="已送货" value="已送货" />
            <el-option label="未送货" value="未送货" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="warning" size="mini" @click="selectPrinting">选择打印</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          align="center"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" width="140" />
          <el-table-column v-show="true" prop="purQuantity" label="库存数量" width="140" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="送货数量" width="140" />
          <el-table-column v-show="true" prop="carryTo" label="发货状态" width="140" />
          <el-table-column v-show="true" prop="boxType" label="箱型" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="length" label="长" width="140" />
          <el-table-column v-show="true" prop="width" label="宽" width="140" />
          <el-table-column v-show="true" prop="height" label="高" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="成本价" width="140" />
          <el-table-column v-show="true" prop="endProductPos" label="成品仓位" width="140" />
          <el-table-column v-show="true" prop="warehousingData" label="入仓时间" width="160" />
          <el-table-column v-show="true" prop="endProductPos" label="实际库存数量" width="140" />
          <el-table-column v-show="true" prop="checkDate" label="盘点时间" width="160" />
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

      <!-- 新增/编辑盘点单 -->
      <el-dialog :title="titleType+'盘点单'" :visible.sync="addCheckVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAddCheck" size="mini" label-width="80px">

          <el-form-item label="入仓单号:" prop="warehouseNo">
            <el-input v-model="formAddCheck.warehouseNo" disabled />
          </el-form-item>
          <el-form-item label="库存数量" prop="purQuantity">
            <el-input v-model="formAddCheck.purQuantity" disabled />
          </el-form-item>
          <el-form-item label="盘点数量" prop="checkNum">
            <el-input v-model="formAddCheck.checkNum" @change="checkNumChange" />
          </el-form-item>
          <el-form-item label="差异数量">
            <el-input v-model="formAddCheck.differencesNum" disabled />
          </el-form-item>
          <el-form-item label="备注:">
            <el-input v-model="formAddCheck.remark" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="addCheckNo">取 消</el-button>
          <el-button size="small" type="primary" @click="addCheckOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/end-product/product'
import { getCustomerById } from '@/api/basedata/customer'
import { updateState } from '@/api/end-product/product'
import { add } from '@/api/end-product/product'
import { getById } from '@/api/end-product/product'

export default {
  name: 'EndProductList',
  mixins: [initData],
  data() {
    return {
      formAdd: { },
      tableData: [],
      customerFor: [],
      purAddVisible: false,
      purRules: {
        deliveryQuantity: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        checkNum: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      form: {
        carryTo: '',
        time: ''
      },
      addCheckVisible: false,
      formAddCheck: {}

    }
  },
  created() {
    this.init()
  },
  methods: {
    purAddNo() {
      this.purAddVisible = false
    },
    addCheckNo() {
      this.addCheckVisible = false
    },
    checkNumChange() {
      this.$set(this.formAddCheck, 'differencesNum', this.formAddCheck.checkNum - this.formAddCheck.purQuantity)
    },
    modifyCheck(scope) {
      this.addCheckVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAddCheck = res
        this.formAddCheck.differencesNum = this.formAddCheck.checkNum - this.formAddCheck.purQuantity
      })
    },
    modifyPur(scope) {
      this.purAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
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
    },
    addCheckOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          add(this.formAddCheck).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.$refs[purForm].resetFields()
              this.loadData()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.addCheckVisible = false
        } else {
          return false
        }
      })
    },
    addCheck(scope) {
      this.addCheckVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAddCheck = {}
      this.$set(this.formAddCheck, 'warehouseNo', scope.row.warehouseNo)
      this.$set(this.formAddCheck, 'purQuantity', scope.row.purQuantity)
      this.$set(this.formAddCheck, 'id', scope.row.id)
    },
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
      this.$set(this.formAdd, 'id', scope.row.id)
      this.$set(this.formAdd, 'warehouseNo', scope.row.warehouseNo)
      this.$set(this.formAdd, 'deliveryQuantity', scope.row.orderQuantity)
    },
    loadData() {
      this.queryParams.carryTo = this.form.carryTo
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total
        this.tableData.forEach(a => {
          getCustomerById(a.customerId).then(data => {
            this.$set(a, 'customerName', data.name)
          })
        })
      })
    },
    // 打印
    printing(scope) {
      if (this.form.carryTo === '已送货') {
        this.multipleSelection.push(scope.row)
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else if (this.form.carryTo === '未送货') {
        this.multipleSelection.push(scope.row)
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_not_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else {
        this.multipleSelection.push(scope.row)
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.carryTo === '已送货') {
        // 更改送货状态
        const idList = []
        this.tableData.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.tableData }
        })
      } else if (this.form.carryTo === '未送货') {
        // 更改送货状态
        const idList = []
        this.tableData.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_not_delivery_note',
          query: { 'data': this.tableData }
        })
      } else {
        // 更改送货状态
        const idList = []
        this.tableData.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.tableData }
        })
      }
    },
    // 选择打印
    selectPrinting() {
      if (this.form.carryTo === '已送货') {
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else if (this.form.carryTo === '未送货') {
        // 更改送货状态
        const idList = []
        this.multipleSelection.forEach(a => {
          idList.push(a.id)
        })
        updateState(idList).then(res => {
        })
        this.$router.push({
          path: '/end_product_list_not_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
        // 更改送货状态
          const idList = []
          this.multipleSelection.forEach(a => {
            idList.push(a.id)
          })
          updateState(idList).then(res => {
          })
          this.$router.push({
            path: '/end_product_list_delivery_note',
            query: { 'data': this.multipleSelection }
          })
        }
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    }

  }
}

</script>
<style scoped lang="scss">
  >>>.el-dialog__header {
    border: 0;
  }
</style>
