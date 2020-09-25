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
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd HH-mm-ss"
          size="mini"
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
        >
          <el-table-column v-for="(item, index) in headers" :key="index" :label="item.label" :prop="item.prop" />>
          <el-table-column v-show="true" prop="" label="合计" />
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

      }

    }
  },
  created() {
    this.initDate()
    this.init()
  },
  methods: {
    initDate() {
      var end = new Date()
      var start = new Date()
      start.setMonth(start.getMonth() - 6)
      this.dates = [start, end]
      // console.log(this.dates)
    },
    supplierList() {},
    // 获取列表数据
    loadData() {
      console.log(this.dates[0])
      this.form.startDate = this.dates[0]
      this.form.endDate = this.dates[1]
      receivable(this.form).then(res => {
        console.log(res)
        this.pagination.total = res.map.data.length
        this.headers = res.map.title
        this.tableData = res.map.data
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
