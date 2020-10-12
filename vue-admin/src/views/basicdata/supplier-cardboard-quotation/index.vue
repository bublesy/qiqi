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
        <el-button type="success" size="mini" @click="wholePrinting(scope)">打印</el-button>
      </el-form>
      <el-table
        ref="singleTable"
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        border=""
        align="center"
      >
        <el-table-column type="index" />
        <el-table-column property="code" label="编码" />
        <el-table-column property="abbreviation" label="简称" />
        <el-table-column property="fullName" label="全称" />
        <el-table-column property="quotationUnit" label="报价单位" />
        <el-table-column property="name" label="纸板名称" />
        <el-table-column property="ridgeType" label="楞型" />
        <el-table-column property="cardboardQuotation" label="纸板报价" />
        <el-table-column property="preferentialSetting" label="优惠设定" />
        <el-table-column property="totalPrice" label="总价" />
        <el-table-column label="操作" width="500px">
          <template slot-scope="scope">
            <el-button type="primary" size="small" :disabled="scope.row.name!==null ?true : false" @click="supCarQuoAdd(scope)">新增纸板报价单</el-button>
            <el-button type="primary" size="small" :disabled="scope.row.name!==null ?false : true" @click="modifyPur(scope)">编辑</el-button>
            <el-button type="danger" size="small" @click="drop(scope)">删除</el-button>
            <el-button type="success" size="small" @click="printing(scope)">生成打印单</el-button>
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

        <el-form-item label="纸板资料" prop="pagerId">
          <el-select v-model="formAdd.pagerId" size="mini">
            <el-option
              v-for="item in pagerFor"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="报价单位" prop="quotationUnit">
          <el-input v-model="formAdd.quotationUnit" />
        </el-form-item>

        <el-form-item label="纸板报价" prop="cardboardQuotation">
          <el-input-number v-model="formAdd.cardboardQuotation" :controls="false" @change="cardboardChange" />
        </el-form-item>

        <el-form-item label="优惠设定" prop="preferentialSetting">
          <el-input-number v-model="formAdd.preferentialSetting" :controls="false" @change="cardboardChange" />
        </el-form-item>

        <el-form-item label="总价" prop="totalPrice">
          <el-input v-model="formAdd.totalPrice" disabled />
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="supplierAddNo('supForm')">取 消</el-button>
        <el-button size="small" type="primary" @click="suppCarQuoAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import pinyin from 'js-pinyin'
// import { export2Excel } from '@/utils/common'
import { add } from '@/api/supplier-cardboard-quotation/cardboard'
import { list } from '@/api/supplier-cardboard-quotation/cardboard'
import { removeById } from '@/api/supplier-cardboard-quotation/cardboard'
import { getById } from '@/api/supplier-cardboard-quotation/cardboard'
import { pagerSelect } from '@/api/supplier-cardboard-quotation/cardboard'

export default {
  name: 'SupplierCardboardQuotation',
  mixins: [initData],

  data() {
    return {
      tableData: [],
      suppCarQuoAddVisible: false,
      formAdd: {
        code: '', supplierId: ''
      },
      titleType: '',
      supRules: {
        pagerId: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        quotationUnit: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        cardboardQuotation: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      form: {
        code: '',
        abbreviation: '',
        time: ''
      },
      pagerFor: [],
      multipleSelection: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    wholePrinting() {
      this.$router.push({
        path: '/supplier_cardboard_printing',
        query: { 'data': this.tableData }
      })
    },
    // 打印
    printing(scope) {
      console.log(scope.row)
      this.multipleSelection.push(scope.row)
      this.$router.push({
        path: '/supplier_cardboard_printing',
        query: { 'data': this.multipleSelection }
      })
    },
    cardboardChange() {
      this.formAdd.totalPrice = this.formAdd.cardboardQuotation * this.formAdd.preferentialSetting
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
      getById(scope.row.id).then(res => {
        // 加载下拉框
        pagerSelect().then(res => {
          this.pagerFor = res
        })
        this.formAdd = res
      })
    },
    // 新增供应商
    supCarQuoAdd(scope) {
      this.suppCarQuoAddVisible = true
      this.titleType = '新增'
      this.formAdd = {}
      this.formAdd.id = scope.row.id
      pagerSelect().then(res => {
        this.pagerFor = res
      })
    },
    // 新增编辑/供应商保存
    suppCarQuoAddOk(supForm) {
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
          this.suppCarQuoAddVisible = false
        } else {
          return false
        }
      })
    },
    // 新增供应商取消
    supplierAddNo(supForm) {
      this.suppCarQuoAddVisible = false
      this.formAdd = {}
      this.$refs[supForm].resetFields()
    }
  }
}
</script>
