<template>
  <el-dialog
    title="结算"
    :visible.sync="dialog.show"
    width="15%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true">
      <el-form-item label="结算状态:">
        <el-select v-model="form.settlement" placeholder="选择结算状态" style="width:150px">
          <el-option value="未结算" label="未结算" />
          <el-option value="部分结算" label="部分结算" />
          <el-option value="已结算" label="已结算" />
        </el-select>
      </el-form-item>
      <el-form-item label="付款金额:">
        <el-input-number v-model="form.payed" :controls="false" :min="0" :precision="2" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { toSettle, getPost } from '@/api/accessories/means'
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
        settlement: '未结算'
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getPost(this.id).then(res => {
          this.form.settlement = res.settlement
        })
      }
    }
  },
  methods: {
    sure() {
      this.form.id = this.id
      console.log(this.form)
      toSettle(this.form).then(res => {
        if (res) {
          this.$message.success('结算成功')
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
