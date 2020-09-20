<template>
  <div class="app-container">
    <h2 style="margin-left:10%">辅料领料</h2>
    <el-form :inline="true" :model="form" size="mini">
      <el-form-item label="领料人:">
        <el-input v-model="form.pickingPeople" clearable @clear="loadData" />
      </el-form-item>
      <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
      <el-button type="primary" size="mini" @click="supplierAdd">新增 </el-button>
      <el-button type="primary" size="mini" @click="toExcel">导出 </el-button>
    </el-form>
    <div id="printTest">
      <el-table
        :data="tableData"
        border
        style="width: 600px"
      >
        <el-table-column
          type="index"
          label="#"
        />
        <el-table-column
          prop="pickingPeople"
          label="领料人"
        />
        <el-table-column
          prop="date"
          label="日期"
        />
        <el-table-column
          prop="partOrder"
          label="开单员"
        />
        <el-table-column
          prop="note"
          label="备注"
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
        click-modal="false"
      >
        <el-form ref="supForm" :inline="true" :rules="supRules" :model="formAdd" size="mini">
          <el-form-item label="领料人:" prop="pickingPeople">
            <el-input v-model="formAdd.pickingPeople" />
          </el-form-item>
          <el-form-item label="日期:" prop="date">
            <el-date-picker
              v-model="formAdd.date"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="开单员:" prop="partOrder">
            <el-input v-model="formAdd.partOrder" />
          </el-form-item>
          <el-form-item label="备注:" prop="note">
            <el-input v-model="formAdd.note" />
          </el-form-item>
          <el-form-item label="品名规格:" prop="pickingPeople">
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in specification"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="单位:" prop="unit">
            <el-select v-model="value1" placeholder="请选择">
              <el-option
                v-for="item in unit"
                :key="item.value"
                :label="item.label"
                :value="item.value1"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="数量:" prop="pickingeople">
            <el-input v-model="formAdd.pickingeople" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="supplierAddNo('supForm')">取 消</el-button>
          <el-button type="primary" @click="supplierAddOk('supForm')">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 分页 -->
      <el-pagination
        style="margin-top: 10px"
        :total="pagination.total"
        :current-page="pagination.page"
        layout="total, prev, pager, next, sizes"
        @size-change="pagination.size"
        @current-change="pageChange"
      />
    </div>
  </div>
</template>
<script>
import initData from '@/mixins/initData'
import { list, add, removeById, getById, updated, listunit } from '@/api/accessories/picking'
import { export2Excel } from '@/utils/common'

export default {
  name: 'Means',
  mixins: [initData],
  data() {
    return {
      form: {
        page: 1,
        count: 10,
        pickingPeople: '',
        data: '',
        partOrder: '',
        no: ''
      },
      formAdd: { page: 1,
        count: 10,
        pickingPeople: '',
        data: '',
        partOrder: '',
        no: '' },
      dialogVisible: false,
      tableData: [],
      titleType: '',
      supRules: {
        name: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
      },
      // 辅料数据
      table: [],
      // 品名规格下拉
      specification: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: '',
      // 单位下拉
      unit: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value1: ''
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 获取列表数据
    loadData() {
      // this.form.name = this.form.name
      // this.queryParams.abbreviation = this.form.abbreviation
      // this.queryParams.time = this.form.time
      // if (this.queryParams.time === null) {
      //   this.$set(this.queryParams, 'time', '')
      // }
      list(this.form).then(res => {
        this.tableData = res.list
        this.pagination.total = res.total

        console.log(res)
      })
      // 获取辅料资料数据
      listunit(this.form).then(res => {
        this.table = res.list
        this.pagination.total = res.total
        console.log(11)
        console.log(res)
      })
    },
    // 编辑
    modifyPur(scope) {
      console.log('id', scope)
      console.log(scope.$index)
      this.titleType = '编辑'
      this.dialogVisible = true
      this.formAdd.id = scope.row.id

      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    // 新增
    supplierAdd() {
      // this.formAdd = {}
      this.formAdd.id = null
      this.dialogVisible = true
      this.titleType = '新增'
    },
    // 新增保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          if (this.formAdd.id !== null) {
            updated(this.formAdd).then(res => {
              console.log(res)
              this.$message.success(this.titleType + '成功')
              this.$refs[supForm].resetFields()
              this.loadData()
            })
          } else {
            console.log('aaa')
            console.log(this.formAdd)
            add(this.formAdd).then(res => {
              this.$message.success(this.titleType + '成功')
              this.$refs[supForm].resetFields()
              this.loadData()
            })
          }
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
          this.loadData()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    toExcel() {
      var list = this.tableData
      const th = ['领料人', '日期', '开单员', '备注']
      const filterVal = ['data', 'supplier', 'no', 'note']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料领料')
    }
  }
}
</script>
<style scoped>

</style>
