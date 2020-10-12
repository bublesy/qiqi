<template>
  <el-container>
    <el-main>
      <el-button @click="toBack">返回</el-button>
      <el-button v-print="'#print'" type="primary">打印</el-button>
      <h1 align="center">供应商纸板报价打印</h1>
      <!-- <h3 align="center">{{ name }}</h3> -->
      <span style="margin-left:70%">打印日期:{{ dateFormat(new Date()) }}</span>
      <div class="app-container">
        <el-table
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column property="code" label="编码" />
          <el-table-column property="abbreviation" label="简称" />
          <el-table-column property="fullName" label="全称" />
          <el-table-column property="quotationUnit" label="报价单位" />
          <el-table-column property="name" label="纸板名称" />
          <el-table-column property="ridgeType" label="楞型" />
          <el-table-column property="cardboardQuotation" label="纸板报价" />
          <el-table-column property="preferentialSetting" label="优惠设定" />
          <el-table-column property="totalPrice" label="总价" />
        </el-table>

        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          align="center"
          :current-page="pagination.page"
          :page-size="pagination.size"
          @size-change="sizeChange"
          @current-change="pageChange"
        />
      </div>
    </el-main>
  </el-container>
</template>

<script scope>
import initData from '@/mixins/initData'
import { list } from '@/api/basedata/firm'

export default {
  name: 'SupplierCardboardPrinting',
  mixins: [initData],
  data() {
    return {
      tableData: [],
      data: [],
      name: ''
    }
  },
  created() {
    this.data = this.$route.query.data
    this.getList()
  },
  methods: {
    dateFormat: function(time) {
      var date = new Date(time)
      var year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      // 拼接
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    },
    getList() {
      this.tableData = this.data
      this.tableData.forEach(a => {
      })
      this.pagination.total = this.tableData.length
      list().then(res => {
        var firmList = []
        firmList = res.list
        firmList.forEach(a => {
          if (a.isFirm === true) {
            this.name = a.name
          }
        })
      })
    },
    // 返回
    toBack() {
      this.$router.push('/supplier_cardboard_quotation')
    }
  }
}
</script>

<style scoped>
i {
  cursor: pointer;
}
</style>
