<template>
  <el-container>
    <el-main>
      <h1 align="center">纸板资料设定管理</h1>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true" align="center">
        <el-form-item label="编码:">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button size="mini" type="primary" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="pagerAdd">新增</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
      </el-form>
      <el-table
        ref="singleTable"
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column type="index" />
        <el-table-column property="code" label="编码" />
        <el-table-column property="name" label="名称" />
        <el-table-column property="ridgeType" label="楞型" />
        <el-table-column property="represent" label="描述" />
        <el-table-column label="操作">
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
    <!-- 新增/编辑纸板资料 -->
    <el-dialog :title="titleType+'纸板资料'" :visible.sync="pagerAddVisible">
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

        <el-form-item label="描述" prop="represent">
          <el-input v-model="formAdd.represent" />
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="supplierAddNo('supForm')">取 消</el-button>
        <el-button size="small" type="primary" @click="supplierAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import pinyin from 'js-pinyin'
import { export2Excel } from '@/utils/common'
import { add } from '@/api/pager-setting/pager'
import { list } from '@/api/pager-setting/pager'
import { removeById } from '@/api/pager-setting/pager'
import { getById } from '@/api/pager-setting/pager'

export default {
  name: 'PaperboardDataSetting',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      pagerAddVisible: false,
      formAdd: { code: '' },
      titleType: '',
      supRules: {
        name: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      form: {
        code: '',
        name: '',
        time: ''
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    loadData() {
      this.queryParams.code = this.form.code
      this.queryParams.name = this.form.name
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total
      })
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
    // 编辑供应商
    modifyPur(scope) {
      console.log('id', scope)
      console.log(scope.$index)
      this.pagerAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    // 新增供应商
    pagerAdd() {
      this.formAdd = {}
      this.pagerAddVisible = true
      this.titleType = '新增'
    },
    // 新增纸板资料保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.$refs[supForm].resetFields()
              this.loadData()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.pagerAddVisible = false
        } else {
          return false
        }
      })
    },
    // 新增供应商取消
    supplierAddNo(supForm) {
      this.pagerAddVisible = false
      this.formAdd = {}
      this.$refs[supForm].resetFields()
    }
  }
}
</script>
