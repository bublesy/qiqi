<template>
  <el-dialog
    title="常用箱资料管理"
    :visible.sync="dialog.show"
    width="400px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" @input="code" />
      </el-form-item>
      <el-form-item label="简称:" prop="shorts">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="全称:" prop="fullName">
        <el-input v-model="form.fullName" />
      </el-form-item>
      <el-form-item label="常用箱:" prop="commonBoxManagement">
        <el-select v-model="form.commonBoxManagement" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.name"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateCommonBox, getSingleCommBox } from '@/api/basedata/commonbox'
import { getBoxClassList } from '@/api/basedata/boxclass'
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
      options: [],
      form: {
      },
      rules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        shorts: [
          { required: true, message: '请输入简称', trigger: 'blur' }
        ],
        fullName: [
          { required: true, message: '请输入全称', trigger: 'blur' }
        ],
        commonBoxManagement: [
          { required: true, message: '请输入常用箱', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id !== '' && this.id !== null) {
          // 编辑
          getSingleCommBox(this.id).then(res => {
            this.form = res
            getBoxClassList().then(res => {
              this.options = res
            })
          })
        } else {
          // 新增
          this.form = Object.assign({}, this.$options.data().form)
          // this.$refs.form.resetFields()
          getBoxClassList().then(res => {
            this.options = res
          })
        }
      }
    }
  },
  methods: {
    code(x) {
      if (isNaN(x)) {
        this.form.code = this.form.code.substring(0, this.form.code.length - 1)
        if (isNaN(this.form.code)) {
          this.form.code = ''
        }
      }
    },
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          var data = this.form
          console.log(data)
          addOrUpdateCommonBox(data).then(res => {
            if (res) {
              this.$message.success('保存成功')
            }
            this.$emit('init')
            this.dialog.show = false
          })
        }
      })
    }
  }
}
</script>

<style>

</style>
