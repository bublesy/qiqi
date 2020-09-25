<template>
  <el-container>
    <el-main>
      <h1 align="center">应收款管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="客户:" prop="name">
          <el-select v-model="form.customerId" filterable placeholder="请选择">
            <el-option
              v-for="item in tableData"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <!-- <el-date-picker
          v-model="form.startDate"
          type="month"
          placeholder="开始时间"
          value-format="yyyy-MM-dd HH-mm-ss"
        />
        <el-date-picker
          v-model="form.endDate"
          type="month"
          placeholder="结束时间"
          value-format="yyyy-MM-dd HH-mm-ss"
        /> -->
        <el-date-picker
          v-model="dates"
          type="monthrange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          size="mini"
          @change="loadHeaders"
        />
        <el-button type="primary" size="mini" @click="selectData">查询</el-button>
        <el-button type="primary" size="mini" :disabled="disabled" @click="printing">生成月份打印单</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border
          row-key="id"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
          >
          <el-table-column label="客户" prop="name" width="100" />
          <el-table-column v-for="(item, index) in headers" :key="index" :label="item.label" :prop="item.prop" width="100">
            <template slot-scope="scope">
              {{ scope.row.dates[item.prop]?scope.row.dates[item.prop]:0 }}
            </template>
          </el-table-column>
          <el-table-column label="合计" prop="total" width="100">
            <template slot-scope="scope">
              {{ scope.row.total }}
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          :current-page="pagination.page"
          :page-size="pagination.size"
          align="center"
          @size-change="sizeChange"
          @current-change="pageChange"
        />
      </div>
    </el-main>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'
import { receivable } from '@/api/finance/receivables'

export default {
  name: 'Verify',
  filters: {
    toFixed(val) {
      return val ? (val / 100).toFixed(2) : val
    }
  },
  mixins: [initData],
  data() {
    return {
      // 选择月份
      month: [],
      // 选择客户
      value: '',
      customer: [],
      formAdd: {
        page: 1,
        count: 10,
        customerId: '',
        startDate: ''
      },
      // 表单数据
      tableData: [],
      tableData1: [],
      purAddVisible: false,
      taskNumberVisible: false,
      multipleSelection: [],
      indexId: {},
      fullNames: [],
      disabled: true,
      pickerOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()])
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date()
            const start = new Date(new Date().getFullYear(), 0)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近六个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setMonth(start.getMonth() - 6)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      headers: [{
        label: '',
        prop: ''
      }],
      hheaders: [{ label: '3月', prop: 'a' }, { label: '4月', prop: 'b' }, { label: '3月', prop: 'c' }],
      value3: [],
      prop: '',
      dates: [],
      form: {
        page: 1,
        count: 10,
        customerId: '',
        startDate: '',
        endDate: '',
        id: ''
      },
      solve: []
      // indexValue: []

    }
  },
  created() {
    this.initDate()
    this.init()
    this.loadHeaders()
  },
  methods: {
    load(tree, treeNode, resolve) {
      setTimeout(() => {
        resolve([
          {
            id: 31,
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          }, {
            id: 32,
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          }
        ])
      }, 1000)
    },
    initDate() {
      var end = new Date()
      var start = new Date()
      start.setMonth(start.getMonth() - 6)
      this.dates = [start, end]
      // console.log(this.dates)
    },
    loadHeaders() {
      // this.headers = [{ label: '客户', prop: 'name' }]
      this.headers = []
      var min = new Date(this.dates[0])
      var max = new Date(this.dates[1])
      while (Date.parse(min) <= Date.parse(max)) {
        var month = min.getMonth() + 1
        this.headers.push({ label: min.getFullYear() + '年' + (month < 10 ? ('0' + month) : month) + '月', prop: min.getFullYear() + '-' + (month < 10 ? ('0' + month) : month) })
        min.setMonth(month)
      }
      // this.headers.push({ label: '合计', prop: 'total' })
      console.log(this.headers)
      this.$set(this.form, 'startTime', this.$moment(this.dates[0]).startOf('month').format('YYYY-MM-DD') + ' 00:00:00')
      this.$set(this.form, 'endTime', this.$moment(this.dates[1]).endOf('month').format('YYYY-MM-DD') + ' 23:59:59')
    },
    supplierList() {},
    // 获取列表数据
    loadData() {
      this.form.startDate = this.dates[0]
      this.form.endDate = this.dates[1]
      receivable(this.form).then(res => {
        console.log(res)
        this.pagination.total = res.total
        this.tableData = res.list
      })
    },
    selectData() {
      this.loadData()
      this.disabled = false
    },
    // 打印
    printing() {
      // tableData1
      this.$router.push({ name: 'Receivables_printing', params: { 'data': this.tableData1 }})
    }
  }
}
</script>
<style scoped lang="scss">
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
