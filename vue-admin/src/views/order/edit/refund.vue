<template>
  <el-dialog
    title="退款"
    :visible.sync="dialog.show"
    width="18%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :rules="rules" :inline="true">
      <el-form-item label="退货数量:" prop="refundNum">
        <el-input v-model="form.refundNum" />
      </el-form-item>
      <!-- <el-form-item label="退货时间:">
        <el-date-picker
          v-model="form.refundTime"
          style="width:190px"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        />
      </el-form-item> -->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { refundNum } from '@/api/order/customerOrder'
export default {
  props: {
    dialog: {
      type: Object,
      default: () => {}
    },
    refundId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      form: {
      },
      rules: {
        refundNum: [
          { required: true, message: '请输入退货数量', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      this.form = this.form = Object.assign({}, this.$options.data().form)
    }
  },
  methods: {
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          this.form.id = parseInt(this.refundId)
          refundNum(this.form).then(res => {
            if (res) {
              this.$message.success('退款成功')
              this.$emit('init')
              this.dialog.show = false
            } else {
              this.$message.success('退款失败')
            }
          })
        }
      })
    }
  }
}
</script>

<style>

</style>
