<template>
  <el-container>
    <el-main>
      <h1 align="center">应收款管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="客户:" prop="name">
          <el-select v-model="form.customerId" filterable placeholder="请选择">
            <el-option
              v-for="item in tableData1"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-date-picker
          v-model="value2"
          size="mini"
          type="monthrange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          :picker-options="pickerOptions"
        />
        <el-button type="primary" size="mini" @click="selectData">查询</el-button>
        <el-button type="primary" size="mini" :disabled="disabled" @click="printing">生成月份打印单</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData1"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border
        >
          <el-table-column v-show="true" prop="name" label="客户名称" />
          <el-table-column v-show="true" prop="name" label="2012年-05月" />
          <el-table-column v-show="true" prop="outNo" label="2012年-06月" />
          <el-table-column v-show="true" prop="shipDate" label="2012年-07月" />
          <el-table-column v-show="true" prop="deliveryDate" label="2012年-08月" />
          <el-table-column v-show="true" prop="modelNo" label="2012年-09月" />
          <el-table-column v-show="true" prop="modelNo" label="合计" />

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
import { record, customer } from '@/api/accessories/means'
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
      valu: '',
      form: {
        count: 10,
        customerId: '',
        deliveryDate: '',
        page: 1,
        fullName: '',
        date: '',
        id: '',
        time: ''
      },
      formAdd: {
        page: 1,
        count: 10
      },
      // 表单数据
      tableData: [{
        customerName: '',
        phone: '',
        data: '2020--11',
        shipment: '',
        goods: '',
        type: '',
        long: '',
        number: '',
        price: '',
        money: ''

      }],
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
      value1: '',
      value2: ''

    }
  },
  created() {
    this.init()
  },
  methods: {
    supplierList() {},
    // 获取列表数据
    loadData() {
      record(this.form).then(res => {
        this.tableData1 = res.orderDOPageEntity.list
        console.log(res)
        this.pagination.total = res.orderDOPageEntity.total
      })
      // 获取用户数据
      customer().then(res => {
        console.log(res)
        this.tableData = res

        // this.pagination.total = res.total
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
