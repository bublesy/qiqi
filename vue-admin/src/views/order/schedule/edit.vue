<template>
  <el-dialog
    title="编辑排期"
    :visible.sync="dialog.show"
    width="18%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true">
      <el-form-item label="排期日期:">
        <el-date-picker
          v-model="form.date"
          style="width:150px"
          type="date"
          placeholder="选择日期"
          @blur="changerModCount"
        />
      </el-form-item>
      <el-form-item label="生产天数:">
        <el-input v-model="form.productDay" @input="productDay" />
      </el-form-item>
      <el-form-item label="已产数量:">
        <el-input v-model="form.productNum" @input="productNum" />
      </el-form-item>
      <el-form-item label="损耗数量:">
        <el-input v-model="form.lossNum" @input="lossNum" />
      </el-form-item>
      <el-form-item label="是否排期:">
        <el-checkbox v-model="form.isSchedule" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateSchedule, getSingleSchedule, updateProductNum } from '@/api/order/schedule'
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
        isSchedule: false,
        modCount: 0
      },
      productNums: 0
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getSingleSchedule(this.id).then(res => {
          this.form = res
          this.form.modCount = 0
          this.productNums = res.orderNum
        })
      }
    }
  },
  methods: {
    productDay(x) {
      if (isNaN(x)) {
        this.form.productDay = this.form.productDay.substring(0, this.form.productDay.length - 1)
        if (isNaN(this.form.productDay)) {
          this.form.productDay = ''
        }
      }
    },
    productNum(x) {
      if (isNaN(x)) {
        this.form.productNum = this.form.productNum.substring(0, this.form.productNum.length - 1)
        if (isNaN(this.form.productNum)) {
          this.form.productNum = ''
        }
      }
    },
    lossNum(x) {
      if (isNaN(x)) {
        this.form.lossNum = this.form.lossNum.substring(0, this.form.lossNum.length - 1)
        if (isNaN(this.form.lossNum)) {
          this.form.lossNum = ''
        }
      }
    },
    changerModCount() {
      this.form.modCount++
    },
    sure() {
      if (this.form.productNum > this.productNums) {
        return this.$message.info('已产数量不能大于订单数量')
      }
      addOrUpdateSchedule(this.form).then(res => {
        updateProductNum().then(res => {
          if (res) {
            this.dialog.show = false
            this.$message.success('修改成功')
            this.$emit('init')
          }
        })
      })
    }
  }
}
</script>

<style>

</style>
