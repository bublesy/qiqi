<template>
  <div class="app-container">
    <h2 style="margin-left:10%">厂商管理</h2>
    <el-form :inline="true" :model="form" size="mini">
      <el-form-item label="厂商名称:">
        <el-input v-model="form.name" clearable />
      </el-form-item>
      <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
      <el-button type="primary" size="mini" @click="supplierAdd">新增 </el-button>
    </el-form>
    <el-table
      :data="tableData"
      border
      style="width: 80%"
    >
      <el-table-column
        prop="name"
        label="厂商名称"
      />
      <el-table-column
        prop="phone"
        label="手机号"
      />
      <el-table-column
        prop="address"
        label="地址"
      />
      <el-table-column
        prop="isFirm"
        label="是否选择厂商"
      />
      <el-table-column
        label="操作"
        width="400"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="modifyPur(scope)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="drop(scope)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
          <el-button size="mini" type="primary" :disabled="firmDis" @click="optFirm(scope)">选择厂商</el-button>
          <el-button v-if="scope.row.isFirm==='是'?true:false" size="mini" type="primary" @click="noOptFirm(scope)">取消厂商</el-button>
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
        <el-form-item label="厂商名称:" prop="name">
          <el-input v-model="formAdd.name" />
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
          <el-input-number v-model="formAdd.phone" :controls="false" />
        </el-form-item>
        <el-form-item label="地址:" prop="address">
          <el-input v-model="formAdd.address" />
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
import { list, add, removeById, getById, updateById } from '@/api/basedata/firm'

export default {
  name: 'Firm',
  mixins: [initData],

  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'))
      } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        console.log(reg.test(value))
        if (reg.test(value)) {
          callback()
        } else {
          return callback(new Error('请输入正确的手机号'))
        }
      }
    }
    return {
      formAdd: { name: '' },
      dialogVisible: false,
      tableData: [],
      titleType: '',
      supRules: {
        name: [{ required: true, message: '该输入为必填项', trigger: 'blur' }],
        phone: [{ validator: checkPhone, trigger: 'blur' }],
        address: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      total: 0,
      form: {
        name: ''
      },
      firmDis: false
    }
  },
  created() {
    this.init()
  },
  methods: {
    noOptFirm(scope) {
      this.formAdd = scope.row
      this.formAdd.isFirm = false
      updateById(this.formAdd).then(res => {
        if (res) {
          this.$message.success('成功')
        } else {
          this.$message.error('失败')
        }
        location.reload()
      })
    },
    optFirm(scope) {
      this.$confirm('是否选择此厂商, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.formAdd = scope.row
        this.formAdd.isFirm = true
        console.log(this.formAdd)
        updateById(this.formAdd).then(res => {
          if (res) {
            this.$message.success('成功')
          } else {
            this.$message.error('失败')
          }
          this.init()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
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
    loadData() {
      console.log(this.form)
      list(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(a => {
          if (a.isFirm === true) {
            a.isFirm = '是'
          } else {
            a.isFirm = '否'
          }
          if (a.isFirm === '是') {
            this.firmDis = true
          }
        })
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
        } else {
          this.$message.error('删除失败')
        }
        location.reload()
      })
    }
  }
}
</script>
<style scoped>

</style>
