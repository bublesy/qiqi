<template>
  <el-container>
    <el-main>
      <h1 align="center">供应商对账明细管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-date-picker
          v-model="form.time"
          type="month"
          placeholder="选择月"
          value-format="yyyy-MM"
        />
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
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
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="inProductDate" label="入仓日期" width="140" />
          <el-table-column v-show="true" prop="documentsNo" label="采购序号" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="购入数量" width="140" />
          <el-table-column v-show="true" prop="returnNum" label="退货数量" width="140" />
          <el-table-column v-show="true" prop="returnAmount" label="退货金额" width="140" />
          <el-table-column v-show="true" prop="costPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="totalAmount" label="总金额" width="140" />
          <el-table-column v-show="true" prop="creditDate" label="账款年月" width="143" />
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
      <!-- 新增/编辑对账明细单 -->
      <el-dialog :title="titleType+'供应商对账明细表'" :visible.sync="purAddVisible" :close-on-click-modal="false">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
          <el-form-item label="任务编号" prop="supplier">
            <el-input v-model="formAdd.customerName" disabled>/>
            </el-input>
          </el-form-item>
          <el-form-item label="入厂日期">
            <el-input v-model="formAdd.ridgeType" disabled />
          </el-form-item>
          <el-form-item label="采购序号">
            <el-input v-model="formAdd.ridgeType" />
          </el-form-item>
          <el-form-item label="材质">
            <el-input v-model="formAdd.ridgeType" />
          </el-form-item>

          <el-form-item label="纸长">
            <el-input v-model="formAdd.ridgeType" />
          </el-form-item>

          <el-form-item label="纸宽">
            <el-input v-model="formAdd.parPreSpe" />
          </el-form-item>

          <el-form-item label="购入数量">
            <el-input v-model="formAdd.material" />
          </el-form-item>

          <el-form-item label="退货数量">
            <el-input v-model="formAdd.paperLength" />
          </el-form-item>

          <el-form-item label="退货金额">
            <el-input v-model="formAdd.paperWidth" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.orderQuantity" />
          </el-form-item>
          <el-form-item label="账款年月">
            <el-input v-model="formAdd.orderQuantity" />
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
import { purList } from '@/api/purchase/purchase'

export default {
  name: 'Verify',
  mixins: [initData],
  data() {
    return {
      // 选择客户
      customer: [],
      formAdd: '',
      // 表单数据
      tableData: [],
      purAddVisible: false,
      purRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricing: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      form: {
        time: ''
      }

    }
  },
  created() {
    this.init()
  },
  methods: {
    loadData() {
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      purList(this.queryParams).then(res => {
        this.tableData = res.list
        this.tableData.forEach(a => {
          a.returnAmount = a.returnNum * a.costPrice
          a.totalAmount = a.purchaseQuantity * a.costPrice - a.returnAmount
        })
        this.pagination.total = res.total
      })
    },
    // 打印
    printing() {
      this.$router.push('/finance/supplier_printing')
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 编辑订单
    modifyPur(row) {
      this.purAddVisible = true
      this.titleType = '编辑'
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
