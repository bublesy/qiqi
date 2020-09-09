<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增角色' : '编辑角色'" append-to-body width="520px">
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" :show-message="false" label-width="80px">
      <el-form-item label="角色名称" prop="name" required>
        <el-input v-model="form.name" style="width: 145px;" />
      </el-form-item>
      <el-form-item label="角色编码" prop="code" required>
        <el-input v-model="form.code" style="width: 145px;" />
      </el-form-item>
      <el-form-item label="描述信息" prop="remark">
        <el-input v-model="form.remark" style="width: 385px;" rows="5" type="textarea" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addRole, editRole } from '@/api/qiqi-dev/role'
export default {
  components: {},
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false,
      dialog: false,
      departments: null,
      form: { name: '', code: '', remark: '' },
      rules: {}
    }
  },
  mounted() {
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.isAdd) {
            this.doAdd()
          } else {
            this.doEdit()
          }
        } else {
          return false
        }
      })
    },
    doAdd() {
      addRole(this.form).then(res => {
        this.resetForm()
        this.$notify({ title: '添加成功', type: 'success', duration: 2500 })
        this.$parent.init()
      }).finally(() => {
        this.loading = false
      })
    },
    doEdit() {
      editRole(this.form).then(res => {
        this.resetForm()
        this.$notify({ title: '修改成功', type: 'success', duration: 2500 })
        this.$parent.init()
      }).finally(() => {
        this.loading = false
      })
    },
    resetForm() {
      this.dialog = false
      this.form = { name: '', code: '', remark: '' }
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    }
  }
}
</script>
