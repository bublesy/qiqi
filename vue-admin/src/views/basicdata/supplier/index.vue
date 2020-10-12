<template>
  <el-container>
    <el-main>
      <h1 align="center">供应商管理</h1>
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
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button size="mini" type="primary" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="supplierAdd">新增</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
      </el-form>
      <el-table
        ref="singleTable"
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        align="center"
        border
      >
        <el-table-column type="index" />
        <el-table-column property="code" label="编码" />
        <el-table-column property="abbreviation" label="简称" />
        <el-table-column property="fullName" label="全称" />
        <el-table-column property="telephone" label="电话" />
        <el-table-column property="mobilePhone" label="手机" />
        <el-table-column property="fax" label="传真" />
        <el-table-column property="contacts" label="联系人" />
        <el-table-column property="website" label="网址" />
        <el-table-column property="address" label="地址" />
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
    <!-- 新增/编辑供应商 -->
    <el-dialog :title="titleType+'供应商'" :visible.sync="supplierAddVisible">
      <el-form ref="supForm" :rules="supRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
        <el-form-item label="编码" prop="code">
          <el-input v-model="formAdd.code" disabled />
        </el-form-item>

        <el-form-item label="简称" prop="abbreviation">
          <el-input v-model="formAdd.abbreviation" @change="abbrevChange" />
        </el-form-item>

        <el-form-item label="全称" prop="fullName">
          <el-input v-model="formAdd.fullName" />
        </el-form-item>

        <el-form-item label="电话">
          <el-input-number v-model="formAdd.telephone" :controls="false" />
        </el-form-item>

        <el-form-item label="手机" prop="mobilePhone">
          <el-input-number v-model="formAdd.mobilePhone" :controls="false" />
        </el-form-item>

        <el-form-item label="传真">
          <el-input v-model="formAdd.fax" />
        </el-form-item>

        <el-form-item label="联系人">
          <el-input v-model="formAdd.contacts" />
        </el-form-item>

        <el-form-item label="网址">
          <el-input v-model="formAdd.website" />
        </el-form-item>

        <el-form-item label="地址">
          <el-input v-model="formAdd.address" />
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
import { add } from '@/api/supplier/supplier'
import { list } from '@/api/supplier/supplier'
import { removeById } from '@/api/supplier/supplier'
import { getById } from '@/api/supplier/supplier'

export default {
  name: 'Supplier',
  mixins: [initData],

  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'))
      } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        console.log(reg.test(value))
        if (reg.test(value)) {
          callback()
        } else {
          return callback(new Error('请输入正确的手机号'))
        }
      }
    }
    return {
      tableData: [],
      supplierAddVisible: false,
      formAdd: { code: '' },
      titleType: '',
      supRules: {
        mobilePhone: [{ validator: checkPhone, trigger: 'blur' }],
        abbreviation: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        fullName: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      form: {
        code: '',
        abbreviation: '',
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
      export2Excel(th, data, '供应商')
    },
    // 自动生成编码
    abbrevChange() {
      this.formAdd.code = pinyin.getCamelChars(this.formAdd.abbreviation)
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
      this.supplierAddVisible = true
      this.titleType = '编辑'
      this.formAdd.id = scope.row.id
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    // 新增供应商
    supplierAdd() {
      this.formAdd = {}
      this.supplierAddVisible = true
      this.titleType = '新增'
    },
    // 新增供应商保存
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
          this.supplierAddVisible = false
        } else {
          return false
        }
      })
    },
    // 新增供应商取消
    supplierAddNo(supForm) {
      this.supplierAddVisible = false
      this.formAdd = {}
      this.$refs[supForm].resetFields()
    }
  }
}
</script>
