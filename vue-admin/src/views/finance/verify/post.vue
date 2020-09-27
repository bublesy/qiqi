<template>
  <el-dialog
    title="编辑过账状态"
    :visible.sync="dialog.show"
    width="10%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true">
      <el-form-item label="是否过账:">
        <el-checkbox v-model="form.post" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getPost, toPost } from '@/api/accessories/means'
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
        post: false
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getPost(this.id).then(res => {
          var state = res.post
          if (state === '已过账') {
            this.form.post = true
          } else if (state === '未过账') {
            this.form.post = false
          } else {
            this.form.post = false
          }
        })
      }
    }
  },
  methods: {
    sure() {
      if (this.form.post === true) {
        this.form.post = '已过账'
      } else {
        this.form.post = '未过账'
      }
      console.log(this.form)
      this.form.id = this.id
      toPost(this.form).then(res => {
        if (res) {
          this.$message.success('过账成功')
          this.$emit('init')
          this.dialog.show = false
        }
      })
    }
  }
}
</script>

<style>

</style>
