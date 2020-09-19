<template>
  <el-dialog
    title="编辑排期"
    :visible.sync="dialog.show"
    width="20%"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" size="mini" :inline="true">
      <el-form-item label="排期日期:">
        <el-date-picker
          v-model="form.date"
          style="width:150px"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="生产天数:">
        <el-input v-model="form.productDay" />
      </el-form-item>
      <el-form-item label="成品数量:">
        <el-input v-model="form.productNum" />
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
        isSchedule: false
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getSingleSchedule(this.id).then(res => {
          this.form = res
        })
      }
    }
  },
  methods: {
    sure() {
      console.log(this.form)
      addOrUpdateSchedule(this.form).then(res => {
        updateProductNum().then(res => {
          console.log('修改数量')
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
