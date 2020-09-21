<template>
  <el-container>
    <el-main>
      <h1 align="center">采购单管理</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="客户名称:">
          <el-input v-model="form.customerName" />
        </el-form-item>
        <el-form-item label="采购未进数量是否过期:">
          <el-select v-model="form.quantityOverdue" :clearable="true">
            <el-option label="已过期" value="已过期" />
            <el-option label="未过期" value="未过期" />
          </el-select>
        </el-form-item>

        <el-form-item label="开单时间:">
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
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          border=""
          align="center"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="no" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="name" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="customerNo" label="客户单号" width="140" />
          <el-table-column v-show="true" prop="stare" label="楞型" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="length" label="长" width="140" />
          <el-table-column v-show="true" prop="width" label="宽" width="140" />
          <el-table-column v-show="true" prop="height" label="高" width="140" />
          <el-table-column v-show="true" prop="pressureSpecification" label="分压规格" width="140" />
          <el-table-column v-show="true" prop="orderNum" label="订单数量" width="140" />
          <el-table-column v-show="true" prop="unit" label="单位" width="140" />
          <el-table-column v-show="true" prop="isPurchase" label="采购单是否生成" width="140" />
          <el-table-column v-show="true" prop="documentsNo" label="采购单号" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" width="140" />
          <el-table-column v-show="true" prop="costPrice" label="成本价" width="140" />
          <el-table-column v-show="true" prop="amount" label="金额" width="140" />
          <el-table-column v-show="true" prop="position" label="仓位" width="140" />
          <el-table-column v-show="true" prop="endProductPos" label="成品仓位" width="140" />
          <el-table-column label="操作" width="400">
            <template slot-scope="scope">
              <el-link type="danger" size="small" @click="drop(scope)">删除</el-link>
              <el-link type="primary" size="small" :disabled="scope.row.documentsNo!==null ?true : false" @click="purAdd(scope)">生成采购单</el-link>
              <el-link type="primary" size="small" :disabled="warehousingDis" @click="modifyPur(scope)">编辑采购单</el-link>
              <el-link type="primary" size="small" :disabled="warehousingDis" @click="warehousing(scope)">入库</el-link>
              <el-link type="warning" size="small" @click="printing(scope)">生成打印单</el-link>
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
      <!-- 新增/编辑采购单 -->
      <el-dialog :title="titleType+'采购单'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
          <el-form-item label="供方" prop="supplierId">
            <el-select v-model="formAdd.supplierId">
              <el-option
                v-for="item in supplierFor"
                :key="item.id"
                :label="item.fullName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="客户名称" prop="customerName">
            <el-input v-model="formAdd.customerName" disabled />
          </el-form-item>

          <el-form-item label="任务编号">
            <el-input v-model="formAdd.taskNumber" disabled />
          </el-form-item>

          <el-form-item label="客户单号">
            <el-input v-model="formAdd.customerNo" disabled />
          </el-form-item>

          <el-form-item label="计价方式" prop="pricingMethod">
            <el-select v-model="formAdd.pricingMethod">
              <el-option label="净边" value="净边" />
              <el-option label="净宽" value="净宽" />
            </el-select>
          </el-form-item>
          <el-form-item label="开单日期" prop="billingDate">
            <el-date-picker
              v-model="formAdd.billingDate"
              align="right"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            />
          </el-form-item>

          <el-form-item label="结算周期" prop="settlementPeriod">
            <el-input-number v-model="formAdd.settlementPeriod" :controls="false" placeholder="单位（天）" />
          </el-form-item>

          <el-form-item label="是否成品">
            <el-checkbox v-model="formAdd.isProduct" disabled />
          </el-form-item>

          <el-form-item v-if="!formAdd.isProduct" label="仓位">
            <el-input v-model="formAdd.position" />
          </el-form-item>

          <el-form-item v-if="formAdd.isProduct" label="成品仓位">
            <el-input v-model="formAdd.endProductPos" />
          </el-form-item>

          <el-form-item label="采购数量">
            <el-input v-model="formAdd.purchaseQuantity" @change="purchaseSelect" />
          </el-form-item>

          <el-form-item label="成本价">
            <el-input v-model="formAdd.costPrice" @change="purchaseSelect" />
          </el-form-item>

          <el-form-item label="金额">
            <el-input-number v-model="formAdd.amount" :controls="false" disabled />
          </el-form-item>

          <el-form-item label="备注">
            <el-input v-model="formAdd.remark" />
          </el-form-item>

        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo('purForm')">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { export2Excel } from '@/utils/common'
import { supplierSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { add } from '@/api/purchase/purchase'
import { list } from '@/api/purchase/purchase'
import { getById } from '@/api/purchase/purchase'
import { delOrder } from '@/api/order/customerOrder'
import { warehousing } from '@/api/purchase/purchase'

export default {
  name: 'PurchaseOrder',
  mixins: [initData],
  data() {
    return {
      formAdd: { },
      tableData: [],
      addTableData: [],
      customerFor: [],
      purAddVisible: false,
      purRules: {
        supplierId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricingMethod: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingDate: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryDate: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        customerName: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      supplierFor: [],
      pricingFor: [],
      titleType: '',
      taskNumberVisible: false,
      multipleSelection: [],
      form: {
        customerName: '',
        quantityOverdue: '',
        time: ''
      },
      orderDate: '',
      identification: '',
      warehousingDis: true
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 采购数量改变金额改变
    purchaseSelect() {
      this.formAdd.amount = this.formAdd.costPrice * this.formAdd.purchaseQuantity
    },
    loadData() {
      this.queryParams.customerName = this.form.customerName
      this.queryParams.quantityOverdue = this.form.quantityOverdue
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total
        this.tableData.forEach(a => {
          if (a.documentsNo !== null) {
            a.isPurchase = '已生成采购单'
          } else {
            a.isPurchase = '未生成采购单'
          }
        })
      })
    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['编码', '名称', '限定最大纸长']
      const filterVal = ['code', 'name', 'limitPaperLength']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '采购单导出')
    },
    // 选择打印
    selectPrinting() {
      if (this.form.quantityOverdue === '已过期') {
        this.$router.push({
          path: '/purchase_not_included_overdue',
          query: { 'data': this.multipleSelection }
        })
      } else if (this.form.quantityOverdue === '未过期') {
        this.$router.push({
          path: '/purchase_not_included',
          query: { 'data': this.multipleSelection }
        })
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
          this.$router.push({
            path: '/purchase_order_printing',
            query: { 'data': this.multipleSelection }
          })
        }
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.quantityOverdue === '已过期') {
        this.$router.push({
          path: '/purchase_not_included_overdue',
          query: { 'data': this.tableData }
        })
      } else if (this.form.quantityOverdue === '未过期') {
        this.$router.push({
          path: '/purchase_not_included',
          query: { 'data': this.tableData }
        })
      } else {
        this.$router.push({
          path: '/purchase_order_printing',
          query: { 'data': this.tableData }
        })
      }
    },
    // 打印
    printing(scope) {
      if (this.form.quantityOverdue === '已过期') {
        scope.row.orderDate = this.orderDate
        this.multipleSelection.push(scope.row)
        this.$router.push({
          path: '/purchase_not_included_overdue',
          query: { 'data': this.multipleSelection }
        })
      } else if (this.form.quantityOverdue === '未过期') {
        scope.row.orderDate = this.orderDate
        this.multipleSelection.push(scope.row)
        this.$router.push({
          path: '/purchase_not_included',
          query: { 'data': this.multipleSelection }
        })
      } else {
        this.multipleSelection.push(scope.row)
        this.$router.push({
          path: '/purchase_order_printing',
          query: { 'data': this.multipleSelection }
        })
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 删除
    drop(scope) {
      delOrder(scope.row.id).then(res => {
        if (res) {
          this.$message.success('删除成功')
          this.loadData()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    // 入库
    warehousing(scope) {
      this.formAdd.id = scope.row.pid
      this.formAdd.isProduct = scope.row.isProduct
      this.formAdd.purchaseQuantity = scope.row.purchaseQuantity
      this.formAdd.deliveryDate = scope.row.deliveryDate
      this.formAdd.boxType = scope.row.boxType
      this.formAdd.stare = scope.row.stare
      this.formAdd.material = scope.row.material
      this.formAdd.orderQuantity = scope.row.orderNum
      this.formAdd.length = scope.row.length
      this.formAdd.width = scope.row.width
      this.formAdd.height = scope.row.height
      this.formAdd.costPrice = scope.row.costPrice
      this.formAdd.taskNumber = scope.row.no
      this.formAdd.endProductPos = scope.row.endProductPos
      this.formAdd.position = scope.row.position
      this.formAdd.customerName = scope.row.id
      this.formAdd.supplierId = scope.row.supplierId
      warehousing(this.formAdd).then(res => {
        if (res) {
          this.identification = this.$message.success('入库成功')
          this.warehousingDis = true
          this.loadData()
        } else {
          this.$message.error('入库失败')
        }
      })
    },
    // 编辑订单
    modifyPur(scope) {
      this.purAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.pid).then(res => {
        this.formAdd = res
        // 加载供应商下拉框
        supplierSelect().then(res => {
          this.supplierFor = res
        })
        this.$set(this.formAdd, 'customerName', scope.row.name)
        this.$set(this.formAdd, 'taskNumber', scope.row.no)
        this.$set(this.formAdd, 'customerNo', scope.row.customerNo)
        if (scope.row.isProduct === '成品') {
          this.$set(this.formAdd, 'isProduct', true)
        }
      })
    },
    // 新增订单
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
      })
      this.$set(this.formAdd, 'customerName', scope.row.name)
      this.$set(this.formAdd, 'taskNumber', scope.row.no)
      this.$set(this.formAdd, 'customerNo', scope.row.customerNo)
      if (scope.row.isProduct === '成品') {
        this.$set(this.formAdd, 'isProduct', true)
      }
      this.warehousingDis = false
    },
    // 取消
    purAddNo(purForm) {
      this.purAddVisible = false
      this.addTableData = []
      this.$refs[purForm].resetFields()
    },
    // 保存
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
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
