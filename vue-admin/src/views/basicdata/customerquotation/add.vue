<template>
  <el-dialog
    title="客户资料"
    :visible.sync="dialog.show"
    width="400px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="名称:" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="限定最大纸长:" label-width="180" style="margin-left:37px">
        <el-checkbox v-model="form.limitPaperLength" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateBoxClass } from '@/api/basedata/boxclass'
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
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
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
          addOrUpdateBoxClass(data).then(res => {

          })
        }
      })
    }
  }
}
</script>

<style>

</style>
