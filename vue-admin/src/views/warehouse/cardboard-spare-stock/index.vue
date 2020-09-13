<template>
  <el-container>
    <el-main>
      <h1 align="center">仓库材料备用库存</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="供方:">
          <el-input v-model="form.supplier" />
        </el-form-item>
        <el-form-item label="材质:">
          <el-input v-model="form.material" />
        </el-form-item>
        <el-button type="primary" size="mini">查询</el-button>
        <el-button type="primary" size="mini" @click="stockSpareAdd">新增</el-button>
        <!-- <el-button type="danger" size="mini" @click="drop">删除</el-button> -->
      </el-form>
      <el-table
        ref="singleTable"
        :data="stockSpareTable"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column property="supplier" label="供方" width="120" />
        <el-table-column property="material" label="材质" width="120" />
        <el-table-column property="ridgeType" label="楞型" width="120" />
        <el-table-column property="parPreSpe" label="分压规格" width="120" />
        <el-table-column property="paperLength" label="纸长" width="120" />
        <el-table-column property="paperWidth" label="纸宽" width="120" />
        <el-table-column property="purQuantity" label="购入数量" width="120" />
        <el-table-column property="unitPrice" label="单价" width="120" />
        <el-table-column property="remark" label="备注" width="120" />
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link>
            <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link>
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
        <el-form-item label="供方:" prop="supplier">
          <el-select v-model="formAdd.supplier">
            <el-option
              v-for="item in supplierFor"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="材质:" prop="material">
          <el-input v-model="formAdd.material" />
        </el-form-item>
        <el-form-item label="楞型:" prop="ridgeType">
          <el-input v-model="formAdd.ridgeType" />
        </el-form-item>
        <el-form-item label="分压规格:" prop="parPreSpe">
          <el-input v-model="formAdd.parPreSpe" />
        </el-form-item>
        <el-form-item label="纸长:" prop="paperLength">
          <el-input v-model="formAdd.paperLength" />
        </el-form-item>
        <el-form-item label="纸宽:" prop="paperWidth">
          <el-input v-model="formAdd.paperWidth" />
        </el-form-item>
        <el-form-item label="购入数量:" prop="purQuantity">
          <el-input v-model="formAdd.purQuantity" />
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

export default {
  name: 'CardboardInventory',
  mixins: [initData],

  data() {
    return {
      stockSpareTable: [],
      form: {},
      stockSpareAddVisible: false,
      formAdd: {},
      addTableData: [],
      titleType: '',
      stockRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        material: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        ridgeType: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        parPreSpe: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        purQuantity: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        unitPrice: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      supplierFor: []
    }
  },

  methods: {
    // 新增保存
    stockSpareAddOk(stockSpareForm) {
      this.$refs[stockSpareForm].validate((valid) => {
        if (valid) {
          this.stockSpareAddVisible = false
        } else {
          return false
        }
      })
    },
    // 删除
    drop() {
      if (this.dropRow.id == null) {
        this.$message.error('请选择一条进行操作')
      } else {
        this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
    },
    // 编辑订单
    modifyPur() {
      this.purAddVisible = true
      this.titleType = '编辑'
    },
    // 库存备用材料增加
    stockSpareAdd() {
      this.stockSpareAddVisible = true
      this.titleType = '新增'
    },
    // 增加取消
    stockSpareAddNo() {
      this.stockSpareAddVisible = false
      this.addTableData = []
    }
  }
}
</script>
