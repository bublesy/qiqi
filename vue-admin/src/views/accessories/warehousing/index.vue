<template>
  <div class="app-container">
    <h2 style="margin-left:10%">辅料入库</h2>
    <el-form :inline="true" :model="form" size="mini">
      <el-form-item label="供方:">
        <el-input v-model="form.supplier" clearable @clear="loadData" />
      </el-form-item>
      <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
      <el-button type="primary" size="mini" @click="supplierAdd">新增 </el-button>
      <el-button type="primary" size="mini" @click="toExcel">导出 </el-button>
    </el-form>
    <div id="printTest">
      <el-table
        :data="tableData"
        border
        style="width: 800px"
      >
        <el-table-column
          type="index"
          label="#"
        />
        <el-table-column
          prop="date"
          label="日期"
        />
        <el-table-column
          prop="supplier"
          label="供方"
        />
        <el-table-column
          prop="no"
          label="原始单号"
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
        :close-on-click-modal="false"
      >
        <el-form ref="supForm" :inline="true" :rules="supRules" :model="formAdd" size="mini">
          <el-form-item label="日期:" prop="date">
            <el-date-picker
              v-model="formAdd.date"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
          <el-form-item label="供方:" prop="supplier">
            <el-input v-model="formAdd.supplier" />
          </el-form-item>
          <el-form-item label="原始单号:" prop="no">
            <el-input v-model="formAdd.no" />
          </el-form-item>
          <el-form-item label="备注:" prop="note">
            <el-input v-model="formAdd.note" />
          </el-form-item>
          <el-form-item label="品名规格:" prop="specification">
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
          <el-form-item label="单价:" prop="perPrice">
            <el-input v-model="formAdd.perPrice" />
          </el-form-item>
          <el-form-item label="金额:" prop="money">
            <el-input v-model="formAdd.money" />
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
import { list, add, removeById, getById, listunit } from '@/api/accessories/warehousing'
import { specificationList } from '@/api/accessories/means'
import { export2Excel } from '@/utils/common'
export default {
  name: 'Warehousing',
  mixins: [initData],
  data() {
    // 验证数量的正则
    // var unm = (rule, value, cb) => {
    //   const reg = /^\+?[1-9][0-9]*$ /
    //   if (reg.test(value)) {
    //     return cb()
    //   }
    //   cb(new Error('请输入数字'))
    // }
    // 验证金额的正则
    var money = (rule, value, cb) => {
      const reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
      if (reg.test(value)) {
        // 合法
        return cb()
      }
      cb(new Error('请输入数字'))
    }
    // 验证单价的正则
    var perPrice = (rule, value, cb) => {
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
        date: '',
        supplier: '',
        no: '',
        note: ''

      },
      formAdd: {
        page: 1,
        count: 10,
        date: '',
        supplier: '',
        no: '',
        note: ''
      },
      dialogVisible: false,
      tableData: [],
      titleType: '',
      supRules: {
        unm: [
          { required: true, message: '请输入数量', trigger: 'blur' },
          { validator: money, trigger: 'blur' }
        ],
        money: [
          { required: true, message: '请输入金额', trigger: 'blur' },
          { validator: money, trigger: 'blur' }
        ],
        perPrice: [
          { required: true, message: '请输入单价', trigger: 'blur' },
          { validator: perPrice, trigger: 'blur' }
        ]
      },
      // 辅料资料数据
      table: [],
      // 品名规格下拉
      specificationFor: [],
      value: '',
      value1: ''
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
        specificationList().then(res => {
          this.specificationFor = res
          this.specificationFor.forEach(a => {
            if (a.id === this.formAdd.specificationId) {
              this.formAdd.unit = a.unit
            }
          })
        })
        this.formAdd = res
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
    },
    // 新增保存
    supplierAddOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          console.log(this.formAdd)
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
      const th = ['日期', '供方', '原始单号', '备注']
      const filterVal = ['data', 'supplier', 'no', 'note']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料入库')
    }
  }
}
</script>
<style scoped>

</style>
