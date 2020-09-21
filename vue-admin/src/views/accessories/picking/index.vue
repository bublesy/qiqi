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
            <el-select v-model="formAdd.pickingPeople" placeholder="请选择" @change="collectorll">
              <el-option
                v-for="item in pickingPeople"
                :key="item.value"
                :label="item.nickname"
                :value="item.nickname"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="日期:" prop="date">
            <el-date-picker
              v-model="formAdd.date"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
          <el-form-item label="开单员:" prop="partOrder">
            <el-select v-model="formAdd.partOrder" placeholder="请选择" @change="collectorll">
              <el-option
                v-for="item in partOrder"
                :key="item.value"
                :label="item.nickname"
                :value="item.nickname"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="备注:" prop="note">
            <el-input v-model="formAdd.note" />
          </el-form-item>
          <el-form-item label="品名规格:" prop="specificationId">
            <el-select v-model="formAdd.specificationId" placeholder="请选择" @change="specificationChange">
              <el-option
                v-for="item in specificationFor"
                :key="item.value"
                :label="item.specification"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="单位:" prop="unit">
            <el-input v-model="formAdd.unit" disabled />
          </el-form-item>
          <el-form-item
            label="数量:"
            prop="num"
          >
            <el-input v-model="formAdd.num" />
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
        @size-change="sizeChange"
        @current-change="pageChange"
      />
    </div>
  </div>
</template>
<script>
import initData from '@/mixins/initData'
import { list, add, removeById, getById, listunit, getUser } from '@/api/accessories/picking'
import { export2Excel } from '@/utils/common'
import { specificationList } from '@/api/accessories/means'

export default {
  name: 'Means',
  mixins: [initData],
  data() {
    // 验证金额的正则
    var money = (rule, value, cb) => {
      const reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
      if (reg.test(value)) {
        // 合法
        return cb()
      }
      cb(new Error('请输入数字'))
    }
    return {
      form: {
        page: 1,
        count: 10,
        pickingPeople: '',
        data: '',
        partOrder: '',
        no: '' },
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
        num: [
          { required: true, message: '请输入数量', trigger: 'blur' },
          { validator: money, trigger: 'blur' }
        ],
        pickingPeople: [{ required: true, message: '此为必选项', trigger: 'blur' }],
        partOrder: [{ required: true, message: '此为必选项', trigger: 'blur' }],
        specificationId: [{ required: true, message: '此为必选项', trigger: 'blur' }]

      },
      // 辅料数据
      table: [],
      // 品名规格下拉
      specificationFor: [],
      value: '',
      value1: '',
      // 领料人
      partOrder: [],
      pickingPeople: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    specificationChange() {
      this.specificationFor.forEach(a => {
        if (a.id === this.formAdd.specificationId) {
          this.formAdd.unit = a.unit
        }
      })
    },
    collectorll() {

    },
    // 获取列表数据
    loadData() {
      // this.form.name = this.form.name
      // this.queryParams.abbreviation = this.form.abbreviation
      // this.queryParams.time = this.form.time
      // if (this.queryParams.time === null) {
      //   this.$set(this.queryParams, 'time', '')
      // }
      this.form.page = this.pagination.page
      this.form.count = this.pagination.size
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

      // 领料人数据
      getUser().then(res => {
        this.partOrder = res
        this.pickingPeople = res
        console.log(res)
      })
    },
    // 新增
    supplierAdd() {
      // this.formAdd = {}
      this.dialogVisible = true
      this.formAdd = {}
      this.titleType = '新增'
      specificationList().then(res => {
        this.specificationFor = res
      })
      // 领料人数据
      getUser().then(res => {
        this.partOrder = res
        this.pickingPeople = res
        console.log(res)
      })
    },
    // 新增保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          add(this.formAdd).then(res => {
            this.$message.success(this.titleType + '成功')
            this.$refs[supForm].resetFields()
            this.loadData()
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
          this.loadData()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    toExcel() {
      var list = this.tableData
      const th = ['领料人', '日期', '开单员', '备注']
      const filterVal = ['pickingPeople', 'date', 'partOrder', 'note']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料领料')
    }
  }
}
</script>
<style scoped>

</style>
