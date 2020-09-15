<template>
  <el-container>
    <el-main>
      <h1 align="center">纸板库存管理</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="query">查询</el-button>
        <el-button type="primary" size="mini" @click="stockAdd">新增</el-button>

        <!-- <el-button type="danger" size="mini" @click="drop">删除</el-button> -->
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

export default {
  name: 'CardboardInventory',
  mixins: [initData],

  data() {
    return {
      stockTable: [],
      stockAddVisible: false,
      formAdd: {},
      titleType: '',
      form: {},
      supRules: {}
    }
  },

  methods: {
    stockAdd() {
      this.stockAddVisible = true
    },
    stockAddNo() {
      this.stockAddVisible = false
      this.formAdd = {}
    },
    stockAddOk() {
      this.stockAddVisible = false
    },
    query() {}
  }
}
</script>
