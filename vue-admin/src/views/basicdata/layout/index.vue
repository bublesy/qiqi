<template>
  <div class="app-container">
    <h2 style="margin-left:10%">印刷版面管理</h2>
    <el-form :inline="true" :model="form" size="mini">
      <el-form-item label="印刷版面名称:">
        <el-input v-model="form.name" clearable />
      </el-form-item>
      <el-button type="primary" size="mini" @click="query">查询</el-button>
      <el-button type="primary" size="mini" @click="supplierAdd">新增 </el-button>
    </el-form>
    <el-table
      :data="tableData"
      border
      style="width: 400px"
    >
      <el-table-column
        type="index"
        label="#"
      />
      <el-table-column
        prop="name"
        label="印刷版面名称"
      />
      <el-table-column
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="modifyPur(scope)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="drop(scope)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增/编辑对话框 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <el-form ref="supForm" :inline="true" :rules="supRules" :model="formAdd" size="mini">
        <el-form-item label="印刷版面名称:" prop="name">
          <el-input v-model="formAdd.name" />
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="supplierAddNo('supForm')">取 消</el-button>
        <el-button type="primary" @click="supplierAddOk('supForm')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 分页 -->
    <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>
<script>
import initData from '@/mixins/initData'
import { list, add, removeById, getById } from '@/api/basedata/layout'

export default {
  name: 'Layout',
  mixins: [initData],

  data() {
    return {
      form: {
        page: 1,
        count: 10,
        name: '' },
      formAdd: { name: '' },
      dialogVisible: false,
      tableData: [],
      titleType: '',
      supRules: {
        name: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      total: 0
    }
  },
  created() {
    this.init()
  },
  methods: {
    query() {
      this.init()
    },
    handleCurrentChange(page) {
      this.form.page = page
      this.init()
    },
    handleSizeChange(size) {
      this.form.size = size
      this.init()
    },
    // 获取列表数据
    init() {
      list(this.form).then(res => {
        this.tableData = res.list
        this.total = res.total
      })
    },
    // 编辑
    modifyPur(scope) {
      this.titleType = '编辑'
      this.dialogVisible = true
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    // 新增
    supplierAdd() {
      this.formAdd = {}
      this.dialogVisible = true
      this.titleType = '新增'
    },
    // 新增保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.$refs[supForm].resetFields()
              this.init()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.dialogVisible = false
        } else {
          return false
        }
      })
    },
    // 新增取消
    supplierAddNo(supForm) {
      this.dialogVisible = false
      this.formAdd = {}
      this.$refs[supForm].resetFields()
    },
    // 删除
    drop(scope) {
      removeById(scope.row.id).then(res => {
        if (res) {
          this.$message.success('删除成功')
          this.init()
        } else {
          this.$message.error('删除失败')
        }
      })
    }
  }
}
</script>
<style scoped>

</style>
