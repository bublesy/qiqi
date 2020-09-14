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
      <el-form-item label="是否排期:">
        <el-checkbox v-model="checked" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
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
      checked: true,
      form: {}
    }
  }
}
</script>

<style>

</style>
