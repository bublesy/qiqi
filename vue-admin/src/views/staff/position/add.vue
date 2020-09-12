<template>
  <el-dialog
    title="职位管理"
    :visible.sync="dialog.show"
    width="400px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="职位名称:" prop="name" label-width="100px">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdatePosition } from '@/api/staff/position'
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
          addOrUpdatePosition(data).then(res => {

          })
        }
      })
    }
  }
}
</script>

<style>

</style>
