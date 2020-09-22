<template>
  <div style="margin:30px">
    <el-button type="info" style="margin-left:84%" @click="back">返回</el-button>
    <el-button v-print="'#printTest'" type="success">打印</el-button>
    <el-card id="printTest">
      <p class="font">客户订单日报表</p>
      <span style="margin-left:25%">制表:{{ name }}</span>
      <span style="margin-left:400px">打印日期:{{ now }}</span><hr>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="客户名称" width="120" />
        <el-table-column prop="no" label="任务编号" width="120" />
        <el-table-column prop="modelNo" label="款号" width="120" />
        <el-table-column prop="material" label="材质" width="120" />
        <el-table-column prop="name" label="订单尺寸" width="120">
          <template slot-scope="scope">
            {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="perPrice" label="单价" width="120" />
        <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="deliveryDate" label="交货日期" width="120" />
      </el-table>
    <!-- <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    /> -->
    </el-card>
  </div>
</template>

<script>
// import { export2Excel } from '@/utils/common'
import { getUser } from '@/api/order/customerOrder'
export default {
  name: 'Daily',
  data() {
    return {
      tableData: [],
      // total: 0,
      // form: {
      //   page: 1,
      //   size: 10,
      //   date: null
      // },
      now: null,
      name: ''
    }
  },
  created() {
    getUser().then(res => {
      this.name = res.nickname
    })
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    var list = []
    var object = this.$route.query
    console.log(object)
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key]
        list.push(element)
      }
    }
    this.tableData = list
  },
  methods: {
    back() {
      this.$router.push('/daily')
    }
  }

}
</script>

<style scoped>
.font{
  font-weight: bold;
  width:400px;
  height: 100px;
  line-height: 100px;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  /* text-align: center; */
  margin-left: 40%;
  margin-bottom: 0%;
}

</style>
