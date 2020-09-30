<template>
  <el-dialog
    title="结算"
    :visible.sync="dialog.show"
    width="25%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :rules="supRules" :inline="true">
      <el-form-item label="结算状态:" prop="settlement">
        <el-select v-model="form.settlement" placeholder="选择结算状态" style="width:150px">
          <el-option value="未结算" label="未结算" />
          <el-option value="部分结算" label="部分结算" />
          <el-option value="已结算" label="已结算" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单金额:" prop="money">
        <el-input-number v-model="form.money" :controls="false" :min="0" :precision="2" />
      </el-form-item>
      <el-form-item label="期初金额:" prop="beginReceive">
        <el-input-number v-model="form.beginReceive" :controls="false" :min="0" :precision="2" style="width:150px" @input="beginReceive" />
      </el-form-item>
      <el-form-item label="已付金额:" prop="payed">
        <el-input-number v-model="form.payed" :controls="false" :min="0" :precision="2" @input="payed" />
      </el-form-item>
      <el-form-item label="待付款金额:">
        <el-input-number v-model="form.unPayed" :controls="false" :min="0" :precision="2" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure('form')">确 定</el-button>
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
      },
      supRules: {
        settlement: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        money: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        beginReceive: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        payed: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
        // unPayed: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getPost(this.id).then(res => {
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
    payed() {
      this.form.unPayed = this.form.money - this.form.beginReceive - this.form.payed
    },
    beginReceive() {
      this.form.unPayed = this.form.money - this.form.beginReceive - this.form.payed
    },
    sure() {
      this.$refs.form.validate((valid) => {
        console.log(this.form)
        if (valid) {
          var data = this.form.money - this.form.beginReceive - this.form.payed
          if (this.form.unPayed > data) {
            return this.$message.info('付款金额大于待付款金额')
          }
          this.form.id = this.id
          this.form.payed = this.form.payed + this.form.unPayed
          toSettle(this.form).then(res => {
            if (res) {
              this.$message.success('结算成功')
              this.$emit('init')
              this.dialog.show = false
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style>

</style>
