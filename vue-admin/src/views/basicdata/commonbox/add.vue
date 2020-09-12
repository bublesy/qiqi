<template>
  <el-dialog
    title="常用箱资料管理"
    :visible.sync="dialog.show"
    width="400px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="简称:" prop="name">
        <el-input v-model="form.as" />
      </el-form-item>
      <el-form-item label="全称:">
        <el-input v-model="form.fullName" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateCommonBox } from '@/api/basedata/commonbox'
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
        as: [
          { required: true, message: '请输入简称', trigger: 'blur' }
        ],
        fullName: [
          { required: true, message: '请输入全称', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id !== '' && this.id !== null) {
          // 编辑
          console.log('aa')
        } else {
          // 新增
          this.form = Object.assign({}, this.$options.data().form)
        }
      }
    }
  },
  methods: {
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          var data = {}
          addOrUpdateCommonBox(data).then(res => {

          })
        }
      })
    }
  }
}
</script>

<style>

</style>
