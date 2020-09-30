<template>
  <el-container>
    <el-main>
      <h1 align="center">纸板库存管理</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="stockAdd">新增</el-button>

      </el-form>
      <el-table
        ref="singleTable"
        :data="stockTable"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column property="material" label="材质" width="120" />
        <el-table-column property="paperLength" label="纸长" width="120" />
        <el-table-column property="paperWidth" label="纸宽" width="120" />
        <el-table-column property="stock" label="库存" width="120" />
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
    <!-- 新增/编辑供应商纸板报价 -->
    <el-dialog :title="titleType+'纸板库存'" :visible.sync="stockAddVisible">
      <el-form ref="supForm" :rules="supRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
        <el-form-item label="材质" prop="material">
          <el-input v-model="formAdd.material" />
        </el-form-item>

        <el-form-item label="纸长" prop="paperLength">
          <el-input v-model="formAdd.paperLength" />
        </el-form-item>

        <el-form-item label="纸宽" prop="paperWidth">
          <el-input v-model="formAdd.paperWidth" />
        </el-form-item>

        <el-form-item label="库存" prop="stock">
          <el-input v-model="formAdd.stock" />
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="stockAddNo">取 消</el-button>
        <el-button size="small" type="primary" @click="stockAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import { add } from '@/api/cardboard-inventory/cardboardInventory'
import { list } from '@/api/cardboard-inventory/cardboardInventory'
import { getById } from '@/api/cardboard-inventory/cardboardInventory'
import { removeById } from '@/api/cardboard-inventory/cardboardInventory'

export default {
  name: 'CardboardInventory',
  mixins: [initData],

  data() {
    return {
      stockTable: [],
      stockAddVisible: false,
      formAdd: {},
      titleType: '',
      form: {
        time: ''
      },
      supRules: {}
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
      console.log(this.queryParams)
      list(this.queryParams).then(res => {
        this.stockTable = res.list
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
    // 编辑订单
    modifyPur(scope) {
      this.stockAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    stockAdd() {
      this.stockAddVisible = true
      this.titleType = '新增'
      this.formAdd = {}
    },
    stockAddNo() {
      this.stockAddVisible = false
      this.formAdd = {}
    },
    stockAddOk() {
      this.stockAddVisible = false
      add(this.formAdd).then(res => {
        if (res) {
          this.$message.success(this.titleType + '成功')
          this.loadData()
        } else {
          this.$message.error(this.titleType + '失败')
        }
      })
    },
    query() {}
  }
}
</script>
