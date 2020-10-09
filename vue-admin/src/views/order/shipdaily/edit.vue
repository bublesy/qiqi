<template>
  <el-dialog
    title="编辑回签状态"
    :visible.sync="dialog.show"
    width="20%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true">
      <el-form-item label="是否回签:">
        <el-checkbox v-model="form.sign" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { updateSign } from '@/api/order/shipdaily'
export default {
  props: {
    dialog: {
      type: Object,
      default: () => {}
    },
    row: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      form: {
        sign: false
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        // getSingleOrder(this.id).then(res => {
        //   this.form = res
        // })
        this.form.sign = (this.row.sign === '已回签')
      }
    }
  },
  methods: {
    sure() {
      console.log(this.row)
      this.form.id = this.row.id
      updateSign(this.form).then(res => {
        if (res) {
          this.$message.success('回签成功')
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
