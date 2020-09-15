<template>
  <el-dialog
    title="客户纸板报价管理"
    :visible.sync="dialog.show"
    width="39%"
    :close-on-click-modal="false"
  >
    <!-- <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="名称:" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="限定最大纸长:" label-width="180" style="margin-left:37px">
        <el-checkbox v-model="form.limitPaperLength" />
      </el-form-item>
    </el-form> --><el-table
      ref="table"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <!-- <el-table-column type="index" /> -->
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="name" label="名称" width="120" />
      <el-table-column prop="code" label="描述" width="120" />
      <el-table-column prop="squaredPrice" label="楞型" width="120" />
      <el-table-column prop="BoxPrice" label="平方价" width="120" />
      <el-table-column prop="BoxPrice" label="备注" width="120" />
    </el-table>
    <el-pagination
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateBoxClass } from '@/api/basedata/boxclass'
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
      },
      rules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id !== '' && this.id !== null) {
          // 编辑
          console.log('aa')
        } else {
          // 新增
          this.form = Object.assign({}, this.$options.data().form)
        }
      }
    }
  },
  methods: {
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          var data = {}
          addOrUpdateBoxClass(data).then(res => {

          })
        }
      })
    }
  }
}
</script>

<style>

</style>
