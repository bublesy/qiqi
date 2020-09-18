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
        <el-button type="primary" size="mini" @click="purAdd">新增</el-button>
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
          <el-table-column v-show="true" prop="documentsNo" label="采购单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="ridgeType" label="楞型" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="parPreSpe" label="分压规格" width="140" />
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" width="140" />
          <el-table-column v-show="true" prop="batching" label="配料面积" width="140" />
          <el-table-column v-show="true" prop="squarePrice" label="平方价" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="amount" label="金额" width="140" />
          <el-table-column v-show="true" prop="unit" label="单位" width="140" />
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-link type="danger" size="small" @click="drop(scope)">删除</el-link>
              <el-link type="primary" size="small" @click="modifyPur(scope)">编辑</el-link>
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
          <el-form-item label="交货日期" prop="deliveryDate">
            <el-date-picker
              v-model="formAdd.deliveryDate"
              align="right"
               value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
              disabled
            />
          </el-form-item>

          <el-form-item label="客户名称" prop="customerName">
            <el-select v-model="formAdd.customerName" size="mini" @change="customerSelect">
              <el-option
                v-for="item in customerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="任务编号">
            <el-input v-model="formAdd.taskNumber" disabled />
          </el-form-item>

          <el-form-item label="楞型">
            <el-input v-model="formAdd.ridgeType" disabled />
          </el-form-item>

          <el-form-item label="分压规格">
            <el-input v-model="formAdd.parPreSpe" disabled/>
          </el-form-item>

          <el-form-item label="材质">
            <el-input v-model="formAdd.material" disabled/>
          </el-form-item>

          <el-form-item label="纸长">
            <el-input v-model="formAdd.paperLength" disabled />
          </el-form-item>

          <el-form-item label="纸宽">
            <el-input v-model="formAdd.paperWidth"  disabled/>
          </el-form-item>

          <el-form-item label="订单数量">
            <el-input v-model="formAdd.orderQuantity" disabled/>
          </el-form-item>

          <el-form-item label="采购数量">
            <el-input v-model="formAdd.purchaseQuantity" />
          </el-form-item>

          <el-form-item label="配料面积">
            <el-input v-model="formAdd.batching" disabled/>
          </el-form-item>

          <el-form-item label="平方价">
            <el-input v-model="formAdd.squarePrice" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.unitPrice" disabled/>
          </el-form-item>

          <el-form-item label="金额">
            <el-input-number v-model="formAdd.amount" :controls="false" disabled/>
          </el-form-item>

          <el-form-item label="单位">
            <el-input v-model="formAdd.unit" disabled/>
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
import { customerSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { add } from '@/api/purchase/purchase'
import { list } from '@/api/purchase/purchase'
import { getById } from '@/api/purchase/purchase'
import { removeById } from '@/api/purchase/purchase'

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
        customerName:"",
        quantityOverdue:"",
        time:""
      }
    }
  },
  created(){
    this.init()
  },
  methods: {
    //选完客户名称 回掉信息
    customerSelect(){
        this.customerFor.forEach(a => {
          console.log("a",this.formAdd.customerName);
          console.log(a.id );
        if (a.id === this.formAdd.customerName) {
          this.formAdd.deliveryDate = a.deliveryDate
            this.formAdd.taskNumber = a.no
               this.formAdd.ridgeType = a.stare
               this.formAdd.parPreSpe = a.partialPressure
               this.formAdd.material = a.material
               this.formAdd.batching = a.paperArea
               this.formAdd.paperLength = a.paperLength
               this.formAdd.paperWidth = a.paperWidth
               this.formAdd.orderQuantity = a.orderNum
               this.formAdd.unitPrice = a.perPrice
               this.formAdd.unit = a.unit
        }
      })
    },
    loadData(){
      this.queryParams.customerName = this.form.customerName
      this.queryParams.quantityOverdue = this.form.quantityOverdue
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      console.log(this.queryParams);
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total
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
        // this.$router.push('/purchase_not_included_overdue')
        console.log(this.multipleSelection);
         this.$router.push({
         path: '/purchase_not_included_overdue', 
         query: { 'ids': this.multipleSelection}
        })
      } else if (this.form.quantityOverdue === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
            // this.multipleSelection.forEach(a=>{
            //   this.ids.push(a.id)
            // })
          this.$router.push({
          path: '/purchase_order_printing', 
           query: { 'data': this.multipleSelection}
        })
        }
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.quantityOverdue === '已过期') {
        this.$router.push('/purchase_not_included_overdue')
      } else if (this.form.quantityOverdue === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        this.$router.push('/purchase_order_printing')
      }
    },
    // 打印
    printing(scope) {
       if (this.form.quantityOverdue === '已过期') {
            this.$router.push({
            path: '/purchase_not_included_overdue', 
            query: { 'ids': scope.row.id }
        })
      } else if (this.form.quantityOverdue === '未过期') {
            this.$router.push({
            path: '/purchase_not_included', 
            query: { 'ids': scope.row.id }
        })
      } else {
        console.log("a",scope.row);
            this.$router.push({
            path: '/purchase_order_printing', 
            query: { 'data': scope.row}
            })
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 删除
    drop(scope) {
      removeById(scope.row.id).then(res => {
        if (res) {
          this.$message.success('删除成功')
          this.loadData()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    // 编辑订单
    modifyPur(scope) {
      this.purAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
      })
        this.formAdd = res
      })
    },
    // 新增订单
    purAdd() {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
      })
      //加载客户名称下拉框
      customerSelect().then(res=>{
        this.customerFor=res
        console.log(res);
      })
    },
    // 取消
    purAddNo(purForm) {
      this.purAddVisible = false
      this.addTableData = []
      this.$refs[purForm].resetFields()
    },
    //保存
    purAddOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          console.log(this.formAdd);
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
