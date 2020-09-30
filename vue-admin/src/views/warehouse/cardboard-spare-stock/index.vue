<template>
  <el-container>
    <el-main>
      <h1 align="center">仓库材料备用库存</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="材质:">
          <el-input v-model="form.material" />
        </el-form-item>
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="stockSpareAdd">新增</el-button>
      </el-form>
      <el-table
        ref="singleTable"
        :data="stockSpareTable"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column property="supplierName" label="供方" width="120" />
        <el-table-column property="materialId" label="材质" width="120" />
        <el-table-column property="ridgeTypeId" label="楞型" width="120" />
        <el-table-column property="specification" label="分压规格" width="120" />
        <el-table-column property="paperLength" label="纸长" width="120" />
        <el-table-column property="paperWidth" label="纸宽" width="120" />
        <el-table-column property="purchaseQuantity" label="购入数量" width="120" />
        <el-table-column property="unitPrice" label="单价" width="120" />
        <el-table-column property="remark" label="备注" width="120" />
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="danger" size="small" @click="drop(scope)">删除</el-button>
            <el-button type="primary" size="small" @click="modifyPur(scope)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <el-pagination
        style="margin-top: 10px"
        :total="pagination.total"
        :current-page="pagination.page"
        align="center"
        layout="total, prev, pager, next, sizes"
        @size-change="pagination.size"
        @current-change="pageChange"
      />
    </el-main>

    <!-- 新增/编辑纸板材料备用库存 -->
    <el-dialog :title="titleType+'纸板材料备用库存'" :visible.sync="stockSpareAddVisible">
      <el-form ref="stockSpareForm" :rules="stockRules" :inline="true" :model="formAdd" size="mini" label-width="80px">
        <el-form-item label="供方:" prop="supplierId">
          <el-select v-model="formAdd.supplierId">
            <el-option
              v-for="item in supplierFor"
              :key="item.id"
              :label="item.fullName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="材质:" prop="materialId">
          <el-select v-model="formAdd.materialId">
            <el-option
              v-for="item in materialFor"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楞型:" prop="ridgeTypeId">
          <el-select v-model="formAdd.ridgeTypeId">
            <el-option
              v-for="item in ridgeTypeFor"
              :key="item.id"
              :label="item.ridgeType"
              :value="item.ridgeType"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="分压规格:" prop="specification">
          <el-input v-model="formAdd.specification" />
        </el-form-item>
        <el-form-item label="纸长:" prop="paperLength">
          <el-input v-model="formAdd.paperLength" />
        </el-form-item>
        <el-form-item label="纸宽:" prop="paperWidth">
          <el-input v-model="formAdd.paperWidth" />
        </el-form-item>
        <el-form-item label="购入数量:" prop="purchaseQuantity">
          <el-input v-model="formAdd.purchaseQuantity" />
        </el-form-item>
        <el-form-item label="单价:" prop="unitPrice">
          <el-input v-model="formAdd.unitPrice" />
        </el-form-item>

        <el-form-item label="备注:" prop="remark">
          <el-input v-model="formAdd.remark" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="stockSpareAddNo">取 消</el-button>
        <el-button size="small" type="primary" @click="stockSpareAddOk('stockSpareForm')">确 定</el-button>
      </span>
    </el-dialog>

  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import { supplierSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { materialSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { ridgeTypeSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { add } from '@/api/material-spare/material'
import { list } from '@/api/material-spare/material'
import { getById } from '@/api/material-spare/material'
import { getSupplierById } from '@/api/supplier/supplier'
import { removeById } from '@/api/material-spare/material'

export default {
  name: 'CardboardInventory',
  mixins: [initData],

  data() {
    return {
      stockSpareTable: [],
      form: {
        material: '',
        time: ''
      },
      stockSpareAddVisible: false,
      formAdd: {},
      addTableData: [],
      titleType: '',
      stockRules: {
        supplierId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        materialId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        ridgeTypeId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        specification: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        purchaseQuantity: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        unitPrice: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      supplierFor: [],
      materialFor: [],
      ridgeTypeFor: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    loadData() {
      this.queryParams.material = this.form.material
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        this.stockSpareTable = res.list
        this.stockSpareTable.forEach(a => {
          getSupplierById(a.supplierId).then(data => {
            // a.customerName = data.name
            this.$set(a, 'supplierName', data.fullName)
          })
        })
        this.pagination.total = res.total
      })
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
    // 新增保存
    stockSpareAddOk(stockSpareForm) {
      this.$refs[stockSpareForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.loadData()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.stockSpareAddVisible = false
        } else {
          return false
        }
      })
    },
    // 编辑订单
    modifyPur(scope) {
      this.stockSpareAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        // 加载供应商下拉框
        supplierSelect().then(res => {
          this.supplierFor = res
        })
        // 加载材质
        materialSelect().then(res => {
          this.materialFor = res
        })
        // 加载楞型
        ridgeTypeSelect().then(res => {
          this.ridgeTypeFor = res
        })
        this.formAdd = res
      })
    },
    // 库存备用材料增加
    stockSpareAdd() {
      this.stockSpareAddVisible = true
      this.titleType = '新增'
      this.formAdd = {}
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
      })
      // 加载材质
      materialSelect().then(res => {
        this.materialFor = res
      })
      // 加载楞型
      ridgeTypeSelect().then(res => {
        this.ridgeTypeFor = res
      })
    },
    // 增加取消
    stockSpareAddNo() {
      this.stockSpareAddVisible = false
      this.addTableData = []
    }
  }
}
</script>
