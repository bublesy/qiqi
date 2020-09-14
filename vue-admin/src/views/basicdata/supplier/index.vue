<template>
  <el-container>
    <el-main>
      <h1 align="center">供应商管理</h1>
      <el-button type="primary" size="mini" @click="supplierAdd">新增</el-button>
      <el-table
        ref="singleTable"
        :data="stockTable"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="code" label="编码" width="120" />
        <el-table-column property="abbreviation" label="简称" width="120" />
        <el-table-column property="fullName" label="全称" width="120" />
        <el-table-column property="telephone" label="电话" width="120" />
        <el-table-column property="mobilePhone" label="手机" width="120" />
        <el-table-column property="fax" label="传真" width="120" />
        <el-table-column property="contacts" label="联系人" width="120" />
        <el-table-column property="website" label="网址" width="120" />
        <el-table-column property="address" label="地址" width="120" />
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
    <!-- 新增/编辑供应商 -->
    <el-dialog :title="titleType+'供应商'" :visible.sync="supplierAddVisible">
      <el-form ref="supForm" :rules="supRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
        <el-form-item label="编码" prop="code">
          <el-input v-model="formAdd.code" />
        </el-form-item>

        <el-form-item label="简称" prop="abbreviation">
          <el-input v-model="formAdd.abbreviation" @change="abbrevChange" />
        </el-form-item>

        <el-form-item label="全称" prop="fullName">
          <el-input v-model="formAdd.fullName" />
        </el-form-item>

        <el-form-item label="电话" prop="telephone">
          <el-input v-model="formAdd.telephone" />
        </el-form-item>

        <el-form-item label="手机" prop="mobilePhone">
          <el-input v-model="formAdd.mobilePhone" />
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
        <el-button size="small" @click="supplierAddNo">取 消</el-button>
        <el-button size="small" type="primary" @click="supplierAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import pinyin from 'js-pinyin'
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
      stockTable: [],
      supplierAddVisible: false,
      formAdd: { code: '' },
      titleType: '',
      supRules: {
        mobilePhone: [{ validator: checkPhone, trigger: 'change' }],
        abbreviation: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        fullName: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        telephone: [{ required: true, message: '该输入为必填项', trigger: 'change' }]

      }
    }
  },

  methods: {
    // 自动生成编码
    abbrevChange() {
      this.formAdd.code = pinyin.getCamelChars(this.formAdd.abbreviation)
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
