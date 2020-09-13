<template>
  <el-container>
    <el-main>
      <h1 align="center">仓库管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="入仓单号:">
          <el-input v-model="form.documentsNo" />
        </el-form-item>
        <el-form-item label="开单人">
          <el-input v-model="form.drawer" />
        </el-form-item>
        <el-button type="primary" size="mini">查询</el-button>
        <el-button type="primary" size="mini" @click="purAdd">新增</el-button>

        <!-- <el-button type="danger" size="mini" @click="drop">删除</el-button> -->
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          @current-change="handleCurrentChange"
        >
          <el-table-column v-show="true" prop="documentsNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="carryTo" label="过账" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="购入数量" width="140" />
          <el-table-column v-show="true" prop="quantityNotEntered" label="入仓日期" width="140" />
          <el-table-column v-show="true" prop="position" label="仓位" width="140" />

          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link>
              <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link>
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
      <el-dialog :title="titleType+'纸板入仓单'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="80px">
          <el-form-item label="单据类型" prop="supplier">
            <el-select v-model="formAdd.supplier">
              <el-option
                v-for="item in supplierFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="入仓单号" prop="pricing">
            <el-input v-model="formAdd.documentsNo" disabled />
          </el-form-item>
          <el-form-item label="开单人">
            <el-input v-model="formAdd.drawer" />
          </el-form-item>
          <el-form-item label="开单日期" prop="billingTime">
            <el-date-picker
              v-model="formAdd.billingTime"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="过账">
            <el-select v-model="formAdd.carryTo">
              <el-option label="已过账" value="已过账" />
              <el-option label="未过账" value="未过账" />
            </el-select>
          </el-form-item>
          <el-form-item label="过账年月">
            <el-date-picker
              v-model="formAdd.carryToYears"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="入仓日期">
            <el-date-picker
              v-model="formAdd.WarehousingDate"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="原始单号:">
            <el-input v-model="formAdd.oldDocumentsNo" />
          </el-form-item>

          <el-form-item label="材质">
            <el-input v-model="formAdd.material" />
          </el-form-item>

          <el-form-item label="纸长">
            <el-input v-model="formAdd.paperLength" />
          </el-form-item>

          <el-form-item label="纸宽">
            <el-input v-model="formAdd.paperWidth" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.unitPrice" />
          </el-form-item>

          <el-form-item label="购入数量">
            <el-input v-model="formAdd.purchaseQuantity" />
          </el-form-item>
          <el-form-item label="仓位">
            <el-input v-model="formAdd.position" />
          </el-form-item>
          <el-form-item label="备注:">
            <el-input v-model="formAdd.remark" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 任务编号(带客户去查客户底下成品的任务编号) -->
      <el-dialog title="请选择" :visible.sync="modifyTaskVisible" width="800px">
        <el-table
          :data="modifyTaskTable"
          stripe
          highlight-current-row
          style="width: 100%"
          @current-change="modifyTaskChange"
        >
          <el-table-column property="taskNumber" label="任务编号" />
          <el-table-column property="customerName" label="客户名称" />
          <el-table-column property="textureOfMaterial" label="材质" />
          <el-table-column property="paperLength" label="纸长" />
          <el-table-column property="paperWidth" label="纸宽" />
          <el-table-column property="orderQuantity" label="订单数量" />
          <el-table-column property="purchaseQuantity" label="购入数量" />
          <el-table-column property="quantityNotEntered" label="未进数量" />
          <el-table-column property="supplier" label="供方" />
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
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="modifyTaskVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="modifyTaskConfirm">确 定</el-button>
        </span>
      </el-dialog>

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
export default {
  name: 'CardboardList',
  mixins: [initData],
  data() {
    return {
      form: {},
      formAdd: { },
      tableData: [{
        documentsNo: '1',
        taskNumber: '1',
        customerName: '迪迦'
      }],
      addTableData: [],
      customerFor: [{
        id: '1',
        name: '迪迦'
      }],
      purAddVisible: false,
      purRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricing: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      supplierFor: [{
        id: '1',
        name: '腾讯'
      }, {
        id: '2',
        name: '阿里'
      }],
      pricingFor: [{
        id: '1',
        name: '净边'
      }, {
        id: '2',
        name: '净宽'
      }],
      titleType: '',
      taskNumberTable: [{
        taskNumber: '1',
        taskName: '就这?'
      }, {
        taskNumber: '2',
        taskName: '就这a ?'
      }],
      currentRowTow: {},
      modifyTaskVisible: false,
      modifyTaskTable: [{
        customerName: '张三',
        taskNumber: '2'
      }],
      dropRow: {},
      indexId: {}

    }
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (row.consignment) {
        return 'green-row'
      }
    },
    /**
     * 记忆选中
     */
    getRowKeys(row) {
      return row.id
    },
    handleCurrentChange(row) {
      this.dropRow = row
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
    delRule(val) {
      if (this.addTableData.length !== 1) {
        this.addTableData.splice(val.$index, 1)
        return
      }
      if (this.addTableData.length === 1) {
        var a = {
          id: null,
          less: 0,
          thanEqual: 0,
          discount: 0
        }
        this.addTableData = []
        this.addTableData.push(a)
      }
    },
    // 确定客户成品信息 回调
    modifyTaskConfirm() {
      this.$set(this.addTableData, this.indexId, this.currentRowTow)
      this.modifyTaskVisible = false
    },
    /**
     * 选中数据改变事件
     */
    modifyTaskChange(val) {
      this.currentRowTow = val
    },

    // 点击任务编号弹出弹窗
    modifyTask(scope) {
      this.indexId = scope.$index
      this.modifyTaskVisible = true
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
  .el-dialog__header {
    border: 0;
  }
</style>
