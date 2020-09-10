<template>
  <el-dialog
    title="提示"
    :visible.sync="dialog.show"
    width="700px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="简称:" prop="">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="全称:" prop="">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="电话:" prop="">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="手机:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="传真:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="联系人:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="网址:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="地址:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="开户银行:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="指定业务员:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="银行账号:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="税务登记证号:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="结算方式:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="币种:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="期初应收:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="信用额度:">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" size="mini" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {
    dialog: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      form: {}
    }
  }
}
</script>

<style>

</style>
