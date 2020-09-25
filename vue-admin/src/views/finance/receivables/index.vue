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
        <el-form-item label="时间范围:">
                     <DateTimeHorizon @dateTimeHorizonCallBack="dateTimeHorizonCallBack"  />
                  </el-form-item>
        </el-form-item label="时间范围:"></el-form>
      <el-button type="primary" size="mini" @click="selectData">查询</el-button>
      <el-button type="primary" size="mini" :disabled="disabled" @click="printing">生成月份打印单</el-button>

      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border
        >
          <el-table-column v-show="true" prop="name" label="客户名称" />
          <el-table-column v-for="(item, index) in tableData" :key="index" :label="item.label" :prop="item.prop" />>
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
import DateTimeHorizon from '@/components/dateTime_horizon'

export default {
  name: 'Verify',
  components: { DateTimeHorizon },
  mixins: [initData],
  data() {
    return {
      month: [],
      // 选择客户
      value: '',
      customer: [],

      // 表单数据
      tableData: [],
      tableData1: [],
      purAddVisible: false,
      taskNumberVisible: false,
      multipleSelection: [],
      indexId: {},
      fullNames: [],
      disabled: true,
      headers: [{
        label: '',
        prop: ''
      }],
      value3: [],
      prop: '',
      form: {
        customerId: '',
        startDate: '',
        endDate: ''
      }

    }
  },
  created() {
    this.init()
  },
  methods: {
    dateTimeHorizonCallBack(val) {
      this.form.startDate = val[0].format('YYYY-MM-DD') + ' 00:00:00'
      this.form.endDate = val[1].format('YYYY-MM-DD') + ' 23:59:59'
    },
    supplierList() {},
    // 获取列表数据
    loadData() {
      console.log(this.form)
      receivable(this.form).then(res => {
        console.log(res)
      })
    },
    selectData() {
      this.loadData()
      this.disabled = false
      // console.log(this.value1)
      // console.log(this.value2)
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
