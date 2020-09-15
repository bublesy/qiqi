<template>
  <el-container>
    <el-main>
      <h1 align="center">供应商纸板报价</h1>

      <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true" align="center">
        <el-form-item label="编码:">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="简称:">
          <el-input v-model="form.abbreviation" />
        </el-form-item>
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            value-format="yyyy-MM-dd"
            align="right"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>

        <el-button size="mini" type="primary" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="supCarQuoAdd">新增</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
      </el-form>
      <el-table
        ref="singleTable"
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="code" label="编码" width="120" />
        <el-table-column property="abbreviation" label="简称" width="120" />
        <el-table-column property="fullName" label="全称" width="120" />
        <el-table-column property="quotationUnit" label="报价单位" width="120" />
        <el-table-column property="cardboardQuotation" label="纸板报价" width="120" />
        <el-table-column property="preferentialSetting" label="优惠设定" width="120" />
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-link type="danger" size="small" @click="drop(scope)">删除</el-link>
            <el-link type="primary" size="small" @click="modifyPur(scope)">编辑</el-link>
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
    <el-dialog :title="titleType+'供应商纸板报价'" :visible.sync="suppCarQuoAddVisible">
      <el-form ref="supForm" :rules="supRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
        <el-form-item label="供应商名称" prop="supplierId">
          <el-select v-model="formAdd.supplierId" @change="supplierChange">
            <el-option
              v-for="item in supplierFor"
              :key="item.id"
              :label="item.fullName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="编码" prop="code">
          <el-input v-model="formAdd.code" disabled />
        </el-form-item>

        <el-form-item label="简称" prop="abbreviation">
          <el-input v-model="formAdd.abbreviation" disabled />
        </el-form-item>

        <el-form-item label="报价单位" prop="quotationUnit">
          <el-input v-model="formAdd.quotationUnit" />
        </el-form-item>

        <el-form-item label="纸板报价" prop="cardboardQuotation">
          <el-input-number v-model="formAdd.cardboardQuotation" :controls="false" />
        </el-form-item>

        <el-form-item label="优惠设定" prop="preferentialSetting">
          <el-input v-model="formAdd.preferentialSetting" />
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="supplierAddNo">取 消</el-button>
        <el-button size="small" type="primary" @click="suppCarQuoAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import pinyin from 'js-pinyin'
import { export2Excel } from '@/utils/common'
import { supplierSelect } from '@/api/supplier-cardboard-quotation/cardboard'
import { add } from '@/api/supplier-cardboard-quotation/cardboard'
import { list } from '@/api/supplier-cardboard-quotation/cardboard'
import { removeById } from '@/api/supplier-cardboard-quotation/cardboard'
import { getById } from '@/api/supplier-cardboard-quotation/cardboard'

export default {
  name: 'SupplierCardboardQuotation',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      suppCarQuoAddVisible: false,
      formAdd: { code: '' },
      titleType: '',
      supRules: {
        supplierId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        quotationUnit: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        cardboardQuotation: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      form: {
        code: '',
        abbreviation: '',
        time: ''
      },
      supplierFor: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 供应商改变 对应的编码和全称也改变
    supplierChange() {
      this.supplierFor.forEach(e => {
        if (e.id === this.formAdd.supplierId) {
          this.formAdd.code = e.code
          this.formAdd.abbreviation = e.abbreviation
        }
      })
    },
    loadData() {
      this.queryParams.code = this.form.code
      this.queryParams.abbreviation = this.form.abbreviation
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
      export2Excel(th, data, '供应商纸板报价')
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
      this.suppCarQuoAddVisible = true
      this.titleType = '编辑'
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
        this.supplierFor.forEach(e => {
          if (e.id !== null) {
            if (e.id === this.formAdd.supplierId) {
              this.$set(this.formAdd, 'code', e.code)
              this.$set(this.formAdd, 'abbreviation', e.abbreviation)
            }
          }
        })
      })
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    // 新增供应商
    supCarQuoAdd() {
      // 加载供应商下拉框
      supplierSelect().then(res => {
        this.supplierFor = res
      })
      this.suppCarQuoAddVisible = true
      this.titleType = '新增'
    },
    // 新增供应商保存
    suppCarQuoAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.loadData()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.suppCarQuoAddVisible = false
        } else {
          return false
        }
      })
    },
    // 新增供应商取消
    supplierAddNo() {
      this.suppCarQuoAddVisible = false
      this.formAdd = {}
    }
  }
}
</script>
