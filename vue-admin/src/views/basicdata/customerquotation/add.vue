<template>
  <el-dialog
    title="客户纸板报价管理"
    :visible.sync="dialog.show"
    width="20%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" @input="code" />
      </el-form-item>
      <el-form-item label="简称:" prop="shorts">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="全称:" prop="fullName">
        <el-input v-model="form.fullName" />
      </el-form-item>
      <el-form-item label="销售平方价:" prop="squaredPrice">
        <el-input-number v-model="form.squaredPrice" size="mini" :precision="2" :controls="false" :min="0" style="width:180px" />
      </el-form-item>
      <el-form-item label="箱型计价:" prop="boxPrice">
        <el-input-number v-model="form.boxPrice" size="mini" :precision="2" :controls="false" :min="0" style="width:180px" />
      </el-form-item>
    </el-form>
    <!-- <el-table
      ref="table"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    > -->
    <!-- <el-table-column type="selection" width="55" /> -->
    <!-- <el-table-column type="index" /> -->
    <!-- <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="name" label="名称" width="120" />
      <el-table-column prop="code" label="描述" width="120" />
      <el-table-column prop="squaredPrice" label="楞型" width="120" />
      <el-table-column prop="BoxPrice" label="平方价" width="120" />
      <el-table-column prop="BoxPrice" label="备注" width="120" />
    </el-table>
    <el-pagination
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    /> -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateCustomerQuotation, getSingleCustomerQuotation } from '@/api/basedata/customerquotation'
export default {
  props: {
    dialog: {
      type: Object,
      default: () => {}
    },
    id: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      form: {
      },
      rules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        shorts: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        fullName: [
          { required: true, message: '请输入全称', trigger: 'blur' }
        ],
        squaredPrice: [
          { required: true, message: '请输入平方价', trigger: 'blur' }
        ],
        boxPrice: [
          { required: true, message: '请输入箱型计价', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id !== '' && this.id !== null) {
          // 编辑
          getSingleCustomerQuotation(this.id).then(res => {
            this.form = res
          })
        } else {
          // 新增
          // this.form = Object.assign({}, this.$options.data().form)
          this.$refs.form.resetFields()
        }
      }
    }
  },
  methods: {
    code(x) {
      if (isNaN(x)) {
        this.form.code = this.form.code.substring(0, this.form.code.length - 1)
        if (isNaN(this.form.code)) {
          this.form.code = ''
        }
      }
    },
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          var data = this.form
          console.log(data)
          addOrUpdateCustomerQuotation(data).then(res => {
            if (res) {
              this.$message.success('保存成功')
            }
            this.$emit('init')
            this.dialog.show = false
          })
        }
      })
    }
  }
}
</script>

<style>

</style>
