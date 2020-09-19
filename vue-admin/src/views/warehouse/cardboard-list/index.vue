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
          @current-change="handleCurrentChange"
        >
          <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="supplierName" label="供应商名称" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="carryTo" label="过账" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="购入数量" width="140" />
          <el-table-column v-show="true" prop="warehousingDate" label="入仓日期" width="140" />
          <el-table-column v-show="true" prop="position" label="仓位" width="140" />

          <!-- <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link>
              <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link>
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
      <!-- 新增/编辑纸板入仓单 -->
      <el-dialog :title="titleType+'纸板入仓单'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="80px">
          <el-form-item label="单据类型" prop="documentType">
            <el-select v-model="formAdd.documentType">
              <el-option label="出仓单" value="出仓单" />
              <el-option label="入仓单" value="入仓单" />
            </el-select>
          </el-form-item>
          <el-form-item label="入仓单号" prop="pricing">
            <el-input v-model="formAdd.documentsNo" disabled />
          </el-form-item>
          <el-form-item label="开单人">
            <el-select v-model="formAdd.drawer">
              <el-option
                v-for="item in drawerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
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

    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/cardboard/cardboard'
import { getById } from '@/api/supplier/supplier'

export default {
  name: 'CardboardList',
  mixins: [initData],
  data() {
    return {
      form: {
        time: ''
      },
      formAdd: { },
      tableData: [],
      addTableData: [],
      purAddVisible: false,
      purRules: {
        documentType: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricing: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      dropRow: {},
      drawerFor: []
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
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.tableData.forEach(a => {
          getById(a.supplierId).then(data => {
            a.supplierName = data.fullName
          })
        })
        this.pagination.total = res.total
      })
    },
    handleCurrentChange(row) {
      this.dropRow = row
    },
    // // 删除
    // drop() {
    //   this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.$message({
    //       type: 'success',
    //       message: '删除成功'
    //     })
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消删除'
    //     })
    //   })
    // },
    // // 编辑订单
    // modifyPur(row) {
    //   this.purAddVisible = true
    //   this.titleType = '编辑'
    // },
    // // 新增订单
    // purAdd() {
    //   this.purAddVisible = true
    //   this.titleType = '新增'
    //   // 新增初始化数据
    //   this.formAdd = {}
    // },
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
