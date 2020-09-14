<template>
  <el-container>
    <el-main>
      <h1 align="center">纸板资料设定管理</h1>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
        <el-form-item label="编码:">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.name" />
        </el-form-item>
      </el-form>
      <el-button size="mini" type="primary" @click="query">查询</el-button>
      <el-button type="primary" size="mini" @click="supplierAdd">新增</el-button>
      <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
      <el-table
        ref="singleTable"
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="code" label="编码" width="120" />
        <el-table-column property="name" label="名称" width="120" />
        <el-table-column property="ridgeType" label="楞型" width="120" />
        <el-table-column property="describe" label="描述" width="120" />
        <el-table-column label="操作" width="180">
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
    <!-- 新增/编辑纸板资料 -->
    <el-dialog :title="titleType+'纸板资料'" :visible.sync="supplierAddVisible">
      <el-form ref="supForm" :rules="supRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
        <el-form-item label="编码" prop="code">
          <el-input v-model="formAdd.code" disabled />
        </el-form-item>

        <el-form-item label="名称" prop="name">
          <el-input v-model="formAdd.name" @change="abbrevChange" />
        </el-form-item>

        <el-form-item label="楞型" prop="ridgeType">
          <el-input v-model="formAdd.ridgeType" />
        </el-form-item>

        <el-form-item label="描述" prop="describe">
          <el-input v-model="formAdd.describe" />
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="supplierAddNo">取 消</el-button>
        <el-button size="small" type="primary" @click="supplierAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import pinyin from 'js-pinyin'
import { export2Excel } from '@/utils/common'
export default {
  name: 'PaperboardDataSetting',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      supplierAddVisible: false,
      formAdd: { code: '' },
      titleType: '',
      supRules: {
        name: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      form: {}
    }
  },

  methods: {
    query() {
    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['编码', '名称']
      const filterVal = ['code', 'name']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '纸板资料设定')
    },
    // 自动生成编码
    abbrevChange() {
      this.formAdd.code = pinyin.getCamelChars(this.formAdd.name)
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
    // 编辑供应商
    modifyPur(row) {
      this.supplierAddVisible = true
      this.titleType = '编辑'
    },
    // 新增供应商
    supplierAdd() {
      this.supplierAddVisible = true
      this.titleType = '新增'
    },
    // 新增供应商保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          this.supplierAddVisible = false
        } else {
          return false
        }
      })
    },
    // 新增供应商取消
    supplierAddNo() {
      this.supplierAddVisible = false
      this.formAdd = {}
    }
  }
}
</script>
