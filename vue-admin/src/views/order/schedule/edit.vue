<template>
  <el-dialog
    title="编辑排期"
    :visible.sync="dialog.show"
    width="18%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true" :rules="rules">
      <el-form-item label="排期日期:" prop="date">
        <el-date-picker
          v-model="form.date"
          style="width:150px"
          type="date"
          placeholder="选择日期"
          @blur="changerModCount"
        />
      </el-form-item>
      <el-form-item label="生产天数:" prop="productDay">
        <el-input v-model="form.productDay" @input="productDay" />
      </el-form-item>
      <el-form-item label="已产数量:">
        <el-input v-model="form.productNum" disabled @input="productNum" />
      </el-form-item>
      <el-form-item label="生产数量:" prop="proNum">
        <el-input v-model="form.proNum" @input="proNum" />
      </el-form-item>
      <el-form-item label="已耗数量:">
        <el-input v-model="form.lossNum" disabled @input="lossNum" />
      </el-form-item>
      <el-form-item label="损耗数量:" prop="losNum">
        <el-input v-model="form.losNum" @input="losNum" />
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
      productNums: 0,
      rules: {
        productDay: [
          { required: true, message: '请输入生产天数', trigger: 'blur' }
        ],
        proNum: [
          { required: true, message: '请输入生产数量', trigger: 'blur' }
        ],
        losNum: [
          { required: true, message: '请输入损耗数量', trigger: 'blur' }
        ],
        date: [
          { required: true, message: '请输入排期日期', trigger: 'blur' }
        ]
      }
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
    proNum(x) {
      if (isNaN(x)) {
        this.form.proNum = this.form.proNum.substring(0, this.form.proNum.length - 1)
        if (isNaN(this.form.proNum)) {
          this.form.proNum = ''
        }
      }
    },
    losNum(x) {
      if (isNaN(x)) {
        this.form.losNum = this.form.losNum.substring(0, this.form.losNum.length - 1)
        if (isNaN(this.form.losNum)) {
          this.form.losNum = ''
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
      this.$refs.form.validate(valid => {
        if (valid) {
          this.form.productNum = parseInt(this.form.productNum == null ? 0 : this.form.productNum) + parseInt(this.form.proNum)
          this.form.lossNum = parseInt(this.form.lossNum == null ? 0 : this.form.lossNum) + parseInt(this.form.losNum)
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
      })
    }
  }
}
</script>

<style>

</style>
