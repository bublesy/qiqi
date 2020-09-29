<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增用户' : '编辑用户'" width="570px">
    <el-form ref="form" :inline="true" :rules="supRules" :model="form" size="small" label-width="66px">
      <el-form-item label="账号" prop="username">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" />
      </el-form-item>
      <el-form-item v-if="passwordShow" label="密码" prop="password">
        <el-input v-model="form.password" show-password />
      </el-form-item>
      <el-form-item style="margin-bottom: 0px;" label="角色" prop="roleIds">
        <el-select v-model="form.roleIds" style="width: 450px;" multiple placeholder="请选择">
          <el-option
            v-for="(item, index) in roles"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addUser, editUser } from '@/api/qiqi-dev/user'
import { getRoleSelector } from '@/api/qiqi-dev/role'
export default {
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
      roles: [],
      form: { id: '', username: '', nickname: '', roleIds: [] },
      supRules: {
        username: [{ required: true, message: '请输入账号', trigger: 'blur' }, { min: 4, message: '长度至少4位数', trigger: 'blur' }],
        nickname: [{ required: true, message: '昵称不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, message: '长度至少6位数', trigger: 'blur' }],
        roleIds: [{ required: true, message: '角色不能为空', trigger: 'change' }]
      },
      passwordShow: true
    }
  },
  mounted() {
    this.getRoleSelector()
  },
  methods: {
    getRoleSelector() {
      getRoleSelector().then(data => {
        this.roles = data
      })
    },
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
      addUser(this.form).then(res => {
        this.resetForm()
        this.$notify({ title: '添加成功', type: 'success', duration: 2500 })
        this.$parent.init()
      }).finally(() => {
        this.loading = false
      })
    },
    doEdit() {
      editUser(this.form).then(res => {
        this.resetForm()
        this.$notify({ title: '修改成功', type: 'success', duration: 2500 })
        this.$parent.init()
      }).finally(() => {
        this.loading = false
      })
    },
    resetForm() {
      this.dialog = false
      this.form = { id: '', username: '', nickname: '', roleIds: [] }
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    }
  }
}
</script>
