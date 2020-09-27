<template>
  <el-dialog
    title="结算"
    :visible.sync="dialog.show"
    width="25%"
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
      <el-form-item label="订单金额:">
        <el-input-number v-model="form.money" :controls="false" :min="0" :precision="2" />
      </el-form-item>
      <el-form-item label="期初金额:">
        <el-input-number v-model="form.beginReceive" :controls="false" :min="0" :precision="2" style="width:150px" />
      </el-form-item>
      <el-form-item label="已付金额:">
        <el-input-number v-model="form.payed" :controls="false" :min="0" :precision="2" />
      </el-form-item>
      <el-form-item label="待付款金额:">
        <el-input-number v-model="form.unPayed" :controls="false" :min="0" :precision="2" />
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
        settlement: '未结算',
        settlementDate: []
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getPost(this.id).then(res => {
          console.log(res)
          this.form.settlement = res.settlement
          this.form.money = res.money
          this.form.beginReceive = res.beginReceive
          this.form.payed = res.payed
          this.form.unPayed = this.form.money - this.form.beginReceive - this.form.payed
        })
      }
    }
  },
  methods: {
    sure() {
      this.form.id = this.id
      console.log(this.form)
      this.form.payed = this.form.payed + this.form.unPayed
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
